package jb.controller;

import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.util.wx.WeixinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 店铺管理
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiShop")
public class ApiShopController extends BaseController {

	@Autowired
	private ZcShopServiceI zcShopService;

	@Autowired
	private ZcAddressServiceI zcAddressService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private ZcOrderServiceI zcOrderService;

	@Autowired
	private ZcShieldorfansServiceI zcShieldorfansService;

	@Autowired
	private ZcProductServiceI zcProductService;

	/**
	 * 跳转-店铺信息
	 * @return
	 */
	@RequestMapping("/shop")
	public String shop(String userId, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		// 不是本人加关注逻辑
		if(!F.empty(userId) && !userId.equals(s.getId())) {
			ZcShieldorfans shieldorfans = new ZcShieldorfans();
			shieldorfans.setObjectType("FS");
			shieldorfans.setObjectById(userId);
			shieldorfans.setObjectId(s.getId());
			zcShieldorfansService.addOrUpdate(shieldorfans, true);
		}

		userId = F.empty(userId) ? s.getId() : userId;
		User user = userService.get(userId, s.getId());
		request.setAttribute("user", user);

		// 评分
		//float grade = zcCommentService.getGradeAvgByUserId(s.getId());
		//request.setAttribute("grade", grade);

		// 订单信誉、订单违约统计
		Map<String, Object> order_status_count = zcOrderService.orderStatusCount(user.getId());
		request.setAttribute("order_status_count", order_status_count);
		return "/wsale/shop/shop_page";
	}

	/**
	 * 跳转-查看全部拍品
	 * @return
	 */
	@RequestMapping("/showAllProducts")
	public String showAllProducts(String userId, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		User user = userService.get(userId, s.getId());
		ZcWallet q = new ZcWallet();
		q.setUserId(userId);
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

		request.setAttribute("user", user);
		request.setAttribute("sessionInfo", s);
		// 判断用户是否关注公众号
		request.setAttribute("subscribe", "cs".equals(s.getId()) ? true : WeixinUtil.getSubscribe(s.getName()));
		return "/wsale/product/product_all_list";
	}

	/**
	 * 跳转我的-店铺设置
	 * @return
	 */
	@RequestMapping("/myShopSet")
	public String myShopSet(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcAddress address = new ZcAddress();
		address.setUserId(s.getId());
		address.setAtype(2); // 退货地址
		address.setOrderId("-1");
		address = zcAddressService.get(address);
		request.setAttribute("address", address);

		ZcShop q = new ZcShop();
		q.setUserId(s.getId());
		ZcShop shop = zcShopService.get(q);
		request.setAttribute("shop", shop);

		String protectionMsg = "";
		ZcWallet w = new ZcWallet();
		w.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(w);
		if(wallet == null || wallet.getProtection() <= 0) {
			protectionMsg = "未缴纳";
		} else if(wallet.getProtection() >= Double.parseDouble(Application.getString("AF06"))) {
			protectionMsg = "已足额";
		} else {
			protectionMsg = "未足额";
		}
		request.setAttribute("protectionMsg", protectionMsg);

		request.setAttribute("sessionInfo", s);

		return "/wsale/shop/myShopSet";
	}

	/**
	 * 跳转-首页钻石店铺
	 * @return
	 */
	@RequestMapping("/starShop")
	public String starShop(HttpServletRequest request) {
		return "/wsale/shop/star_shop";
	}

	/**
	 * 保存店铺设置
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveShopSet")
	@ResponseBody
	public Json saveShopSet(ZcShop shop, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			shop.setUserId(s.getId());

			ZcShop q = new ZcShop();
			q.setUserId(s.getId());
			ZcShop exist = zcShopService.get(q);
			if(exist == null) {
				zcShopService.add(shop);
			} else {
				shop.setId(exist.getId());
				zcShopService.edit(shop);
			}

			j.success();
			j.setMsg("操作成功");
		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}

		return j;
	}

	/**
	 * 编辑地址信息
	 * http://localhost:8080/api/apiShop/editAddress?tokenId=1D96DACB84F21890ED9F4928FA8B352B&atype=2
	 * @param request
	 * @return
	 */
	@RequestMapping("/editAddress")
	@ResponseBody
	public Json editAddress(ZcAddress address, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			address.setUserId(s.getId());
			address.setOrderId("-1"); // 订单id默认为-1
			if(F.empty(address.getId())) {
				zcAddressService.add(address);
			} else {
				zcAddressService.edit(address);
			}
			j.success();
			j.setMsg("操作成功");
			j.setObj(address.getId());
		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}

		return j;
	}

	/**
	 * 获取钻石店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/starShopList")
	@ResponseBody
	public Json starShopList(User user, PageHelper ph, HttpServletRequest request) {
		Json j = new Json();
		try {
			ph.setSort("shopSeq");
			ph.setOrder("desc");

			user.setIsDeleted(false);
			user.setShopSeq(1);
			DataGrid dataGrid = userService.dataGrid(user, ph);
			List<User> list = (List<User>) dataGrid.getRows();
			if(!CollectionUtils.isEmpty(list)) {
				String[] userIds = new String[list.size()];
				int i = 0;
				for (User u : list) {
					userIds[i] = u.getId();
					i++;
				}
				Map<String, Integer> biddingNumMap = zcProductService.getCountBiddingNum(userIds);
				final CompletionService completionService = CompletionFactory.initCompletion();
				for (User u : list) {
					Integer num = biddingNumMap.get(u.getId());
					if(num != null) u.setBiddingNums(num);
					// 成交额
					completionService.submit(new Task<User, Map<String, Double>>(u) {
						@Override
						public Map<String, Double> call() throws Exception {
							return zcOrderService.getTurnover(getD().getId());
						}

						protected void set(User d, Map<String, Double> v) {
							if (v != null) {
								d.setTurnover(v);
							}
						}
					});
				}
				completionService.sync();
			}

			j.setObj(dataGrid);
			j.success();
			j.setMsg("操作成功");
		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}

		return j;
	}

}
