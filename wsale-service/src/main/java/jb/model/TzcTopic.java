
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
@Table(name = "zc_topic")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcTopic implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcTopic";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TITLE = "专题标题";
	public static final String ALIAS_ICON = "封面icon";
	public static final String ALIAS_CONTENT = "专题内容";
	public static final String ALIAS_TOPIC_COMMENT = "留言数";
	public static final String ALIAS_TOPIC_READ = "阅读数";
	public static final String ALIAS_TOPIC_REWARD = "打赏数";
	public static final String ALIAS_TOPIC_PRAISE = "点赞数";
	public static final String ALIAS_TOPIC_COLLECT = "收藏数";
	public static final String ALIAS_SEQ = "热门排序";
	public static final String ALIAS_ADD_USER_ID = "发布人";
	public static final String ALIAS_ADDTIME = "发布时间";
	public static final String ALIAS_UPDATE_USER_ID = "更新人ID";
	public static final String ALIAS_UPDATETIME = "更新时间";
	public static final String ALIAS_IS_DELETED = "是否删除,1删除，0未删除";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private String categoryId;
	//@Length(max=100)
	private java.lang.String title;
	//@Length(max=255)
	private java.lang.String icon;
	//@Length(max=2147483647)
	private java.lang.String content;
	//
	private java.lang.Integer topicComment;
	//
	private java.lang.Integer topicRead;
	//
	private java.lang.Integer topicReward;
	//
	private java.lang.Integer topicPraise;
	//
	private java.lang.Integer topicCollect;
	//
	private java.lang.Integer seq;
	//@Length(max=36)
	private java.lang.String addUserId;
	//
	private java.util.Date addtime;
	//@Length(max=36)
	private java.lang.String updateUserId;
	//
	private java.util.Date updatetime;
	//
	private java.lang.Boolean isDeleted;
	//columns END


		public TzcTopic(){
		}
		public TzcTopic(String id) {
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

	@Column(name = "category_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public java.lang.String getIcon() {
		return this.icon;
	}
	
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	@Column(name = "topic_comment", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getTopicComment() {
		return this.topicComment;
	}
	
	public void setTopicComment(java.lang.Integer topicComment) {
		this.topicComment = topicComment;
	}
	
	@Column(name = "topic_read", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getTopicRead() {
		return this.topicRead;
	}
	
	public void setTopicRead(java.lang.Integer topicRead) {
		this.topicRead = topicRead;
	}
	
	@Column(name = "topic_reward", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getTopicReward() {
		return this.topicReward;
	}
	
	public void setTopicReward(java.lang.Integer topicReward) {
		this.topicReward = topicReward;
	}
	
	@Column(name = "topic_praise", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getTopicPraise() {
		return this.topicPraise;
	}
	
	public void setTopicPraise(java.lang.Integer topicPraise) {
		this.topicPraise = topicPraise;
	}
	
	@Column(name = "topic_collect", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getTopicCollect() {
		return this.topicCollect;
	}
	
	public void setTopicCollect(java.lang.Integer topicCollect) {
		this.topicCollect = topicCollect;
	}
	
	@Column(name = "seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(java.lang.Integer seq) {
		this.seq = seq;
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
	
	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(java.lang.Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Title",getTitle())
			.append("Icon",getIcon())
			.append("Content",getContent())
			.append("TopicComment",getTopicComment())
			.append("TopicRead",getTopicRead())
			.append("TopicReward",getTopicReward())
			.append("TopicPraise",getTopicPraise())
			.append("TopicCollect",getTopicCollect())
			.append("Seq",getSeq())
			.append("AddUserId",getAddUserId())
			.append("Addtime",getAddtime())
			.append("UpdateUserId",getUpdateUserId())
			.append("Updatetime",getUpdatetime())
			.append("IsDeleted",getIsDeleted())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcTopic == false) return false;
		if(this == obj) return true;
		ZcTopic other = (ZcTopic)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

