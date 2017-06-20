package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcChatFriend implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String userId;	
	private java.lang.String friendUserId;	
	private java.lang.Boolean isDeleted;	
	private java.lang.String lastContent;	
	private Date lastTime;			
	private Date addtime;			

	private int unreadCount;
	private String lastTimeStr;
	private User friendUser;
	private Boolean isBoth; // 条件是否双方好友

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setFriendUserId(java.lang.String friendUserId) {
		this.friendUserId = friendUserId;
	}
	
	public java.lang.String getFriendUserId() {
		return this.friendUserId;
	}
	public void setIsDeleted(java.lang.Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public java.lang.Boolean getIsDeleted() {
		return this.isDeleted;
	}
	public void setLastContent(java.lang.String lastContent) {
		this.lastContent = lastContent;
	}
	
	public java.lang.String getLastContent() {
		return this.lastContent;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	public Date getLastTime() {
		return this.lastTime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public String getLastTimeStr() {
		return lastTimeStr;
	}

	public void setLastTimeStr(String lastTimeStr) {
		this.lastTimeStr = lastTimeStr;
	}

	public User getFriendUser() {
		return friendUser;
	}

	public void setFriendUser(User friendUser) {
		this.friendUser = friendUser;
	}

	public Boolean getIsBoth() {
		return isBoth;
	}

	public void setIsBoth(Boolean isBoth) {
		this.isBoth = isBoth;
	}
}
