package jb.pageModel;

import jb.util.EnumConstants;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcReward implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String objectType;	
	private java.lang.String objectId;	
	private java.lang.Long rewardFee;	
	private java.lang.String userId;	
	private Date addtime;			
	private java.lang.String payStatus;	
	private Date paytime;			

	private User user;
	private String objectName;
	private String userName; // 打赏人姓名
	private String byUserId; // 被打赏人ID
	private String byUserName; // 被打赏人姓名

	private Boolean auth; // 前端数据权限控制

	public String getObjectTypeZh() {
		return EnumConstants.OBJECT_TYPE.getCnName(this.objectType);
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setObjectType(java.lang.String objectType) {
		this.objectType = objectType;
	}
	
	public java.lang.String getObjectType() {
		return this.objectType;
	}
	public void setObjectId(java.lang.String objectId) {
		this.objectId = objectId;
	}
	
	public java.lang.String getObjectId() {
		return this.objectId;
	}
	public void setRewardFee(java.lang.Long rewardFee) {
		this.rewardFee = rewardFee;
	}
	
	public java.lang.Long getRewardFee() {
		return this.rewardFee;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	
	public java.lang.String getPayStatus() {
		return this.payStatus;
	}
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	public Date getPaytime() {
		return this.paytime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getByUserId() {
		return byUserId;
	}

	public void setByUserId(String byUserId) {
		this.byUserId = byUserId;
	}

	public String getByUserName() {
		return byUserName;
	}

	public void setByUserName(String byUserName) {
		this.byUserName = byUserName;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}
}
