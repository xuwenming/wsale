package jb.controller;

import com.alibaba.fastjson.JSONObject;
import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.RedisUserServiceImpl;
import jb.util.*;
import jb.util.MD5Util;
import jb.util.wx.*;
import net.sf.json.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 我的-钱包余额管理
 * Created by wenming on 2016/8/22.
 */
@Controller
@RequestMapping("/api/apiWallet")
public class ApiWalletController extends BaseController {

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private ZcWalletDetailServiceI zcWalletDetailService;

	@Autowired
	private ZcOrderServiceI zcOrderService;

	@Autowired
	private ZcProtectionServiceI zcProtectionService;

	@Autowired
	private ZcOfflineTransferServiceI zcOfflineTransferService;

	@javax.annotation.Resource
	private RedisUserServiceImpl redisUserService;

	@Autowired
	private BasedataServiceI basedataService;

	/**
	 * 跳转我的余额
	 * @return
	 */
	@RequestMapping("/myWallet")
	public String myWallet(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		if(wallet == null) {
			wallet = q;
			wallet.setAmount(0.0);
			wallet.setFrozenAmount(0.0);
			zcWalletService.add(wallet);
		}

		request.setAttribute("wallet", wallet);
		// 订单金额统计
		Map<String, Object> order_amount_count = zcOrderService.orderAmountCount(s.getId());
		request.setAttribute("order_amount_count", order_amount_count);

		return "/wsale/wallet/myWallet";
	}

	/**
	 * 获取钱包信息
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/getWallet")
	@ResponseBody
	public Json getWallet(HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			// 可用余额
			ZcWallet q = new ZcWallet();
			q.setUserId(s.getId());
			ZcWallet wallet = zcWalletService.get(q);
			if(wallet == null) {
				wallet = new ZcWallet();
				wallet.setAmount(0.0);
			}

			j.setObj(wallet);
			j.success();
		}catch(Exception e){
			j.setMsg("获取钱包接口异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 跳转支付
	 * @return
	 */
	@RequestMapping("/recharge")
	public String recharge() {
		return "/wsale/wallet/recharge";
	}

	/**
	 * 跳转提现
	 * @return
	 */
	@RequestMapping("/cash")
	public String cash(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		request.setAttribute("wallet", wallet);

		int cashNum = s.getIsAuth() ? 2 : 1;

		//if(!"DA810FE5750049698C09DD2761267A39".equals(s.getId())) {
			ZcWalletDetail walletDetail = new ZcWalletDetail();
			walletDetail.setUserId(s.getId());
			walletDetail.setWtype("WT02");
			walletDetail.setAddtime(new Date());
			List<ZcWalletDetail> walletDetails = zcWalletDetailService.query(walletDetail);
			if (CollectionUtils.isNotEmpty(walletDetails)) {
				cashNum -= walletDetails.size();
			}
		//}
		request.setAttribute("cashNum", cashNum < 0 ? 0 : cashNum);

		return "/wsale/wallet/cash";
	}

	/**
	 * 跳转余额明细清单列表
	 * @return
	 */
	@RequestMapping("/walletBillList")
	public String walletBillList(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		request.setAttribute("wallet", wallet);
		return "/wsale/wallet/wallet_bill_list";
	}

	/**
	 * 跳转余额明细
	 * @return
	 */
	@RequestMapping("/walletBillDetail")
	public String walletBillDetail(String id, HttpServletRequest request) {
		ZcWalletDetail detail = zcWalletDetailService.get(id);
		request.setAttribute("detail", detail);
		return "/wsale/wallet/wallet_bill_detail";
	}

	/**
	 * 跳转余额明细
	 * @return
	 */
	@RequestMapping("/offline_transfer_one")
	public String offline_transfer_one(double transferAmount, HttpServletRequest request) {
		request.setAttribute("transferAmount", transferAmount);
		BaseData baseData = new BaseData();
		baseData.setBasetypeCode("OT");
		List<BaseData> bds = basedataService.getBaseDatas(baseData);
		List<Map> banks = new ArrayList<Map>();
		if(CollectionUtils.isNotEmpty(bds)) {
			for(BaseData bd : bds) {
				String desc = bd.getDescription();
				Map m = new HashMap();
				if(!F.empty(desc)) m = JSONObject.parseObject(desc, Map.class);
				if(m.get("isdeleted") == null || (Integer)m.get("isdeleted") == 1) continue;
				m.put("bank_code", bd.getId());
				m.put("bank_icon", bd.getIcon());
				m.put("bank_name", bd.getName());
				banks.add(m);
			}
		}
		request.setAttribute("banks", banks);
		return "/wsale/wallet/offline_transfer_one";
	}

	/**
	 * 跳转余额明细
	 * @return
	 */
	@RequestMapping("/offline_transfer_two")
	public String offline_transfer_two(double transferAmount,String bankCode, HttpServletRequest request) {
		request.setAttribute("transferAmount", transferAmount);
		request.setAttribute("bankCode", bankCode);
		return "/wsale/wallet/offline_transfer_two";
	}

	/**
	 * 银行卡管理
	 * @return
	 */
	@RequestMapping("/bankManage")
	public String bankManage() {
		return "/wsale/wallet/bankManage";
	}

	/**
	 * 银行卡管理
	 * @return
	 */
	@RequestMapping("/toAddBank")
	public String toAddBank() {
		return "/wsale/wallet/bankAdd";
	}

	/**
	 * 支付安全
	 * @return
	 */
	@RequestMapping("/toSafetySet")
	public String toSafetySet(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		request.setAttribute("wallet", wallet);
		return "/wsale/wallet/safetySet";
	}

