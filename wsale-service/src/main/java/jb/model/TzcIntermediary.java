
/*
 * @author John
 * @date - 2017-07-11
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_intermediary")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcIntermediary implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcIntermediary";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_IM_NO = "交易编号";
	public static final String ALIAS_BBS_ID = "帖子标题";
	public static final String ALIAS_SELL_USER_ID = "卖家";
	public static final String ALIAS_USER_ID = "买家";
	public static final String ALIAS_AMOUNT = "交易金额";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_ADDTIME = "申请时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@NotBlank @Length(max=64)
	private java.lang.String imNo;
	//@Length(max=36)
	private java.lang.String bbsId;
	//@Length(max=36)
	private java.lang.String sellUserId;
	//@Length(max=36)
	private java.lang.String userId;
	//
	private java.lang.Long amount;
	//@Length(max=500)
	private java.lang.String remark;
	//@Length(max=4)
	private java.lang.String status;
	//
	private java.util.Date addtime;
	//columns END


		public TzcIntermediary(){
		}
		public TzcIntermediary(String id) {
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
	
	@Column(name = "im_no", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
	public java.lang.String getImNo() {
		return this.imNo;
	}
	
	public void setImNo(java.lang.String imNo) {
		this.imNo = imNo;
	}
	
	@Column(name = "bbs_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getBbsId() {
		return this.bbsId;
	}
	
	public void setBbsId(java.lang.String bbsId) {
		this.bbsId = bbsId;
	}
	
	@Column(name = "sell_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getSellUserId() {
		return this.sellUserId;
	}
	
	public void setSellUserId(java.lang.String sellUserId) {
		this.sellUserId = sellUserId;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getAmount() {
		return this.amount;
	}
	
	public void setAmount(java.lang.Long amount) {
		this.amount = amount;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ImNo",getImNo())
			.append("BbsId",getBbsId())
			.append("SellUserId",getSellUserId())
			.append("UserId",getUserId())
			.append("Amount",getAmount())
			.append("Remark",getRemark())
			.append("Status",getStatus())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcIntermediary == false) return false;
		if(this == obj) return true;
		ZcIntermediary other = (ZcIntermediary)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

