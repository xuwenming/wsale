
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
@Table(name = "zc_reward")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcReward implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcReward";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_OBJECT_TYPE = "对象类型";
	public static final String ALIAS_OBJECT_ID = "对象ID";
	public static final String ALIAS_REWARD_FEE = "打赏金额";
	public static final String ALIAS_USER_ID = "打赏人";
	public static final String ALIAS_ADDTIME = "打赏时间";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAYTIME = "支付时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PAYTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String objectType;
	//@Length(max=36)
	private java.lang.String objectId;
	//
	private java.lang.Long rewardFee;
	//@Length(max=36)
	private java.lang.String userId;
	//
	private java.util.Date addtime;
	//@Length(max=4)
	private java.lang.String payStatus;
	//
	private java.util.Date paytime;
	//columns END


		public TzcReward(){
		}
		public TzcReward(String id) {
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
	
	@Column(name = "object_type", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(java.lang.String objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "object_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(java.lang.String objectId) {
		this.objectId = objectId;
	}
	
	@Column(name = "reward_fee", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getRewardFee() {
		return this.rewardFee;
	}
	
	public void setRewardFee(java.lang.Long rewardFee) {
		this.rewardFee = rewardFee;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	

	@Column(name = "paytime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getPaytime() {
		return this.paytime;
	}
	
	public void setPaytime(java.util.Date paytime) {
		this.paytime = paytime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ObjectType",getObjectType())
			.append("ObjectId",getObjectId())
			.append("RewardFee",getRewardFee())
			.append("UserId",getUserId())
			.append("Addtime",getAddtime())
			.append("PayStatus",getPayStatus())
			.append("Paytime",getPaytime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcReward == false) return false;
		if(this == obj) return true;
		ZcReward other = (ZcReward)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

