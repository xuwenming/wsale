package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcPositionApply implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String categoryId;
	private String roleId;
	private String applyUserId;
	private String applyContent;
	private String recommend;
	private String companyName;
	private String specialty;
	private String advice;
	private String activityForum;
	private Integer onlineDuration;
	private String auditStatus;
	private Date auditTime;			
	private String auditUserId;
	private String auditRemark;
	private Date addtime;			
	private String payStatus;
	private Date paytime;

	private String categoryName;
	private String roleName;
	private String applyUserName;
	private String auditUserName;

	public String getAuditStatusZh() {
		return Application.getString(this.auditStatus);
	}

	public String getPayStatusZh() {
		return Application.getString(this.payStatus);
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryId() {
		return this.categoryId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleId() {
		return this.roleId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getApplyUserId() {
		return this.applyUserId;
	}
	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}
	
	public String getApplyContent() {
		return this.applyContent;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	public String getRecommend() {
		return this.recommend;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String getSpecialty() {
		return this.specialty;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
	public String getAdvice() {
		return this.advice;
	}
	public void setActivityForum(String activityForum) {
		this.activityForum = activityForum;
	}
	
	public String getActivityForum() {
		return this.activityForum;
	}
	public void setOnlineDuration(Integer onlineDuration) {
		this.onlineDuration = onlineDuration;
	}
	
	public Integer getOnlineDuration() {
		return this.onlineDuration;
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

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
}
