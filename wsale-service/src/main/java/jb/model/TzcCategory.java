
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
@Table(name = "zc_category")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcCategory implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcCategory";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_ICON = "icon图标";
	public static final String ALIAS_SEQ = "排序";
	public static final String ALIAS_PID = "上级分类";
	public static final String ALIAS_FORUM_INTRODUCE = "版块介绍";
	public static final String ALIAS_CHIEF_MODERATOR_ID = "首席版主";
	public static final String ALIAS_ADD_USER_ID = "创建人";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_UPDATE_USER_ID = "更新人ID";
	public static final String ALIAS_UPDATETIME = "更新时间";
	public static final String ALIAS_IS_DELETED = "是否删除,1删除，0未删除";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String name;
	//@Length(max=100)
	private String icon;
	//@Length(max=500)
	private String summary;
	//
	private Integer seq;
	//@Length(max=36)
	private String pid;
	//@Length(max=500)
	private String forumIntroduce;
	//@Length(max=36)
	private String chiefModeratorId;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	//@Length(max=36)
	private String updateUserId;
	//
	private Date updatetime;
	//
	private Boolean isDeleted;
	//columns END

	private Integer autoRead;
		public TzcCategory(){
		}
		public TzcCategory(String id) {
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
	
	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getIcon() {
		return this.icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "summary", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Column(name = "pid", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getPid() {
		return this.pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name = "forum_introduce", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getForumIntroduce() {
		return this.forumIntroduce;
	}
	
	public void setForumIntroduce(String forumIntroduce) {
		this.forumIntroduce = forumIntroduce;
	}
	
	@Column(name = "chief_moderator_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getChiefModeratorId() {
		return this.chiefModeratorId;
	}
	
	public void setChiefModeratorId(String chiefModeratorId) {
		this.chiefModeratorId = chiefModeratorId;
	}
	
	@Column(name = "addUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAddUserId() {
		return this.addUserId;
	}
	
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "updateUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUpdateUserId() {
		return this.updateUserId;
	}
	
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	

	@Column(name = "updatetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "auto_read", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAutoRead() {
		return autoRead;
	}

	public void setAutoRead(Integer autoRead) {
		this.autoRead = autoRead;
	}
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Icon",getIcon())
			.append("Seq",getSeq())
			.append("Pid",getPid())
			.append("ForumIntroduce",getForumIntroduce())
			.append("ChiefModeratorId",getChiefModeratorId())
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
		if(obj instanceof ZcCategory == false) return false;
		if(this == obj) return true;
		ZcCategory other = (ZcCategory)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

