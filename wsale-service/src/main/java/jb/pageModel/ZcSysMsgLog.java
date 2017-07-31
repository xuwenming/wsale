package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcSysMsgLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String sysMsgId;	
	private java.lang.String mtype;	
	private java.lang.String timeUnit;	
	private java.lang.String content;
	private String url;
	private java.lang.Boolean isRead;	
	private Date addtime;			

	private ZcSysMsg sysMsg;

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setSysMsgId(java.lang.String sysMsgId) {
		this.sysMsgId = sysMsgId;
	}
	
	public java.lang.String getSysMsgId() {
		return this.sysMsgId;
	}
	public void setMtype(java.lang.String mtype) {
		this.mtype = mtype;
	}
	
	public java.lang.String getMtype() {
		return this.mtype;
	}
	public void setTimeUnit(java.lang.String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public java.lang.String getTimeUnit() {
		return this.timeUnit;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setIsRead(java.lang.Boolean isRead) {
		this.isRead = isRead;
	}
	
	public java.lang.Boolean getIsRead() {
		return this.isRead;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public ZcSysMsg getSysMsg() {
		return sysMsg;
	}

	public void setSysMsg(ZcSysMsg sysMsg) {
		this.sysMsg = sysMsg;
	}
}
