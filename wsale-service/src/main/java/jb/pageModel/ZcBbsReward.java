package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcBbsReward implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bbsId;
	private Double rewardFee;
	private String userId;
	private Date addtime;			
	private String payStatus;
	private Date paytime;

	private User user; // 打赏人
	private String userName; // 打赏人姓名
	private String byUserId; // 被打赏人ID
	private String byUserName; // 被打赏人姓名
	private String bbsTitle;

	private Boolean auth; // 前端数据权限控制

	public String getBbsTitle() {
		return bbsTitle;
	}

	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}
	
	public String getBbsId() {
		return this.bbsId;
	}
	public void setRewardFee(Double rewardFee) {
		this.rewardFee = rewardFee;
	}
	
	public Double getRewardFee() {
		return this.rewardFee;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
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
