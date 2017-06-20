package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcAuthenticationServiceI;
import jb.service.ZcReportServiceI;
import jb.service.ZcShopServiceI;
import jb.util.wx.DownloadMediaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础数据
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiAuth")
public class ApiAuthController extends BaseController {

	@Autowired
	private ZcAuthenticationServiceI zcAuthenticationService;

	@Autowired
	private ZcShopServiceI zcShopService;

	@Autowired
	private UserServiceI userService;

	/**
	 * 认证申请
	 * @return
	 */
	@RequestMapping("/authApply")
	public String authApply(HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("user", userService.getByZc(s.getId()));
		return "/wsale/shop/auth_apply";
	}

	/**
	 * 认证申请-认证流程介绍页
	 * @return
	 */
	@RequestMapping("/toAuth")
	public String toAuth(String authType) {
		if("AT01".equals(authType)) {
			return "/wsale/shop/personal_auth";
		}
		return "/wsale/shop/company_auth";
	}

	/**
	 * 认证申请-我要认证
	 * @return
	 */
	@RequestMapping("/auth")
	public String auth(String authType, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcAuthentication q = new ZcAuthentication();
		q.setAddUserId(s.getId());
		q.setAuthType(authType);
		ZcAuthentication auth = zcAuthenticationService.get(q);
		if(auth == null) auth = new ZcAuthentication();

		String returnPath = "/wsale/shop/auth_company_info";
		if("AT01".equals(authType)) {
			if(auth.getAuthStep() == null) {
				q.setAuthType("AT02");
				ZcAuthentication cAuth = zcAuthenticationService.get(q);
				if(cAuth != null && cAuth.getAuthStep() >= 2) {
					auth.setUserName(cAuth.getUserName());
					auth.setIdNo(cAuth.getIdNo());
					auth.setPhone(cAuth.getPhone());
					auth.setIdFront(cAuth.getIdFront());
					auth.setIdBack(cAuth.getIdBack());
					auth.setIdFrontByhand(cAuth.getIdFrontByhand());
				}
			}
			returnPath = "/wsale/shop/auth_personal_info";
		}

		request.setAttribute("auth", auth);
		request.setAttribute("authType", authType);
		return returnPath;
	}

	/**
	 * 认证申请-跳转至个人信息
	 * @return
	 */
	@RequestMapping("/toPersonalInfo")
	public String toPersonalInfo(String authId, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcAuthentication auth = zcAuthenticationService.get(authId);
		if(auth.getAuthStep() < 2) {
			ZcAuthentication q = new ZcAuthentication();
			q.setAddUserId(s.getId());
			q.setAuthType("AT01");
			ZcAuthentication pAuth = zcAuthenticationService.get(q);
			if(pAuth != null) {
				auth.setUserName(pAuth.getUserName());
				auth.setIdNo(pAuth.getIdNo());
				auth.setPhone(pAuth.getPhone());
				auth.setIdFront(pAuth.getIdFront());
				auth.setIdBack(pAuth.getIdBack());
				auth.setIdFrontByhand(pAuth.getIdFrontByhand());
			}
		}
		request.setAttribute("auth", auth);
		request.setAttribute("authType", auth.getAuthType());
		return "/wsale/shop/auth_personal_info";
	}

	/**
	 * 认证申请-跳转至店铺信息
	 * @return
	 */
	@RequestMapping("/toShopInfo")
	 public String toShopInfo(String authId, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcShop q = new ZcShop();
		q.setUserId(s.getId());
		ZcShop shop = zcShopService.get(q);
		if(shop == null) shop = new ZcShop();
		request.setAttribute("shop", shop);

		ZcAuthentication auth = zcAuthenticationService.get(authId);
		request.setAttribute("auth", auth);

		request.setAttribute("headImage", s.getHeadImage());
		return "/wsale/shop/auth_shop_info";
	}

	/**
	 * 认证申请-跳转至提交审核页
	 * @return
	 */
	@RequestMapping("/toSubmitAudit")
	public String toSubmitAudit(String authId, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		ZcAuthentication auth = zcAuthenticationService.get(authId);
		request.setAttribute("auth", auth);
		ZcShop shop = new ZcShop();
		shop.setUserId(s.getId());
		request.setAttribute("shop", zcShopService.get(shop));
		return "/wsale/shop/submit_audit";
	}
	
