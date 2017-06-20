
/*
 * @author John
 * @date - 2016-08-22
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_authentication")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcAuthentication implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcAuthentication";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_AUTH_TYPE = "类型，0个人1企业";
	public static final String ALIAS_USER_NAME = "姓名";
	public static final String ALIAS_ID_TYPE = "证件类型 1身份证2驾驶证";
	public static final String ALIAS_ID_NO = "证件号码";
	public static final String ALIAS_PHONE = "手机号码";
	public static final String ALIAS_ID_FRONT_BYHAND = "手持证件正面";
	public static final String ALIAS_ID_FRONT = "证件正面";
	public static final String ALIAS_ID_BACK = "证件反面";
	public static final String ALIAS_COMPANY_NAME = "企业名称";
	public static final String ALIAS_CREDIT_ID = "统一社会信用码";
	public static final String ALIAS_BUSSINESS_LICENSE = "营业执照";
	public static final String ALIAS_LEGAL_PERSON_NAME = "法人姓名";
	public static final String ALIAS_LEGAL_PERSON_ID = "法人身份证号";
	public static final String ALIAS_LEGAL_PERSON_ID_FRONT = "法人身份证正面";
	public static final String ALIAS_LEGAL_PERSON_ID_BACK = "法人身份证反面";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAYTIME = "支付时间";
	public static final String ALIAS_ADD_USER_ID = "创建人ID";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_AUDIT_STATUS = "审核状态";
	public static final String ALIAS_AUDIT_TIME = "审核时间";
	public static final String ALIAS_AUDIT_USER_ID = "审核人";
	public static final String ALIAS_AUDIT_REMARK = "审核备注";
	
	//date formats
	public static final String FORMAT_PAYTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_AUDIT_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=4)
	private String authType;
	//@Length(max=255)
	private String userName;
	//@Length(max=4)
	private String idType;
	//@Length(max=255)
	private String idNo;
	//@Length(max=255)
	private String phone;
	//@Length(max=255)
	private String idFrontByhand;
	//@Length(max=255)
	private String idFront;
	//@Length(max=255)
	private String idBack;
	//@Length(max=255)
	private String companyName;
	//@Length(max=255)
	private String creditId;
	//@Length(max=255)
	private String bussinessLicense;
	//@Length(max=255)
	private String legalPersonName;
	//@Length(max=255)
	private String legalPersonId;
	//@Length(max=255)
	private String legalPersonIdFront;
	//@Length(max=255)
	private String legalPersonIdBack;
	//@Length(max=4)
	private String payStatus;
	//
	private Date paytime;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	//@Length(max=4)
	private String auditStatus;
	//
	private Date auditTime;
	//@Length(max=36)
	private String auditUserId;
	//@Length(max=500)
	private String auditRemark;
	private Integer authStep;
	//columns END


		public TzcAuthentication(){
		}
		public TzcAuthentication(String id) {
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
	
	@Column(name = "auth_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getAuthType() {
		return this.authType;
	}
	
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	@Column(name = "user_name", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "id_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getIdType() {
		return this.idType;
	}
	
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Column(name = "id_no", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getIdNo() {
		return this.idNo;
	}
	
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Column(name = "phone", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "id_front_byhand", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getIdFrontByhand() {
		return this.idFrontByhand;
	}
	
	public void setIdFrontByhand(String idFrontByhand) {
		this.idFrontByhand = idFrontByhand;
	}
	
	@Column(name = "id_front", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getIdFront() {
		return this.idFront;
	}
	
	public void setIdFront(String idFront) {
		this.idFront = idFront;
	}
	
	@Column(name = "id_back", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getIdBack() {
		return this.idBack;
	}
	
	public void setIdBack(String idBack) {
		this.idBack = idBack;
	}
	
	@Column(name = "company_name", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Column(name = "credit_id", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getCreditId() {
		return this.creditId;
	}
	
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	
	@Column(name = "bussiness_license", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getBussinessLicense() {
		return this.bussinessLicense;
	}
	
	public void setBussinessLicense(String bussinessLicense) {
		this.bussinessLicense = bussinessLicense;
	}
	
	@Column(name = "legal_person_name", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getLegalPersonName() {
		return this.legalPersonName;
	}
	
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	
	@Column(name = "legal_person_id", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getLegalPersonId() {
		return this.legalPersonId;
	}
	
	public void setLegalPersonId(String legalPersonId) {
		this.legalPersonId = legalPersonId;
	}
	
	@Column(name = "legal_person_id_front", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getLegalPersonIdFront() {
		return this.legalPersonIdFront;
	}
	
	public void setLegalPersonIdFront(String legalPersonIdFront) {
		this.legalPersonIdFront = legalPersonIdFront;
	}
	
	@Column(name = "legal_person_id_back", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getLegalPersonIdBack() {
		return this.legalPersonIdBack;
	}
	
	public void setLegalPersonIdBack(String legalPersonIdBack) {
		this.legalPersonIdBack = legalPersonIdBack;
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
		return this.paytime;
	}
	
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	@Column(name = "addUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAddUserId() {
		return this.addUserId;
	}
	
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
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
		return this.auditRemark;
	}
	
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	@Column(name = "auth_step", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAuthStep() {
		return authStep;
	}

	public void setAuthStep(Integer authStep) {
		this.authStep = authStep;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AuthType",getAuthType())
			.append("UserName",getUserName())
			.append("IdType",getIdType())
			.append("IdNo",getIdNo())
			.append("Phone",getPhone())
			.append("IdFrontByhand",getIdFrontByhand())
			.append("IdFront",getIdFront())
			.append("IdBack",getIdBack())
			.append("CompanyName",getCompanyName())
			.append("CreditId",getCreditId())
			.append("BussinessLicense",getBussinessLicense())
			.append("LegalPersonName",getLegalPersonName())
			.append("LegalPersonId",getLegalPersonId())
			.append("LegalPersonIdFront",getLegalPersonIdFront())
			.append("LegalPersonIdBack",getLegalPersonIdBack())
			.append("PayStatus",getPayStatus())
			.append("Paytime",getPaytime())
			.append("AddUserId",getAddUserId())
			.append("Addtime",getAddtime())
			.append("AuditStatus",getAuditStatus())
			.append("AuditTime",getAuditTime())
			.append("AuditUserId",getAuditUserId())
			.append("AuditRemark",getAuditRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcAuthentication == false) return false;
		if(this == obj) return true;
		ZcAuthentication other = (ZcAuthentication)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

