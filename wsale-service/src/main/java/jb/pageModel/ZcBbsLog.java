package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcBbsLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String logType;	
	private java.lang.String bbsId;	
	private java.lang.String userId;	
	private java.lang.String content;	
	private java.lang.String remark;	
	private Date addtime;

	private String userName;

	public String getLogTypeZh() {
		return Application.getString(this.logType);
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setLogType(java.lang.String logType) {
		this.logType = logType;
	}
	
	public java.lang.String getLogType() {
		return this.logType;
	}
	public void setBbsId(java.lang.String bbsId) {
		this.bbsId = bbsId;
	}
	
	public java.lang.String getBbsId() {
		return this.bbsId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