	/**
	 * 实名认证-保存个人信息
	 * 编辑时需要传ID
	 * http://localhost:8080/api/apiAuth/savePerson?tokenId=1D96DACB84F21890ED9F4928FA8B352B&authType=AT01&userName=小五&idType=IT01&idNo=123456789012345678&phone=18711111111&idFrontMediaId=idFrontMediaId&idBackMediaId=idBackMediaId&idFrontByhandMediaId=idFrontByhandMediaId
	 * @return
	 */
	@RequestMapping("/savePerson")
	@ResponseBody
	public Json savePerson(ZcAuthentication auth, String idFrontMediaId, String idBackMediaId, String idFrontByhandMediaId, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			String realPath = request.getSession().getServletContext().getRealPath("/");
			if(!F.empty(idFrontMediaId)) {
				// 证件正面
				auth.setIdFront(DownloadMediaUtil.downloadMedia(realPath, idFrontMediaId, "AUTH"));
			}
			if(!F.empty(idBackMediaId))  {
				// 证件反面
				auth.setIdBack(DownloadMediaUtil.downloadMedia(realPath, idBackMediaId, "AUTH"));
			}
			if(!F.empty(idFrontByhandMediaId)) {
				// 手持证件正面
				auth.setIdFrontByhand(DownloadMediaUtil.downloadMedia(realPath, idFrontByhandMediaId, "AUTH"));
			}
			auth.setAddUserId(s.getId());

			ZcAuthentication q = new ZcAuthentication();
			q.setAddUserId(s.getId());
			q.setAuthType(auth.getAuthType());
			ZcAuthentication exist = zcAuthenticationService.get(q);
			if(exist == null) {
				auth.setAuthStep(1);
				zcAuthenticationService.add(auth);
			} else {
				auth.setId(exist.getId());
				if("AT02".equals(auth.getAuthType()) && exist.getAuthStep() < 2) auth.setAuthStep(2);
				zcAuthenticationService.edit(auth);
			}

			j.setObj(auth);
			j.setMsg("操作成功");
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 实名认证-保存店铺信息
	 * http://localhost:8080/api/apiAuth/saveShop?tokenId=1D96DACB84F21890ED9F4928FA8B352B&authId=80157E151C414778940B50C42BD0F39B&authType=AT01&name=第一店铺&introduction=我是店铺介绍&logoUrlMediaId=logoUrlMediaId
	 * @return
	 */
	@RequestMapping("/saveShop")
	@ResponseBody
	public Json saveShop(ZcShop zcShop, String authId, String authType, String logoUrlMediaId, HttpServletRequest request) {
		Json j = new Json();
		try{
			// TODO 缺图片上传，从微信服务器上下载图片上传到本地服务器，不加水印
			SessionInfo s = getSessionInfo(request);
			zcShop.setUserId(s.getId());
			if(!F.empty(logoUrlMediaId))  {
				// 店铺LOGO
				String realPath = request.getSession().getServletContext().getRealPath("/");
				zcShop.setLogoUrl(DownloadMediaUtil.downloadMedia(realPath, logoUrlMediaId, "SHOP"));
			}

			ZcShop q = new ZcShop();
			q.setUserId(s.getId());
			if(zcShopService.get(q) == null) {
				zcShopService.add(zcShop);
			} else {
				zcShopService.edit(zcShop);
			}

			// 更新认证阶段
			ZcAuthentication auth = new ZcAuthentication();
			auth.setId(authId);
			auth.setAuthStep("AT01".equals(authType) ? 2 : 3);
			zcAuthenticationService.edit(auth);

			j.setMsg("操作成功");
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 实名认证-保存企业信息
	 * http://localhost:8080/api/apiAuth/saveCompany?tokenId=1D96DACB84F21890ED9F4928FA8B352B&companyName=莫辩&creditId=888888&legalPersonName=小五&legalPersonId=123456789012345678&legalPersonIdFrontMediaId=legalPersonIdFrontMediaId&legalPersonIdBackMediaId=legalPersonIdBackMediaId&bussinessLicenseMediaId=bussinessLicenseMediaId
	 * @return
	 */
	@RequestMapping("/saveCompany")
	@ResponseBody
	public Json saveCompany(ZcAuthentication auth, String legalPersonIdFrontMediaId, String legalPersonIdBackMediaId, String bussinessLicenseMediaId, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			auth.setAuthType("AT02");
			auth.setAddUserId(s.getId());

			String realPath = request.getSession().getServletContext().getRealPath("/");
			if(!F.empty(legalPersonIdFrontMediaId)) {
				// 法人身份证正面
				auth.setLegalPersonIdFront(DownloadMediaUtil.downloadMedia(realPath, legalPersonIdFrontMediaId, "AUTH"));
			}
			if(!F.empty(legalPersonIdBackMediaId))  {
				// 法人身份证反面
				auth.setLegalPersonIdBack(DownloadMediaUtil.downloadMedia(realPath, legalPersonIdBackMediaId, "AUTH"));
			}
			if(!F.empty(bussinessLicenseMediaId)) {
				// 营业执照
				auth.setBussinessLicense(DownloadMediaUtil.downloadMedia(realPath, bussinessLicenseMediaId, "AUTH"));
			}

			ZcAuthentication q = new ZcAuthentication();
			q.setAddUserId(s.getId());
			q.setAuthType(auth.getAuthType());
			ZcAuthentication exist = zcAuthenticationService.get(q);
			if(exist == null) {
				auth.setAuthStep(1);
				zcAuthenticationService.add(auth);
			} else {
				auth.setId(exist.getId());
				zcAuthenticationService.edit(auth);
			}

			j.setObj(auth.getId());
			j.setMsg("操作成功");
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

}
