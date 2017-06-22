
/*
 * @author John
 * @date - 2017-06-21
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_topic_comment")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcTopicComment implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcTopicComment";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TOPIC_ID = "专题ID";
	public static final String ALIAS_COMMENT = "留言内容";
	public static final String ALIAS_CTYPE = "类型：text、image、audio";
	public static final String ALIAS_PID = "父留言ID";
	public static final String ALIAS_IS_DELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_USER_ID = "留言人";
	public static final String ALIAS_ADDTIME = "留言时间";
	public static final String ALIAS_AUDIT_STATUS = "审核状态";
	public static final String ALIAS_AUDIT_TIME = "审核时间";
	public static final String ALIAS_AUDIT_USER_ID = "审核人";
	public static final String ALIAS_AUDIT_REMARK = "审核备注";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_AUDIT_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String topicId;
	//@Length(max=500)
	private java.lang.String comment;
	//@Length(max=18)
	private java.lang.String ctype;
	//@Length(max=36)
	private java.lang.String pid;
	//
	private java.lang.Boolean isDeleted;
	//@Length(max=36)
	private java.lang.String userId;
	//
	private java.util.Date addtime;
	//@Length(max=4)
	private java.lang.String auditStatus;
	//
	private java.util.Date auditTime;
	//@Length(max=36)
	private java.lang.String auditUserId;
	//@Length(max=500)
	private java.lang.String auditRemark;
	//columns END


		public TzcTopicComment(){
		}
		public TzcTopicComment(String id) {
			this.id = id;
		}
	

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "topic_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getTopicId() {
		return this.topicId;
	}
	
	public void setTopicId(java.lang.String topicId) {
		this.topicId = topicId;
	}
	
	@Column(name = "comment", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getComment() {
		return this.comment;
	}
	
	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}
	
	@Column(name = "ctype", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public java.lang.String getCtype() {
		return this.ctype;
	}
	
	public void setCtype(java.lang.String ctype) {
		this.ctype = ctype;
	}
	
	@Column(name = "pid", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getPid() {
		return this.pid;
	}
	
	public void setPid(java.lang.String pid) {
		this.pid = pid;
	}
	
	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(java.lang.Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "audit_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getAuditStatus() {
		return this.auditStatus;
	}
	
	public void setAuditStatus(java.lang.String auditStatus) {
		this.auditStatus = auditStatus;
	}
	

	@Column(name = "audit_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@Column(name = "audit_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getAuditUserId() {
		return this.auditUserId;
	}
	
	public void setAuditUserId(java.lang.String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	@Column(name = "audit_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getAuditRemark() {
		return this.auditRemark;
	}
	
	public void setAuditRemark(java.lang.String auditRemark) {
		this.auditRemark = auditRemark;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TopicId",getTopicId())
			.append("Comment",getComment())
			.append("Ctype",getCtype())
			.append("Pid",getPid())
			.append("IsDeleted",getIsDeleted())
			.append("UserId",getUserId())
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
		if(obj instanceof ZcTopicComment == false) return false;
		if(this == obj) return true;
		ZcTopicComment other = (ZcTopicComment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

