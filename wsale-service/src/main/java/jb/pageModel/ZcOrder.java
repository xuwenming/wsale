package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String orderNo;
	private String productId;
	private Boolean isCommented;
	private Date delayTime;			
	private Integer delayTimes;
	private Date deliverTime;			
	private Date receiveTime;			
	private Date returnApplyTime;			
	private Date returnConfirmTime;
	private Date returnTime;
	private Double compensation;
	private String addUserId;
	private Date addtime;			
	private String updateUserId;
	private Date updatetime;			
	private String payStatus;
	private Date paytime;			
	private String sendStatus;
	private String backStatus;
	private String orderStatus;
	private Date orderStatusTime;
	private String orderCloseReason;
	private String faceStatus;
	private Date faceTime;
	private String expressName;
	private String expressNo;
	private String returnApplyReason;
	private String returnApplyReasonOther;
	private String refuseReturnReason;
	private String refuseReturnReasonOther;
	private Date returnDeliverTime;
	private String returnExpressName;
	private String returnExpressNo;

	private Boolean isBuyer; // 是否买家
	private ZcProduct product;
	private ZcComment comment;
	private User buyer; // 买家
	private User seller; // 卖家
	private String pname;

	private Object other; //其他条件
	private String keyPath;

	private Boolean isXiaoer; // 是否申请小二介入
	private ZcOrderXiaoer xiaoer; // 订单小二介入

	public String getPayStatusZh() {
		return Application.getString(this.payStatus);
	}

	public String getSendStatusZh() {
		return Application.getString(this.sendStatus);
	}

	public String getBackStatusZh() {
		return Application.getString(this.backStatus);
	}

	public String getOrderStatusZh() {
		return Application.getString(this.orderStatus);
	}

	public String getOrderCloseReasonZh() {
		return Application.getString(this.orderCloseReason);
	}

	public String getReturnApplyReasonZh() {
		return Application.getString(this.returnApplyReason);
	}
	public String getRefuseReturnReasonZh() {
		return Application.getString(this.refuseReturnReason);
	}

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
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductId() {
		return this.productId;
	}
	public void setIsCommented(Boolean isCommented) {
		this.isCommented = isCommented;
	}
	
	public Boolean getIsCommented() {
		return this.isCommented;
	}
	public void setDelayTime(Date delayTime) {
		this.delayTime = delayTime;
	}
	
	public Date getDelayTime() {
		return this.delayTime;
	}
	public void setDelayTimes(Integer delayTimes) {
		this.delayTimes = delayTimes;
	}
	
	public Integer getDelayTimes() {
		return this.delayTimes;
	}
	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}
	
	public Date getDeliverTime() {
		return this.deliverTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	public Date getReceiveTime() {
		return this.receiveTime;
	}
	public void setReturnApplyTime(Date returnApplyTime) {
		this.returnApplyTime = returnApplyTime;
	}
	
	public Date getReturnApplyTime() {
		return this.returnApplyTime;
	}

	public Date getReturnConfirmTime() {
		return returnConfirmTime;
	}

	public void setReturnConfirmTime(Date returnConfirmTime) {
		this.returnConfirmTime = returnConfirmTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	public Date getReturnTime() {
		return this.returnTime;
	}
	public void setCompensation(Double compensation) {
		this.compensation = compensation;
	}
	
	public Double getCompensation() {
		return this.compensation;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	
	public String getAddUserId() {
		return this.addUserId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public String getUpdateUserId() {
		return this.updateUserId;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime() {
		return this.updatetime;
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
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	public String getSendStatus() {
		return this.sendStatus;
	}
	public void setBackStatus(String backStatus) {
		this.backStatus = backStatus;
	}
	
	public String getBackStatus() {
		return this.backStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getOrderStatus() {
		return this.orderStatus;
	}
	public void setOrderStatusTime(Date orderStatusTime) {
		this.orderStatusTime = orderStatusTime;
	}
	
	public Date getOrderStatusTime() {
		return this.orderStatusTime;
	}

	public String getOrderCloseReason() {
		return orderCloseReason;
	}

	public void setOrderCloseReason(String orderCloseReason) {
		this.orderCloseReason = orderCloseReason;
	}

	public String getFaceStatus() {
		return faceStatus;
	}

	public void setFaceStatus(String faceStatus) {
		this.faceStatus = faceStatus;
	}

	public Date getFaceTime() {
		return faceTime;
	}

	public void setFaceTime(Date faceTime) {
		this.faceTime = faceTime;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Boolean getIsBuyer() {
		return isBuyer;
	}

	public void setIsBuyer(Boolean isBuyer) {
		this.isBuyer = isBuyer;
	}

	public ZcProduct getProduct() {
		return product;
	}

	public void setProduct(ZcProduct product) {
		this.product = product;
	}

	public ZcComment getComment() {
		return comment;
	}

	public void setComment(ZcComment comment) {
		this.comment = comment;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getReturnApplyReason() {
		return returnApplyReason;
	}

	public void setReturnApplyReason(String returnApplyReason) {
		this.returnApplyReason = returnApplyReason;
	}

	public String getReturnApplyReasonOther() {
		return returnApplyReasonOther;
	}

	public void setReturnApplyReasonOther(String returnApplyReasonOther) {
		this.returnApplyReasonOther = returnApplyReasonOther;
	}

	public String getRefuseReturnReason() {
		return refuseReturnReason;
	}

	public void setRefuseReturnReason(String refuseReturnReason) {
		this.refuseReturnReason = refuseReturnReason;
	}

	public String getRefuseReturnReasonOther() {
		return refuseReturnReasonOther;
	}

	public void setRefuseReturnReasonOther(String refuseReturnReasonOther) {
		this.refuseReturnReasonOther = refuseReturnReasonOther;
	}

	public Date getReturnDeliverTime() {
		return returnDeliverTime;
	}

	public void setReturnDeliverTime(Date returnDeliverTime) {
		this.returnDeliverTime = returnDeliverTime;
	}

	public String getReturnExpressName() {
		return returnExpressName;
	}

	public void setReturnExpressName(String returnExpressName) {
		this.returnExpressName = returnExpressName;
	}

	public String getReturnExpressNo() {
		return returnExpressNo;
	}

	public void setReturnExpressNo(String returnExpressNo) {
		this.returnExpressNo = returnExpressNo;
	}

	public Boolean getIsXiaoer() {
		return isXiaoer;
	}

	public void setIsXiaoer(Boolean isXiaoer) {
		this.isXiaoer = isXiaoer;
	}

	public ZcOrderXiaoer getXiaoer() {
		return xiaoer;
	}

	public void setXiaoer(ZcOrderXiaoer xiaoer) {
		this.xiaoer = xiaoer;
	}
}
