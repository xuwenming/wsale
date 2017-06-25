package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.service.impl.order.OrderState;
import jb.util.ConfigUtil;
import jb.util.EnumConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * ZcOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcOrderController")
public class ZcOrderController extends BaseController {

	@Autowired
	private ZcOrderServiceI zcOrderService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcOrderXiaoerServiceI zcOrderXiaoerService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Resource(name = "order10StateImpl")
	private OrderState order10State;

	@Resource(name = "order15StateImpl")
	private OrderState order15State;

	@Autowired
	private ZcCommentServiceI zcCommentService;

	@Autowired
	private ZcAddressServiceI zcAddressService;
	@Autowired
	private ZcCategoryServiceI zcCategoryService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Autowired
	private ZcPayOrderServiceI zcPayOrderService;

	/**
	 * 跳转到ZcOrder管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcorder/zcOrder";
	}

	/**
	 * 获取ZcOrder数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcOrder zcOrder, PageHelper ph) {
		DataGrid dataGrid =zcOrderService.dataGrid(zcOrder, ph);
		List<ZcOrder> list = (List<ZcOrder>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(ZcOrder o : list) {
				completionService.submit(new Task<ZcOrder, ZcProduct>(o) {
					@Override
					public ZcProduct call() throws Exception {
						ZcProduct p = zcProductService.get(getD().getProductId(), null);
						return p;
					}
					protected void set(ZcOrder d, ZcProduct v) {
						if(v != null) {
							d.setProduct(v);
							final String sellerUserId = v.getAddUserId(); // 卖家ID
							final String buyerUserId = v.getUserId(); // 买家ID
							completionService.submit(new Task<ZcOrder, User>(d) {
								@Override
								public User call() throws Exception {
									User user = userService.getByZc(sellerUserId);
									return user;
								}
								protected void set(ZcOrder d, User v) {
									if(v != null) {
										d.setSeller(v);
									}
								}
							});
							completionService.submit(new Task<ZcOrder, User>(d) {
								@Override
								public User call() throws Exception {
									User user = userService.getByZc(buyerUserId);
									return user;
								}
								protected void set(ZcOrder d, User v) {
									if(v != null) {
										d.setBuyer(v);
									}
								}
							});
						}

						// 检查买家是否申请小二介入条件：卖家拒绝退货且订单未结束
						if("RS02".equals(d.getBackStatus()) && !"OS10".equals(d.getOrderStatus()) && !"OS15".equals(d.getOrderStatus())) {
							ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
							xiaoerQ.setOrderId(d.getId());
							xiaoerQ.setIdType(1);
							ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
							if(xiaoer != null) {
								d.setIsXiaoer(true);
								d.setXiaoer(xiaoer);
							} else {
								d.setIsXiaoer(false);
							}
						}

						// 检查卖家是否申请小二介入条件：买家已退货发货且订单未结束
						if("RS04".equals(d.getBackStatus()) && !"OS10".equals(d.getOrderStatus()) && !"OS15".equals(d.getOrderStatus())) {
							ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
							xiaoerQ.setOrderId(d.getId());
							xiaoerQ.setIdType(2);
							ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
							if(xiaoer != null) {
								d.setIsXiaoer(true);
								d.setXiaoer(xiaoer);
							} else {
								d.setIsXiaoer(false);
							}
						}
					}
				});

			}
			completionService.sync();
		}
		return dataGrid;
	}
	/**
	 * 获取ZcOrder数据表格excel
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
	public void download(ZcOrder zcOrder, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcOrder,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcOrder页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcOrder zcOrder = new ZcOrder();
		zcOrder.setId(jb.absx.UUID.uuid());
		return "/zcorder/zcOrderAdd";
	}

	/**
	 * 添加ZcOrder
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcOrder zcOrder) {
		Json j = new Json();		
		zcOrderService.add(zcOrder);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcOrder order = zcOrderService.get(id);
		viewRequest(request, order);
		return "/zcorder/zcOrderView";
	}

	@RequestMapping("/viewByProduct")
	public String viewByProduct(HttpServletRequest request, String productId) {
		ZcOrder order = new ZcOrder();
		order.setProductId(productId);
		order = zcOrderService.get(order);
		if(order != null) viewRequest(request, order);

		return "/zcproduct/zcOrderView";
	}

	private void viewRequest(HttpServletRequest request, ZcOrder order) {
		ZcProduct product = zcProductService.get(order.getProductId(), null);
		ZcCategory category = zcCategoryService.get(product.getCategoryId());
		ZcCategory pc = null;
		if(!F.empty(category.getPid())) {
			pc = zcCategoryService.get(category.getPid());
		}
		product.setCname((pc != null ? pc.getName() + " - " : "") + category.getName());
		order.setProduct(product);

		String sellerUserId = product.getAddUserId(); // 卖家ID
		String buyerUserId = product.getUserId(); // 买家ID

		order.setSeller(userService.getByZc(sellerUserId));
		order.setBuyer(userService.getByZc(buyerUserId));

		if(order.getIsCommented()) {
			ZcComment c = new ZcComment();
			c.setOrderId(order.getId());
			order.setComment(zcCommentService.get(c));
		}

		order.setIsXiaoer(false);
		// 检查买家是否申请小二介入条件：卖家拒绝退货且订单未结束
		if("RS02".equals(order.getBackStatus()) && !"OS10".equals(order.getOrderStatus()) && !"OS15".equals(order.getOrderStatus())) {
			ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
			xiaoerQ.setOrderId(order.getId());
			xiaoerQ.setIdType(1);
			ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
			if(xiaoer != null) {
				// 获取图片集合
				ZcFile file = new ZcFile();
				file.setObjectType(EnumConstants.OBJECT_TYPE.XR.getCode());
				file.setObjectId(xiaoer.getId());
				file.setFileType("FT01");
				List<ZcFile> files = zcFileService.queryFiles(file);
				xiaoer.setFiles(files);

				if(!F.empty(xiaoer.getUpdateUserId())) {
					User user = userService.get(xiaoer.getUpdateUserId());
					xiaoer.setUpdateUserName(user.getNickname());
				}

				order.setIsXiaoer(true);
				order.setXiaoer(xiaoer);
			}
		}

		// 检查卖家是否申请小二介入条件：买家已退货发货且订单未结束
		if("RS04".equals(order.getBackStatus()) && !"OS10".equals(order.getOrderStatus()) && !"OS15".equals(order.getOrderStatus())) {
			ZcOrderXiaoer xiaoerQ = new ZcOrderXiaoer();
			xiaoerQ.setOrderId(order.getId());
			xiaoerQ.setIdType(2);
			ZcOrderXiaoer xiaoer = zcOrderXiaoerService.get(xiaoerQ);
			if(xiaoer != null) {
				// 获取图片集合
				ZcFile file = new ZcFile();
				file.setObjectType(EnumConstants.OBJECT_TYPE.XR.getCode());
				file.setObjectId(xiaoer.getId());
				file.setFileType("FT01");
				List<ZcFile> files = zcFileService.queryFiles(file);
				xiaoer.setFiles(files);
				if(!F.empty(xiaoer.getUpdateUserId())) {
					User user = userService.get(xiaoer.getUpdateUserId());
					xiaoer.setUpdateUserName(user.getNickname());
				}

				order.setIsXiaoer(true);
				order.setXiaoer(xiaoer);
			}
		}

		request.setAttribute("order", order);

		// 买家收货地址
		ZcAddress address = new ZcAddress();
		address.setUserId(buyerUserId);
		address.setAtype(1); // 1:收货地址; 2:退货地址
		address.setOrderId(order.getId());
		address = zcAddressService.get(address);
		request.setAttribute("address", address);

		// 卖家退货地址
		ZcAddress backAddress = new ZcAddress();
		backAddress.setUserId(sellerUserId);
		backAddress.setAtype(2); // 1:收货地址; 2:退货地址
		backAddress.setOrderId(order.getId());
		backAddress = zcAddressService.get(backAddress);
		request.setAttribute("backAddress", backAddress);

		// 支付信息
		ZcPayOrder payOrder = new ZcPayOrder();
		payOrder.setObjectType("PO05");
		payOrder.setObjectId(order.getId());
		payOrder = zcPayOrderService.get(payOrder);
		request.setAttribute("payOrder", payOrder);
	}

	/**
	 * 跳转到ZcOrder修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcOrder zcOrder = zcOrderService.get(id);
		request.setAttribute("zcOrder", zcOrder);
		return "/zcorder/zcOrderEdit";
	}

	/**
	 * 修改ZcOrder
	 * 
	 * @param zcOrder
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcOrder zcOrder) {
		Json j = new Json();		
		zcOrderService.edit(zcOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ZcOrder
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcOrderService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 撤销小二介入
	 *
	 * @return
	 */
	@RequestMapping("/cancelXr")
	@ResponseBody
	public Json cancelXr(ZcOrderXiaoer xiaoer, HttpServletRequest request) {
		Json j = new Json();
		try{
			xiaoer.setUpdateUserId(((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId());
			xiaoer.setUpdatetime(new Date());
			xiaoer.setStatus("XS02");
			zcOrderXiaoerService.edit(xiaoer);
			ZcOrder order = zcOrderService.get(xiaoer.getOrderId());
			ZcProduct product = zcProductService.get(order.getProductId());
			String content = product.getContent();
			content = content.length() > 20 ? content.substring(0, 20) + "..." : content;
			if(xiaoer.getIdType() == 1) {
				// 卖家拒绝退货超过3天则交易完成结束订单
				if(!"OS10".equals(order.getOrderStatus()) && new Date().getTime() - order.getReturnConfirmTime().getTime() > 72*60*60*1000) {
					order10State.handle(order);
					j.success();
					j.setMsg("撤销小二成功，且交易完成！");
					return j;
				}

				// 给买家发送小二撤回消息
				sendWxMessage.sendCustomMessageByUserId(product.getUserId(), "您的拍品\""+content+"\"申请的小儿介入被小二驳回，如有疑问请移步，“站务公告”--“投诉纠纷版”申诉。");
			} else {
				// 买家退货发货超过14天则自动退款结束订单
				if(!"OS15".equals(order.getOrderStatus()) && new Date().getTime() - order.getReturnDeliverTime().getTime() > 14*24*60*60*1000) {
					order.setBackStatus("RS05");
					order.setReturnTime(new Date());
					order.setOrderCloseReason("OC003"); // 买家已退货
					order15State.handle(order);
					j.success();
					j.setMsg("撤销小二成功，且退货成功！");
					return j;
				}

				// 给卖家发送小二撤回消息
				sendWxMessage.sendCustomMessageByUserId(product.getAddUserId(), "您的拍品\""+content+"\"申请的小儿介入被小二驳回，如有疑问请移步，“站务公告”--“投诉纠纷版”申诉。");
			}

			j.success();
			j.setMsg("撤销小二成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}


	/**
	 * 同意退货
	 * @return
	 */
	@RequestMapping("/agreeBack")
	@ResponseBody
	public Json agreeBack(ZcOrder order, HttpServletRequest request) {
		Json j = new Json();
		try{
			order.setBackStatus("RS03");
			order.setReturnConfirmTime(new Date());
			zcOrderService.edit(order);

			// 新增订单退货地址（卖家）
			ZcProduct product = zcProductService.get(order.getProductId());
			ZcAddress address = new ZcAddress();
			address.setUserId(product.getAddUserId());
			address.setAtype(2); // 退货地址
			address.setOrderId("-1");
			address = zcAddressService.get(address);
			if(address != null) {
				address.setOrderId(order.getId());
				zcAddressService.add(address);
			}

			String content = product.getContent();
			content = content.length() > 20 ? content.substring(0, 20) + "..." : content;
			// 给卖家发送小二撤回消息
			sendWxMessage.sendCustomMessageByUserId(product.getUserId(), "您的拍品\""+content+"\"申请的小二介入已受理，小二已同意您的退货，您可以去交易中心发货退回拍品。");

			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}
}
