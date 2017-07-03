
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
@Table(name = "zc_shieldorfans")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcShieldorfans implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcShieldorfans";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_OBJECT_TYPE = "类型 1粉丝2屏蔽";
	public static final String ALIAS_OBJECT_BY_ID = "被关注Id/被屏蔽人ID";
	public static final String ALIAS_OBJECT_ID = "关注人Id/屏蔽人ID";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=4)
	private String objectType;
	//@Length(max=36)
	private String objectById;
	//@Length(max=36)
	private String objectId;
	private Boolean isDeleted;
	//
	private Date addtime;
	//columns END


		public TzcShieldorfans(){
		}
		public TzcShieldorfans(String id) {
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
	
	@Column(name = "object_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "object_by_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getObjectById() {
		return this.objectById;
	}
	
	public void setObjectById(String objectById) {
		this.objectById = objectById;
	}
	
	@Column(name = "object_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
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
			.append("ObjectType",getObjectType())
			.append("ObjectById",getObjectById())
			.append("ObjectId",getObjectId())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcShieldorfans == false) return false;
		if(this == obj) return true;
		ZcShieldorfans other = (ZcShieldorfans)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

