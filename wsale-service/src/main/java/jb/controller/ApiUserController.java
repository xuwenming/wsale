package jb.controller;

import com.aliyun.mns.model.TopicMessage;
import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.*;
import jb.util.EnumConstants;
import jb.util.PathUtil;
import jb.util.QrcodeUtil;
import jb.util.Util;
import jb.util.mns.MNSTemplate;
import jb.util.mns.MNSUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/userController")
public class ApiUserController extends BaseController {

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ZcShieldorfansServiceI zcShieldorfansService;

	@Autowired
	private ZcOrderServiceI zcOrderService;

	@Autowired
	private ZcProductServiceI zcProductService;

	@Autowired
	private ZcShopServiceI zcShopService;

	@Autowired
	private ProductCommon productCommon;

	@Autowired
	private ZcForumBbsServiceI zcForumBbsService;

	@Autowired
	private ForumBbsCommon forumBbsCommon;

	@Autowired
	private TopicCommon topicCommon;

	@Autowired
	private ZcAddressServiceI zcAddressService;

	@Autowired
	private TokenManage tokenManage;

	@Autowired
	private ZcWalletServiceI zcWalletService;

	@Autowired
	private ZcCommentServiceI zcCommentService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Autowired
	private ZcChatMsgServiceI zcChatMsgService;

	@Autowired
	private RedisUserServiceImpl redisUserService;

	@Autowired
	private ZcFileServiceI zcFileService;

	@Autowired
	private ZcBbsLogServiceI zcBbsLogService;

	@Autowired
	private ZcBbsCommentServiceI zcBbsCommentService;

