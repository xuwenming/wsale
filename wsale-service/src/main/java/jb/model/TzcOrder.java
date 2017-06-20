
/*
 * @author John
 * @date - 2016-08-30
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcOrder implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcOrder";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ORDER_NO = "订单号";
	public static final String ALIAS_PRODUCT_ID = "拍品id";
	public static final String ALIAS_IS_COMMENTED = "是否评价1：已评价0：未评价";
	public static final String ALIAS_DELAY_TIME = "延期到期时间";
	public static final String ALIAS_DELAY_TIMES = "延期次数,最多两次";
	public static final String ALIAS_DELIVER_TIME = "发货时间";
	public static final String ALIAS_RECEIVE_TIME = "收货时间";
	public static final String ALIAS_RETURN_APPLY_TIME = "退款申请时间";
	public static final String ALIAS_RETURN_TIME = "退款时间";
	public static final String ALIAS_COMPENSATION = "补偿金额";
	public static final String ALIAS_ADD_USER_ID = "创建人ID-废弃";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_UPDATE_USER_ID = "更新人ID";
	public static final String ALIAS_UPDATETIME = "更新时间";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAYTIME = "支付时间";
	public static final String ALIAS_SEND_STATUS = "收发货状态";
	public static final String ALIAS_BACK_STATUS = "退款状态";
	public static final String ALIAS_ORDER_STATUS = "订单状态";
	public static final String ALIAS_ORDER_STATUS_TIME = "订单状态时间";

	//date formats
	public static final String FORMAT_DELAY_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_DELIVER_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_RECEIVE_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_RETURN_APPLY_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_RETURN_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PAYTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ORDER_STATUS_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=255)
	private String orderNo;
	//@Length(max=36)
	private String productId;
	//
	private Boolean isCommented;
	//
	private Date delayTime;
	//
	private Integer delayTimes;
	//
	private Date deliverTime;
	//
	private Date receiveTime;
	//
	private Date returnApplyTime;
	private Date returnConfirmTime;
	//
	private Date returnTime;
	//
	private Double compensation;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	//@Length(max=36)
	private String updateUserId;
	//
	private Date updatetime;
	//@Length(max=4)
	private String payStatus;
	//
	private Date paytime;
	//@Length(max=4)
	private String sendStatus;
	//@Length(max=4)
	private String backStatus;
	//@Length(max=4)
	private String orderStatus;
	//
	private Date orderStatusTime;
	//@Length(max=6)
	private String orderCloseReason;
	//
	private String faceStatus;
	private Date faceTime;
	//@Length(max=128)
	private String expressName;
	//@Length(max=128)
	private String expressNo;
	private String returnApplyReason;
	private String returnApplyReasonOther;
	private String refuseReturnReason;
	private String refuseReturnReasonOther;
	private Date returnDeliverTime;
	private String returnExpressName;
	private String returnExpressNo;
	//columns END


		public TzcOrder(){
		}
		public TzcOrder(String id) {
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
	
	@Column(name = "order_no", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "product_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getProductId() {
		return this.productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Column(name = "isCommented", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsCommented() {
		return this.isCommented;
	}
	
	public void setIsCommented(Boolean isCommented) {
		this.isCommented = isCommented;
	}
	

	@Column(name = "delay_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDelayTime() {
		return this.delayTime;
	}
	
	public void setDelayTime(Date delayTime) {
		this.delayTime = delayTime;
	}
	
	@Column(name = "delay_times", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getDelayTimes() {
		return this.delayTimes;
	}
	
	public void setDelayTimes(Integer delayTimes) {
		this.delayTimes = delayTimes;
	}
	

	@Column(name = "deliver_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDeliverTime() {
		return this.deliverTime;
	}
	
	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}
	

	@Column(name = "receive_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getReceiveTime() {
		return this.receiveTime;
	}
	
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	

	@Column(name = "return_apply_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getReturnApplyTime() {
		return this.returnApplyTime;
	}
	
	public void setReturnApplyTime(Date returnApplyTime) {
		this.returnApplyTime = returnApplyTime;
	}

	@Column(name = "return_confirm_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getReturnConfirmTime() {
		return returnConfirmTime;
	}

	public void setReturnConfirmTime(Date returnConfirmTime) {
		this.returnConfirmTime = returnConfirmTime;
	}

	@Column(name = "return_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getReturnTime() {
		return this.returnTime;
	}
	
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@Column(name = "compensation", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getCompensation() {
		return this.compensation;
	}
	
	public void setCompensation(Double compensation) {
		this.compensation = compensation;
	}
	
	@Column(name = "addUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAddUserId() {
		return this.addUserId;
	}
	
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "updateUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUpdateUserId() {
		return this.updateUserId;
	}
	
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	

	@Column(name = "updatetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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
		return this.paytime;
	}
	
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	@Column(name = "send_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getSendStatus() {
		return this.sendStatus;
	}
	
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	@Column(name = "back_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getBackStatus() {
		return this.backStatus;
	}
	
	public void setBackStatus(String backStatus) {
		this.backStatus = backStatus;
	}
	
	@Column(name = "order_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getOrderStatus() {
		return this.orderStatus;
	}
	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	

	@Column(name = "order_status_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getOrderStatusTime() {
		return this.orderStatusTime;
	}
	
	public void setOrderStatusTime(Date orderStatusTime) {
		this.orderStatusTime = orderStatusTime;
	}

	@Column(name = "order_close_reason", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public String getOrderCloseReason() {
		return orderCloseReason;
	}

	public void setOrderCloseReason(String orderCloseReason) {
		this.orderCloseReason = orderCloseReason;
	}

	@Column(name = "face_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getFaceStatus() {
		return faceStatus;
	}

	public void setFaceStatus(String faceStatus) {
		this.faceStatus = faceStatus;
	}

	@Column(name = "face_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getFaceTime() {
		return faceTime;
	}

	public void setFaceTime(Date faceTime) {
		this.faceTime = faceTime;
	}

	@Column(name = "express_name", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	@Column(name = "express_no", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	@Column(name = "return_apply_reason", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getReturnApplyReason() {
		return returnApplyReason;
	}

	public void setReturnApplyReason(String returnApplyReason) {
		this.returnApplyReason = returnApplyReason;
	}

	@Column(name = "return_apply_reason_other", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getReturnApplyReasonOther() {
		return returnApplyReasonOther;
	}

	public void setReturnApplyReasonOther(String returnApplyReasonOther) {
		this.returnApplyReasonOther = returnApplyReasonOther;
	}

	@Column(name = "refuse_return_reason", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getRefuseReturnReason() {
		return refuseReturnReason;
	}

	public void setRefuseReturnReason(String refuseReturnReason) {
		this.refuseReturnReason = refuseReturnReason;
	}

	@Column(name = "refuse_return_reason_other", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getRefuseReturnReasonOther() {
		return refuseReturnReasonOther;
	}

	public void setRefuseReturnReasonOther(String refuseReturnReasonOther) {
		this.refuseReturnReasonOther = refuseReturnReasonOther;
	}

	@Column(name = "return_deliver_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getReturnDeliverTime() {
		return returnDeliverTime;
	}

	public void setReturnDeliverTime(Date returnDeliverTime) {
		this.returnDeliverTime = returnDeliverTime;
	}

	@Column(name = "return_express_name", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getReturnExpressName() {
		return returnExpressName;
	}

	public void setReturnExpressName(String returnExpressName) {
		this.returnExpressName = returnExpressName;
	}

	@Column(name = "return_express_no", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getReturnExpressNo() {
		return returnExpressNo;
	}

	public void setReturnExpressNo(String returnExpressNo) {
		this.returnExpressNo = returnExpressNo;
	}
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OrderNo",getOrderNo())
			.append("ProductId",getProductId())
			.append("IsCommented",getIsCommented())
			.append("DelayTime",getDelayTime())
			.append("DelayTimes",getDelayTimes())
			.append("DeliverTime",getDeliverTime())
			.append("ReceiveTime",getReceiveTime())
			.append("ReturnApplyTime",getReturnApplyTime())
			.append("ReturnTime",getReturnTime())
			.append("Compensation",getCompensation())
			.append("AddUserId",getAddUserId())
			.append("Addtime",getAddtime())
			.append("UpdateUserId",getUpdateUserId())
			.append("Updatetime",getUpdatetime())
			.append("PayStatus",getPayStatus())
			.append("Paytime",getPaytime())
			.append("SendStatus",getSendStatus())
			.append("BackStatus",getBackStatus())
			.append("OrderStatus",getOrderStatus())
			.append("OrderStatusTime",getOrderStatusTime())
			.append("FaceToFace",getFaceToFace())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcOrder == false) return false;
		if(this == obj) return true;
		ZcOrder other = (ZcOrder)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

