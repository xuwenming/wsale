
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
@Table(name = "zc_wallet_detail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcWalletDetail implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "ZcWalletDetail";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_ORDER_NO = "交易号";
	public static final String ALIAS_AMOUNT = "交易金额";
	public static final String ALIAS_WTYPE = "交易类型";
	public static final String ALIAS_DESCRIPTION = "交易描述";
	public static final String ALIAS_CHANNEL = "支付渠道";
	public static final String ALIAS_BANK_INFO = "银行信息（银行卡支付）";
	public static final String ALIAS_ADDTIME = "交易时间";
	public static final String ALIAS_HANDLE_STATUS = "处理状态";
	public static final String ALIAS_HANDLE_USER_ID = "处理人";
	public static final String ALIAS_HANDLE_REMARK = "处理结果";
	public static final String ALIAS_HANDLE_TIME = "处理时间";

	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_HANDLE_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;


	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=64)
	private java.lang.String orderNo;
	//
	private java.lang.Double amount;
	private java.lang.Double walletAmount;
	//@Length(max=4)
	private java.lang.String wtype;
	//@Length(max=255)
	private java.lang.String description;
	//@Length(max=4)
	private java.lang.String channel;
	//@Length(max=255)
	private java.lang.String bankInfo;
	//
	private java.util.Date addtime;
	//@Length(max=4)
	private java.lang.String handleStatus;
	//@Length(max=36)
	private java.lang.String handleUserId;
	//@Length(max=36)
	private java.lang.String handleRemark;
	//
	private java.util.Date handleTime;
	private java.lang.String bankAccount;
	private java.lang.String bankPhone;
	private java.lang.String bankIdNo;
	private java.lang.String bankCard;
	//columns END


	public TzcWalletDetail(){
	}
	public TzcWalletDetail(String id) {
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

	@Column(name = "order_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.lang.Double getAmount() {
		return this.amount;
	}

	public void setAmount(java.lang.Double amount) {
		this.amount = amount;
	}

	@Column(name = "wallet_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}

	@Column(name = "wtype", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getWtype() {
		return this.wtype;
	}

	public void setWtype(java.lang.String wtype) {
		this.wtype = wtype;
	}

	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	@Column(name = "channel", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getChannel() {
		return this.channel;
	}

	public void setChannel(java.lang.String channel) {
		this.channel = channel;
	}

	@Column(name = "bank_info", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public java.lang.String getBankInfo() {
		return this.bankInfo;
	}

	public void setBankInfo(java.lang.String bankInfo) {
		this.bankInfo = bankInfo;
	}


	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	@Column(name = "handle_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getHandleStatus() {
		return this.handleStatus;
	}

	public void setHandleStatus(java.lang.String handleStatus) {
		this.handleStatus = handleStatus;
	}

	@Column(name = "handle_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getHandleUserId() {
		return this.handleUserId;
	}

	public void setHandleUserId(java.lang.String handleUserId) {
		this.handleUserId = handleUserId;
	}

	@Column(name = "handle_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getHandleRemark() {
		return this.handleRemark;
	}

	public void setHandleRemark(java.lang.String handleRemark) {
		this.handleRemark = handleRemark;
	}


	@Column(name = "handle_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(java.util.Date handleTime) {
		this.handleTime = handleTime;
	}

	@Column(name = "bank_account", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "bank_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	@Column(name = "bank_id_no", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getBankIdNo() {
		return bankIdNo;
	}

	public void setBankIdNo(String bankIdNo) {
		this.bankIdNo = bankIdNo;
	}

	@Column(name = "bank_card", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("OrderNo",getOrderNo())
			.append("Amount",getAmount())
			.append("Wtype",getWtype())
			.append("Description",getDescription())
			.append("Channel",getChannel())
			.append("BankInfo",getBankInfo())
			.append("Addtime",getAddtime())
			.append("HandleStatus",getHandleStatus())
			.append("HandleUserId",getHandleUserId())
			.append("HandleRemark",getHandleRemark())
			.append("HandleTime",getHandleTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcWalletDetail == false) return false;
		if(this == obj) return true;
		ZcWalletDetail other = (ZcWalletDetail)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

