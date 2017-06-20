
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
@Table(name = "zc_bbs_reward")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcBbsReward implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcBbsReward";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BBS_ID = "帖子ID";
	public static final String ALIAS_REWARD_FEE = "打赏金额";
	public static final String ALIAS_USER_ID = "打赏人";
	public static final String ALIAS_ADDTIME = "打赏时间";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String bbsId;
	//
	private Double rewardFee;
	//@Length(max=36)
	private String userId;
	//
	private Date addtime;
	//@Length(max=4)
	private String payStatus;
	private Date paytime;
	//columns END


		public TzcBbsReward(){
		}
		public TzcBbsReward(String id) {
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
	
	@Column(name = "reward_fee", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getRewardFee() {
		return this.rewardFee;
	}
	
	public void setRewardFee(Double rewardFee) {
		this.rewardFee = rewardFee;
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
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	@Column(name = "paytime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("BbsId",getBbsId())
			.append("RewardFee",getRewardFee())
			.append("UserId",getUserId())
			.append("Addtime",getAddtime())
			.append("PayStatus",getPayStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcBbsReward == false) return false;
		if(this == obj) return true;
		ZcBbsReward other = (ZcBbsReward)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

