package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcWallet implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String userId;	
	private java.lang.Double amount;	
	private java.lang.Double frozenAmount;	
	private java.lang.Double protection;
	private java.lang.String payPassword;
	private java.lang.String realName;	
	private java.lang.String idNo;	
	private Date addtime;			
	private Date updatetime;			

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setAmount(java.lang.Double amount) {
		this.amount = amount;
	}
	
	public java.lang.Double getAmount() {
		return this.amount;
	}
	public void setFrozenAmount(java.lang.Double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	
	public java.lang.Double getFrozenAmount() {
		return this.frozenAmount;
	}

	public Double getProtection() {
		return protection;
	}

	public void setProtection(Double protection) {
		this.protection = protection;
	}

	public void setPayPassword(java.lang.String payPassword) {
		this.payPassword = payPassword;
	}
	
	public java.lang.String getPayPassword() {
		return this.payPassword;
	}
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	
	public java.lang.String getRealName() {
		return this.realName;
	}
	public void setIdNo(java.lang.String idNo) {
		this.idNo = idNo;
	}
	
	public java.lang.String getIdNo() {
		return this.idNo;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime() {
		return this.updatetime;
	}

}
