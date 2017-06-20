
/*
 * @author John
 * @date - 2017-05-26
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_bbs_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcBbsLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcBbsLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_LOG_TYPE = "日志类型";
	public static final String ALIAS_BBS_ID = "帖子id";
	public static final String ALIAS_USER_ID = "操作人";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_ADDTIME = "操作时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=10)
	private java.lang.String logType;
	//@Length(max=36)
	private java.lang.String bbsId;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=512)
	private java.lang.String content;
	//@Length(max=512)
	private java.lang.String remark;
	//
	private java.util.Date addtime;
	//columns END


		public TzcBbsLog(){
		}
		public TzcBbsLog(String id) {
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
	
	@Column(name = "log_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getLogType() {
		return this.logType;
	}
	
	public void setLogType(java.lang.String logType) {
		this.logType = logType;
	}
	
	@Column(name = "bbs_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getBbsId() {
		return this.bbsId;
	}
	
	public void setBbsId(java.lang.String bbsId) {
		this.bbsId = bbsId;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
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
			.append("LogType",getLogType())
			.append("BbsId",getBbsId())
			.append("UserId",getUserId())
			.append("Content",getContent())
			.append("Remark",getRemark())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcBbsLog == false) return false;
		if(this == obj) return true;
		ZcBbsLog other = (ZcBbsLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