	/**
	 * 获取用户信息
	 * http://localhost:8080/api/userController/get?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @param request
	 * @return
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Json get(String userId, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			userId = F.empty(userId) ? s.getId() : userId;
			User user = userService.getByZc(userId);
			j.setObj(user);
			j.setSuccess(true);
			j.setMsg("获取成功");

		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}

		return j;
	}

	/**
	 * 用户设置
	 * @param request
	 * @return
	 */
	@RequestMapping("/info")
	public String info(HttpServletRequest request) {
		try {
			SessionInfo s = getSessionInfo(request);
			User user = userService.getByZc(s.getId());
			request.setAttribute("user", user);

			ZcAddress address = new ZcAddress();
			address.setUserId(s.getId());
			address.setAtype(1); // 收货地址
			address.setOrderId("-1");
			address = zcAddressService.get(address);
			request.setAttribute("address", address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wsale/my/info";
	}

	/**
	 * 昵称
	 * @param request
	 * @return
	 */
	@RequestMapping("/nickname")
	public String nickname(String nickname, HttpServletRequest request) {
		try {
			request.setAttribute("nickname", nickname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wsale/my/nickname";
	}

	/**
	 * 个性签名
	 * @param request
	 * @return
	 */
	@RequestMapping("/bardian")
	public String bardian(String bardian, HttpServletRequest request) {
		try {
			request.setAttribute("bardian", bardian);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wsale/my/bardian";
	}

	/**
	 * 联系人
	 * @param request
	 * @return
	 */
	@RequestMapping("/contact")
	public String contact(String contact, HttpServletRequest request) {
		try {
			request.setAttribute("contact", contact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wsale/my/contact";
	}

	/**
	 * 微信号
	 * @param request
	 * @return
	 */
	@RequestMapping("/wechatNo")
	public String wechatNo(String wechatNo, HttpServletRequest request) {
		try {
			request.setAttribute("wechatNo", wechatNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wsale/my/wechatNo";
	}

	/**
	 * 手机号
	 * @param request
	 * @return
	 */
	@RequestMapping("/mobile")
	public String mobile(String mobile, HttpServletRequest request) {
		try {
			request.setAttribute("mobile", mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wsale/my/mobile";
	}

	/**
	 * 同步更新用户信息
	 * @return
	 */
	@RequestMapping("/syncInfo")
	@ResponseBody
	public Json syncInfo(HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			userService.update(s);
			tokenManage.buildToken(request.getParameter(TokenManage.TOKEN_FIELD), s);
			j.success();
			j.setMsg("操作成功");
		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}

		return j;
	}

	/**
	 * 我的
	 * http://localhost:8080/api/userController/my?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @param request
	 * @return
	 */
	@RequestMapping("/my")
	public String my(HttpServletRequest request) {
		try {
			SessionInfo s = getSessionInfo(request);
			if(!F.empty(s.getId())) {
				User user = userService.get(s.getId(), null);
				request.setAttribute("user", user);

				// 竞拍中
				ZcProduct p = new ZcProduct();
				p.setStatus("PT03");
				p.setAddUserId(s.getId());
				p.setIsDeleted(false);
				List<ZcProduct> products = zcProductService.query(p);
				request.setAttribute("auction_in_count", CollectionUtils.isEmpty(products) ? 0 : products.size());

				// 订单数量统计
				Map<String, Object> order_count = zcOrderService.orderCount(s.getId());
				request.setAttribute("order_count", order_count);

				// 余额
				ZcWallet q = new ZcWallet();
				q.setUserId(s.getId());
				ZcWallet wallet = zcWalletService.get(q);
				double amount = wallet != null ? wallet.getAmount() : 0;
				request.setAttribute("amount", amount);

				// 评分
				//float grade = zcCommentService.getGradeAvgByUserId(s.getId());
				//request.setAttribute("grade", grade);

				// 订单信誉、订单违约统计
				Map<String, Object> order_status_count = zcOrderService.orderStatusCount(user.getId());
				request.setAttribute("order_status_count", order_status_count);

				// 消息中心数量-未读消息数
				ZcChatMsg msg = new ZcChatMsg();
				msg.setToUserId(s.getId());
				msg.setUnread(true);
				request.setAttribute("chat_unread_count", zcChatMsgService.count(msg));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/wsale/my/my";
	}

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(User user, String vcode,String bbsId, HttpServletRequest request) {
		Json j = new Json();
		try {
			if(!F.empty(user.getMobile())) {
				String oldCode = redisUserService.getValidateCode(user.getMobile());
				if(F.empty(oldCode) || !oldCode.equals(vcode)) {
					j.setMsg("验证码错误！");
					return j;
				}
				redisUserService.deleteValidateCode(user.getMobile());
			}

			SessionInfo s = getSessionInfo(request);
			String userId = F.empty(user.getId()) ? s.getId() : user.getId();
			user.setId(userId);
			userService.edit(user);
			if(!F.empty(user.getNickname())) {
				s.setNickname(user.getNickname());
				tokenManage.buildToken(request.getParameter(TokenManage.TOKEN_FIELD), s);
			}
			if(!F.empty(user.getMobile())) {
				s.setMobile(user.getMobile());
				tokenManage.buildToken(request.getParameter(TokenManage.TOKEN_FIELD), s);
			}
			if(user.getIsGag() != null) {
				String content = null;
				if(user.getIsGag()) {
					sendWxMessage.sendCustomMessage(user.getName(), "您被管理员禁言，申诉请移步“站务公告”申诉专版。");
					content = "【禁言】";
				} else {
					sendWxMessage.sendCustomMessage(user.getName(), "您的禁言已取消，现在就可以去发言了，发言请遵守论坛制度。");
					content = "【取消禁言】";
				}

				ZcBbsLog bbsLog = new ZcBbsLog();
				bbsLog.setBbsId(bbsId);
				bbsLog.setUserId(s.getId());
				bbsLog.setLogType("BL006");
				bbsLog.setContent(content);
				zcBbsLogService.add(bbsLog);
			}

			j.setSuccess(true);
			j.setMsg("操作成功");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.fail();
		}

		return j;
	}

	/**
	 * 用户主页
	 * http://localhost:8080/api/userController/homePage?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * userId不为空则查他人主页信息
	 * @return
	 */
	@RequestMapping("/homePage")
	public String homePage(String userId, HttpServletRequest request) {
		try {
			SessionInfo s = getSessionInfo(request);

			// 不是本人加关注逻辑
			if(!F.empty(userId) && !userId.equals(s.getId())) {
				ZcShieldorfans shieldorfans = new ZcShieldorfans();
				shieldorfans.setObjectType("FS");
				shieldorfans.setObjectById(userId);
				shieldorfans.setObjectId(s.getId());
				zcShieldorfansService.addOrUpdate(shieldorfans, true);
			}

			User user = userService.get(F.empty(userId) ? s.getId() : userId, F.empty(userId) ? null : s.getId());
			request.setAttribute("user", user);

			// 订单信誉、订单违约统计
			Map<String, Object> order_status_count = zcOrderService.orderStatusCount(user.getId());
			request.setAttribute("order_status_count", order_status_count);

			// 是否屏蔽他人
			boolean shieldored = false;
			if(!F.empty(userId) && !userId.equals(s.getId())) {
				ZcShieldorfans shieldorfans = new ZcShieldorfans();
				shieldorfans.setObjectType(EnumConstants.SHIELDOR_FANS.SD.getCode());
				shieldorfans.setObjectById(userId);
				shieldorfans.setObjectId(s.getId());
				shieldorfans.setIsDeleted(false);
				if (zcShieldorfansService.get(shieldorfans) != null) {
					shieldored = true;
				}
			}
			request.setAttribute("shieldored", shieldored);

			// 自己主页主题回复数
			int commentNums = 0;
			if(F.empty(userId)) {
				commentNums = zcForumBbsService.getTextthemeCommentNums(s.getId());
			}
			request.setAttribute("commentNums", commentNums);


			ZcShop shop = new ZcShop();
			if(user.getIsAuth()) {
				shop.setUserId(user.getId());
				shop = zcShopService.get(shop);
			}
			request.setAttribute("shop", shop);

			String protectionMsg = "";
			boolean isPayBond = false;
			ZcWallet w = new ZcWallet();
			w.setUserId(user.getId());
			ZcWallet wallet = zcWalletService.get(w);
			if(wallet != null) {
				if(wallet.getProtection() <= 0) {
					protectionMsg = "未缴纳";
				} else if(wallet.getProtection() >= Double.parseDouble(Application.getString("AF06"))) {
					protectionMsg = "已足额" + wallet.getProtection().longValue() + "元";
					isPayBond = true;
				} else {
					protectionMsg = "未足额" + wallet.getProtection().longValue() + "元";
				}
			} else {
				protectionMsg = "未缴纳";
			}

			request.setAttribute("protectionMsg", protectionMsg);
			request.setAttribute("isPayBond", isPayBond);

			// 设置主题数据
			setThemes(userId, request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/wsale/my/homePage";
	}

	private void setThemes(String userId, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("userId", F.empty(userId) ? s.getId() : userId);
		// 拍品主题
		ZcProduct zcProduct = new ZcProduct();
		zcProduct.setIsDeleted(false);
		zcProduct.setAddUserId(F.empty(userId) ? s.getId() : userId);
		PageHelper ph = new PageHelper();
		ph.setPage(1);
		ph.setRows(4);
		if(F.empty(userId)) {
			ph.setSort("addtime");
		} else {
			ph.setSort("startingTime");
			zcProduct.setStatus("PT03");
		}
		ph.setOrder("desc");
		request.setAttribute("productThemes", productCommon.dataGrid(ph, zcProduct, null));

		ZcForumBbs bbs = new ZcForumBbs();
		bbs.setIsDeleted(false);
		bbs.setAddUserId(F.empty(userId) ? s.getId() : userId);
		ph = new PageHelper();
		ph.setSort("isEssence desc, t.addtime");
		ph.setOrder("desc");
		ph.setPage(1);
		ph.setRows(2);
		if(!F.empty(userId)) bbs.setBbsStatus("BS01");
		// 文字主题
		bbs.setThemeType(EnumConstants.MSG_TYPE.TEXT.getCode());
		request.setAttribute("textThemes", forumBbsCommon.dataGrid(ph, bbs));

		// 声音主题
		bbs.setThemeType(EnumConstants.MSG_TYPE.AUDIO.getCode());
		request.setAttribute("audioThemes", forumBbsCommon.dataGrid(ph, bbs));

		// 专题主题
		ZcTopic zcTopic = new ZcTopic();
		zcTopic.setAddUserId(F.empty(userId) ? s.getId() : userId);
		ph = new PageHelper();
		ph.setSort("seq desc, t.addtime");
		ph.setOrder("desc");
		ph.setPage(1);
		ph.setRows(2);
		request.setAttribute("topicThemes", topicCommon.dataGrid(ph, zcTopic));
	}

	/**
	 * 我的主题
	 * http://localhost:8080/api/userController/myTheme?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * userId不为空则查他人主题信息
	 * @return
	 */
	@RequestMapping("/myTheme")
	public String myTheme(String userId, HttpServletRequest request) {
		try {
			// 设置主题数据
			setThemes(userId, request);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/wsale/my/myTheme";
	}

	/**
	 * 更多帖子主题（文字、声音）
	 * http://localhost:8080/api/userController/moreBbsTheme?tokenId=1D96DACB84F21890ED9F4928FA8B352B
	 * @return
	 */
	@RequestMapping("/moreBbsTheme")
	public String moreBbsTheme(String userId, String themeType, HttpServletRequest request) {
		SessionInfo s = getSessionInfo(request);
		request.setAttribute("themeType", themeType);
		request.setAttribute("userId", F.empty(userId) ? s.getId() : userId);
		request.setAttribute("bbsStatus", !F.empty(userId) ? "BS01" : "");
		return "/wsale/my/moreBbsTheme";
	}

	/**
	 * 关注/屏蔽 objectType(FS:粉丝；SD：屏蔽)
	 * http://localhost:8080/api/userController/addShieldorfans?tokenId=1D96DACB84F21890ED9F4928FA8B352B&objectType=FS&userId=guest
	 * @return
	 */
	@RequestMapping("/addShieldorfans")
	@ResponseBody
	public synchronized Json addShieldorfans(String userId, String objectType, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcShieldorfans shieldorfans = new ZcShieldorfans();
			shieldorfans.setObjectType(objectType);
			shieldorfans.setObjectById(userId);
			shieldorfans.setObjectId(s.getId());
			boolean r = zcShieldorfansService.addOrUpdate(shieldorfans);
			if(r && objectType.equals(EnumConstants.SHIELDOR_FANS.FS.getCode())) {
				User user = userService.getByZc(userId);
				if("UT02".equals(user.getUtype())) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("『" + s.getNickname() + "』关注了您。").append("\n\n");
					buffer.append("<a href='" + PathUtil.getUrlPath("api/userController/homePage?userId=" + s.getId()) + "'>查看主页</a>");
					sendWxMessage.sendCustomMessage(user.getName(), buffer.toString());
				}
			}

			j.setMsg("操作成功");
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 取消关注/取消屏蔽 objectType(FS:粉丝；SD：屏蔽)
	 * http://localhost:8080/api/userController/delShieldorfans?tokenId=1D96DACB84F21890ED9F4928FA8B352B&objectType=FS&objectId=guest
	 * @return
	 */
	@RequestMapping("/delShieldorfans")
	@ResponseBody
	public Json delShieldorfans(String userId, String objectType, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcShieldorfans shieldorfans = new ZcShieldorfans();
			shieldorfans.setObjectType(objectType);
			shieldorfans.setObjectById(userId);
			shieldorfans.setObjectId(s.getId());
			shieldorfans.setIsDeleted(false);
			ZcShieldorfans old = zcShieldorfansService.get(shieldorfans);
			if(old != null)
				zcShieldorfansService.delete(old.getId());

			j.setMsg("操作成功");
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 跳转至屏蔽用户列表
	 * @return
	 */
	@RequestMapping("/shieldors")
	public String shieldors() {
		return "/wsale/my/shieldors";
	}

	/**
	 * 获取关注/屏蔽列表
	 * http://localhost:8080/api/userController/shieldorAttrs?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10&objectType=FS
	 * @return
	 */
	@RequestMapping("/shieldorAttrs")
	@ResponseBody
	public Json shieldorAttrs(PageHelper ph, ZcShieldorfans shieldorfans, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			shieldorfans.setObjectId(s.getId());
			shieldorfans.setIsDeleted(false);
			ph.setSort("addtime");
			ph.setOrder("desc");
			DataGrid dataGrid = zcShieldorfansService.dataGrid(shieldorfans, ph);
			List<ZcShieldorfans> list = (List<ZcShieldorfans>) dataGrid.getRows();
			if(!CollectionUtils.isEmpty(list)) {
				List<User> users = new ArrayList<User>();
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcShieldorfans sd : list) {
					final String objectById = sd.getObjectById();
					completionService.submit(new Task<List<User>, User>(new CacheKey("user", objectById), users) {
						@Override
						public User call() throws Exception {
							User user = userService.get(objectById, true);
							// 订单信誉、订单违约统计
							Map<String, Object> order_status_count = zcOrderService.orderStatusCount(user.getId());
							user.setCredit(((BigInteger)order_status_count.get("OS10")).intValue());
							return user;
						}

						protected void set(List<User> d, User v) {
							if (v != null)
								d.add(v);
						}

					});

				}
				completionService.sync();
				dataGrid.setRows(users);
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

	/**
	 * 跳转至我的粉丝
	 * @return
	 */
	@RequestMapping("/myFans")
	public String myFans() {
		return "/wsale/my/my_fans";
	}

	/**
	 * 获取粉丝列表
	 * http://localhost:8080/api/userController/fans?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10
	 * @return
	 */
	@RequestMapping("/fans")
	@ResponseBody
	public Json fans(PageHelper ph, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcShieldorfans shieldorfans = new ZcShieldorfans();
			shieldorfans.setObjectType(EnumConstants.SHIELDOR_FANS.FS.getCode());
			shieldorfans.setObjectById(s.getId());
			shieldorfans.setIsDeleted(false);
			ph.setSort("addtime");
			ph.setOrder("desc");
			DataGrid dataGrid = zcShieldorfansService.dataGrid(shieldorfans, ph);
			List<ZcShieldorfans> list = (List<ZcShieldorfans>) dataGrid.getRows();
			if(!CollectionUtils.isEmpty(list)) {
				List<User> users = new ArrayList<User>();
				final CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcShieldorfans sd : list) {
					final String objectId = sd.getObjectId();
					final String userId = s.getId();
					completionService.submit(new Task<List<User>, User>(new CacheKey("user", objectId), users) {
						@Override
						public User call() throws Exception {
							User user = userService.get(objectId, userId);
							return user;
						}

						protected void set(List<User> d, User v) {
							if (v != null)
								d.add(v);
						}

					});

				}
				completionService.sync();
				dataGrid.setRows(users);
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

	/**
	 * 获取二维码
	 * http://localhost:8080/api/userController/getQR?objectType=USER&objectId=cs&content='test'
	 * @return
	 */
	@RequestMapping("/getQR")
	@ResponseBody
	public Json getQR(String content, String objectType, String objectId, HttpServletRequest request) {
		Json j = new Json();
		try{
			Object o = null;
			if(EnumConstants.OBJECT_TYPE.USER.getCode().equals(objectType)) {
				o = userService.getByZc(objectId);
			} else if(EnumConstants.OBJECT_TYPE.BBS.getCode().equals(objectType)){
				ZcForumBbs bbs = zcForumBbsService.get(objectId);
				ZcFile file = new ZcFile();
				file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
				file.setObjectId(objectId);
				file.setFileType("FT01");
				ZcFile f = zcFileService.get(file);
				if(f != null) bbs.setIcon(f.getFileHandleUrl());

				o = bbs;
			} else {
				o = zcProductService.get(objectId, null);
			}
			String qrUrl = QrcodeUtil.getLogoQRCode(content, o, request);
			j.setObj(qrUrl);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取收货/退货地址
	 * @return
	 */
	@RequestMapping("/getAddress")
	@ResponseBody
	public Json getAddress(String userId, Integer atype, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			ZcAddress address = new ZcAddress();
			address.setUserId(F.empty(userId) ? s.getId() : userId);
			address.setAtype(atype == null ? 1 : atype); // 1:收货地址; 2:退货地址
			address.setOrderId("-1");
			address = zcAddressService.get(address);
			j.setObj(address);
			j.success();
			j.setMsg("操作成功");
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/sendVCode")
	public Json sendVCode(String mobile, boolean checkMobile, HttpServletRequest request) {
		Json j = new Json();
		try {
			if(F.empty(mobile)) {
				SessionInfo s = getSessionInfo(request);
				User user = userService.getByZc(s.getId());
				mobile = user.getMobile();
			} else {
				if(checkMobile && !userService.checkMobile(mobile)) {
					j.setMsg("手机号码已使用");
					return j;
				}
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

	/**
	 * 跳转至我的收藏
	 * @return
	 */
	@RequestMapping("/myCollect")
	public String myCollect() {
		return "/wsale/my/my_collect";
	}

	/**
	 * 跳转至我的评论
	 * @return
	 */
	@RequestMapping("/myComment")
	public String myComment(HttpServletRequest request) {
		request.setAttribute("sessionInfo", getSessionInfo(request));
		return "/wsale/my/my_comment";
	}

	/**
	 * 收藏列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/comments")
	public Json comments(ZcBbsComment bbsComment, PageHelper ph, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);
			bbsComment.setUserId(s.getId());
			bbsComment.setIsDeleted(false);
			ph.setSort("addtime");
			ph.setOrder("desc");
			DataGrid dataGrid = zcBbsCommentService.dataGrid(bbsComment, ph);
			List<ZcBbsComment> comments = (List<ZcBbsComment>) dataGrid.getRows();
			if(CollectionUtils.isNotEmpty(comments)) {
				CompletionService completionService = CompletionFactory.initCompletion();
				for(ZcBbsComment comment : comments) {
					if (!F.empty(comment.getBbsId())) {
						completionService.submit(new Task<ZcBbsComment, ZcForumBbs>(new CacheKey("bbs", comment.getBbsId()), comment) {
							@Override
							public ZcForumBbs call() throws Exception {
								ZcForumBbs bbs = zcForumBbsService.get(getD().getBbsId());
								ZcFile file = new ZcFile();
								file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
								file.setObjectId(bbs.getId());
								file.setFileType("FT01");
								ZcFile f = zcFileService.get(file);
								bbs.setIcon(f.getFileHandleUrl());
								return bbs;
							}

							protected void set(ZcBbsComment d, ZcForumBbs v) {
								if (v != null)
									d.setBbs(v);
							}
						});
					}
				}
				completionService.sync();
			}
			j.setObj(dataGrid);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}
}
