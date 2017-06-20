
/*
 * @author John
 * @date - 2017-06-01
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_chat_friend")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcChatFriend implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcChatFriend";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_FRIEND_USER_ID = "好友id";
	public static final String ALIAS_IS_DELETED = "0:未删除 1：删除";
	public static final String ALIAS_LAST_CONTENT = "最后消息内容";
	public static final String ALIAS_LAST_TIME = "最后消息时间";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_LAST_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=36)
	private java.lang.String friendUserId;
	//
	private java.lang.Boolean isDeleted;
	//@Length(max=2147483647)
	private java.lang.String lastContent;
	//
	private java.util.Date lastTime;
	//
	private java.util.Date addtime;
	//columns END


		public TzcChatFriend(){
		}
		public TzcChatFriend(String id) {
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
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "friend_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getFriendUserId() {
		return this.friendUserId;
	}
	
	public void setFriendUserId(java.lang.String friendUserId) {
		this.friendUserId = friendUserId;
	}
	
	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(java.lang.Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Column(name = "last_content", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public java.lang.String getLastContent() {
		return this.lastContent;
	}
	
	public void setLastContent(java.lang.String lastContent) {
		this.lastContent = lastContent;
	}
	

	@Column(name = "last_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getLastTime() {
		return this.lastTime;
	}
	
	public void setLastTime(java.util.Date lastTime) {
		this.lastTime = lastTime;
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
			.append("UserId",getUserId())
			.append("FriendUserId",getFriendUserId())
			.append("IsDeleted",getIsDeleted())
			.append("LastContent",getLastContent())
			.append("LastTime",getLastTime())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcChatFriend == false) return false;
		if(this == obj) return true;
		ZcChatFriend other = (ZcChatFriend)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

