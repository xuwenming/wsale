package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcPayOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String orderNo;
	private String objectType;
	private String objectId;
	private String channel;
	private Double totalFee;
	private Long serviceFee;
	private String userId;
	private String payStatus;
	private Date paytime;
	private String refTransactionNo;
	private String refundNo;
	private Long refundFee;
	private Date refundtime;
	private Date addtime;			

	private boolean isRefundServiceFee;
	private String attachType;

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	public String getObjectType() {
		return this.objectType;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	public String getObjectId() {
		return this.objectId;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getChannel() {
		return this.channel;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	public Double getTotalFee() {
		return this.totalFee;
	}

	public Long getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Long serviceFee) {
		this.serviceFee = serviceFee;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	public Date getPaytime() {
		return this.paytime;
	}

	public String getRefTransactionNo() {
		return refTransactionNo;
	}

	public void setRefTransactionNo(String refTransactionNo) {
		this.refTransactionNo = refTransactionNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public Long getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Long refundFee) {
		this.refundFee = refundFee;
	}

	public Date getRefundtime() {
		return refundtime;
	}

	public void setRefundtime(Date refundtime) {
		this.refundtime = refundtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public boolean isRefundServiceFee() {
		return isRefundServiceFee;
	}

	public void setRefundServiceFee(boolean isRefundServiceFee) {
		this.isRefundServiceFee = isRefundServiceFee;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
}
