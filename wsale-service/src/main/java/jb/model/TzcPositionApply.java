
/*
 * @author John
 * @date - 2016-08-15
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_position_apply")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcPositionApply implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcPositionApply";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CATEGORY_ID = "所属分类";
	public static final String ALIAS_ROLE_ID = "申请职位";
	public static final String ALIAS_APPLY_USER_ID = "申请人";
	public static final String ALIAS_APPLY_CONTENT = "申请内容";
	public static final String ALIAS_RECOMMEND = "推荐人";
	public static final String ALIAS_COMPANY_NAME = "就职单位";
	public static final String ALIAS_SPECIALTY = "个人专长/特长";
	public static final String ALIAS_ADVICE = "版面发展/建议";
	public static final String ALIAS_ACTIVITY_FORUM = "经常参与的版块";
	public static final String ALIAS_ONLINE_DURATION = "在线时长(小时)";
	public static final String ALIAS_AUDIT_STATUS = "审核状态";
	public static final String ALIAS_AUDIT_TIME = "审核时间";
	public static final String ALIAS_AUDIT_USER_ID = "审核人";
	public static final String ALIAS_ADDTIME = "申请时间";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	
	//date formats
	public static final String FORMAT_AUDIT_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String categoryId;
	//@Length(max=36)
	private String roleId;
	//@Length(max=36)
	private String applyUserId;
	//@Length(max=500)
	private String applyContent;
	//@Length(max=36)
	private String recommend;
	//@Length(max=100)
	private String companyName;
	//@Length(max=500)
	private String specialty;
	//@Length(max=500)
	private String advice;
	//@Length(max=100)
	private String activityForum;
	//
	private Integer onlineDuration;
	//@Length(max=4)
	private String auditStatus;
	//
	private Date auditTime;
	//@Length(max=36)
	private String auditUserId;
	//@Length(max=500)
	private String auditRemark;
	//
	private Date addtime;
	//@Length(max=4)
	private String payStatus;
	//
	private Date paytime;
	//columns END


	public TzcPositionApply(){
		}
		public TzcPositionApply(String id) {
			this.id = id;
		}
	

	public void setId(String id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}
	
	@Column(name = "category_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Column(name = "role_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "apply_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getApplyUserId() {
		return this.applyUserId;
	}
	
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	@Column(name = "apply_content", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getApplyContent() {
		return this.applyContent;
	}
	
	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}
	
	@Column(name = "recommend", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getRecommend() {
		return this.recommend;
	}
	
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	@Column(name = "company_name", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Column(name = "specialty", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getSpecialty() {
		return this.specialty;
	}
	
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Column(name = "advice", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getAdvice() {
		return this.advice;
	}
	
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
	@Column(name = "activity_forum", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getActivityForum() {
		return this.activityForum;
	}
	
	public void setActivityForum(String activityForum) {
		this.activityForum = activityForum;
	}
	
	@Column(name = "online_duration", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getOnlineDuration() {
		return this.onlineDuration;
	}
	
	public void setOnlineDuration(Integer onlineDuration) {
		this.onlineDuration = onlineDuration;
	}
	
	@Column(name = "audit_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getAuditStatus() {
		return this.auditStatus;
	}
	
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	

	@Column(name = "audit_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAuditTime() {
		return this.auditTime;
	}
	
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@Column(name = "audit_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAuditUserId() {
		return this.auditUserId;
	}
	
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}

	@Column(name = "audit_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "paytime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CategoryId",getCategoryId())
			.append("RoleId",getRoleId())
			.append("ApplyUserId",getApplyUserId())
			.append("ApplyContent",getApplyContent())
			.append("Recommend",getRecommend())
			.append("CompanyName",getCompanyName())
			.append("Specialty",getSpecialty())
			.append("Advice",getAdvice())
			.append("ActivityForum",getActivityForum())
			.append("OnlineDuration",getOnlineDuration())
			.append("AuditStatus",getAuditStatus())
			.append("AuditTime",getAuditTime())
			.append("AuditUserId",getAuditUserId())
			.append("Addtime",getAddtime())
			.append("PayStatus",getPayStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcPositionApply == false) return false;
		if(this == obj) return true;
		ZcPositionApply other = (ZcPositionApply)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

