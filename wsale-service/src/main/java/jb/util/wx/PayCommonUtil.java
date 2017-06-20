package jb.util.wx;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import jb.listener.Application;
import jb.pageModel.ZcPayOrder;
import jb.pageModel.ZcWalletDetail;
import jb.util.DateUtil;
import jb.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class PayCommonUtil {
	private static Logger log = LoggerFactory.getLogger(PayCommonUtil.class);

	/**
	 * @author 李欣桦
	 * @date 2014-12-5下午2:29:34
	 * @Description：sign签名
	 * @param characterEncoding 编码格式
	 * @param parameters 请求参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + Application.getString(WeixinUtil.API_KEY));
//		sb.append("key=" + "58sf8pYRxqPIsX2B2gdFQy4ojFlPV5cA");
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
	    
	    return sign;
	}
	/**
	 * @author 李欣桦
	 * @date 2014-12-5下午2:32:05
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters  请求参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getRequestXml(SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * @author 李欣桦
	 * @date 2014-12-3上午10:17:43
	 * @Description：返回给微信的参数
	 * @param return_code 返回编码
	 * @param return_msg  返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	/**
	 * 微信退款请求参数
	 * @return
	 */
	public static String requestTransfersXML(Map<String, Object> params) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 金额  必填（单位为分必须为整数）
			parameters.put("amount", (long)((double)params.get("amount")*100) + "");
			// 校验用户姓名选项 NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名 OPTION_CHECK：针对已实名认证的用户才校验真实姓名
			parameters.put("check_name", "OPTION_CHECK");
			// 企业付款描述信息
			parameters.put("desc", "集东集西提现");
			// 公众账号ID 必填
			parameters.put("mch_appid", Application.getString(WeixinUtil.APPID));
			// 商户号 必填
			parameters.put("mchid", Application.getString(WeixinUtil.MCH_ID));
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
			// 用户openid，此参数必传
			parameters.put("openid", params.get("openid").toString());
			// 商户订单号  必填
			parameters.put("partner_trade_no", params.get("partner_trade_no").toString());
			// 收款用户姓名
			parameters.put("re_user_name", params.get("re_user_name").toString());
			// 调用接口的机器Ip地址
			parameters.put("spbill_create_ip", params.get("spbill_create_ip").toString());

			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 微信退款请求参数
	 * @return
	 */
	public static String requestRefundXML(Map<String, Object> params) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 公众账号ID 必填
			parameters.put("appid", Application.getString(WeixinUtil.APPID));
			// 商户号 必填
			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
			// 操作员  必填
			parameters.put("op_user_id", Application.getString(WeixinUtil.MCH_ID));
			// 商户退款单号  必填
			parameters.put("out_refund_no", params.get("refund_no").toString());
			// 商户订单号  必填
			parameters.put("out_trade_no", params.get("trade_no").toString());
			// 退款金额  必填（单位为分必须为整数）
			parameters.put("refund_fee", params.get("refund_fee") + "");
			// 总金额  必填（单位为分必须为整数）
			parameters.put("total_fee", params.get("total_fee") + "");

			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
