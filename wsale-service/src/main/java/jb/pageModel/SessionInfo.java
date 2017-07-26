package jb.pageModel;

import java.util.List;

/**
 * session信息模型
 * 
 * @author John
 * 
 */
@SuppressWarnings("serial")
public class SessionInfo implements java.io.Serializable {

	private String id;// 用户ID
	private String name;// 用户登录名
	private String ip;// 用户IP
	private String roleIds; // 用户角色Id集合

	private String nickname; // 昵称
	private String positionId; // 职位
	private String position; // 职位
	private String headImage; // 头像
	private String mobile; // 手机号
	private String utype;
	private Boolean isGag;
	private Boolean isDeleted;
	private Boolean isAuth;
	private Boolean isPayBond;
	private String hxPassword;

	private List<String> resourceList;// 用户可以访问的资源地址列表

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public Boolean getIsGag() {
		return isGag;
	}

	public void setIsGag(Boolean isGag) {
		this.isGag = isGag;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsPayBond() {
		return isPayBond;
	}

	public void setIsPayBond(Boolean isPayBond) {
		this.isPayBond = isPayBond;
	}

	public Boolean getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Boolean isAuth) {
		this.isAuth = isAuth;
	}

	public String getHxPassword() {
		return hxPassword;
	}

	public void setHxPassword(String hxPassword) {
		this.hxPassword = hxPassword;
	}
}
