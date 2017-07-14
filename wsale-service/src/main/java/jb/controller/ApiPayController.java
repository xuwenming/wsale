package jb.controller;

import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcPayOrderServiceI;
import jb.service.ZcWalletDetailServiceI;
import jb.service.ZcWalletServiceI;
import jb.service.impl.CompletionFactory;
import jb.util.EnumConstants;
import jb.util.IpUtil;
import jb.util.Util;
import jb.util.wx.HttpUtil;
import jb.util.wx.PayCommonUtil;
import jb.util.wx.WeixinUtil;
import jb.util.wx.XMLUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 基础数据
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/pay")
public class ApiPayController extends BaseController {

	private static final Logger log = Logger.getLogger(ApiPayController.class);

	@Autowired
	private ZcPayOrderServiceI zcPayOrderService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private ZcWalletDetailServiceI zcWalletDetailService;

	/**
	 * 跳转统一支付页
	 * @return
	 */
	@RequestMapping("/toPay")
	public String toPay(ZcPayOrder payOrder, String userId, Boolean isIntermediary, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("payOrder", payOrder);
		// 附加参数类型
		if(!F.empty(payOrder.getAttachType())) request.setAttribute("attachType", payOrder.getAttachType());

		// 可用余额
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		if(wallet == null) {
			wallet = new ZcWallet();
			wallet.setAmount(0.0);
		}
		request.setAttribute("wallet", wallet);

		double serviceFee = 0;
		if("PO05".equals(payOrder.getObjectType()) && !F.empty(userId) && isIntermediary != null) { // 拍品订单支付
			int serviceFeePer = 0;
			if(isIntermediary) { // 中介百分比读取数据字典
				try {
					serviceFeePer = Integer.valueOf(Application.getString("AF10"));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				User user = userService.getByZc(userId);
				serviceFeePer = user.getServiceFeePer();
				if(serviceFeePer == 0) {
					try {
						serviceFeePer = Integer.valueOf(Application.getString("AF20"));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}

			if(serviceFeePer > 0) {
				serviceFee = (new BigDecimal(payOrder.getTotalFee()).multiply(new BigDecimal(serviceFeePer).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP))).doubleValue();
				if(serviceFee < 5) serviceFee = 5; // 最低收取5元
			}
		}
		request.setAttribute("serviceFee", serviceFee);

		return "/wsale/pay/pay";
	}

	/**
	 * 跳转充值付款页
	 * @return
	 */
	@RequestMapping("/toRecharge")
	public String toRecharge(ZcPayOrder payOrder,String backUrl, HttpServletRequest request) {
		request.setAttribute("payOrder", payOrder);
		request.setAttribute("backUrl", backUrl);
		return "/wsale/pay/recharge_pay";
	}

	/**
	 * 跳转提现页
	 * @return
	 */
	@RequestMapping("/toCash")
	public String toCash(double amount, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("amount", amount);

		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		request.setAttribute("wallet", wallet);

		// 获取最近一次银行卡提现记录
		ZcWalletDetail walletDetail = new ZcWalletDetail();
		walletDetail.setUserId(s.getId());
		walletDetail.setWtype("WT02"); // 提现
		walletDetail.setChannel("CS03"); // 银行卡
		walletDetail = zcWalletDetailService.get(walletDetail);
		request.setAttribute("walletDetail", walletDetail == null ? new ZcWalletDetail() : walletDetail);

		return "/wsale/pay/cash_pay";
	}

	/**
	 * 微信支付
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/pay")
	@ResponseBody
	public Json pay(ZcPayOrder payOrder, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);

			ZcPayOrder exist = null;
			if(!"PO06".equals(payOrder.getObjectType())) {
				ZcPayOrder q = new ZcPayOrder();
				q.setObjectId(payOrder.getObjectId());
				q.setObjectType(payOrder.getObjectType());
				q.setUserId(s.getId());
				exist = zcPayOrderService.get(q);
			}
			if(exist != null) {
				if("PS02".equals(exist.getPayStatus())) {
					j.setMsg("已支付或审核中请耐心等待！");
					j.fail();
					return j;
				}
				payOrder.setId(exist.getId());
				zcPayOrderService.edit(payOrder);
				payOrder.setOrderNo(exist.getOrderNo());
			} else {
				payOrder.setUserId(s.getId());
				payOrder.setOrderNo(Util.CreatePayOrderNo());
				payOrder.setPayStatus("PS01");
				zcPayOrderService.add(payOrder);
			}

			User user = userService.getByZc(s.getId());
			String requestXml = requestXML(payOrder, user.getName(), request);
			System.out.println("~~~~~~~~~~~~微信支付接口请求参数requestXml:" + requestXml);
			String result = HttpUtil.httpsRequest(WeixinUtil.UNIFIED_ORDER_URL, "POST", requestXml);
			System.out.println("~~~~~~~~~~~~微信支付接口返回结果result:" + result);
			Map<String, String> resultMap = XMLUtil.doXMLParse(result);

			j.setObj(returnJsonStr(resultMap, request));
			j.success();
		}catch(Exception e){
			j.setMsg("支付异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 钱包支付
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/walletPay")
	@ResponseBody
	public Json walletPay(ZcPayOrder payOrder, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			final String userId = s.getId();

			ZcPayOrder exist = null;
			if(!"PO06".equals(payOrder.getObjectType())) {
				ZcPayOrder q = new ZcPayOrder();
				q.setObjectId(payOrder.getObjectId());
				q.setObjectType(payOrder.getObjectType());
				q.setUserId(userId);
				exist = zcPayOrderService.get(q);
			}
			if(exist != null) {
				if("PS02".equals(exist.getPayStatus())) {
					j.setMsg("已支付或审核中请耐心等待！");
					j.fail();
					return j;
				}

				payOrder.setOrderNo(exist.getOrderNo());
			} else {
				payOrder.setUserId(userId);
				payOrder.setOrderNo(Util.CreatePayOrderNo());
				payOrder.setPayStatus("PS01");
				zcPayOrderService.add(payOrder);
			}

			CompletionService completionService = CompletionFactory.initCompletion();
			completionService.submit(new Task<ZcPayOrder, Boolean>(payOrder) {
				@Override
				public Boolean call() throws Exception {
					getD().setPayStatus("PS02");
					zcPayOrderService.editByParam(getD());
					return true;
				}
			});

			// 更新付款人钱包余额
			completionService.submit(new Task<ZcPayOrder, Boolean>(payOrder) {
				@Override
				public Boolean call() throws Exception {
//					zcPayOrderService.updateWallet(userId, -getD().getTotalFee());
					// 新增钱包收支明细
					ZcWalletDetail walletDetail = new ZcWalletDetail();
					walletDetail.setUserId(userId);
					walletDetail.setOrderNo(getD().getOrderNo());
					walletDetail.setAmount(getD().getTotalFee());
					walletDetail.setChannel(getD().getChannel());
					if("PO01".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT03"); // 在线支付
						walletDetail.setDescription("申请职务");
					} else if("PO02".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT03"); // 在线支付
						walletDetail.setDescription("实名认证");
					} else if("PO03".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT03"); // 在线支付
						walletDetail.setDescription("申请首页精拍");
					} else if("PO04".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT06"); // 打赏支出
						if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(getD().getAttachType())) {
							walletDetail.setDescription("帖子打赏");
						} else if(EnumConstants.OBJECT_TYPE.TOPIC.getCode().equals(getD().getAttachType())) {
							walletDetail.setDescription("专题打赏");
						} else {
							walletDetail.setDescription("打赏");
						}

					} else if("PO05".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT03"); // 在线支付
						String desc = "拍品订单";
						if(getD().getServiceFee() > 0) {
							double serviceFee = ((double)getD().getServiceFee())/100;
							desc += "(含"+serviceFee+"元技术服务费)";
						}
						walletDetail.setDescription(desc);
					} else if("PO07".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT03"); // 在线支付
						walletDetail.setDescription("消保金缴纳");
					} else if("PO08".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT03"); // 在线支付
						walletDetail.setDescription("保证金");
					} else if("PO09".equals(getD().getObjectType())) {
						walletDetail.setWtype("WT03"); // 在线支付
						walletDetail.setDescription("申请分类精拍");
					}
					zcWalletDetailService.addAndUpdateWallet(walletDetail);
					return true;
				}
			});

			j.success();
		}catch(Exception e){
			j.setMsg("支付异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 退款
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/refund")
	@ResponseBody
	public Json refund(ZcPayOrder payOrder, HttpServletRequest request) {
		Json j = new Json();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount", payOrder.getTotalFee());
			params.put("orderNo", payOrder.getOrderNo());
			String requestXml = PayCommonUtil.requestRefundXML(params);
			System.out.println("~~~~~~~~~~~~微信支付接口请求参数requestXml:" + requestXml);
			String result = HttpUtil.httpsRequest(WeixinUtil.REFUND_URL, "POST", requestXml);
			System.out.println("~~~~~~~~~~~~微信支付接口返回结果result:" + result);
			Map<String, String> resultMap = XMLUtil.doXMLParse(result);

			j.setObj(resultMap);
			j.success();
		}catch(Exception e){
			j.setMsg("支付异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 企业向个人付款(提现)
	 * http://localhost:8080/api/pay/transfers?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @param
	 * @return
	 */
	@RequestMapping("/transfers")
	@ResponseBody
	public Json transfers(ZcPayOrder payOrder, String bankAccount, String bankPhone,
  			String bankIdNo, String bankCard, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcWallet q = new ZcWallet();
			q.setUserId(s.getId());
			ZcWallet wallet = zcWalletService.get(q);
			if(wallet == null || wallet.getAmount() < payOrder.getTotalFee()) {
				j.fail();
				j.setMsg("提现失败，余额不足！");
				return j;
			}
//			zcPayOrderService.updateWallet(s.getId(), -payOrder.getTotalFee());
			// 新增钱包收支明细
			ZcWalletDetail walletDetail = new ZcWalletDetail();
			walletDetail.setUserId(s.getId());
			walletDetail.setOrderNo(Util.CreateTransfersNo());
			walletDetail.setAmount(payOrder.getTotalFee());
			walletDetail.setChannel(payOrder.getChannel());

			if(!F.empty(bankAccount)) walletDetail.setBankAccount(bankAccount);
			if(!F.empty(bankPhone)) walletDetail.setBankPhone(bankPhone);
			if(!F.empty(bankIdNo)) walletDetail.setBankIdNo(bankIdNo);
			if(!F.empty(bankCard)) walletDetail.setBankCard(bankCard);

			walletDetail.setWtype("WT02"); // 提现
			walletDetail.setDescription("余额提现");
			walletDetail.setHandleStatus("HS01");
			zcWalletDetailService.addAndUpdateWallet(walletDetail);

			j.success();
			j.setMsg("提现成功！");
		}catch(Exception e){
			j.setMsg("操作异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/paySuccess")
	public synchronized void paySuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
		outSteam.close();
		inStream.close();
		String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
		Map<Object, Object> map = XMLUtil.doXMLParse(result);
		for(Object keyValue : map.keySet()){
			System.out.println(keyValue+"="+map.get(keyValue));
		}

		// 对数据库的操作
		String orderNo = map.get("out_trade_no").toString();
		String transaction_id = map.get("transaction_id").toString(); // 微信支付订单号
		String attach = map.get("attach").toString(); // 微信支付订单号
		ZcPayOrder payOrder = new ZcPayOrder();
		payOrder.setOrderNo(orderNo);
		payOrder.setRefTransactionNo(transaction_id);
		payOrder.setAttachType(attach);
		if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
			payOrder.setPayStatus("PS02");
		} else {
			payOrder.setPayStatus("PS03");
		}

		zcPayOrderService.editByParam(payOrder);
		response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了
		System.out.println("-------------"+PayCommonUtil.setXML("SUCCESS", ""));
	}

	/**
	 * 微信支付请求参数
	 * @return
	 */
	private String requestXML(ZcPayOrder payOrder, String openid, HttpServletRequest request) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 公众账号ID 必填
			parameters.put("appid", Application.getString(WeixinUtil.APPID));
			// 附加数据 不是必填
			parameters.put("attach", F.empty(payOrder.getAttachType()) ? payOrder.getOrderNo() : payOrder.getAttachType());
			// 商品描述  必填
			String body = "";
			if("PO01".equals(payOrder.getObjectType())) {
				body = "职位申请费 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			} else if("PO02".equals(payOrder.getObjectType())) {
				body = "实名认证费 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			} else if("PO03".equals(payOrder.getObjectType())){
				body = "申请首页精拍费 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			} else if("PO04".equals(payOrder.getObjectType())) {
				if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(payOrder.getAttachType())) {
					body += "帖子打赏 - ";
				} else if(EnumConstants.OBJECT_TYPE.TOPIC.getCode().equals(payOrder.getAttachType())) {
					body += "专题打赏 - ";
				} else {
					body += "打赏 - ";
				}
				body += new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			} else if("PO05".equals(payOrder.getObjectType())) {
				body = "支付拍品订单 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
				if(payOrder.getServiceFee() > 0) {
					double serviceFee = ((double)payOrder.getServiceFee())/100;
					body += "(含"+serviceFee+"元技术服务费)";
				}
			} else if("PO06".equals(payOrder.getObjectType())) {
				body = "余额充值 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			} else if("PO07".equals(payOrder.getObjectType())) {
				body = "消保金缴纳 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			} else if("PO08".equals(payOrder.getObjectType())) {
				body = "保证金 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			} else if("PO09".equals(payOrder.getObjectType())) {
				body = "申请分类精拍费 - " + new DecimalFormat("#,###0.00").format(payOrder.getTotalFee()) + "元";
			}
			parameters.put("body", body); // Application.getString(WeixinUtil.BODY)
			// 商户号 必填
			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
			// 通知地址  必填
			parameters.put("notify_url", Application.getString(WeixinUtil.NOTIFY_URL));
			// 用户标识  trade_type=JSAPI，此参数必传
			parameters.put("openid", openid);
			// 商户订单号  必填
			parameters.put("out_trade_no", payOrder.getOrderNo());
			// 终端IP  必填
			parameters.put("spbill_create_ip", IpUtil.getIp(request));
			// 总金额  必填（单位为分必须为整数）
			parameters.put("total_fee", (long)(payOrder.getTotalFee()*100) + "");
			// 交易类型  必填
			parameters.put("trade_type", "JSAPI");

			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private SortedMap<Object,Object> returnJsonStr(Map<String, String> resultMap, HttpServletRequest request) {
		Date nowDate = new Date();
		SortedMap<Object,Object> params = new TreeMap<Object,Object>();
		// 公众账号ID
		params.put("appId", resultMap.get("appid"));
		// 时间戳
		params.put("timeStamp", Long.toString(nowDate.getTime()));
		// 随机串
		params.put("nonceStr", WeixinUtil.CreateNoncestr());
		// 商品包信息
		params.put("package", "prepay_id=" + resultMap.get("prepay_id"));
		// 微信签名方式
		params.put("signType", WeixinUtil.SIGN_TYPE);
		// 微信签名
		String paySign = PayCommonUtil.createSign("UTF-8", params);
		// paySign的生成规则和Sign的生成规则一致
		params.put("paySign", paySign);
		// 这里用packageValue是预防package是关键字在js获取值出错
		params.put("packageValue", "prepay_id=" + resultMap.get("prepay_id"));

		// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
		String userAgent = request.getHeader("user-agent");
		char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
		params.put("agent", new String(new char[]{agent}));

		return params;
	}

}
