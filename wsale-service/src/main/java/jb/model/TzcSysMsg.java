
/*
 * @author John
 * @date - 2017-07-11
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
	public static final String ALIAS_NEWTIME = "最新消息时间";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_NEWTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
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
	//
	private java.util.Date newtime;
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
	

	@Column(name = "newtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getNewtime() {
		return this.newtime;
	}
	
	public void setNewtime(java.util.Date newtime) {
		this.newtime = newtime;
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
			.append("Newtime",getNewtime())
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

