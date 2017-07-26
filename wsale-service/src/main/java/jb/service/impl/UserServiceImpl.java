package jb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jb.absx.F;
import jb.dao.*;
import jb.listener.Application;
import jb.model.Tresource;
import jb.model.Trole;
import jb.model.Tuser;
import jb.model.TzcProduct;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcShieldorfansServiceI;
import jb.util.*;
import jb.util.easemob.HuanxinUtil;
import jb.util.wx.DownloadMediaUtil;
import jb.util.wx.HttpUtil;
import jb.util.wx.WeixinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private RoleDaoI roleDao;

	@Autowired
	private ResourceDaoI resourceDao;

	@Autowired
	private ZcForumBbsDaoI zcForumBbsDao;

	@Autowired
	private ZcShieldorfansServiceI zcShieldorfansService;

	@Autowired
	private ZcShieldorfansDaoI zcShieldorfansDao;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", MD5Util.md5(user.getPwd()));
		Tuser t = userDao.get("from Tuser t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			return user;
		}
		return null;
	}

	/**
	 * 二维码登录
	 * @param code
	 * @return
	 */
	@Override
	public SessionInfo loginByQrcode(String code) {
		SessionInfo sessionInfo = new SessionInfo();
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getAuthorizeUrl(code), "get", null));
		if(!jsonObject.containsKey("openid")) return null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", jsonObject.getString("openid"));
		Tuser t = userDao.get("from Tuser t where t.name = :name", params);
		if (t != null) {
			BeanUtils.copyProperties(t, sessionInfo);
			sessionInfo.setResourceList(resourceList(t.getId()));
			sessionInfo.setRoleIds(get(t.getId()).getRoleIds());

			return sessionInfo;
		}

		return null;
	}

	/**
	 * 微信账号登录
	 * @param code
	 * @return
	 */
	@Override
	public SessionInfo loginByWx(String code, boolean snsapi_userinfo) {
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getAuthorizeUrl(code), "get", null));
		if(jsonObject == null || !jsonObject.containsKey("openid") || F.empty(jsonObject.getString("openid"))) return null;
		String access_token = null;
		if(snsapi_userinfo) access_token = jsonObject.getString("access_token");
		return login(jsonObject.getString("openid"), access_token);
	}

	@Override
	public SessionInfo login(String openid) {
		return login(openid, null);
	}

	@Override
	public SessionInfo login(String openid, String access_token) {
		SessionInfo sessionInfo = new SessionInfo();

		User user = new User();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", openid);
		Tuser t = userDao.get("from Tuser t where t.name = :name", params);
		if(t != null) {
			BeanUtils.copyProperties(t, user);
		} else {
			t = new Tuser();
			user.setId(jb.absx.UUID.uuid());
			user.setName(openid);
			JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getUserInfoUrl(openid, access_token), "GET", null));
			// 未关注公众号
			if(F.empty(access_token) && jsonObject.getIntValue("subscribe") == 0) {
				return null;
			}
			String pwd = Util.CreateNonceNumstr(6);
			user.setPwd(MD5Util.md5(pwd));
			user.setHxPassword(pwd);
			String nickname = Util.filterEmoji(jsonObject.getString("nickname"));
			nickname = nickname.length() > 8 ? nickname.substring(0, 8) : nickname;
			params = new HashMap<String, Object>();
			params.put("nickname", nickname);
			if(userDao.count("select count(*) from Tuser t where t.nickname = :nickname", params) > 0) {
				nickname = (nickname.length() > 6 ? nickname.substring(0, 6) : nickname) + Util.CreateNonceNumstr(4);
			}
			user.setNickname(nickname);
			user.setSex(jsonObject.getInteger("sex"));
			user.setHeadImage(DownloadMediaUtil.downloadHeadImage(jsonObject.getString("headimgurl")));
			user.setArea(jsonObject.getString("province") + " " + jsonObject.getString("city"));
			user.setUtype("UT02");
			user.setCreatedatetime(new Date());
			if(!F.empty(HuanxinUtil.createUser(user.getId(), pwd))) {
				user.setHxStatus(true);
			} else {
				user.setHxStatus(false);
			}
			MyBeanUtils.copyProperties(user, t, true);
			userDao.save(t);

			// 默认布衣角色
			List<Trole> roles = new ArrayList<Trole>();
			roles.add(roleDao.get(Trole.class, "by"));
			t.setTroles(new HashSet<Trole>(roles));
		}
		setPosition(user);
		BeanUtils.copyProperties(user, sessionInfo);
		sessionInfo.setResourceList(resourceList(user.getId(), true));

		return sessionInfo;
	}

	@Override
	synchronized public void reg(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			u.setId(jb.absx.UUID.uuid());
			u.setName(user.getName());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setCreatedatetime(new Date());
			userDao.save(u);
		}
	}

	@Override
	public DataGrid dataGrid(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		List<Tuser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				Set<Trole> roles = t.getTroles();
				if (roles != null && !roles.isEmpty()) {
					String roleIds = "";
					String roleNames = "";
					boolean b = false;
					for (Trole tr : roles) {
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
						roleIds += tr.getId();
						roleNames += tr.getName();
					}
					u.setRoleIds(roleIds);
					u.setRoleNames(roleNames);
				}
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
		return dg;
	}

	public DataGrid dataGridSimple(User user, PageHelper ph, String where) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		where = F.empty(where) ? "" : where;
		List<Tuser> l = userDao.find(hql + whereHql(user, params) + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
		return dg;
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + user.getName() + "%%");
			}
			if(!F.empty(user.getNickname())) {
				hql += " and t.nickname like :nickname";
				params.put("nickname", "%%" + user.getNickname() + "%%");
			}
			if(!F.empty(user.getMobile())) {
				hql += " and t.mobile like :mobile";
				params.put("mobile", "%%" + user.getMobile() + "%%");
			}
			if(user.getIsGag() != null) {
				hql += " and t.isGag = :isGag";
				params.put("isGag", user.getIsGag());
			}
			if(user.getIsDeleted() != null) {
				hql += " and t.isDeleted = :isDeleted";
				params.put("isDeleted", user.getIsDeleted());
			}
			if(!F.empty(user.getUtype())) {
				hql += " and t.utype = :utype";
				params.put("utype", user.getUtype());
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", user.getModifydatetimeEnd());
			}
			if(!F.empty(user.getQ())) {
				hql += " and (t.nickname like :q or t.mobile like :q or t.wechatNo like :q)";
				params.put("q", "%%" + user.getQ() + "%%");
			}
		}
		return hql;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	synchronized public void add(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			BeanUtils.copyProperties(user, u);
			u.setCreatedatetime(new Date());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setNickname(user.getName());
			userDao.save(u);
		}
	}

	@Override
	public User get(String id) {
		return get(id, false);
	}

	@Override
	public User get(String id, String userId) {
		User u = new User();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("from Tuser t where t.id = :id", params);
		BeanUtils.copyProperties(t, u);

		// 设置职位
		setPosition(u);

		//  获取用户帖子数
		params = new HashMap<String, Object>();
		params.put("addUserId", id);
		u.setBbsNums(zcForumBbsDao.count("select count(*) from TzcForumBbs t where t.addUserId = :addUserId and t.isDeleted = 0", params).intValue());

		// 获取粉丝数
		params = new HashMap<String, Object>();
		params.put("objectType", EnumConstants.SHIELDOR_FANS.FS.getCode());
		params.put("objectById", id);
		u.setFans(zcShieldorfansDao.count("select count(*) from TzcShieldorfans t where t.objectById = :objectById and t.objectType = :objectType", params).intValue());

		// 获取屏蔽数
		params = new HashMap<String, Object>();
		params.put("objectType", EnumConstants.SHIELDOR_FANS.SD.getCode());
		params.put("objectId", id);
		u.setShieldors(zcShieldorfansDao.count("select count(*) from TzcShieldorfans t where t.objectId = :objectId and t.objectType = :objectType", params).intValue());

		//  是否关注
		if(!F.empty(userId)) {
			if(!userId.equals(id)) {
				ZcShieldorfans shieldorfans = new ZcShieldorfans();
				shieldorfans.setObjectType("FS");
				shieldorfans.setObjectById(id);
				shieldorfans.setObjectId(userId);
				shieldorfans.setIsDeleted(false);
				if (zcShieldorfansService.get(shieldorfans) == null) {
					u.setAttred(false);
				} else {
					u.setAttred(true);
				}
			} else {
				u.setSelf(true); // 本人
			}
		} else {
			u.setSelf(true); // 本人
		}

		return u;
	}

	@Override
	public User get(String id, boolean isZcUser) {
		if(isZcUser) return getByZc(id);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("select distinct t from Tuser t left join fetch t.troles role where t.id = :id", params);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		if (t.getTroles() != null && !t.getTroles().isEmpty()) {
			String roleIds = "";
			String roleNames = "";
			boolean b = false;
			for (Trole role : t.getTroles()) {
				if (b) {
					roleIds += ",";
					roleNames += ",";
				} else {
					b = true;
				}
				roleIds += role.getId();
				roleNames += role.getName();
			}
			u.setRoleIds(roleIds);
			u.setRoleNames(roleNames);
		}
		return u;
	}

	@Override
	public User getByZc(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("from Tuser t where t.id = :id", params);
		User u = new User();
		if(t != null) {
			BeanUtils.copyProperties(t, u);
			setPosition(u);
		}

		return u;
	}

	@Override
	synchronized public void edit(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name and t.id != :id", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			user.setNickname(Util.filterEmoji(user.getNickname()));
			params.put("name", user.getNickname());
			if(!F.empty(user.getNickname()) && userDao.count("select count(*) from Tuser t where t.nickname = :name and t.id != :id", params) > 0) {
				throw new Exception("昵称已存在！");
			} else if(!F.empty(user.getMobile())){
				params.put("name", user.getMobile());
				if(userDao.count("select count(*) from Tuser t where t.mobile = :name and t.id != :id", params) > 0) {
					throw new Exception("手机号码已使用！");
				}
			}
		}

		Tuser u = userDao.get(Tuser.class, user.getId());
		boolean isSendGag = false;
		if(user.getIsGag() != null && user.getIsGag() != u.getIsGag())
			isSendGag = true;

		MyBeanUtils.copyProperties(user, u, new String[] { "pwd", "createdatetime" }, true);
		u.setModifydatetime(new Date());
		if(!"UT02".equals(u.getUtype()))
			u.setNickname(user.getName());
		else
			user.setName(u.getName());

		if(!isSendGag) user.setIsGag(null);
	}

	@Override
	public void updateHeadImage(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("headImage", user.getHeadImage());
		userDao.executeHql("update Tuser t set t.headImage = :headImage where t.id = :id", params);
	}

	@Override
	public User getByName(String name) {
		User user = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		Tuser t = userDao.get("from Tuser t where t.name = :name", params);
		if(t != null) {
			user = new User();
			BeanUtils.copyProperties(t, user);
		}
		return user;
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		userDao.executeHql("update Tuser t set t.isDeleted = 1 where t.id = :id", params);
		//userDao.delete(userDao.get(Tuser.class, id));
	}

	@Override
	public void grant(String ids, User user, String categoryId) {
		if (ids != null && ids.length() > 0) {
			List<Trole> roles = new ArrayList<Trole>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(Trole.class, roleId));
				}
			}

			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					//Tuser t = userDao.get(Tuser.class, id);
					//t.setTroles(new HashSet<Trole>(roles));
					// 删除用户角色关系
					String where = "";
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("userId", id);
					where += " where TUSER_ID = :userId ";
					if(F.empty(categoryId)) {
						where += " and category_id is null ";
					} else {
						where += " and TROLE_ID != 'sxbz' ";
					}
					userDao.executeSql("delete from tuser_trole " + where, params);

					// 新增角色关系
					for (Trole role : roles) {
						if(F.empty(categoryId) && role.getTrole() != null && "99".equals(role.getTrole().getId())) {
							continue;
						}
						if("sxbz".equals(role.getId())) {
							BigInteger count = userDao.countBySql("select count(*) from tuser_trole where TUSER_ID = '"+id+"' and category_id = '"+categoryId+"' and TROLE_ID = 'sxbz'");
							if(count != null && count.intValue() > 0) {
								continue;
							}
						}

						params.put("roleId", role.getId());
						userDao.executeSql("insert into tuser_trole (TUSER_ID, TROLE_ID) values (:userId, :roleId)", params);
					}
				}
			}
		}
	}

	@Override
	public void updateGrant(String ids, User user, String categoryId) {
		if (ids != null && ids.length() > 0) {
			List<Trole> roles = new ArrayList<Trole>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(Trole.class, roleId));
				}
			}
			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					for (Trole role : roles) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("userId", id);
						params.put("categoryId", categoryId);
						if ("99".equals(role.getTrole().getId())) {
							StringBuffer buffer = new StringBuffer();

							params.put("roleId" , role.getId());
							params.put("valid_start_time", null);
							params.put("valid_end_time", null);
							Calendar c = Calendar.getInstance();
							if("zsjs".equals(role.getId())) {
								params.put("valid_start_time", c.getTime());
								String rd03 = Application.getString("RD03");
								c.add(Calendar.MONTH, rd03 == null ? 3 : Integer.valueOf(rd03));
								params.put("valid_end_time", c.getTime());

								buffer.append("恭喜您成为资深讲师，拥有了更多特权，快去讲堂发声音吧。").append("\n\n");
								buffer.append("有效期：3个月").append("\n");
								buffer.append("到期日期：" + DateUtil.format(c.getTime(), Constants.DATE_FORMAT_YMD)).append("\n\n");
								buffer.append("查看<a href='https://mp.weixin.qq.com/s/q65CxNiEB7kirteEFJcRTQ'>查看《讲堂使用手册》</a>");
							} else if("zscj".equals(role.getId())) {
								params.put("valid_start_time", c.getTime());
								String rd12 = Application.getString("RD12");
								c.add(Calendar.MONTH, rd12 == null ? 12 : Integer.valueOf(rd12));
								params.put("valid_end_time", c.getTime());

								buffer.append("恭喜您成为资深藏家，拥有了更多特权。").append("\n\n");
								//buffer.append("有效期：1年").append("\n");
								//buffer.append("到期日期：" + DateUtil.format(c.getTime(), Constants.DATE_FORMAT_YMD)).append("\n\n");
								buffer.append("查看<a href='https://mp.weixin.qq.com/s/Zl0EJWYK4q8sVgtcuTNxzw'>“什么是资深藏家？”</a>");
							} else if("bz".equals(role.getId())) {
								buffer.append("恭喜您成为集东集西的版主，尊敬的版主我们非常期待与你一起愉快的合作，同时欢迎您为网站建设谏言献策。").append("\n\n");
								buffer.append("<a href='https://mp.weixin.qq.com/s/v4rwWPI680QO2wh-kaM6qw'>查看《版主、贵宾使用手册》</a>");
							} else if("gb".equals(role.getId())) {
								buffer.append("恭喜您成为集东集西的特邀贵宾，我们非常期待与您一起愉快的合作，同时欢迎您为网站建设谏言献策。").append("\n\n");
								buffer.append("<a href='https://mp.weixin.qq.com/s/v4rwWPI680QO2wh-kaM6qw'>查看《版主、贵宾使用手册》</a>");
							} else if("gly".equals(role.getId())) {
								buffer.append("恭喜您晋升为集东集西的管理员，成为我们管理团队的一员。我们非常期待与你一起愉快的合作，同时欢迎您为网站建设谏言献策。");
							}
							userDao.executeSql("update tuser_trole set category_id = :categoryId, valid_start_time = :valid_start_time, valid_end_time = :valid_end_time where category_id is null && TUSER_ID = :userId and TROLE_ID = :roleId", params);

							// 推送职务通知
							if(!F.empty(buffer.toString())) {
								sendWxMessage.sendCustomMessageByUserId(id, buffer.toString());
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<String> resourceList(String id) {
		return resourceList(id, false);
	}

	private List<String> resourceList(String id, boolean isZcUser) {
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String where = " where t.id = :id ";
		Tuser t = null;
		if(isZcUser) {
			params.put("pid", "99"); // 只保留前端角色
			where += " and role.trole.id = :pid ";
			t = userDao.get("from Tuser t join fetch t.troles role join fetch role.trole prole join fetch role.tresources resource " + where, params);
		} else {
			t = userDao.get("from Tuser t join fetch t.troles role join fetch role.tresources resource " + where, params);
		}

		if (t != null) {
			Set<Trole> roles = t.getTroles();
			if (roles != null && !roles.isEmpty()) {
				params = new HashMap<String, Object>();
				params.put("userId", t.getId());
				params.put("now", new Date());
				for (Trole role : roles) {
					if(isZcUser) {
						// 判断用户角色是否过期
						params.put("roleId", role.getId());
						BigInteger count = roleDao.countBySql("select count(*) from tuser_trole ur where (ur.valid_end_time is null or ur.valid_end_time > :now) and ur.TUSER_ID = :userId and ur.TROLE_ID = :roleId", params);
						if(count == null || count.intValue() == 0) continue;
					}
					Set<Tresource> resources = role.getTresources();
					if (resources != null && !resources.isEmpty()) {
						for (Tresource resource : resources) {
							if (resource != null && !F.empty(resource.getUrl()) && !resourceList.contains(resource.getUrl())) {
								resourceList.add(resource.getUrl());
							}
						}
					}
				}
			}
		}
		return resourceList;
	}

	@Override
	public void editPwd(User user) {
		if (user != null && user.getPwd() != null && !user.getPwd().trim().equalsIgnoreCase("")) {
			Tuser u = userDao.get(Tuser.class, user.getId());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setModifydatetime(new Date());
		}
	}

	@Override
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
		Tuser u = userDao.get(Tuser.class, sessionInfo.getId());
		if (u.getPwd().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPwd(MD5Util.md5(pwd));
			u.setModifydatetime(new Date());
			return true;
		}
		return false;
	}

	@Override
	public List<User> loginCombobox(String q) {
		if (q == null) {
			q = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<Tuser> tl = userDao.find("from Tuser t where t.name like :name order by name", params, 1, 10);
		List<User> ul = new ArrayList<User>();
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setName(t.getName());
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	public DataGrid loginCombogrid(String q, PageHelper ph) {
		if (q == null) {
			q = "";
		}
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<Tuser> tl = userDao.find("from Tuser t where t.name like :name order by " + ph.getSort() + " " + ph.getOrder(), params, ph.getPage(), ph.getRows());
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setName(t.getName());
				u.setCreatedatetime(t.getCreatedatetime());
				u.setModifydatetime(t.getModifydatetime());
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) from Tuser t where t.name like :name", params));
		return dg;
	}

	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("s", k);
			params.put("e", k + 2);
			k = k + 2;
			l.add(userDao.count("select count(*) from Tuser t where HOUR(t.createdatetime)>=:s and HOUR(t.createdatetime)<:e", params));
		}
		return l;
	}

	/**
	 * 设置当前职位
	 * @param
	 * @return
	 */
	public void setPosition(User user) {
		String positionId = "by";
		String position = "布衣";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getId());
		params.put("now", new Date());
		params.put("pid", "99");
		List<Map> l = roleDao.findBySql2Map("select t.id, t.name from tuser_trole ur, trole t where ur.TROLE_ID = t.ID and  (ur.valid_end_time is null or ur.valid_end_time > :now) " +
				" and ur.TUSER_ID = :userId and t.PID = :pid order by t.SEQ asc limit 0,1", params);
		if(CollectionUtils.isNotEmpty(l)) {
			positionId = (String)l.get(0).get("id");
			position = (String)l.get(0).get("name");
		}
		user.setPositionId(positionId);
		user.setPosition(position);
	}

	@Override
	public void syncHxAccount() {
		int page = 1;
		int pageSize = 50;
		while(true) {
			int count = 0;
			List<Tuser> l = userDao.find("from Tuser t where t.hxStatus=0 and t.utype='UT02'", page, pageSize);
			if(l != null && l.size() > 0) {
				count = l.size();
				List<Map<String, String>> r = new ArrayList<Map<String,String>>();
				String userIds = "";
				Map<String, String> m = null;
				for(Tuser t : l) {
					userIds += ",'" + t.getId() + "'";
					m = new HashMap<String, String>();
					m.put("username", t.getId());
					m.put("password", t.getHxPassword());
					r.add(m);
				}
				if(!F.empty(HuanxinUtil.createUsers(JSON.toJSONString(r))) && !F.empty(userIds)) {
					userDao.executeSql("update tuser set hx_status = 1 where id in (" + userIds.substring(1) + ")");
				}
			}

			page ++;
			if(count < pageSize) break;
		}
	}

	@Override
	public void update(SessionInfo s) throws Exception {
		User user = new User();
		user.setId(s.getId());
		user.setName(s.getName());
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getUserInfoUrl(s.getName(), null), "GET", null));
//		user.setNickname(jsonObject.getString("nickname"));
		String headimgurl = DownloadMediaUtil.downloadHeadImage(jsonObject.getString("headimgurl"));
		user.setHeadImage(headimgurl);
		user.setSex(jsonObject.getInteger("sex"));
		user.setArea(jsonObject.getString("province") + " " + jsonObject.getString("city"));
		edit(user);
