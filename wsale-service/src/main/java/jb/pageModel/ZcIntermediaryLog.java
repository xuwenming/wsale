package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcIntermediaryLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String imId;	
	private java.lang.String userId;	
	private java.lang.String logType;	
	private java.lang.String content;	
	private Date addtime;			

	private ZcIntermediary intermediary;

	public String getLogTypeZh() {
		return Application.getString(logType);
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setImId(java.lang.String imId) {
		this.imId = imId;
	}
	
	public java.lang.String getImId() {
		return this.imId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setLogType(java.lang.String logType) {
		this.logType = logType;
	}
	
	public java.lang.String getLogType() {
		return this.logType;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public ZcIntermediary getIntermediary() {
		return intermediary;
	}

	public void setIntermediary(ZcIntermediary intermediary) {
		this.intermediary = intermediary;
	}
}
