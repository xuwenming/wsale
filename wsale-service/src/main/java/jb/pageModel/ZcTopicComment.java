package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcTopicComment implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String topicId;	
	private java.lang.String comment;	
	private java.lang.String ctype;	
	private java.lang.String pid;	
	private java.lang.Boolean isDeleted;	
	private java.lang.String userId;	
	private Date addtime;			
	private java.lang.String auditStatus;	
	private Date auditTime;			
	private java.lang.String auditUserId;
	private java.lang.String auditRemark;	

	private String userName;
	private String auditUserName;
	private User user;
	private ZcTopicComment replyComment;

	public String getAuditStatusZh() {
		return Application.getString(this.auditStatus);
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setTopicId(java.lang.String topicId) {
		this.topicId = topicId;
	}
	
	public java.lang.String getTopicId() {
		return this.topicId;
	}
	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}
	
	public java.lang.String getComment() {
		return this.comment;
	}
	public void setCtype(java.lang.String ctype) {
		this.ctype = ctype;
	}
	
	public java.lang.String getCtype() {
		return this.ctype;
	}
	public void setPid(java.lang.String pid) {
		this.pid = pid;
	}
	
	public java.lang.String getPid() {
		return this.pid;
	}
	public void setIsDeleted(java.lang.Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public java.lang.Boolean getIsDeleted() {
		return this.isDeleted;
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
	public void setAuditStatus(java.lang.String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.String getAuditStatus() {
		return this.auditStatus;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public Date getAuditTime() {
		return this.auditTime;
	}
	public void setAuditUserId(java.lang.String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	public java.lang.String getAuditUserId() {
		return this.auditUserId;
	}
	public void setAuditRemark(java.lang.String auditRemark) {
		this.auditRemark = auditRemark;
	}
	
	public java.lang.String getAuditRemark() {
		return this.auditRemark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ZcTopicComment getReplyComment() {
		return replyComment;
	}

	public void setReplyComment(ZcTopicComment replyComment) {
		this.replyComment = replyComment;
	}
}
