package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ZcBestProduct管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcBestProductController")
public class ZcBestProductController extends BaseController {

	@Autowired
	private ZcBestProductServiceI zcBestProductService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private ZcOrderServiceI zcOrderService;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	/**
	 * 跳转到ZcBestProduct管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		String utype = ((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getUtype();
		request.setAttribute("utype", utype);
		return "/zcbestproduct/zcBestProduct";
	}

	/**
	 * 获取ZcBestProduct数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcBestProduct zcBestProduct, PageHelper ph) {
		DataGrid dataGrid = zcBestProductService.dataGrid(zcBestProduct, ph);
		List<ZcBestProduct> list = (List<ZcBestProduct>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcBestProduct bestProduct : list) {
				completionService.submit(new Task<ZcBestProduct, ZcProduct>(bestProduct) {
					@Override
					public ZcProduct call() throws Exception {
						ZcProduct p = zcProductService.get(getD().getProductId());
						ZcCategory c = zcCategoryService.get(p.getCategoryId());
						p.setCname(c.getName());
						return p;
					}

					protected void set(ZcBestProduct d, ZcProduct v) {
						if (v != null) {
							d.setPno(v.getPno());
							d.setCategoryName(v.getCname());
							d.setZcProduct(v);
						}

					}
				});

				completionService.submit(new Task<ZcBestProduct, User>(new CacheKey("user", bestProduct.getAddUserId()), bestProduct) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getAddUserId());
						return user;
					}

					protected void set(ZcBestProduct d, User v) {
						if (v != null)
							d.setAddUserName(v.getNickname());
					}
				});

				if(!F.empty(bestProduct.getAuditUserId()))
					// 审核人
					completionService.submit(new Task<ZcBestProduct, User>(new CacheKey("user", bestProduct.getAuditUserId()), bestProduct) {
						@Override
						public User call() throws Exception {
							User user = userService.get(getD().getAuditUserId(), true);
							return user;
						}

						protected void set(ZcBestProduct d, User v) {
							if (v != null)
								d.setAuditUserName(v.getNickname());
						}
					});

			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcBestProduct数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/download")
	public void download(ZcBestProduct zcBestProduct, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcBestProduct,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcBestProduct页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcBestProduct zcBestProduct = new ZcBestProduct();
		zcBestProduct.setId(jb.absx.UUID.uuid());
		return "/zcbestproduct/zcBestProductAdd";
	}

	/**
	 * 添加ZcBestProduct
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcBestProduct zcBestProduct) {
		Json j = new Json();		
		zcBestProductService.add(zcBestProduct);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcBestProduct查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcBestProduct zcBestProduct = zcBestProductService.get(id);
		User user = userService.getByZc(zcBestProduct.getAddUserId());
		zcBestProduct.setAddUserName(user.getNickname());
		if(!F.empty(zcBestProduct.getAuditUserId()))
			zcBestProduct.setAuditUserName(userService.getByZc(zcBestProduct.getAuditUserId()).getNickname());
		request.setAttribute("zcBestProduct", zcBestProduct);

		ZcProduct product = zcProductService.get(zcBestProduct.getProductId(), null);
		ZcCategory category = zcCategoryService.get(product.getCategoryId());
		ZcCategory pc = null;
		if(!F.empty(category.getPid())) {
			pc = zcCategoryService.get(category.getPid());
		}
		product.setCname((pc != null ? pc.getName() + " - " : "") + category.getName());
		request.setAttribute("product", product);
		return "/zcbestproduct/zcBestProductView";
	}

	/**
	 * 跳转到ZcBestProduct修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		String utype = ((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getUtype();
		request.setAttribute("utype", utype);

		ZcBestProduct zcBestProduct = zcBestProductService.get(id);
		User user = userService.getByZc(zcBestProduct.getAddUserId());
		zcBestProduct.setAddUserName(user.getNickname());
		if(!F.empty(zcBestProduct.getAuditUserId()))
			zcBestProduct.setAuditUserName(userService.getByZc(zcBestProduct.getAuditUserId()).getNickname());
		request.setAttribute("zcBestProduct", zcBestProduct);

		ZcProduct product = zcProductService.get(zcBestProduct.getProductId(), null);
		ZcCategory category = zcCategoryService.get(product.getCategoryId());
		ZcCategory pc = null;
		if(!F.empty(category.getPid())) {
			pc = zcCategoryService.get(category.getPid());
		}
		product.setCname((pc != null ? pc.getName() + " - " : "") + category.getName());
		request.setAttribute("product", product);

		// 订单信誉、订单违约统计
		Map<String, Object> order_status_count = zcOrderService.orderStatusCount(user.getId());
		request.setAttribute("order_status_count", order_status_count);
		request.setAttribute("user", user);

		String protection = "";
		ZcWallet w = new ZcWallet();
		w.setUserId(user.getId());
		ZcWallet wallet = zcWalletService.get(w);
		if(wallet != null) {
			if(wallet.getProtection() <= 0) {
				protection = "未缴纳";
			} else if(wallet.getProtection() >= Double.parseDouble(Application.getString("AF06"))) {
				protection = "已足额" + wallet.getProtection().longValue() + "元";
			} else {
				protection = "未足额" + wallet.getProtection().longValue() + "元";
			}
		} else {
			protection = "未缴纳";
		}

		request.setAttribute("protection", protection);

		return "/zcbestproduct/zcBestProductEdit";
	}

	/**
	 * 修改ZcBestProduct
	 * 
	 * @param zcBestProduct
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcBestProduct zcBestProduct, String startTimeStr, String endTimeStr, HttpServletRequest request) {
		Json j = new Json();
		StringBuffer buffer = new StringBuffer();
		ZcProduct product = zcProductService.get(zcBestProduct.getProductId());
		String content = product.getContent();
		content = content.length() > 20 ? content.substring(0, 20) + "..." : content;
		if("AS02".equals(zcBestProduct.getAuditStatus())) {
			Calendar c = Calendar.getInstance();
			if(!F.empty(startTimeStr)) c.setTime(DateUtil.parse(startTimeStr, Constants.DATE_FORMAT));
			Date realDeadline = product.getRealDeadline();
			if(c.getTime().after(realDeadline)) {
				j.fail();
				j.setMsg("申请失败！该拍品已街拍或流拍，若商家已付款请尽快联系商家或退款！");
				return j;
			}
			if("PT01".equals(product.getStatus()) || product.getIsDeleted()) {
				j.fail();
				j.setMsg("申请失败！该拍品不存在或已下架，若商家已付款请尽快联系商家或退款！");
				return j;
			}
			zcBestProduct.setStartTime(c.getTime());
			if(!F.empty(endTimeStr)) {
				zcBestProduct.setEndTime(DateUtil.parse(endTimeStr, Constants.DATE_FORMAT));
			} else {
				// 有效时间24小时
				c.add(Calendar.DAY_OF_MONTH, 1);
				zcBestProduct.setEndTime(c.getTime());
			}

			// 精拍至少保留2天
			/*if(realDeadline.getTime() - c.getTime().getTime() < 48*60*60*1000) {
				c.add(Calendar.DAY_OF_MONTH, 2);
				zcBestProduct.setEndTime(c.getTime());

				// 更新拍品实际截止时间,与精拍结束时间同步
				ZcProduct p = new ZcProduct();
				p.setId(zcBestProduct.getProductId());
				p.setRealDeadline(c.getTime());
				zcProductService.edit(p);
			} else {
				zcBestProduct.setEndTime(realDeadline);
			}*/