	/**
	 * 忘记支付密码
	 * @return
	 */
	@RequestMapping("/forgetPwd")
	public String forgetPwd(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		request.setAttribute("wallet", wallet);
		return "/wsale/wallet/forget_pwd";
	}

	/**
	 * 修改身份信息
	 * @return
	 */
	@RequestMapping("/toEditIdentity")
	public String toEditIdentity(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		if(wallet != null && !F.empty(wallet.getIdNo())) {
			String idNo = wallet.getIdNo().substring(0, 4) + "**********" + wallet.getIdNo().substring(wallet.getIdNo().length() - 2, wallet.getIdNo().length());
			wallet.setIdNo(idNo);
		}
		request.setAttribute("wallet", wallet);
		return "/wsale/wallet/identityEdit";
	}

	/**
	 * 我的消保金
	 * @return
	 */
	@RequestMapping("/myProtection")
	public String myProtection(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		if(wallet == null) {
			wallet = q;
			wallet.setAmount(0.0);
			wallet.setFrozenAmount(0.0);
			wallet.setProtection(0.0);
			zcWalletService.add(wallet);
		}
		request.setAttribute("protection", wallet.getProtection());
		return "/wsale/wallet/myProtection";
	}

	/**
	 * 缴纳消保金
	 * @return
	 */
	@RequestMapping("/payProtection")
	public String payProtection(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcWallet q = new ZcWallet();
		q.setUserId(s.getId());
		ZcWallet wallet = zcWalletService.get(q);
		request.setAttribute("protection", wallet.getProtection());
		return "/wsale/wallet/payProtection";
	}

	/**
	 * 余额明细清单列表分页查询
	 * @return
	 */
	@RequestMapping("/bills")
	@ResponseBody
	public Json bills(PageHelper ph, ZcWalletDetail walletDetail, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("addtime");
			ph.setOrder("desc");
			walletDetail.setUserId(s.getId());

			j.setObj(zcWalletDetailService.dataGrid(walletDetail, ph));
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 新增钱包收支明细
	 * @param
	 * @return
	 */
	@RequestMapping("/editWallet")
	@ResponseBody
	public Json editWallet(ZcWallet wallet, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(!F.empty(wallet.getPayPassword())) {
				String privateKey = redisUserService.getRSAPrivateKey(s.getId());
				String payPassword = RSAUtil.decryptByPravite(wallet.getPayPassword(), privateKey);
				wallet.setPayPassword(MD5Util.md5(payPassword));
			}
			if(F.empty(wallet.getId())) {
				wallet.setUserId(s.getId());
				wallet.setAmount(0.0);
				wallet.setFrozenAmount(0.0);
				zcWalletService.add(wallet);
			} else {
				zcWalletService.edit(wallet);
			}

			j.success();
		}catch(Exception e){
			j.setMsg("操作异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 新增钱包收支明细
	 * @param
	 * @return
	 */
//	@RequestMapping("/addWalletDetail")
//	@ResponseBody
//	public Json addWalletDetail(ZcWalletDetail walletDetail, HttpServletRequest request) {
//		Json j = new Json();
//		try{
//			SessionInfo s = getSessionInfo(request);
//			walletDetail.setUserId(s.getId());
//			walletDetail.setOrderNo(Util.CreateWalletNo());
//			zcWalletDetailService.add(walletDetail);
//			j.setObj(walletDetail.getId());
//			j.success();
//		}catch(Exception e){
//			j.setMsg("操作异常！");
//			j.fail();
//			e.printStackTrace();
//		}
//		return j;
//	}

	/**
	 * 删除钱包收支明细（未支付删除记录）
	 * @param
	 * @return
	 */
//	@RequestMapping("/delWalletDetail")
//	@ResponseBody
//	public Json delWalletDetail(String id) {
//		Json j = new Json();
//		try{
//			zcWalletDetailService.delete(id);
//			j.success();
//		}catch(Exception e){
//			j.setMsg("操作异常！");
//			j.fail();
//			e.printStackTrace();
//		}
//		return j;
//	}

	/**
	 * 新增消保金流水记录
	 * @param
	 * @return
	 */
	@RequestMapping("/addProtection")
	@ResponseBody
	public Json addProtection(ZcProtection protection, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			protection.setUserId(s.getId());
			protection.setPayStatus("PS01");
			zcProtectionService.add(protection);
			j.setObj(protection.getId());
			j.success();
		}catch(Exception e){
			j.setMsg("操作异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 消保金流水记录分页查询
	 * @return
	 */
	@RequestMapping("/protections")
	@ResponseBody
	public Json protections(PageHelper ph, ZcProtection protection, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ph.setSort("addtime");
			ph.setOrder("desc");
			protection.setUserId(s.getId());
			protection.setPayStatus("PS02");

			j.setObj(zcProtectionService.dataGrid(protection, ph));
			j.success();
			j.setMsg("操作成功");

		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 新增线下转账记录
	 * @param
	 * @return
	 */
	@RequestMapping("/addOfflineTransfer")
	@ResponseBody
	public Json addOfflineTransfer(ZcOfflineTransfer zcOfflineTransfer, String transferTimeStr, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			if(!F.empty(transferTimeStr)) {
				zcOfflineTransfer.setTransferTime(DateUtil.parse(transferTimeStr, Constants.DATE_FORMAT_YMDHM));
			}
			zcOfflineTransfer.setUserId(s.getId());
			zcOfflineTransfer.setTransferNo(Util.CreateHKNo());
			zcOfflineTransferService.add(zcOfflineTransfer);
			j.success();
		}catch(Exception e){
			j.setMsg("操作异常！");
			j.fail();
			e.printStackTrace();
		}
		return j;
	}
}
