
/*
 * @author John
 * @date - 2016-10-26
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_new_history")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcNewHistory implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcNewHistory";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_OPENID = "推送用户的openid";
	public static final String ALIAS_USER_ID = "推送拍品所属用户id";
	public static final String ALIAS_PRODUCT_IDS = "推送的拍品id集合";
	public static final String ALIAS_ADDTIME = "推送时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@NotBlank @Length(max=36)
	private String id;
	//@Length(max=36)
	private String openid;
	//@Length(max=36)
	private String userId;
	//@Length(max=500)
	private String productIds;
	private Boolean isRead;
	//
	private Date addtime;
	private Date updatetime;
	//columns END


		public TzcNewHistory(){
		}
		public TzcNewHistory(String id) {
			this.id = id;
		}


	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "openid", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getOpenid() {
		return this.openid;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "product_ids", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getProductIds() {
		return this.productIds;
	}
	
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	@Column(name = "is_read", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Column(name = "updatetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Openid",getOpenid())
			.append("UserId",getUserId())
			.append("ProductIds",getProductIds())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcNewHistory == false) return false;
		if(this == obj) return true;
		ZcNewHistory other = (ZcNewHistory)obj;
		return new EqualsBuilder()
			.isEquals();
	}*/
}

