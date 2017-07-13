package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcNotice implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String content;	
	private java.lang.String status;
	private Boolean isDeleted;
	private java.lang.String addUserId;	
	private Date addtime;			
	private java.lang.String updateUserId;	
	private Date updatetime;			

	private String addUserName;
	private String updateUserName;

	public String getStatusZh() {
		return Application.getString(status);
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
}
