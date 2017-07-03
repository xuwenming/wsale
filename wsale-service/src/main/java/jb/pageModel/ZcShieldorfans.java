package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcShieldorfans implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String objectType;
	private String objectById;
	private String objectId;
	private Boolean isDeleted;
	private Date addtime;			

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	public String getObjectType() {
		return this.objectType;
	}
	public void setObjectById(String objectById) {
		this.objectById = objectById;
	}
	
	public String getObjectById() {
		return this.objectById;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	public String getObjectId() {
		return this.objectId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}
