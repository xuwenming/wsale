
/*
 * @author John
 * @date - 2016-09-22
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_chat_msg")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcChatMsg implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcChatMsg";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_MTYPE = "消息类型";
	public static final String ALIAS_CONTENT = "消息内容";
	public static final String ALIAS_FROM_USER_ID = "发送人";
	public static final String ALIAS_TO_USER_ID = "接收人";
	public static final String ALIAS_UNREAD = "是否未读1：是 0：否";
	public static final String ALIAS_ADDTIME = "发送时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=18)
	private String mtype;
	//@Length(max=2147483647)
	private String content;
	//@Length(max=36)
	private String fromUserId;
	//@Length(max=36)
	private String toUserId;
	//
	private Boolean unread;
	private String msgId;
	private Integer duration;
	private Integer isDeleted;
	//
	private Date addtime;
	//columns END


		public TzcChatMsg(){
		}
		public TzcChatMsg(String id) {
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
	
	@Column(name = "mtype", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getMtype() {
		return this.mtype;
	}
	
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "from_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getFromUserId() {
		return this.fromUserId;
	}
	
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	@Column(name = "to_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getToUserId() {
		return this.toUserId;
	}
	
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	
	@Column(name = "unread", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getUnread() {
		return this.unread;
	}
	
	public void setUnread(Boolean unread) {
		this.unread = unread;
	}

	@Column(name = "msg_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	@Column(name = "duration", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Mtype",getMtype())
			.append("Content",getContent())
			.append("FromUserId",getFromUserId())
			.append("ToUserId",getToUserId())
			.append("Unread",getUnread())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcChatMsg == false) return false;
		if(this == obj) return true;
		ZcChatMsg other = (ZcChatMsg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

