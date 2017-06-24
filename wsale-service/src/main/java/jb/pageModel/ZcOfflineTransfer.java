package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcOfflineTransfer implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String transferNo;
	private String userId;
	private String transferUserName;
	private Double transferAmount;
	private Date transferTime;			
	private String remark;
	private String bankCode;
	private String handleStatus;
	private String handleUserId;
	private String handleRemark;
	private Date handleTime;
	private Boolean isWallet;
	private Date addtime;			

	private User user; // 汇款人
	private String handleUserName; // 处理人

	public String getBankCodeZh() {
		return Application.getString(this.bankCode);
	}
	public String getHandleStatusZh() {
		return Application.getString(this.handleStatus);
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}
	
	public String getTransferUserName() {
		return this.transferUserName;
	}
	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	public Double getTransferAmount() {
		return this.transferAmount;
	}
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public Date getTransferTime() {
		return this.transferTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	public String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}
	
	public String getHandleUserId() {
		return this.handleUserId;
	}
	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}
	
	public String getHandleRemark() {
		return this.handleRemark;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	public Date getHandleTime() {
		return this.handleTime;
	}

	public Boolean getIsWallet() {
		return isWallet;
	}

	public void setIsWallet(Boolean isWallet) {
		this.isWallet = isWallet;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getHandleUserName() {
		return handleUserName;
	}

	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}
}
