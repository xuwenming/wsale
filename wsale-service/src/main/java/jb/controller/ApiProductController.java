package jb.controller;

import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.ProductCommon;
import jb.service.impl.SendWxMessageImpl;
import jb.service.impl.order.OrderState;
import jb.util.*;
import jb.util.wx.DownloadMediaUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.message.req.templateMessage.TemplateData;
import jb.util.wx.message.req.templateMessage.WxTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiProductController")
public class ApiProductController extends BaseController {

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Autowired
	private ZcProductRangeServiceI zcProductRangeService;

	@Autowired
	private ZcProductLikeServiceI zcProductLikeService;

	@Autowired
	private ZcAuctionServiceI zcAuctionService;

	@Autowired
	private ZcUserAutoPriceServiceI zcUserAutoPriceService;

	@Autowired
	private ProductCommon productCommon;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private ZcBestProductServiceI zcBestProductService;

	@Autowired
	private ZcShieldorfansServiceI zcShieldorfansService;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private ZcProductMarginServiceI zcProductMarginService;

	@javax.annotation.Resource(name = "order01StateImpl")
	private OrderState order01State;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	/**
	 * 发布拍品-跳转第一页
	 * http://localhost:8080/api/apiProductController/toFirst?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/toFirst")
	public String toFirst(String productId, String serverIds, String localIds, HttpServletRequest request) {
		if(F.empty(productId)) {
			List<Map<String, String>> images = new ArrayList<Map<String, String>>();
			if(!F.empty(serverIds) && !F.empty(localIds)) {
				String[] serverIdArr = serverIds.split(",");
				String[] localIdArr = localIds.split(",");
				for(int i=0; i<serverIdArr.length; i++) {
					Map<String, String> image = new HashMap<String, String>();
					image.put("serverId", serverIdArr[i]);
					image.put("localId", localIdArr[i]);
					images.add(image);
				}
			}
			request.setAttribute("images", images);
		} else {
			ZcProduct product = zcProductService.get(productId, null);
			request.setAttribute("product", product);
		}
		request.setAttribute("productId", productId);
		return "/wsale/product/add_first";
	}

	/**
	 * 发布拍品-跳转第二页
	 * http://localhost:8080/api/apiProductController/toSecond?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=
	 * @return
	 */
	@RequestMapping("/toSecond")
	public String toSecond(String id, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);

		ZcProduct product = zcProductService.get(id);
		if(!F.empty(product.getCategoryId())) {
			ZcCategory c = zcCategoryService.get(product.getCategoryId());
			ZcCategory pc = null;
			if(!F.empty(c.getPid())) {
				pc = zcCategoryService.get(c.getPid());
			}
			request.setAttribute("categoryName", (pc != null ? pc.getName() + " - " : "") + c.getName());
		}
		request.setAttribute("product", product);

		// 获取分类
		ZcCategory c = new ZcCategory();
		c.setPid("0");
		c.setIsDeleted(false);
		List<ZcCategory> categorys = zcCategoryService.query(c);
		if(CollectionUtils.isNotEmpty(categorys)) {
			c.setPid(categorys.get(0).getId());
			List<ZcCategory> childCategorys = zcCategoryService.query(c);
			request.setAttribute("childCategorys", childCategorys);
		}
		request.setAttribute("categorys", categorys);

		// 获取加价幅度
		ZcProductRange range = zcProductRangeService.getLastByUserId(s.getId());
		ZcProductRange q = new ZcProductRange();
		if(range != null) {
			q.setProductId(range.getProductId());
		} else {
			q.setProductId("-1"); // 默认
		}
		List<ZcProductRange> ranges = zcProductRangeService.query(q);
		request.setAttribute("ranges", ranges);

		request.setAttribute("sessionInfo", s);

