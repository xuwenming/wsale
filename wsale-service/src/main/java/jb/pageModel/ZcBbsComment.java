package jb.pageModel;

import jb.absx.F;
import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcBbsComment implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bbsId;
	private String comment;
	private String ctype;
	private String pid;
	private String fid;
	private Boolean isDeleted;
	private String userId;
	private Date addtime;			

	private User user; // 评论人信息
	private ZcBbsComment parentComment;
	private String bbsTitle;
	private ZcForumBbs bbs;
	private String userName;
	private String categoryId;
	private String categoryName;

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
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		if(!F.empty(this.comment))
			this.comment = this.comment.replaceAll(Application.getSWordReg(), "***");
		return this.comment;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getPid() {
		return this.pid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Boolean getIsDeleted() {
		return this.isDeleted;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ZcBbsComment getParentComment() {
		return parentComment;
	}

	public void setParentComment(ZcBbsComment parentComment) {
		this.parentComment = parentComment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public ZcForumBbs getBbs() {
		return bbs;
	}

	public void setBbs(ZcForumBbs bbs) {
		this.bbs = bbs;
	}
}
