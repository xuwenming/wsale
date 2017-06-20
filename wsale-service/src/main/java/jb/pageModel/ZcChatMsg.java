package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcChatMsg implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String mtype;
	private String content;
	private String fromUserId;
	private String toUserId;
	private Boolean unread;
	private String msgId;
	private Integer duration;
	private Integer isDeleted;
	private Date addtime;			

	private User user;
	private ZcProduct product;
	private ZcForumBbs bbs;
	private String fromUserName;
	private String toUserName;

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	
	public String getMtype() {
		return this.mtype;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	public String getFromUserId() {
		return this.fromUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	
	public String getToUserId() {
		return this.toUserId;
	}
	public void setUnread(Boolean unread) {
		this.unread = unread;
	}
	
	public Boolean getUnread() {
		return this.unread;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ZcProduct getProduct() {
		return product;
	}

	public void setProduct(ZcProduct product) {
		this.product = product;
	}

	public ZcForumBbs getBbs() {
		return bbs;
	}

	public void setBbs(ZcForumBbs bbs) {
		this.bbs = bbs;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
}
