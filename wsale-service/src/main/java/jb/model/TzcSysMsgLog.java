
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
@Table(name = "zc_sys_msg_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcSysMsgLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcSysMsgLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_SYS_MSG_ID = "消息id";
	public static final String ALIAS_MTYPE = "消息类型";
	public static final String ALIAS_TIME_UNIT = "时间单位";
	public static final String ALIAS_CONTENT = "消息内容";
	public static final String ALIAS_IS_READ = "是否已读（1:已读；0：未读）";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String sysMsgId;
	//@Length(max=4)
	private java.lang.String mtype;
	//@Length(max=20)
	private java.lang.String timeUnit;
	//@Length(max=500)
	private java.lang.String content;
	//
	private java.lang.Boolean isRead;
	//
	private java.util.Date addtime;
	//columns END


		public TzcSysMsgLog(){
		}
		public TzcSysMsgLog(String id) {
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
	
	@Column(name = "sys_msg_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getSysMsgId() {
		return this.sysMsgId;
	}
	
	public void setSysMsgId(java.lang.String sysMsgId) {
		this.sysMsgId = sysMsgId;
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
	public java.lang.Boolean getIsRead() {
		return this.isRead;
	}
	
	public void setIsRead(java.lang.Boolean isRead) {
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
			.append("SysMsgId",getSysMsgId())
			.append("Mtype",getMtype())
			.append("TimeUnit",getTimeUnit())
			.append("Content",getContent())
			.append("IsRead",getIsRead())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcSysMsgLog == false) return false;
		if(this == obj) return true;
		ZcSysMsgLog other = (ZcSysMsgLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

