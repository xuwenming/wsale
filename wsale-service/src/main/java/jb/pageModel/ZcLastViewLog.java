package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcLastViewLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;
	private java.lang.String businessType;
	private java.lang.String objectType;	
	private java.lang.String objectId;	
	private Date lastViewTime;			
	private java.lang.String userId;	
	private Date addtime;			

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setObjectType(java.lang.String objectType) {
		this.objectType = objectType;
	}
	
	public java.lang.String getObjectType() {
		return this.objectType;
	}
	public void setObjectId(java.lang.String objectId) {
		this.objectId = objectId;
	}
	
	public java.lang.String getObjectId() {
		return this.objectId;
	}
	public void setLastViewTime(Date lastViewTime) {
		this.lastViewTime = lastViewTime;
	}
	
	public Date getLastViewTime() {
		return this.lastViewTime;
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

}
