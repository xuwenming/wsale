package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcAuthentication implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String authType;
	private String userName;
	private String idType;
	private String idNo;
	private String phone;
	private String idFrontByhand;
	private String idFront;
	private String idBack;
	private String companyName;
	private String creditId;
	private String bussinessLicense;
	private String legalPersonName;
	private String legalPersonId;
	private String legalPersonIdFront;
	private String legalPersonIdBack;
	private String payStatus;
	private Date paytime;			
	private String addUserId;
	private Date addtime;			
	private String auditStatus;
	private Date auditTime;			
	private String auditUserId;
	private String auditRemark;
	private Integer authStep;

	private String addUserName;
	private String auditUserName;
	private Date addtimeBegin;
	private Date addtimeEnd;

	public String getAuthTypeZh() {
		return Application.getString(this.authType);
	}

	public String getIdTypeZh() {
		return Application.getString(this.idType);
	}

	public String getPayStatusZh() {
		return Application.getString(this.payStatus);
	}

	public String getAuditStatusZh() {
		return Application.getString(this.auditStatus);
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	public String getAuthType() {
		return this.authType;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public String getIdType() {
		return this.idType;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getIdNo() {
		return this.idNo;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setIdFrontByhand(String idFrontByhand) {
		this.idFrontByhand = idFrontByhand;
	}
	
	public String getIdFrontByhand() {
		return this.idFrontByhand;
	}
	public void setIdFront(String idFront) {
		this.idFront = idFront;
	}
	
	public String getIdFront() {
		return this.idFront;
	}
	public void setIdBack(String idBack) {
		this.idBack = idBack;
	}
	
	public String getIdBack() {
		return this.idBack;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	
	public String getCreditId() {
		return this.creditId;
	}
	public void setBussinessLicense(String bussinessLicense) {
		this.bussinessLicense = bussinessLicense;
	}
	
	public String getBussinessLicense() {
		return this.bussinessLicense;
	}
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	
	public String getLegalPersonName() {
		return this.legalPersonName;
	}
	public void setLegalPersonId(String legalPersonId) {
		this.legalPersonId = legalPersonId;
	}
	
	public String getLegalPersonId() {
		return this.legalPersonId;
	}
	public void setLegalPersonIdFront(String legalPersonIdFront) {
		this.legalPersonIdFront = legalPersonIdFront;
	}
	
	public String getLegalPersonIdFront() {
		return this.legalPersonIdFront;
	}
	public void setLegalPersonIdBack(String legalPersonIdBack) {
		this.legalPersonIdBack = legalPersonIdBack;
	}
	
	public String getLegalPersonIdBack() {
		return this.legalPersonIdBack;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	public Date getPaytime() {
		return this.paytime;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	
	public String getAddUserId() {
		return this.addUserId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getAuditStatus() {
		return this.auditStatus;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public Date getAuditTime() {
		return this.auditTime;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	public String getAuditUserId() {
		return this.auditUserId;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	
	public String getAuditRemark() {
		return this.auditRemark;
	}

	public Integer getAuthStep() {
		return authStep;
	}

	public void setAuthStep(Integer authStep) {
		this.authStep = authStep;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public Date getAddtimeBegin() {
		return addtimeBegin;
	}

	public void setAddtimeBegin(Date addtimeBegin) {
		this.addtimeBegin = addtimeBegin;
	}

	public Date getAddtimeEnd() {
		return addtimeEnd;
	}

	public void setAddtimeEnd(Date addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}
}
