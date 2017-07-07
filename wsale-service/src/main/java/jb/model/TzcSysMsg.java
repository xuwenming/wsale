
/*
 * @author John
 * @date - 2017-07-06
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_sys_msg")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcSysMsg implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcSysMsg";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_OBJECT_TYPE = "对象类型";
	public static final String ALIAS_OBJECT_ID = "对象ID";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_MTYPE = "消息类型";
	public static final String ALIAS_TIME_UNIT = "时间单位";
	public static final String ALIAS_CONTENT = "消息内容";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String objectType;
	//@Length(max=36)
	private java.lang.String objectId;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=4)
	private java.lang.String mtype;
	//@Length(max=20)
	private java.lang.String timeUnit;
	//@Length(max=500)
	private java.lang.String content;
	private Boolean isRead;
	//
	private java.util.Date addtime;
	//columns END


		public TzcSysMsg(){
		}
		public TzcSysMsg(String id) {
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
	
	@Column(name = "object_type", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(java.lang.String objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "object_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(java.lang.String objectId) {
		this.objectId = objectId;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "mtype", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getMtype() {
		return this.mtype;
	}
	
	public void setMtype(java.lang.String mtype) {
		this.mtype = mtype;
	}
	
	@Column(name = "time_unit", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(java.lang.String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
	}

	@Column(name = "is_read", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ObjectType",getObjectType())
			.append("ObjectId",getObjectId())
			.append("UserId",getUserId())
			.append("Mtype",getMtype())
			.append("TimeUnit",getTimeUnit())
			.append("Content",getContent())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcSysMsg == false) return false;
		if(this == obj) return true;
		ZcSysMsg other = (ZcSysMsg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

