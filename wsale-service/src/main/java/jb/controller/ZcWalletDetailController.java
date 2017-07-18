package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcWalletDetailServiceI;
import jb.service.ZcWalletServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.RedisUserServiceImpl;
import jb.service.impl.SendWxMessageImpl;
import jb.util.*;
import jb.util.wx.HttpUtil;
import jb.util.wx.PayCommonUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.XMLUtil;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ZcWalletDetail管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/zcWalletDetailController")
public class ZcWalletDetailController extends BaseController {

	@Autowired
	private ZcWalletDetailServiceI zcWalletDetailService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Autowired
	private RedisUserServiceImpl redisUserService;


	/**
	 * 跳转到ZcWalletDetail管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/zcwalletdetail/zcWalletDetail";
	}

	@RequestMapping("/rechargeManager")
	public String rechargeManager(HttpServletRequest request) {
		return "/zcwalletdetail/recharge/zcWalletDetail";
	}
	@RequestMapping("/cashManager")
	public String cashManager(HttpServletRequest request) {
		return "/zcwalletdetail/cash/zcWalletDetail";
	}

	/**
	 * 获取ZcWalletDetail数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ZcWalletDetail zcWalletDetail, PageHelper ph) {
		DataGrid dataGrid = zcWalletDetailService.dataGrid(zcWalletDetail, ph);

		List<ZcWalletDetail> list = (List<ZcWalletDetail>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (ZcWalletDetail walletDetail : list) {
				// 申请人
				completionService.submit(new Task<ZcWalletDetail, User>(new CacheKey("user", walletDetail.getUserId()), walletDetail) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(getD().getUserId());
						return user;
					}

					protected void set(ZcWalletDetail d, User v) {
						if (v != null)
							d.setUserName(v.getNickname());
					}
				});

				// 钱包余额
//				completionService.submit(new Task<ZcWalletDetail, ZcWallet>(new CacheKey("wallet", walletDetail.getUserId()), walletDetail) {
//					@Override
//					public ZcWallet call() throws Exception {
//						ZcWallet q = new ZcWallet();
//						q.setUserId(getD().getUserId());
//						ZcWallet wallet = zcWalletService.get(q);
//						return wallet;
//					}
//
//					protected void set(ZcWalletDetail d, ZcWallet v) {
//						d.setWalletAmount(v == null ? 0 : v.getAmount());
//					}
//				});

				if(!F.empty(walletDetail.getHandleUserId()))
					// 处理人
					completionService.submit(new Task<ZcWalletDetail, User>(new CacheKey("user", walletDetail.getHandleUserId()), walletDetail) {
						@Override
						public User call() throws Exception {
							User user = userService.getByZc(getD().getHandleUserId());
							return user;
						}

						protected void set(ZcWalletDetail d, User v) {
							if (v != null)
								d.setHandleUserName(v.getNickname());
						}
					});
			}
			completionService.sync();
		}
		return dataGrid;
	}
	@RequestMapping("/rechargeDataGrid")
	@ResponseBody
	public DataGrid rechargeDataGrid(ZcWalletDetail zcWalletDetail, PageHelper ph) {
		return dataGrid(zcWalletDetail, ph);
	}
	@RequestMapping("/cashDataGrid")
	@ResponseBody
	public DataGrid cashDataGrid(ZcWalletDetail zcWalletDetail, PageHelper ph) {
		return dataGrid(zcWalletDetail, ph);
	}
	@RequestMapping("/dataGridByUser")
	@ResponseBody
	public DataGrid dataGridByUser(ZcWalletDetail zcWalletDetail, PageHelper ph) {
		return dataGrid(zcWalletDetail, ph);
	}
	/**
	 * 获取ZcWalletDetail数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(ZcWalletDetail zcWalletDetail, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(zcWalletDetail,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ZcWalletDetail页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ZcWalletDetail zcWalletDetail = new ZcWalletDetail();
		zcWalletDetail.setId(jb.absx.UUID.uuid());
		return "/zcwalletdetail/recharge/zcWalletDetailAdd";
	}

	/**
	 * 添加ZcWalletDetail
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ZcWalletDetail zcWalletDetail, String checkPwd, HttpServletRequest request) {
		Json j = new Json();
		// 获取提现充值密码
		String privateKey = (String)request.getSession().getAttribute(RSAUtil.PRIVATE_KEY);
		if(F.empty(privateKey)) {
			j.setMsg("操作失败，请刷新或关闭当前浏览器重新打开！");
			return j;
		}
		checkPwd = RSAUtil.decryptByPravite(checkPwd, privateKey);

		User admin = userService.get(Constants.MANAGERADMIN);
		if(F.empty(checkPwd) || !checkPwd.equals(admin.getHxPassword())) {
			j.fail();
			j.setMsg("校验密码错误");
			return j;
		}

		String handleUserId = ((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId();
		zcWalletDetail.setOrderNo(Util.CreateWalletNo());
		zcWalletDetail.setHandleUserId(handleUserId);
		zcWalletDetail.setWtype("WT01");
		zcWalletDetail.setChannel("CS04");
		zcWalletDetailService.addAndUpdateWallet(zcWalletDetail);

		// 推送消息
		sendWxMessage.sendRechargeTemplateMessage(zcWalletDetail);

		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ZcWalletDetail查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		ZcWalletDetail zcWalletDetail = zcWalletDetailService.get(id);
		zcWalletDetail.setUserName(userService.getByZc(zcWalletDetail.getUserId()).getNickname());
		if(!F.empty(zcWalletDetail.getHandleUserId()))
			zcWalletDetail.setHandleUserName(userService.getByZc(zcWalletDetail.getHandleUserId()).getNickname());
		request.setAttribute("zcWalletDetail", zcWalletDetail);
		return "/zcwalletdetail/recharge/zcWalletDetailView";
	}

	@RequestMapping("/viewByCash")
	public String viewByCash(HttpServletRequest request, String id) {
		ZcWalletDetail zcWalletDetail = zcWalletDetailService.get(id);
		zcWalletDetail.setUserName(userService.getByZc(zcWalletDetail.getUserId()).getNickname());
		if(!F.empty(zcWalletDetail.getHandleUserId()))
			zcWalletDetail.setHandleUserName(userService.getByZc(zcWalletDetail.getHandleUserId()).getNickname());
		request.setAttribute("zcWalletDetail", zcWalletDetail);
		return "/zcwalletdetail/cash/zcWalletDetailView";
	}

	/**
	 * 跳转到ZcWalletDetail修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		ZcWalletDetail zcWalletDetail = zcWalletDetailService.get(id);
		request.setAttribute("zcWalletDetail", zcWalletDetail);
		return "/zcwalletdetail/cash/zcWalletDetailEdit";
	}

	/**
	 * 修改ZcWalletDetail
	 * 
	 * @param zcWalletDetail
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ZcWalletDetail zcWalletDetail, String checkPwd, HttpServletRequest request) {
		Json j = new Json();

		// 获取提现充值密码
		String privateKey = (String)request.getSession().getAttribute(RSAUtil.PRIVATE_KEY);
		if(F.empty(privateKey)) {
			j.setMsg("操作失败，请刷新或关闭当前浏览器重新打开！");
			return j;
		}
		checkPwd = RSAUtil.decryptByPravite(checkPwd, privateKey);

		User admin = userService.get(Constants.MANAGERADMIN);
		if(F.empty(checkPwd) || !checkPwd.equals(admin.getHxPassword())) {
			j.fail();
			j.setMsg("校验密码错误");
			return j;
		}

		String userId = ((SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName())).getId();
		zcWalletDetail.setHandleUserId(userId);
		zcWalletDetail.setHandleTime(new Date());
		zcWalletDetailService.edit(zcWalletDetail);

		j.setSuccess(true);
		try {
			String ip = IpUtil.getIp(request);
			User user = userService.getByZc(zcWalletDetail.getUserId());
			// 处理成功 调用微信企业付款接口
			if ("HS03".equals(zcWalletDetail.getHandleStatus()) && "CS01".equals(zcWalletDetail.getChannel())) {
				ZcWallet q = new ZcWallet();
				q.setUserId(user.getId());
				ZcWallet wallet = zcWalletService.get(q);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("amount", zcWalletDetail.getAmount());
				params.put("openid", user.getName());
				params.put("partner_trade_no", zcWalletDetail.getOrderNo());
				params.put("re_user_name", wallet.getRealName());
				params.put("spbill_create_ip", ip);
				String requestXml = PayCommonUtil.requestTransfersXML(params);
				System.out.println("~~~~~~~~~~~~微信企业付款接口请求参数requestXml:" + requestXml);
				String result = HttpUtil.httpsRequestSSL(WeixinUtil.TRANSFERS_URL, requestXml);
				System.out.println("~~~~~~~~~~~~微信企业付款接口返回结果result:" + result);
				Map<String, String> resultMap = XMLUtil.doXMLParse(result);
				if (F.empty(resultMap.get("result_code")) || !resultMap.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
					zcWalletDetail.setHandleStatus("HS01");
					zcWalletDetail.setHandleRemark("微信提现失败--" + resultMap.get("err_code_des"));
					zcWalletDetailService.edit(zcWalletDetail);
				} else {
					// 提现成功通知
					sendWxMessage.sendCashTemplateMessage(zcWalletDetail.getId(), true);
				}

			} else if ("HS03".equals(zcWalletDetail.getHandleStatus()) && "CS03".equals(zcWalletDetail.getChannel())) {
				// 提现成功通知
				sendWxMessage.sendCashTemplateMessage(zcWalletDetail.getId(), true);
			} else if ("HS04".equals(zcWalletDetail.getHandleStatus())) {
				// 新增钱包收支明细
				ZcWalletDetail detail = new ZcWalletDetail();
				detail.setUserId(zcWalletDetail.getUserId());
				detail.setOrderNo(Util.CreateWalletNo());
				detail.setAmount(zcWalletDetail.getAmount());
				detail.setWtype("WT10"); // 提现退回
				detail.setChannel("CS02");
				detail.setDescription("提现失败，余额退回");
				zcWalletDetailService.addAndUpdateWallet(detail);

				// 提现失败通知
				sendWxMessage.sendCashTemplateMessage(zcWalletDetail.getId(), false);
			}

			j.setMsg("提现成功！");
		} catch (Exception e) {
			zcWalletDetail.setHandleStatus("HS01");
			zcWalletDetail.setHandleRemark("提现失败--接口异常");
			zcWalletDetailService.edit(zcWalletDetail);
		}
		return j;
	}

	/**
	 * 删除ZcWalletDetail
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		zcWalletDetailService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}


}