//		s.setNickname(jsonObject.getString("nickname"));
		s.setHeadImage(headimgurl);
	}

	@Override
	public boolean checkRoleIsValid(String userId, String roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("now", new Date());
		params.put("roleId", roleId);
		BigInteger count = roleDao.countBySql("select count(ur.id) from tuser_trole ur where (ur.valid_end_time is null or ur.valid_end_time > :now) " +
				" and ur.TUSER_ID = :userId and ur.TROLE_ID = :roleId", params);
		int r = count == null ? 0 : count.intValue();
		return r == 0 ? false : true;
	}

	/**
	 * 删除所有的过期用户角色数据
	 */
	public void deleteExpireUserRole() {
		userDao.executeSql("delete from tuser_trole where valid_end_time <= now()");
	}

	public List<String> queryExpireUserIds(Map<String, Object> params) {
		List<String> userIds = new ArrayList<String>();
		List<Map> l = userDao.findBySql2Map("select TUSER_ID as userId from tuser_trole where TROLE_ID = :roleId and date_format(valid_end_time, '%Y-%m-%d') = :expireTime", params);
		if(CollectionUtils.isNotEmpty(l)) {
			for(Map m : l) {
				userIds.add((String)m.get("userId"));
			}
		}

		return userIds;
	}

	@Override
	public void editAuth(User user) throws Exception {
		edit(user);
		if(user.getIsAuth()) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", user.getId());
			params.put("roleId", "vip");
			BigInteger count = userDao.countBySql("select count(*) from tuser_trole t where t.TUSER_ID = :userId and t.TROLE_ID = :roleId", params);
			if(count == null || count.intValue() == 0) {
				userDao.executeSql("insert into tuser_trole (TUSER_ID, TROLE_ID) values (:userId, :roleId)", params);
			}
		}
	}

	@Override
	public List<User> query(User user) {
		List<User> r = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(user, params);
		List<Tuser> l = userDao.find("from Tuser t " + whereHql + " order by t.createdatetime desc", params);
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User o = new User();
				BeanUtils.copyProperties(t, o);
				r.add(o);
			}
		}
		return r;
	}

	@Override
	public boolean checkMobile(String mobile) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		if (userDao.count("select count(*) from Tuser t where t.mobile = :mobile", params) > 0) {
			return false;
		}
		return true;
	}

}
