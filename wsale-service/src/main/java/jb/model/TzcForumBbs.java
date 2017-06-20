
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
@Table(name = "zc_forum_bbs")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcForumBbs implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcForumBbs";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CATEGORY_ID = "分类ID";
	public static final String ALIAS_BBS_TITLE = "帖子标题";
	public static final String ALIAS_BBS_CONTENT = "帖子内容";
	public static final String ALIAS_BBS_TYPE = "帖子类别";
	public static final String ALIAS_BBS_STATUS = "帖子状态（打开、关闭）";
	public static final String ALIAS_IS_DELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_IS_OFF_REPLY = "是否关闭回复";
	public static final String ALIAS_IS_TOP = "是否置顶";
	public static final String ALIAS_IS_LIGHT = "是否加亮";
	public static final String ALIAS_IS_ESSENCE = "是否加精";
	public static final String ALIAS_ADD_USER_ID = "发帖人ID";
	public static final String ALIAS_ADDTIME = "发帖时间";
	public static final String ALIAS_UPDATE_USER_ID = "更新人ID";
	public static final String ALIAS_UPDATETIME = "更新时间";
	public static final String ALIAS_BBS_COMMENT = "评论数";
	public static final String ALIAS_BBS_READ = "围观数";
	public static final String ALIAS_BBS_REWARD = "打赏数";
	public static final String ALIAS_BBS_SHARE = "转发数";
	public static final String ALIAS_BBS_LISTEN = "收听数";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String categoryId;
	//@Length(max=100)
	private String bbsTitle;
	//@Length(max=500)
	private String bbsContent;
	//@Length(max=4)
	private String bbsType;
	//@Length(max=4)
	private String bbsStatus;
	//
	private Boolean isDeleted;
	//
	private Boolean isOffReply;
	//
	private Boolean isTop;
	//
	private Boolean isLight;
	//
	private Boolean isEssence;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	//@Length(max=36)
	private String updateUserId;
	//
	private Date updatetime;
	//
	private Integer bbsComment;
	//
	private Integer bbsRead;
	//
	private Integer bbsReward;
	//
	private Integer bbsShare;
	//
	private Integer bbsListen;
	private Date lastCommentTime;
	private String lastUpdateUserId;
	private Date lastUpdateTime;

	private Boolean isHomeHot;
	private Integer seq;
	//columns END


		public TzcForumBbs(){
		}
		public TzcForumBbs(String id) {
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
	
	@Column(name = "category_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Column(name = "bbs_title", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getBbsTitle() {
		return this.bbsTitle;
	}
	
	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}
	
	@Column(name = "bbs_content", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getBbsContent() {
		return this.bbsContent;
	}
	
	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
	}
	
	@Column(name = "bbs_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getBbsType() {
		return this.bbsType;
	}
	
	public void setBbsType(String bbsType) {
		this.bbsType = bbsType;
	}
	
	@Column(name = "bbs_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getBbsStatus() {
		return this.bbsStatus;
	}
	
	public void setBbsStatus(String bbsStatus) {
		this.bbsStatus = bbsStatus;
	}
	
	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Column(name = "isOffReply", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsOffReply() {
		return this.isOffReply;
	}
	
	public void setIsOffReply(Boolean isOffReply) {
		this.isOffReply = isOffReply;
	}
	
	@Column(name = "isTop", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsTop() {
		return this.isTop;
	}
	
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	
	@Column(name = "isLight", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsLight() {
		return this.isLight;
	}
	
	public void setIsLight(Boolean isLight) {
		this.isLight = isLight;
	}
	
	@Column(name = "isEssence", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsEssence() {
		return this.isEssence;
	}
	
	public void setIsEssence(Boolean isEssence) {
		this.isEssence = isEssence;
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
	
	@Column(name = "bbs_comment", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBbsComment() {
		return this.bbsComment;
	}
	
	public void setBbsComment(Integer bbsComment) {
		this.bbsComment = bbsComment;
	}
	
	@Column(name = "bbs_read", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBbsRead() {
		return this.bbsRead;
	}
	
	public void setBbsRead(Integer bbsRead) {
		this.bbsRead = bbsRead;
	}
	
	@Column(name = "bbs_reward", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBbsReward() {
		return this.bbsReward;
	}
	
	public void setBbsReward(Integer bbsReward) {
		this.bbsReward = bbsReward;
	}
	
	@Column(name = "bbs_share", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBbsShare() {
		return this.bbsShare;
	}
	
	public void setBbsShare(Integer bbsShare) {
		this.bbsShare = bbsShare;
	}
	
	@Column(name = "bbs_listen", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBbsListen() {
		return this.bbsListen;
	}
	
	public void setBbsListen(Integer bbsListen) {
		this.bbsListen = bbsListen;
	}

	@Column(name = "last_comment_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getLastCommentTime() {
		return lastCommentTime;
	}

	public void setLastCommentTime(Date lastCommentTime) {
		this.lastCommentTime = lastCommentTime;
	}

	@Column(name = "last_update_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	@Column(name = "last_update_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "isHomeHot", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsHomeHot() {
		return isHomeHot;
	}

	public void setIsHomeHot(Boolean isHomeHot) {
		this.isHomeHot = isHomeHot;
	}

	@Column(name = "seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CategoryId",getCategoryId())
			.append("BbsTitle",getBbsTitle())
			.append("BbsContent",getBbsContent())
			.append("BbsType",getBbsType())
			.append("BbsStatus",getBbsStatus())
			.append("IsDeleted",getIsDeleted())
			.append("IsOffReply",getIsOffReply())
			.append("IsTop",getIsTop())
			.append("IsLight",getIsLight())
			.append("IsEssence",getIsEssence())
			.append("AddUserId",getAddUserId())
			.append("Addtime",getAddtime())
			.append("UpdateUserId",getUpdateUserId())
			.append("Updatetime",getUpdatetime())
			.append("BbsComment",getBbsComment())
			.append("BbsRead",getBbsRead())
			.append("BbsReward",getBbsReward())
			.append("BbsShare",getBbsShare())
			.append("BbsListen",getBbsListen())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcForumBbs == false) return false;
		if(this == obj) return true;
		ZcForumBbs other = (ZcForumBbs)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