			if(EnumConstants.BEST_CHANNEL.HOME.getCode().equals(zcBestProduct.getChannel())) {
				buffer.append("恭喜您，您申请的首页精选拍品“"+content+"”审核通过。<a href='"+ PathUtil.getUrlPath("api/apiBestProductController/homeBest")+"'>点击查看</a>");
			} else {
				buffer.append("恭喜您，您申请的分类精选藏品“"+content+"”审核通过。<a href='"+PathUtil.getUrlPath("api/apiCategoryController/forum?id=" + product.getCategoryId())+"'>点击查看</a>");
			}
		} else {
			if(EnumConstants.BEST_CHANNEL.HOME.getCode().equals(zcBestProduct.getChannel())) {
				buffer.append("您申请的首页精选拍品“"+content+"”审核未通过，您可以在帮助中心查询，怎样的藏品优先上精拍。<a href='https://mp.weixin.qq.com/s/27WoYwSdODTzvEInw69rSg'>点击查看</a>");
			} else {
				buffer.append("您申请的分类精选藏品“"+content+"”审核未通过，您可以在帮助中心查询，怎样的藏品优先上精拍。<a href='https://mp.weixin.qq.com/s/27WoYwSdODTzvEInw69rSg'>点击查看</a>");
			}
		}

		zcBestProduct.setAuditUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
		zcBestProduct.setAuditTime(new Date());
		zcBestProductService.edit(zcBestProduct);

		// 推送精拍审核结果通知
		if(!F.empty(buffer.toString())) {
			sendWxMessage.sendCustomMessageByUserId(zcBestProduct.getAddUserId(), buffer.toString());
		}

		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcBestProduct
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcBestProductService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
