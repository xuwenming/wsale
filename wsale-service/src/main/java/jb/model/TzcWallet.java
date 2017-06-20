
/*
 * @author John
 * @date - 2016-10-30
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_wallet")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcWallet implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcWallet";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_AMOUNT = "可用余额";
	public static final String ALIAS_FROZEN_AMOUNT = "冻结款项";
	public static final String ALIAS_PAY_PASSWORD = "支付密码";
	public static final String ALIAS_REAL_NAME = "姓名";
	public static final String ALIAS_ID_NO = "证件号码";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_UPDATETIME = "更新时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String userId;
	//
	private java.lang.Double amount;
	//
	private java.lang.Double frozenAmount;

	private java.lang.Double protection;
	//@Length(max=100)
	private java.lang.String payPassword;
	//@Length(max=36)
	private java.lang.String realName;
	//@Length(max=36)
	private java.lang.String idNo;
	//
	private java.util.Date addtime;
	//
	private java.util.Date updatetime;
	//columns END


		public TzcWallet(){
		}
		public TzcWallet(String id) {
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
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.lang.Double getAmount() {
		return this.amount;
	}
	
	public void setAmount(java.lang.Double amount) {
		this.amount = amount;
	}
	
	@Column(name = "frozen_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.lang.Double getFrozenAmount() {
		return this.frozenAmount;
	}
	
	public void setFrozenAmount(java.lang.Double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	@Column(name = "protection", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getProtection() {
		return protection;
	}

	public void setProtection(Double protection) {
		this.protection = protection;
	}

	@Column(name = "pay_password", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getPayPassword() {
		return this.payPassword;
	}
	
	public void setPayPassword(java.lang.String payPassword) {
		this.payPassword = payPassword;
	}
	
	@Column(name = "real_name", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getRealName() {
		return this.realName;
	}
	
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	
	@Column(name = "id_no", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getIdNo() {
		return this.idNo;
	}
	
	public void setIdNo(java.lang.String idNo) {
		this.idNo = idNo;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	

	@Column(name = "updatetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Amount",getAmount())
			.append("FrozenAmount",getFrozenAmount())
			.append("PayPassword",getPayPassword())
			.append("RealName",getRealName())
			.append("IdNo",getIdNo())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcWallet == false) return false;
		if(this == obj) return true;
		ZcWallet other = (ZcWallet)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

