
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
@Table(name = "zc_intermediary_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcIntermediaryLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcIntermediaryLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_IM_ID = "中介交易ID";
	public static final String ALIAS_USER_ID = "操作人";
	public static final String ALIAS_LOG_TYPE = "日志类型";
	public static final String ALIAS_CONTENT = "日志内容";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String imId;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=4)
	private java.lang.String logType;
	//@Length(max=500)
	private java.lang.String content;
	//
	private java.util.Date addtime;
	//columns END


		public TzcIntermediaryLog(){
		}
		public TzcIntermediaryLog(String id) {
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
	
	@Column(name = "im_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getImId() {
		return this.imId;
	}
	
	public void setImId(java.lang.String imId) {
		this.imId = imId;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "log_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getLogType() {
		return this.logType;
	}
	
	public void setLogType(java.lang.String logType) {
		this.logType = logType;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
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
			.append("ImId",getImId())
			.append("UserId",getUserId())
			.append("LogType",getLogType())
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
		if(obj instanceof ZcIntermediaryLog == false) return false;
		if(this == obj) return true;
		ZcIntermediaryLog other = (ZcIntermediaryLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

