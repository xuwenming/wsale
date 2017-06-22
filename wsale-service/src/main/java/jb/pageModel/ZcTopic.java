package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcTopic implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String title;	
	private java.lang.String icon;	
	private java.lang.String content;	
	private java.lang.Integer topicComment;	
	private java.lang.Integer topicRead;	
	private java.lang.Integer topicReward;	
	private java.lang.Integer topicPraise;	
	private java.lang.Integer topicCollect;	
	private java.lang.Integer seq;	
	private java.lang.String addUserId;	
	private Date addtime;			
	private java.lang.String updateUserId;	
	private Date updatetime;			
	private java.lang.Boolean isDeleted;	

	private User user;

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	
	public java.lang.String getIcon() {
		return this.icon;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setTopicComment(java.lang.Integer topicComment) {
		this.topicComment = topicComment;
	}
	
	public java.lang.Integer getTopicComment() {
		return this.topicComment;
	}
	public void setTopicRead(java.lang.Integer topicRead) {
		this.topicRead = topicRead;
	}
	
	public java.lang.Integer getTopicRead() {
		return this.topicRead;
	}
	public void setTopicReward(java.lang.Integer topicReward) {
		this.topicReward = topicReward;
	}
	
	public java.lang.Integer getTopicReward() {
		return this.topicReward;
	}
	public void setTopicPraise(java.lang.Integer topicPraise) {
		this.topicPraise = topicPraise;
	}
	
	public java.lang.Integer getTopicPraise() {
		return this.topicPraise;
	}
	public void setTopicCollect(java.lang.Integer topicCollect) {
		this.topicCollect = topicCollect;
	}
	
	public java.lang.Integer getTopicCollect() {
		return this.topicCollect;
	}
	public void setSeq(java.lang.Integer seq) {
		this.seq = seq;
	}
	
	public java.lang.Integer getSeq() {
		return this.seq;
	}
	public void setAddUserId(java.lang.String addUserId) {
		this.addUserId = addUserId;
	}
	
	public java.lang.String getAddUserId() {
		return this.addUserId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdateUserId(java.lang.String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public java.lang.String getUpdateUserId() {
		return this.updateUserId;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime() {
		return this.updatetime;
	}
	public void setIsDeleted(java.lang.Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public java.lang.Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
