package jb.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.model.TopicMessage;
import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.ResourceServiceI;
import jb.service.RoleServiceI;
import jb.service.UserServiceI;
import jb.service.ZcWalletServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.RedisUserServiceImpl;
import jb.service.impl.SendWxMessageImpl;
import jb.util.CacheOperation;
import jb.util.ConfigUtil;
import jb.util.RSAUtil;
import jb.util.Util;
import jb.util.mns.MNSTemplate;
import jb.util.mns.MNSUtil;
import jb.util.wx.WeixinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 用户控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {

	@Autowired
	private UserServiceI userService;

	@Autowired
	private RoleServiceI roleService;

	@Autowired
	private ResourceServiceI resourceService;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private RedisUserServiceImpl redisUserService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户对象
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Json login(User user, HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		String privateKey = (String)session.getAttribute(RSAUtil.PRIVATE_KEY);
		user.setName(RSAUtil.decryptByPravite(user.getName(), privateKey));
		user.setPwd(RSAUtil.decryptByPravite(user.getPwd(), privateKey));
		User u = userService.login(user);
		if (u != null) {
			j.setSuccess(true);
			j.setMsg("登陆成功！");

			SessionInfo sessionInfo = new SessionInfo();
			BeanUtils.copyProperties(u, sessionInfo);
//			sessionInfo.setIp(IpUtil.getIpAddr(request));
			sessionInfo.setResourceList(userService.resourceList(u.getId()));
			sessionInfo.setRoleIds(userService.get(u.getId()).getRoleIds());
			session.setMaxInactiveInterval(30*60);
			session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);

			j.setObj(sessionInfo);
		} else {
			j.setMsg("用户名或密码错误！");
		}
		return j;
	}

	/**
	 * 获取RSA公钥接口
	 */
	@RequestMapping("/getPublicKey")
	@ResponseBody
	public Json getPublicKey(HttpSession session) {
		Json j = new Json();
		try {
			Map<String,String> keyMap = RSAUtil.generateKeyPair();
			String publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
			session.setAttribute(RSAUtil.PRIVATE_KEY, keyMap.get(RSAUtil.PRIVATE_KEY));
			j.setSuccess(true);
			j.setMsg("获取RSA公钥接口成功！");
			j.setObj(publicKey);
		} catch (Exception e) {
			j.setMsg("获取RSA公钥接口失败！");
		}

		return j;
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reg")
	public Json reg(User user) {
		Json j = new Json();
		try {
			userService.reg(user);
			j.setSuccess(true);
			j.setMsg("注册成功！新注册的用户没有任何权限，请让管理员赋予权限后再使用本系统！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public Json logout(HttpSession session) {
		Json j = new Json();
		if (session != null) {
			session.invalidate();
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/user";
	}

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(User user, PageHelper ph) {
		DataGrid dataGrid = userService.dataGrid(user, ph);
		List<User> list = (List<User>) dataGrid.getRows();
		if(!CollectionUtils.isEmpty(list)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for (User u : list) {
				if("UT02".equals(u.getUtype()))
					// 余额, 消保金
					completionService.submit(new Task<User, ZcWallet>(u) {
						@Override
						public ZcWallet call() throws Exception {
							ZcWallet q = new ZcWallet();
							q.setUserId(getD().getId());
							ZcWallet wallet = zcWalletService.get(q);
							return wallet;
						}

						protected void set(User d, ZcWallet v) {
							if (v != null) {
								d.setWalletAmount(v.getAmount());
								d.setProtection(v.getProtection());
							}

						}
					});

			}
			completionService.sync();
		}
		return dataGrid;
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		User u = new User();
		u.setId(UUID.randomUUID().toString());
		request.setAttribute("user", u);
		return "/admin/userAdd";
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(User user) {
		Json j = new Json();
		try {
			userService.add(user);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 跳转到用户修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEdit";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(User user) {
		Json j = new Json();
		try {
			userService.edit(user);

			if(user.getIsGag() != null) {
				if(user.getIsGag()) {
					sendWxMessage.sendCustomMessage(user.getName(), "您被管理员禁言，申诉请移步“站务公告”申诉专版。");
				} else {
					sendWxMessage.sendCustomMessage(user.getName(), "您的禁言已取消，现在就可以去发言了，发言请遵守论坛制度。");
				}
			}
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		User user = userService.get(id, null);
		ZcWallet q = new ZcWallet();
		q.setUserId(id);
		ZcWallet wallet = zcWalletService.get(q);
		if(wallet != null) {
			user.setWalletAmount(wallet.getAmount());
			user.setProtection(wallet.getProtection());
		}
		request.setAttribute("user", user);
		return "/admin/userView";
	}

	@RequestMapping("/viewWallet")
	public String viewWallet(HttpServletRequest request, String id) {
		request.setAttribute("userId", id);
		return "/zcwallet/userWalletView";
	}

	@RequestMapping("/viewProtection")
	public String viewProtection(HttpServletRequest request, String id) {
		request.setAttribute("userId", id);
		return "/zcprotection/userProtectionView";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		if (id != null && !id.equalsIgnoreCase(sessionInfo.getId())) {// 不能删除自己
			userService.delete(id);
		}
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 *            ('0','1','2')
	 * @return
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Json batchDelete(String ids, HttpSession session) {
		Json j = new Json();
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null) {
					this.delete(id, session);
				}
			}
		}
		j.setMsg("批量删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到用户授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/grantPage")
	public String grantPage(String ids, HttpServletRequest request) {
		request.setAttribute("ids", ids);
		if (ids != null && !ids.equalsIgnoreCase("") && ids.indexOf(",") == -1) {
			User u = userService.get(ids);
			request.setAttribute("user", u);
		}
		return "/admin/userGrant";
	}

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/grant")
	@ResponseBody
	public Json grant(String ids, User user, String categoryId) {
		Json j = new Json();
		userService.grant(ids, user, categoryId);
		if(!F.empty(categoryId)) {
			userService.updateGrant(ids, user, categoryId);
		}
		j.setSuccess(true);
		j.setMsg("授权成功！");
		return j;
	}

	/**
	 * 跳转到编辑用户密码页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/editPwdPage")
	public String editPwdPage(String id, HttpServletRequest request) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEditPwd";
	}

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/editPwd")
	@ResponseBody
	public Json editPwd(User user) {
		Json j = new Json();
		userService.editPwd(user);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 跳转到编辑自己的密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwdPage")
	public String editCurrentUserPwdPage() {
		return "/user/userEditPwd";
	}

	/**
	 * 修改自己的密码
	 * 
	 * @param session
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwd")
	@ResponseBody
	public Json editCurrentUserPwd(HttpSession session, String oldPwd, String pwd) {
		Json j = new Json();
		if (session != null) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			if (sessionInfo != null) {
				if (userService.editCurrentUserPwd(sessionInfo, oldPwd, pwd)) {
					j.setSuccess(true);
					j.setMsg("编辑密码成功，下次登录生效！");
				} else {
					j.setMsg("原密码错误！");
				}
			} else {
				j.setMsg("登录超时，请重新登录！");
			}
		} else {
			j.setMsg("登录超时，请重新登录！");
		}
		return j;
	}

	/**
	 * 跳转到显示用户角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/currentUserRolePage")
	public String currentUserRolePage(HttpServletRequest request, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		request.setAttribute("userRoles", JSON.toJSONString(roleService.tree(sessionInfo)));
		return "/user/userRole";
	}

	/**
	 * 跳转到显示用户权限页面
	 * 
	 * @return
	 */
	@RequestMapping("/currentUserResourcePage")
	public String currentUserResourcePage(HttpServletRequest request, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		request.setAttribute("userResources", JSON.toJSONString(resourceService.allTree(sessionInfo)));
		return "/user/userResource";
	}

	/**
	 * 用户登录时的autocomplete
	 * 
	 * @param q
	 *            参数
	 * @return
	 */
	@RequestMapping("/loginCombobox")
	@ResponseBody
	public List<User> loginCombobox(String q) {
		return userService.loginCombobox(q);
	}

	/**
	 * 用户登录时的combogrid
	 * 
	 * @param q
	 * @param ph
	 * @return
	 */
	@RequestMapping("/loginCombogrid")
	@ResponseBody
	public DataGrid loginCombogrid(String q, PageHelper ph) {
		return userService.loginCombogrid(q, ph);
	}

	/**
	 * 获取二维码登录url
	 *
	 * @return
	 */
	@RequestMapping("/getQrcodeUrl")
	@ResponseBody
	public Json getQrcodeUrl(HttpServletRequest request) {
		Json j = new Json();
		Map<String, Object> obj = new HashMap<String, Object>();
		String uuid = jb.absx.UUID.uuid();
		CacheOperation cache = CacheOperation.getInstance();
		cache.addCacheData(uuid, true);
		String url = Application.getString("SV101");
		url += request.getContextPath() + "/userController/scanQrcode?uuid=" + uuid;

		obj.put("url", url);
		obj.put("uuid", uuid);
		j.setObj(obj);
		j.success();
		return j;
	}

	/**
	 * 二维码扫描
	 *
	 * @return
	 */
	@RequestMapping("/scanQrcode")
	@ResponseBody
	public Json scanQrcode(String code, String uuid, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Json j = new Json();
		if(F.empty(code)) {
			String redirect_uri = Application.getString("SV101");
			redirect_uri += request.getContextPath()      // 项目名称
					+ request.getServletPath();      // 请求页面或其他地址
			if(request.getQueryString() != null)
				redirect_uri += "?" + (request.getQueryString()); //参数
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Application.getString(WeixinUtil.APPID)+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
			return null;
		}
		CacheOperation cache = CacheOperation.getInstance();
		Object o = cache.getCacheData(uuid, 3*60*1000, 0); // 三分钟后失效
		if(o == null) {
			j.setMsg("qrcode invalid!");
			j.fail();
			return j;
		}
		SessionInfo sessionInfo = userService.loginByQrcode(code);
		if(sessionInfo != null) {
			//session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
			cache.addCacheData(uuid, sessionInfo, false);
			j.success();
			j.setMsg("login success!");
		} else {
			j.fail();
			j.setMsg("Login failed, you have not yet concerned about the WeChat public number!");
		}


		return j;
	}

	/**
	 * 检查是否被扫描
	 *
	 * @return
	 */
	@RequestMapping("/checkScan")
	@ResponseBody
	public Json checkScan(String uuid, HttpSession session) {
		Json j = new Json();
		CacheOperation cache = CacheOperation.getInstance();
		Object o = cache.getCacheData(uuid, 3*60*1000, 0);
		if(o == null || session.getAttribute(ConfigUtil.getSessionInfoName()) != null) {
			cache.removeCacheData(uuid);
			j.setMsg("invalid");
			j.fail();
			return j;
		}
		if(o instanceof SessionInfo) {
			SessionInfo sessionInfo = (SessionInfo) o;
			cache.removeCacheData(uuid);
			session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
			j.setMsg("ok");
			j.success();
		} else {
			j.setMsg("native");
			j.fail();
		}

		return j;
	}

	/**
	 * 同步环信账号
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/syncHxAccount")
	@ResponseBody
	public Json syncHxAccount() {
		Json j = new Json();
		userService.syncHxAccount();
		j.setMsg("同步成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/queryUsers")
	@ResponseBody
	public List<User> queryUsers(String q) {
		List<User> list = new ArrayList<User>();
		User user = new User();
		if(F.empty(q)) {
			return list;
		} else {
			user.setQ(q);
		}
		list = userService.query(user);

		return list;
	}

	@ResponseBody
	@RequestMapping("/sendVCode")
	public Json sendVCode(String mobile, HttpSession session) {
		Json j = new Json();
		try {
			if(F.empty(mobile)) {
				SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
				User user = userService.get(sessionInfo.getId());
				mobile = user.getMobile();
			}

			if(!F.empty(mobile)) {

				String code = Util.CreateNonceNumstr(6); //生成短信验证码
				MNSTemplate template = new MNSTemplate();
				template.setTemplateCode("SMS_70255045");
				Map<String, String> params = new HashMap<String, String>();
				params.put("code", code);
				template.setParams(params);
				TopicMessage topicMessage = MNSUtil.sendMns(mobile, template);
				if(topicMessage != null) {
					redisUserService.setValidateCode(mobile, code);
					j.setSuccess(true);
					j.setMsg("获取短信验证码成功！");
					j.setObj(mobile);
					return j;
				}
			}
			j.setMsg("获取短信验证码失败！");
		} catch (Exception e) {
			j.setMsg("获取短信验证码接口异常");
		}
		return j;
	}
}
