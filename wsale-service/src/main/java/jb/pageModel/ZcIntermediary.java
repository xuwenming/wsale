package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcIntermediary implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String imNo;	
	private java.lang.String bbsId;	
	private java.lang.String sellUserId;	
	private java.lang.String userId;	
	private java.lang.Long amount;	
	private java.lang.String remark;	
	private java.lang.String status;	
	private Date addtime;			

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setImNo(java.lang.String imNo) {
		this.imNo = imNo;
	}
	
	public java.lang.String getImNo() {
		return this.imNo;
	}
	public void setBbsId(java.lang.String bbsId) {
		this.bbsId = bbsId;
	}
	
	public java.lang.String getBbsId() {
		return this.bbsId;
	}
	public void setSellUserId(java.lang.String sellUserId) {
		this.sellUserId = sellUserId;
	}
	
	public java.lang.String getSellUserId() {
		return this.sellUserId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setAmount(java.lang.Long amount) {
		this.amount = amount;
	}
	
	public java.lang.Long getAmount() {
		return this.amount;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}
