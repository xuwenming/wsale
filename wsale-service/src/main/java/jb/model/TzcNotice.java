
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
@Table(name = "zc_notice")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcNotice implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcNotice";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CONTENT = "消息内容";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_ADD_USER_ID = "创建人ID";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_UPDATE_USER_ID = "更新人ID";
	public static final String ALIAS_UPDATETIME = "更新时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=2147483647)
	private java.lang.String content;
	//@Length(max=4)
	private java.lang.String status;
	private Boolean isDeleted;
	//@Length(max=36)
	private java.lang.String addUserId;
	//
	private java.util.Date addtime;
	//@Length(max=36)
	private java.lang.String updateUserId;
	//
	private java.util.Date updatetime;
	//columns END


		public TzcNotice(){
		}
		public TzcNotice(String id) {
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
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "addUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getAddUserId() {
		return this.addUserId;
	}
	
	public void setAddUserId(java.lang.String addUserId) {
		this.addUserId = addUserId;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "updateUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUpdateUserId() {
		return this.updateUserId;
	}
	
	public void setUpdateUserId(java.lang.String updateUserId) {
		this.updateUserId = updateUserId;
	}
	

	@Column(name = "updatetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Content",getContent())
			.append("Status",getStatus())
			.append("AddUserId",getAddUserId())
			.append("Addtime",getAddtime())
			.append("UpdateUserId",getUpdateUserId())
			.append("Updatetime",getUpdatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcNotice == false) return false;
		if(this == obj) return true;
		ZcNotice other = (ZcNotice)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

