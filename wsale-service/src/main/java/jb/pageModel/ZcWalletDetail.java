package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcWalletDetail implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;
	private java.lang.String userId;
	private java.lang.String orderNo;
	private java.lang.Double amount;
	private java.lang.String wtype;
	private java.lang.String description;
	private java.lang.String channel;
	private java.lang.String bankInfo;
	private Date addtime;
	private java.lang.String handleStatus;
	private java.lang.String handleUserId;
	private java.lang.String handleRemark;
	private Date handleTime;
	private java.lang.String bankAccount;
	private java.lang.String bankPhone;
	private java.lang.String bankIdNo;
	private java.lang.String bankCard;

	private String userName;
	private String handleUserName;
	private Date addtimeBegin;
	private Date addtimeEnd;
	private java.lang.Double walletAmount; // 钱包余额

	// 是否收入
	public boolean getIsIncome() {
		// 充值、打赏收入、拍品订单收入、交易退款、保证金转入、提现退回
		if("WT01".equals(this.wtype) || "WT05".equals(this.wtype)
				|| "WT07".equals(this.wtype) || "WT04".equals(this.wtype)
				|| "WT09".equals(this.wtype) || "WT10".equals(this.wtype))
			return true;
		return false;
	}

	public String getChannelZh() {
		return Application.getString(this.channel);
	}

	public String getWtypeZh() {
		return Application.getString(this.wtype);
	}

	public String getHandleStatusZh() {
		return Application.getString(this.handleStatus);
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}

	public java.lang.String getId() {
		return this.id;
	}


	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}

	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setAmount(java.lang.Double amount) {
		this.amount = amount;
	}

	public java.lang.Double getAmount() {
		return this.amount;
	}
	public void setWtype(java.lang.String wtype) {
		this.wtype = wtype;
	}

	public java.lang.String getWtype() {
		return this.wtype;
	}
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getDescription() {
		return this.description;
	}
	public void setChannel(java.lang.String channel) {
		this.channel = channel;
	}

	public java.lang.String getChannel() {
		return this.channel;
	}
	public void setBankInfo(java.lang.String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public java.lang.String getBankInfo() {
		return this.bankInfo;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getAddtime() {
		return this.addtime;
	}
	public void setHandleStatus(java.lang.String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public java.lang.String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleUserId(java.lang.String handleUserId) {
		this.handleUserId = handleUserId;
	}

	public java.lang.String getHandleUserId() {
		return this.handleUserId;
	}
	public void setHandleRemark(java.lang.String handleRemark) {
		this.handleRemark = handleRemark;
	}

	public java.lang.String getHandleRemark() {
		return this.handleRemark;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Date getHandleTime() {
		return this.handleTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHandleUserName() {
		return handleUserName;
	}

	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}

	public Date getAddtimeBegin() {
		return addtimeBegin;
	}

	public void setAddtimeBegin(Date addtimeBegin) {
		this.addtimeBegin = addtimeBegin;
	}

	public Date getAddtimeEnd() {
		return addtimeEnd;
	}

	public void setAddtimeEnd(Date addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getBankIdNo() {
		return bankIdNo;
	}

	public void setBankIdNo(String bankIdNo) {
		this.bankIdNo = bankIdNo;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}
}
