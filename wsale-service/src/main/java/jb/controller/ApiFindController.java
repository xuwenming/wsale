package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.util.EnumConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发现
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiFindController")
public class ApiFindController extends BaseController {

	@Autowired
	private ZcBannerServiceI zcBannerService;

	@Autowired
	private ZcNewHistoryServiceI zcNewHistoryService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcChatMsgServiceI zcChatMsgService;

	@Autowired
	private ZcBestProductServiceI zcBestProductService;


	/**
	 * 跳转发现
	 * http://localhost:8080/api/apiFindController/find?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/find")
	public String find(HttpServletRequest request) {
		try {
			SessionInfo s = getSessionInfo(request);
			// 消息中心-未读消息数
			ZcChatMsg msg = new ZcChatMsg();
			msg.setToUserId(s.getId());
			msg.setUnread(true);
			request.setAttribute("chat_unread_count", zcChatMsgService.count(msg));

			// 精选拍品数
			ZcBestProduct best = new ZcBestProduct();
			best.setAuditStatus("AS02");
			best.setEndTime(new Date());
			best.setProductStatus("PT03");
			best.setChannel(EnumConstants.BEST_CHANNEL.HOME.getCode());
			request.setAttribute("home_best_count", zcBestProductService.getCount(best));

			// 新品开拍未读数
			ZcNewHistory zcNewHistory = new ZcNewHistory();
			zcNewHistory.setOpenid(s.getName());
			zcNewHistory.setIsRead(false);
			request.setAttribute("new_unread_count", zcNewHistoryService.count(zcNewHistory));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wsale/find/find";
	}

	/**
	 * 跳转新品开拍
	 * http://localhost:8080/api/apiFindController/newProduct?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/newProduct")
	public String newProduct(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		zcNewHistoryService.updateReaded(s.getName()); // 更新已读
		return "/wsale/find/newProduct";
	}

	/**
	 * 新品开拍列表
	 * http://localhost:8080/api/apiFindController/newProductList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=5
	 * @return
	 */
	@RequestMapping("/newProductList")
	@ResponseBody
	public Json newProductList(PageHelper ph, ZcNewHistory zcNewHistory, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			ph.setSort("addtime");
			ph.setOrder("desc");
			zcNewHistory.setOpenid(s.getName());

			DataGrid dataGrid = zcNewHistoryService.dataGrid(zcNewHistory, ph);
			List<ZcNewHistory> newProducts = (List<ZcNewHistory>) dataGrid.getRows();
			if(!CollectionUtils.isEmpty(newProducts)) {
				final CompletionService completionService = CompletionFactory.initCompletion();
				for (ZcNewHistory newProduct : newProducts) {
					completionService.submit(new Task<ZcNewHistory, User>(new CacheKey("user", newProduct.getUserId()), newProduct) {
						@Override
						public User call() throws Exception {
							User user = userService.getByZc(getD().getUserId());
							return user;
						}

						protected void set(ZcNewHistory d, User v) {
							if (v != null)
								d.setUser(v);
						}
					});
					if(!F.empty(newProduct.getProductIds())) {
						List<ZcProduct> products = new ArrayList<ZcProduct>();
						String[] productIds = newProduct.getProductIds().split(",");
						for(final String productId : productIds) {
							if(F.empty(productId)) continue;
							completionService.submit(new Task<List<ZcProduct>, ZcProduct>(new CacheKey("product", productId), products) {
								@Override
								public ZcProduct call() throws Exception {
									ZcProduct product = zcProductService.get(productId, null);
									return product;
								}

								protected void set(List<ZcProduct> d, ZcProduct v) {
									if (v != null)
										d.add(v);
								}
							});
						}
						newProduct.setProducts(products);
					}

				}
				completionService.sync();
			}
			j.setObj(dataGrid);
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

}