		return "/wsale/product/add_second";
	}

	/**
	 * 存为草稿或下一步
	 * http://localhost:8080/api/apiProductController/add?tokenId=1D96DACB84F21890ED9F4928FA8B352B&content=我是拍品内容&mediaIds=mediaId1,mediaId2,mediaId3
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcProduct zcProduct, String delFileIds, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(F.empty(zcProduct.getId())) {
				zcProduct.setAddUserId(s.getId());
				zcProduct.setPno(Util.CreatePNo());
				zcProduct.setStatus("PT01"); // 草稿
				zcProduct.setIsClose(false);
				zcProduct.setIsDeleted(false);
				zcProductService.add(zcProduct);
			} else {
				zcProduct.setReadCount(0);
				zcProductService.edit(zcProduct);
			}

			if(!F.empty(zcProduct.getMediaIds())) {
				String[] mediaIds = zcProduct.getMediaIds().split(",");
				final CompletionService completionService = CompletionFactory.initCompletion();
				final String realPath = request.getSession().getServletContext().getRealPath("/");
				ZcFile zcFile = new ZcFile();
				zcFile.setObjectType(EnumConstants.OBJECT_TYPE.PRODUCT.getCode()); // 对象类型：拍品
				zcFile.setObjectId(zcProduct.getId());
				zcFile.setFileType("FT01"); // 文件类型：图片
				int index = zcFileService.getMaxSeq(zcFile);
				for(final String mediaId : mediaIds) {
					zcFile = new ZcFile();
					zcFile.setObjectType(EnumConstants.OBJECT_TYPE.PRODUCT.getCode()); // 对象类型：拍品
					zcFile.setObjectId(zcProduct.getId());
					zcFile.setFileType("FT01"); // 文件类型：图片
					zcFile.setSeq(++index);
					completionService.submit(new Task<ZcFile, Object>(zcFile){
						@Override
						public Boolean call() throws Exception {
							String filePath = DownloadMediaUtil.downloadMedia(realPath, mediaId, "PRODUCT");
							getD().setFileOriginalUrl(filePath);
							getD().setFileHandleUrl(ImageUtils.pressImage(filePath, realPath));
							zcFileService.add(getD());
							return true;
						}
					});
				}
				completionService.sync();
			}

			if(!F.empty(delFileIds)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				String[] fileIds = delFileIds.split(",");
				for(String fileId : fileIds) {
					if(F.empty(fileId)) continue;
					completionService.submit(new Task<String, Object>(fileId) {
						@Override
						public Boolean call() throws Exception {
							zcFileService.delete(getD());
							return true;
						}
					});
				}
				completionService.sync();
			}
			j.setObj(zcProduct.getId());
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 发布拍品
	 * http://localhost:8080/api/apiProductController/publish?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=06166240E78147C0B6DC4BFC813E5E51&deadlineStr=2016-08-25 12:00&categoryId=988F9AB6C2BF4428BA3ED58F0F799385&startingPrice=100&approvalDays=AD07&isFreeShipping=1&margin=1000&isNeedRealId=0&isNeedProtectionPrice=0&fixedPrice=1000&referencePrice=1000&
	 * @return
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public Json publish(ZcProduct zcProduct, String deadlineStr, String startingTimeStr,
						boolean isOpenBest,ZcProductRange range, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(!F.empty(deadlineStr)) {
				zcProduct.setDeadline(DateUtil.parse(deadlineStr, Constants.DATE_FORMAT_YMDHM));
				zcProduct.setRealDeadline(zcProduct.getDeadline());
			}
			if(!F.empty(startingTimeStr)) {
				zcProduct.setStatus("PT02");// 未开始
				// 开拍时间格式待定
				zcProduct.setDeadline(DateUtil.parse(startingTimeStr, Constants.DATE_FORMAT_YMDHM));
			} else {
				zcProduct.setStatus("PT03");// 竞拍中
				zcProduct.setStartingTime(new Date());
			}
			zcProductService.edit(zcProduct);

			// 申请精拍
			if(isOpenBest) {
				ZcBestProduct bp = new ZcBestProduct();
				bp.setChannel(EnumConstants.BEST_CHANNEL.HOME.getCode());// 首页精拍
				bp.setProductId(zcProduct.getId());
				bp.setAuditStatus("AS01");
				bp.setPayStatus("PS01");
				bp.setAddUserId(s.getId());
				zcBestProductService.add(bp);

				j.setObj(bp.getId());
			}

			// 新增加价幅度
			range.setProductId(zcProduct.getId());
			addRange(range, request);

			// 推送消息
			StringBuffer buffer = new StringBuffer();
			buffer.append("集东集西藏品：").append("\n\n");
			buffer.append(zcProduct.getContentLine()).append("\n");
			buffer.append("截拍时间：" + deadlineStr).append("\n");
			buffer.append("点击链接出价").append("\n");
			String url = PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + zcProduct.getId());
			buffer.append(url);
			sendWxMessage.sendCustomMessage(s.getName(), buffer.toString());

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 重新上架获取拍品发布信息
	 * http://localhost:8080/api/apiProductController/productInfo?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/productInfo")
	@ResponseBody
	public Json productInfo(ZcProduct zcProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			// TODO 待完善
			zcProduct = zcProductService.get(zcProduct.getId());
			j.setObj(zcProduct);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 拍品列表
	 * http://localhost:8080/api/apiProductController/productList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10&categoryId=988F9AB6C2BF4428BA3ED58F0F799385&status=PT03
	 * @return
	 */
	@RequestMapping("/productList")
	@ResponseBody
	public Json productList(PageHelper ph, ZcProduct zcProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(!F.empty(zcProduct.getAddUserId()) && s.getId().equals(zcProduct.getAddUserId())) {
				ph.setSort("addtime");
			} else {
				ph.setSort("startingTime");
			}
			ph.setOrder("desc");
			zcProduct.setIsDeleted(false);
			j.setObj(productCommon.dataGrid(ph, zcProduct, s.getId()));
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 全部拍品列表
	 * @return
	 */
	@RequestMapping("/productAllList")
	@ResponseBody
	public Json productAllList(PageHelper ph, ZcProduct zcProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(s.getId().equals(zcProduct.getAddUserId())) {
				ph.setSort("realDeadline desc, t.addtime");
			} else {
				ph.setSort("realDeadline desc, t.startingTime");
			}
			ph.setOrder("desc");

			zcProduct.setIsDeleted(false);
			j.setObj(productCommon.dataGridComplex(ph, zcProduct, s.getId()));
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 所有关注人的拍品列表
	 * @return
	 */
	@RequestMapping("/productAllAttredList")
	@ResponseBody
	public Json productAllAttredList(PageHelper ph, ZcProduct zcProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("startingTime");
			ph.setOrder("desc");
			zcProduct.setAtteId(s.getId());
			zcProduct.setIsDeleted(false);
			zcProduct.setStatus("PT03");// 竞拍中
			j.setObj(productCommon.dataGridComplex(ph, zcProduct, s.getId()));
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 拍品详情
	 * http://localhost:8080/api/apiProductController/productDetail?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=06166240E78147C0B6DC4BFC813E5E51
	 * @return
	 */
	@RequestMapping("/productDetail")
	public String productDetail(ZcProduct zcProduct, HttpServletRequest request) {
		try{
			SessionInfo s = getSessionInfo(request);
			zcProduct = zcProductService.addReadAndDetail(zcProduct.getId(), s.getId());
			// 拍品不存在或已被删除
			if(zcProduct.getIsDeleted() || "PT01".equals(zcProduct.getStatus())) {
				request.setAttribute("userId", zcProduct.getAddUserId());
				request.setAttribute("errorMsg", "拍品不存在或已被删除");
				return "/wsale/product/product_not_exist";
			}
			// 卖家已封存本拍品
			if(zcProduct.getIsClose() && !s.getId().equals(zcProduct.getAddUserId())) {
				ZcAuction auction = new ZcAuction();
				auction.setProductId(zcProduct.getId());
				auction.setBuyerId(s.getId());
				if(CollectionUtils.isEmpty(zcAuctionService.query(auction))) {
					request.setAttribute("userId", zcProduct.getAddUserId());
					request.setAttribute("errorMsg", "卖家已封存本拍品，只有参拍用户才能查看");
					return "/wsale/product/product_not_exist";
				}
			}

			// 距街拍
			long deadlineLen = zcProduct.getRealDeadline().getTime() - new Date().getTime();
			deadlineLen = deadlineLen <= 0 ? 0 : deadlineLen/1000;
			zcProduct.setDeadlineLen(deadlineLen);

			User user = userService.get(zcProduct.getAddUserId(), s.getId());

			ZcWallet q = new ZcWallet();
			q.setUserId(zcProduct.getAddUserId());
			ZcWallet wallet = zcWalletService.get(q);
			if(wallet != null) {
				if(wallet.getProtection() >= Double.parseDouble(Application.getString("AF06"))) {
					user.setIsPayBond(true);
				} else {
					user.setIsPayBond(false);
				}
			} else {
				user.setIsPayBond(false);
			}

			// 获取当前加价幅度
			double rangePrice = zcProductRangeService.getRangePrice(zcProduct.getId(), zcProduct.getCurrentPrice());

			// 获取点赞/围观列表
			PageHelper ph = new PageHelper();
			ph.setPage(1);
			ph.setRows(12);
			ph.setSort("addtime");
			ph.setOrder("desc");
			ZcProductLike like = new ZcProductLike();
			like.setProductId(zcProduct.getId());
			//List<ZcProductLike> likes = zcProductLikeService.query(like);
			DataGrid likesDataGrid = zcProductLikeService.dataGrid(like, ph);
			List<ZcProductLike> likes = (List<ZcProductLike>)likesDataGrid.getRows();
			if(!CollectionUtils.isEmpty(likes)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcProductLike productLike : likes) {
					completionService.submit(new Task<ZcProductLike, User>(new CacheKey("user", productLike.getUserId()), productLike) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getUserId(), true);
							return user;
						}

						protected void set(ZcProductLike d, User v) {
							if (v != null)
								d.setUser(v);
						}

					});
				}
				completionService.sync();
			}

			// 其他拍品
			ph = new PageHelper();
			ph.setPage(1);
			ph.setRows(6);
			ph.setSort("readCount");
			ph.setOrder("desc");
			ZcProduct product = new ZcProduct();
			product.setAddUserId(zcProduct.getAddUserId());
			product.setIsDeleted(false);
			product.setId(zcProduct.getId());
			product.setOthers(true);
			product.setStatus("PT03");
			DataGrid dataGrid = productCommon.dataGrid(ph, product, s.getId());

			// 是否需要跳转保证金
			boolean marginFlag = false;
			if(zcProduct.getMargin() != null && zcProduct.getMargin() > 0) {
				ZcProductMargin margin = new ZcProductMargin();
				margin.setProductId(zcProduct.getId());
				margin.setBuyUserId(s.getId());
				margin.setPayStatus("PS02");
				margin = zcProductMarginService.get(margin);
				if(margin == null) {
					marginFlag = true;
				}
			}

			request.setAttribute("product", zcProduct);
			request.setAttribute("rangePrice", rangePrice);
			request.setAttribute("user", user);
			request.setAttribute("likes", likesDataGrid);
			request.setAttribute("otherProducts", (List<ZcProduct>) dataGrid.getRows());
			request.setAttribute("sessionInfo", s);
			request.setAttribute("marginFlag", marginFlag);

			// 判断用户是否关注公众号
			request.setAttribute("subscribe", "cs".equals(s.getId()) ? true : WeixinUtil.getSubscribe(s.getName()));

		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wsale/product/product_detail";
	}

	@RequestMapping("/likes")
	@ResponseBody
	public Json likes(ZcProductLike like) {
		Json j = new Json();
		try{
			List<ZcProductLike> likes = zcProductLikeService.query(like);
			if(!CollectionUtils.isEmpty(likes)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcProductLike productLike : likes) {
					completionService.submit(new Task<ZcProductLike, User>(new CacheKey("user", productLike.getUserId()), productLike) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getUserId(), true);
							return user;
						}

						protected void set(ZcProductLike d, User v) {
							if (v != null)
								d.setUser(v);
						}

					});
				}
				completionService.sync();
			}
			j.setObj(likes);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 出价列表分页查
	 * http://localhost:8080/api/apiProductController/auctions?tokenId=1D96DACB84F21890ED9F4928FA8B352B&productId=06166240E78147C0B6DC4BFC813E5E51&page=1&rows=5
	 * @return
	 */
	@RequestMapping("/auctions")
	@ResponseBody
	public Json auctions(PageHelper ph, ZcAuction zcAuction) {
		Json j = new Json();
		try{
			j.setObj(auctionDataGrid(ph, zcAuction));
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 出价
	 * http://localhost:8080/api/apiProductController/bid?tokenId=1D96DACB84F21890ED9F4928FA8B352B&productId=06166240E78147C0B6DC4BFC813E5E51&bid=100
	 * @return
	 */
	@RequestMapping("/bid")
	@ResponseBody
	public Json bid(ZcAuction zcAuction, HttpServletRequest request) {
		Json j = new Json();
		try{
			ZcProduct p = zcProductService.get(zcAuction.getProductId());
			ZcAuction auction = new ZcAuction();
			auction.setProductId(zcAuction.getProductId());
			auction = zcAuctionService.get(auction);
			if(p.getIsDeleted() == true || !"PT03".equals(p.getStatus()) || p.getIsClose() == true) {
				j.fail();
				j.setMsg("出价失败，拍品不存在或已下架！");
				return j;
			}
			if(auction != null && auction.getBid() >= zcAuction.getBid()) {
				j.fail();
				j.setMsg("出价失败，当前出价有更新！");
				return j;
			}

			SessionInfo s = getSessionInfo(request);
			// 是否被屏蔽
			ZcShieldorfans shieldorfans = new ZcShieldorfans();
			shieldorfans.setObjectType(EnumConstants.SHIELDOR_FANS.SD.getCode());
			shieldorfans.setObjectId(p.getAddUserId());
			shieldorfans.setObjectById(s.getId());
			if(zcShieldorfansService.get(shieldorfans) != null) {
				j.fail();
				j.setMsg("出价失败，您已被该用户屏蔽！");
				return j;
			}

			Map<String, Object> obj = new HashMap<String, Object>();

			boolean isDeal = false;
			if(p.getFixedPrice() > 0 && zcAuction.getBid() >= p.getFixedPrice()) {
				isDeal = true;
				zcAuction.setBid(p.getFixedPrice());
			}

			zcAuction.setBuyerId(s.getId());
			zcAuction.setStatus(isDeal ? "DS02" : "DS01");
			zcAuction.setIsAuto(false);
			zcAuctionService.add(zcAuction);

			Date now = new Date();
			if(isDeal) {
				p.setRealDeadline(now);
			} else {
				// 获取当前加价幅度
				double rangePrice = zcProductRangeService.getRangePrice(zcAuction.getProductId(), zcAuction.getBid());
				obj.put("rangePrice", rangePrice);

				long deadlineLen = 0;
				if(p.getRealDeadline() != null && p.getRealDeadline().getTime() - now.getTime() < 5*60*1000) {
					p.setRealDeadline(DateUtil.addMinuteToDate(now, 5));
					deadlineLen = 5*60;
				}
				obj.put("deadlineLen", deadlineLen);
			}
			zcAuction.setUser(userService.getByZc(s.getId()));
			obj.put("auction", zcAuction);
			obj.put("isDeal", isDeal);

			p.setCurrentPrice(zcAuction.getBid());
			// 更新拍品当前价、自动出价、加关注
			bidOtherHandle(p, isDeal, s.getId());

			j.setObj(obj);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	private void bidOtherHandle(ZcProduct product, boolean isDeal, final String userId) {
		double currentPrice = product.getCurrentPrice();
		Date now = new Date();
		// 更新拍品当前价
		ZcProduct p = new ZcProduct();
		p.setId(product.getId());
		p.setCurrentPrice(currentPrice);
		p.setRealDeadline(product.getRealDeadline());
		if(isDeal) {
			// 生成订单
			ZcOrder order = new ZcOrder();
			order.setProductId(product.getId());
			order01State.handle(order);

			p.setStatus("PT04");
			p.setUserId(userId);
			p.setHammerPrice(currentPrice);
			p.setHammerTime(now);
			p.setRefund(true);
		}
		zcProductService.edit(p);

		// 出价被超通知
		sendExceedTemplateMessage(p, userId, isDeal);
		if(!isDeal) {
			// 给卖家发送出价成功通知
			p.setAddUserId(product.getAddUserId());
			sendBidTemplateMessage(p, userId);
		}

		// 触发自动出价
		if(!isDeal) {
			ZcUserAutoPrice q = new ZcUserAutoPrice();
			q.setProductId(product.getId());
			q.setMaxPrice(currentPrice);
			ZcUserAutoPrice auto = zcUserAutoPriceService.get(q);
			if(auto != null && !auto.getUserId().equals(userId)) { // 存在自动出价且不是自己
				double rangePrice = zcProductRangeService.getRangePrice(product.getId(), product.getCurrentPrice());
				isDeal = false;
				double bid = currentPrice + rangePrice;
				if(product.getFixedPrice() > 0 && bid >= product.getFixedPrice()) {
					isDeal = true;
					bid = product.getFixedPrice();
				}

				ZcAuction auction = new ZcAuction();
				auction.setProductId(product.getId());
				auction.setBid(bid);
				auction.setBuyerId(auto.getUserId());
				auction.setStatus(isDeal ? "DS02" : "DS01");
				auction.setIsAuto(true);
				zcAuctionService.add(auction);

				// 更新拍品当前价
				ZcProduct p1 = new ZcProduct();
				p1.setId(product.getId());
				p1.setCurrentPrice(bid);
				p1.setRealDeadline(product.getRealDeadline());
				if(isDeal) {
					// 生成订单
					ZcOrder order = new ZcOrder();
					order.setProductId(product.getId());
					order01State.handle(order);

					p1.setRealDeadline(now);
					p1.setStatus("PT04");
					p1.setUserId(auto.getUserId());
					p1.setHammerPrice(bid);
					p1.setHammerTime(now);
					p1.setRefund(true);
				}
				zcProductService.edit(p1);

				// 出价被超通知
				sendExceedTemplateMessage(p1, auto.getUserId(), isDeal);
				if(!isDeal) {
					// 给卖家发送出价成功通知
					p1.setAddUserId(product.getAddUserId());
					sendBidTemplateMessage(p1, auto.getUserId());
				}
			}
		}

		// 异步加关注
		final CompletionService completionService = CompletionFactory.initCompletion();
		completionService.submit(new Task<String, Boolean>(product.getAddUserId()) {
			@Override
			public Boolean call() throws Exception {
				if(!userId.equals(getD())) {
					ZcShieldorfans shieldorfans = new ZcShieldorfans();
					shieldorfans.setObjectType(EnumConstants.SHIELDOR_FANS.FS.getCode());
					shieldorfans.setObjectById(getD());
					shieldorfans.setObjectId(userId);
					if (zcShieldorfansService.get(shieldorfans) == null) {
						zcShieldorfansService.add(shieldorfans);
					}
				}
				return null;
			}
		});
	}

	/**
	 * 给卖家发送出价成功通知
	 * @param product
	 */
	private void sendBidTemplateMessage(final ZcProduct product, final String buyerId) {
		final CompletionService completionService = CompletionFactory.initCompletion();
		completionService.submit(new Task<Object, Boolean>(null) {
			@Override
			public Boolean call() throws Exception {
				// 卖家
				User seller = userService.getByZc(product.getAddUserId());
				// 买家
				User buyer = userService.getByZc(buyerId);
				WxTemplate temp = new WxTemplate();
				temp.setTouser(seller.getName());
				temp.setUrl(PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + product.getId()));
				temp.setTemplate_id(WeixinUtil.BID_TEMPLATE_ID);
				Map<String, TemplateData> data = new HashMap<String, TemplateData>();

				TemplateData first = new TemplateData();
				first.setValue("尊敬的『"+seller.getNickname()+"』您的拍品，『"+buyer.getNickname()+"』出价￥"+product.getCurrentPrice()+"元，目前领先！\n");
				data.put("first", first);
				// 拍品名称
				TemplateData keyword1 = new TemplateData();
				String content = product.getContentLine();
				content = content.length() > 20 ? content.substring(0, 20) + "..." : content;
				keyword1.setValue(content);
				data.put("keyword1", keyword1);
				// 拍卖截止时间
				TemplateData keyword2 = new TemplateData();
				keyword2.setValue(DateUtil.format(product.getRealDeadline(), "MM月dd日 HH:mm"));
				data.put("keyword2", keyword2);

				temp.setData(data);
				WeixinUtil.sendTemplateMessage(temp);
				return true;
			}
		});
	}

	/**
	 * 发送出价被超通知
	 * @param product
	 * @param userId
	 * @param isDeal
	 */
	private void sendExceedTemplateMessage(final ZcProduct product, final String userId, final boolean isDeal) {
		final CompletionService completionService = CompletionFactory.initCompletion();
		completionService.submit(new Task<Object, Boolean>(null) {
			@Override
			public Boolean call() throws Exception {
				ZcAuction a = new ZcAuction();
				a.setProductId(product.getId());
				List<ZcAuction> alist = zcAuctionService.query(a);
				if(alist.size() >= 2) {
					ZcAuction exceed = alist.get(1);
					if(!exceed.getBuyerId().equals(userId)) {
						User u = userService.getByZc(exceed.getBuyerId());
						WxTemplate temp = new WxTemplate();
						temp.setTouser(u.getName());
						temp.setUrl(PathUtil.getUrlPath("api/apiProductController/productDetail?id=" + product.getId()));
						temp.setTemplate_id(WeixinUtil.EXCEED_TEMPLATE_ID);
						Map<String, TemplateData> data = new HashMap<String, TemplateData>();

						TemplateData first = new TemplateData();
						first.setValue("『"+u.getNickname()+"』您好，您的出价￥"+exceed.getBid()+"已被超越！");
						data.put("first", first);
						// 拍品名称
						TemplateData keyword1 = new TemplateData();
						String content = product.getContentLine();
						content = content.length() > 20 ? content.substring(0, 20) + "..." : content;
						keyword1.setValue(content);
						data.put("keyword1", keyword1);
						// 当前价
						TemplateData keyword2 = new TemplateData();
						keyword2.setValue("￥" + product.getCurrentPrice());
						//keyword2.setColor("#0000E3");
						data.put("keyword2", keyword2);

						TemplateData remark = new TemplateData();
						String dealTime = DateUtil.format(product.getRealDeadline(), "MM月dd日 HH:mm");
						remark.setValue(isDeal ? dealTime + "拍卖已结束" : "截拍时间：" + dealTime);
						remark.setColor("#0000E3");
						data.put("remark", remark);

						temp.setData(data);
						WeixinUtil.sendTemplateMessage(temp);
					}
				}
				return true;
			}
		});
	}

	/**
	 * 自动出价设置
	 * http://localhost:8080/api/apiProductController/autoBid?tokenId=1D96DACB84F21890ED9F4928FA8B352B&productId=06166240E78147C0B6DC4BFC813E5E51&maxPrice=2000
	 * @return
	 */
	@RequestMapping("/autoBid")
	@ResponseBody
	public Json autoBid(ZcUserAutoPrice zcUserAutoPrice, HttpServletRequest request) {
		Json j = new Json();
		try{
			ZcProduct p = zcProductService.get(zcUserAutoPrice.getProductId());
			ZcAuction auction = new ZcAuction();
			auction.setProductId(p.getId());
			auction = zcAuctionService.get(auction);
			if(p.getIsDeleted() == true || !"PT03".equals(p.getStatus()) || p.getIsClose() == true) {
				j.fail();
				j.setMsg("自动出价失败，拍品不存在或已下架！");
				return j;
			}
			if(auction != null && auction.getBid() >= zcUserAutoPrice.getMaxPrice()) {
				j.fail();
				j.setMsg("自动出价失败，当前价已超出最高价，请手动刷新后再试！");
				return j;
			}

			SessionInfo s = getSessionInfo(request);
			// 是否被屏蔽
			ZcShieldorfans shieldorfans = new ZcShieldorfans();
			shieldorfans.setObjectType(EnumConstants.SHIELDOR_FANS.SD.getCode());
			shieldorfans.setObjectId(p.getAddUserId());
			shieldorfans.setObjectById(s.getId());
			if(zcShieldorfansService.get(shieldorfans) != null) {
				j.fail();
				j.setMsg("出价失败，您已被该用户屏蔽！");
				return j;
			}

			// 获取当前加价幅度
			double rangePrice = zcProductRangeService.getRangePrice(p.getId(), p.getCurrentPrice());
			boolean isDeal = false;
			double bid = 0;
			if(p.getCurrentPrice() == 0) {
				bid = p.getStartingPrice() == 0 ? rangePrice : p.getStartingPrice();
			} else {
				bid = p.getCurrentPrice() + rangePrice;
			}

			if(p.getFixedPrice() > 0 && bid >= p.getFixedPrice()) {
				isDeal = true;
				bid = p.getFixedPrice();
			}

			// 获取是否存在自动出价
			ZcUserAutoPrice q = new ZcUserAutoPrice();
			q.setProductId(p.getId());
			q.setMaxPrice(bid);
			final ZcUserAutoPrice hisAuto = zcUserAutoPriceService.get(q);

			zcUserAutoPrice.setUserId(s.getId());
			ZcUserAutoPrice exist = zcUserAutoPriceService.get(zcUserAutoPrice);
			if(exist != null) {
				zcUserAutoPrice.setId(exist.getId());
				zcUserAutoPriceService.edit(zcUserAutoPrice);
			} else {
				zcUserAutoPriceService.add(zcUserAutoPrice);
			}

			// 当前拍品无出价记录或最高出价非自己则启动自动出价
			if(auction == null || !auction.getBuyerId().equals(s.getId())) {
				ZcAuction a = new ZcAuction();
				a.setProductId(p.getId());
				a.setBid(bid);
				a.setBuyerId(s.getId());
				a.setStatus(isDeal ? "DS02" : "DS01");
				a.setIsAuto(true);
				zcAuctionService.add(a);

				final CompletionService completionService = CompletionFactory.initCompletion();
				final String userId = s.getId();
				final double maxPrice = zcUserAutoPrice.getMaxPrice();
				final boolean isDealT = isDeal;
				p.setCurrentPrice(bid);
				completionService.submit(new Task<ZcProduct, Object>(p) {
					@Override
					public Object call() throws Exception {
						String productId = getD().getId();
						String dealUserId = userId;
						boolean isDeal = isDealT;
						double bid = getD().getCurrentPrice();
						if(hisAuto != null && !hisAuto.getUserId().equals(userId)) {
							boolean self = true, maxB = false;
							while(true) {
								double oldBid = bid;
								boolean b = true;
								if(!isDeal) {
									dealUserId = self ? hisAuto.getUserId() : userId;
									double rangePrice = zcProductRangeService.getRangePrice(productId, bid);
									double max = self ? hisAuto.getMaxPrice() : maxPrice;
									if(bid >= max) {
										b = false;
									} else {
										bid += rangePrice;
										if(bid >= max) {
											bid = max;
										}
									}

									if(getD().getFixedPrice() > 0 && bid >= getD().getFixedPrice()) {
										isDeal = true;
										bid = getD().getFixedPrice();
									}

									if(b) {
										ZcAuction e = new ZcAuction();
										e.setProductId(productId);
										e = zcAuctionService.get(e);
										if(e != null && e.getBuyerId().equals(dealUserId)) {
											bid = oldBid;
											break;
										}
										if(e != null && e.getBid() == bid) break;

										ZcAuction a = new ZcAuction();
										a.setProductId(productId);
										a.setBid(bid);
										a.setBuyerId(dealUserId);
										a.setStatus(isDeal ? "DS02" : "DS01");
										a.setIsAuto(true);
										zcAuctionService.add(a);
										if(maxB)
											break;
									} else {
										if(maxB)
											break;

										maxB = true;
									}
									self = self ? false : true;
								} else {
									break;
								}
							}
						}

						Date now = new Date();
						// 更新拍品当前价
						ZcProduct p1 = new ZcProduct();
						p1.setId(productId);
						p1.setCurrentPrice(bid);
						p1.setRealDeadline(getD().getRealDeadline());
						if(isDeal) {
							// 生成订单
							ZcOrder order = new ZcOrder();
							order.setProductId(productId);
							order01State.handle(order);

							p1.setRealDeadline(now);
							p1.setStatus("PT04");
							p1.setUserId(dealUserId);
							p1.setHammerPrice(bid);
							p1.setHammerTime(now);
							p1.setRefund(true);
						} else {
							// 延时五分钟设置
							if(getD().getRealDeadline() != null && getD().getRealDeadline().getTime() - now.getTime() < 5*60*1000) {
								p1.setRealDeadline(DateUtil.addMinuteToDate(now, 5));
							}
						}
						zcProductService.edit(p1);

						// 出价被超通知
						sendExceedTemplateMessage(p1, userId, isDeal);
						if(!isDeal) {
							// 给卖家发送出价成功通知
							p1.setAddUserId(getD().getAddUserId());
							sendBidTemplateMessage(p1, dealUserId);
						}


						if(!userId.equals(getD().getAddUserId())) {
							ZcShieldorfans shieldorfans = new ZcShieldorfans();
							shieldorfans.setObjectType(EnumConstants.SHIELDOR_FANS.FS.getCode());
							shieldorfans.setObjectById(getD().getAddUserId());
							shieldorfans.setObjectId(userId);
							if (zcShieldorfansService.get(shieldorfans) == null) {
								zcShieldorfansService.add(shieldorfans);
							}
						}

						return null;
					}
				});

			}

			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 手动更新
	 * @return
	 */
	@RequestMapping("/updateBid")
	@ResponseBody
	public Json updateBid(String id, double currentPrice,  HttpServletRequest request) {
		Json j = new Json();
		try{
			Map<String, Object> obj = new HashMap<String, Object>();
			ZcProduct p = zcProductService.get(id);
			// 距街拍
			long deadlineLen = p.getRealDeadline().getTime() - new Date().getTime();
			deadlineLen = deadlineLen <= 0 ? 0 : deadlineLen/1000;
			p.setDeadlineLen(deadlineLen);
			obj.put("product", p);

			SessionInfo s = getSessionInfo(request);
			ZcAuction auction = new ZcAuction();
			auction.setProductId(id);
			auction = zcAuctionService.get(auction);
			if(auction == null || auction.getBid() == currentPrice
					|| (auction.getBuyerId().equals(s.getId()) && !auction.getIsAuto())) {
				j.fail();
				j.setMsg("暂无变动，无需更新");
				j.setObj(obj);
				return j;
			}

			// 获取当前加价幅度
			double rangePrice = zcProductRangeService.getRangePrice(id, p.getCurrentPrice());
			obj.put("rangePrice", rangePrice);

			j.setObj(obj);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	private DataGrid auctionDataGrid(PageHelper ph, ZcAuction zcAuction) {
		ph.setSort("bid");
		ph.setOrder("desc, t.addtime desc");
		DataGrid dataGrid = zcAuctionService.dataGrid(zcAuction, ph);
		List<ZcAuction> list = (List<ZcAuction>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcAuction auction : list) {
				completionService.submit(new Task<ZcAuction, User>(new CacheKey("user", auction.getBuyerId()), auction) {
					@Override
					public User call() throws Exception {
						User user = userService.get(getD().getBuyerId(), true);
						return user;
					}

					protected void set(ZcAuction d, User v) {
						if (v != null)
							d.setUser(v);
					}

				});
			}
			completionService.sync();
		}
		return dataGrid;
	}

	/**
	 * 拍品编辑
	 * http://localhost:8080/api/apiProductController/edit?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=06166240E78147C0B6DC4BFC813E5E51
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcProduct zcProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			zcProductService.edit(zcProduct);
//			j.setObj(zcProduct);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 下架（草稿）
	 * http://localhost:8080/api/apiProductController/productOff?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=06166240E78147C0B6DC4BFC813E5E51
	 * @return
	 */
	@RequestMapping("/productOff")
	@ResponseBody
	public Json productOff(ZcProduct zcProduct, HttpServletRequest request) {
		Json j = new Json();
		try{
			// 竞拍中有出价的不允许下架
			if("PT03".equals(zcProduct.getStatus())) {
				ZcAuction auction = new ZcAuction();
				auction.setProductId(zcProduct.getId());
				if(CollectionUtils.isNotEmpty(zcAuctionService.query(auction))) {
					j.fail();
					j.setMsg("该拍品已有出价，禁止下架！");
					return j;
				}
			}

			zcProduct.setStatus("PT01");
			zcProductService.edit(zcProduct);
//			j.setObj(zcProduct);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 删除
	 * http://localhost:8080/api/apiProductController/del?tokenId=1D96DACB84F21890ED9F4928FA8B352B&id=06166240E78147C0B6DC4BFC813E5E51
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Json del(String id, HttpServletRequest request) {
		Json j = new Json();
		try{
			zcProductService.delete(id);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取拍品加价幅度信息
	 * http://localhost:8080/api/apiProductController/getRange?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/getRange")
	@ResponseBody
	public Json getRange(HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcProductRange range = zcProductRangeService.getLastByUserId(s.getId());
			ZcProductRange q = new ZcProductRange();
			if(range != null) {
				q.setProductId(range.getProductId());
			} else {
				q.setProductId("-1"); // 默认
			}
			List<ZcProductRange> ranges = zcProductRangeService.query(q);

			j.setObj(ranges);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	private void addRange(ZcProductRange range, HttpServletRequest request) {
		try{
			SessionInfo s = getSessionInfo(request);
			ZcProductRange q = new ZcProductRange();
			q.setProductId(range.getProductId());
			List<ZcProductRange> ranges = zcProductRangeService.query(q);
			if(CollectionUtils.isNotEmpty(ranges)) zcProductRangeService.delete(q);
			if(range.getStartPrices() != null) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(int i=0; i<range.getStartPrices().length; i++) {
					if(range.getStartPrices()[i] == null) continue;
 					ZcProductRange productRange = new ZcProductRange();
					productRange.setProductId(range.getProductId());
					productRange.setAddUserId(s.getId());
					productRange.setStartPrice(range.getStartPrices()[i]);
					productRange.setEndPrice(range.getEndPrices()[i]);
					productRange.setPrice(range.getPrices()[i]);
					completionService.submit(new Task<ZcProductRange, Object>(productRange){
						@Override
						public Boolean call() throws Exception {
							zcProductRangeService.add(getD());
							return true;
						}
					});
				}
				completionService.sync();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 点赞/围观
	 * http://localhost:8080/api/apiProductController/addLike?tokenId=1D96DACB84F21890ED9F4928FA8B352B&productId=06166240E78147C0B6DC4BFC813E5E51
	 * @return
	 */
	@RequestMapping("/addLike")
	@ResponseBody
	public Json addLike(String productId, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcProductLike like = new ZcProductLike();
			like.setProductId(productId);
			like.setUserId(s.getId());
			if(zcProductLikeService.get(like) == null) {
				zcProductLikeService.add(like);
				zcProductService.updateCount(productId, 1, "like_count");
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 取消点赞/围观
	 * http://localhost:8080/api/apiProductController/cancelLike?tokenId=1D96DACB84F21890ED9F4928FA8B352B&productId=06166240E78147C0B6DC4BFC813E5E51
	 * @return
	 */
	@RequestMapping("/cancelLike")
	@ResponseBody
	public Json cancelLike(String productId, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcProductLike like = new ZcProductLike();
			like.setProductId(productId);
			like.setUserId(s.getId());
			like = zcProductLikeService.get(like);
			if(like != null) {
				zcProductLikeService.delete(like.getId());
				zcProductService.updateCount(productId, -1, "like_count");
			}

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 我的-拍品管理
	 * http://localhost:8080/api/apiProductController/productManage?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/productManage")
	public String productManage(boolean isDraft, HttpServletRequest request) {
		request.setAttribute("isDraft", isDraft); // 是否定位草稿箱tab
		return "/wsale/product/product_manage";
	}

	/**
	 * 买家保证金
	 * @param request
	 * @return
	 */
	@RequestMapping("/addMargin")
	@ResponseBody
	public Json addMargin(ZcProductMargin productMargin, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			productMargin.setBuyUserId(s.getId());
			ZcProductMargin exist = zcProductMarginService.get(productMargin);
			if(exist == null) {
				productMargin.setPayStatus("PS01");
				zcProductMarginService.add(productMargin);
			} else {
				productMargin.setId(exist.getId());
			}
			j.setObj(productMargin.getId());
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}
}
