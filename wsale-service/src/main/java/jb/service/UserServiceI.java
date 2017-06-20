package jb.service;

import java.util.List;
import java.util.Map;

import jb.pageModel.*;

/**
 * 用户Service
 * 
 * @author John
 * 
 */
public interface UserServiceI {

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @return 用户对象
	 */
	public User login(User user);

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @throws Exception
	 */
	public void reg(User user) throws Exception;

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	public DataGrid dataGrid(User user, PageHelper ph);

	public DataGrid dataGridSimple(User user, PageHelper ph, String where);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void add(User user) throws Exception;

	/**
	 * 获得用户对象
	 * 
	 * @param id
	 * @param isZcUser 是否臻藏用户
	 * @return
	 */
	public User get(String id, boolean isZcUser);
	public User get(String id);
	public User getByZc(String id);

	/**
	 *
	 * @param id 用户ID
	 * @param userId 当前操作人
	 * @return
	 */
	public User get(String id, String userId);


	/**
	 * 编辑用户
	 * 
	 * @param user
	 */
	public void edit(User user) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @param user
	 *            需要user.roleIds的属性值
	 */
	public void grant(String ids, User user, String categoryId);
	public void updateGrant(String ids, User user, String categoryId);

	/**
	 * 获得用户能访问的资源地址
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public List<String> resourceList(String id);

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 */
	public void editPwd(User user);

	/**
	 * 修改用户自己的密码
	 * 
	 * @param sessionInfo
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

	/**
	 * 用户登录时的autocomplete
	 * 
	 * @param q
	 *            参数
	 * @return
	 */
	public List<User> loginCombobox(String q);

	/**
	 * 用户登录时的combogrid
	 * 
	 * @param q
	 * @param ph
	 * @return
	 */
	public DataGrid loginCombogrid(String q, PageHelper ph);

	/**
	 * 用户创建时间图表
	 * 
	 * @return
	 */
	public List<Long> userCreateDatetimeChart();

	SessionInfo loginByWx(String code, boolean snsapi_userinfo);
	SessionInfo login(String openid, String access_token);
	SessionInfo login(String openid);
	SessionInfo loginByQrcode(String code);

	public void setPosition(User user);

	void syncHxAccount();

	void update(SessionInfo s) throws Exception;

	boolean checkRoleIsValid(String userId, String roleId);

	void deleteExpireUserRole();
	List<String> queryExpireUserIds(Map<String, Object> params);

	void editAuth(User user) throws Exception;

	List<User> query(User user);

	boolean checkMobile(String mobile);

	void updateHeadImage(User o);

	User getByName(String name);
}
