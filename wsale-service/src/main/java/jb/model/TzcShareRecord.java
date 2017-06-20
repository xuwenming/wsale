
/*
 * @author John
 * @date - 2016-08-22
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_share_record")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcShareRecord implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcShareRecord";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BBS_ID = "帖子ID";
	public static final String ALIAS_SHARE_CHANNEL = "分享渠道";
	public static final String ALIAS_USER_ID = "转发用户";
	public static final String ALIAS_ADDTIME = "转发时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String bbsId;
	//@Length(max=18)
	private String shareChannel;
	//@Length(max=36)
	private String userId;
	//
	private Date addtime;
	//columns END


		public TzcShareRecord(){
		}
		public TzcShareRecord(String id) {
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
	
	@Column(name = "share_channel", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getShareChannel() {
		return this.shareChannel;
	}
	
	public void setShareChannel(String shareChannel) {
		this.shareChannel = shareChannel;
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
			.append("ShareChannel",getShareChannel())
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
		if(obj instanceof ZcShareRecord == false) return false;
		if(this == obj) return true;
		ZcShareRecord other = (ZcShareRecord)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

