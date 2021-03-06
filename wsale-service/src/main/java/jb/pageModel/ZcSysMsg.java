package jb.pageModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ZcSysMsg implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String objectType;	
	private java.lang.String objectId;	
	private java.lang.String userId;
	private Integer idType;
	private Date newtime;			
	private Date addtime;			

	private List<ZcSysMsgLog> sysMsgLogs;
	private Map<String, Object> product;

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
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
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setNewtime(Date newtime) {
		this.newtime = newtime;
	}
	
	public Date getNewtime() {
		return this.newtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public List<ZcSysMsgLog> getSysMsgLogs() {
		return sysMsgLogs;
	}

	public void setSysMsgLogs(List<ZcSysMsgLog> sysMsgLogs) {
		this.sysMsgLogs = sysMsgLogs;
	}

	public Map<String, Object> getProduct() {
		return product;
	}

	public void setProduct(Map<String, Object> product) {
		this.product = product;
	}
}
