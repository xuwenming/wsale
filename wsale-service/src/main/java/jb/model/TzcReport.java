
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
@Table(name = "zc_report")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcReport implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcReport";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_OBJECT_TYPE = "举报对象类型";
	public static final String ALIAS_OBJECT_ID = "举报对象ID";
	public static final String ALIAS_REPORT_REASON = "举报理由";
	public static final String ALIAS_USER_ID = "举报人";
	public static final String ALIAS_ADDTIME = "举报时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String objectType;
	//@Length(max=36)
	private String objectId;
	//@Length(max=300)
	private String reportReason;
	//@Length(max=36)
	private String userId;
	//
	private Date addtime;
	//@Length(max=4)
	private java.lang.String handleStatus;
	//@Length(max=36)
	private java.lang.String handleUserId;
	//@Length(max=36)
	private java.lang.String handleRemark;
	//
	private java.util.Date handleTime;
	//columns END


		public TzcReport(){
		}
		public TzcReport(String id) {
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
	
	@Column(name = "object_type", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "object_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	@Column(name = "report_reason", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public String getReportReason() {
		return this.reportReason;
	}
	
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Column(name = "handle_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getHandleStatus() {
		return this.handleStatus;
	}

	public void setHandleStatus(java.lang.String handleStatus) {
		this.handleStatus = handleStatus;
	}

	@Column(name = "handle_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getHandleUserId() {
		return this.handleUserId;
	}

	public void setHandleUserId(java.lang.String handleUserId) {
		this.handleUserId = handleUserId;
	}

	@Column(name = "handle_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getHandleRemark() {
		return this.handleRemark;
	}

	public void setHandleRemark(java.lang.String handleRemark) {
		this.handleRemark = handleRemark;
	}


	@Column(name = "handle_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(java.util.Date handleTime) {
		this.handleTime = handleTime;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ObjectType",getObjectType())
			.append("ObjectId",getObjectId())
			.append("ReportReason",getReportReason())
			.append("UserId",getUserId())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcReport == false) return false;
		if(this == obj) return true;
		ZcReport other = (ZcReport)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

