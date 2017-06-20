
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
@Table(name = "zc_bbs_comment")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcBbsComment implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcBbsComment";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BBS_ID = "帖子ID";
	public static final String ALIAS_COMMENT = "评论内容";
	public static final String ALIAS_PID = "父评论ID";
	public static final String ALIAS_IS_DELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_USER_ID = "评论人";
	public static final String ALIAS_ADDTIME = "评论时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String bbsId;
	//@Length(max=500)
	private String comment;
	private String ctype;
	//@Length(max=36)
	private String pid;
	private String fid;
	//
	private Boolean isDeleted;
	//@Length(max=36)
	private String userId;
	//
	private Date addtime;
	//columns END


		public TzcBbsComment(){
		}
		public TzcBbsComment(String id) {
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
	
	@Column(name = "bbs_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getBbsId() {
		return this.bbsId;
	}
	
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}
	
	@Column(name = "comment", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "ctype", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	@Column(name = "pid", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getPid() {
		return this.pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "fid", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
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
			.append("BbsId",getBbsId())
			.append("Comment",getComment())
			.append("Pid",getPid())
			.append("IsDeleted",getIsDeleted())
			.append("UserId",getUserId())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcBbsComment == false) return false;
		if(this == obj) return true;
		ZcBbsComment other = (ZcBbsComment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

