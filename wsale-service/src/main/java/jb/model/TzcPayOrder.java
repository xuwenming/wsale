
/*
 * @author John
 * @date - 2016-09-05
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_pay_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcPayOrder implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcPayOrder";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ORDER_NO = "订单编号";
	public static final String ALIAS_OBJECT_TYPE = "类型";
	public static final String ALIAS_OBJECT_ID = "业务id";
	public static final String ALIAS_CHANNEL = "支付渠道";
	public static final String ALIAS_TOTAL_FEE = "支付金额";
	public static final String ALIAS_USER_ID = "支付人";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAYTIME = "支付时间";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PAYTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;


	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=64)
	private String orderNo;
	//@Length(max=6)
	private String objectType;
	//@Length(max=36)
	private String objectId;
	//@Length(max=4)
	private String channel;
	//
	private Double totalFee;
	private java.lang.Long serviceFee;
	//@Length(max=36)
	private String userId;
	//@Length(max=4)
	private String payStatus;
	//
	private Date paytime;
	private String refTransactionNo;
	//@Length(max=64)
	private String refundNo;
	private java.lang.Long refundFee;
	private Date refundtime;
	//
	private Date addtime;
	//columns END


		public TzcPayOrder(){
		}
		public TzcPayOrder(String id) {
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
	
	@Column(name = "order_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "object_type", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "object_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	@Column(name = "channel", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getChannel() {
		return this.channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Column(name = "total_fee", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getTotalFee() {
		return this.totalFee;
	}
	
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "service_fee", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getServiceFee() {
		return this.serviceFee;
	}

	public void setServiceFee(java.lang.Long serviceFee) {
		this.serviceFee = serviceFee;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
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

	@Column(name = "ref_transaction_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getRefTransactionNo() {
		return refTransactionNo;
	}

	public void setRefTransactionNo(String refTransactionNo) {
		this.refTransactionNo = refTransactionNo;
	}

	@Column(name = "refund_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	@Column(name = "refund_fee", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Long getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Long refundFee) {
		this.refundFee = refundFee;
	}

	@Column(name = "refundtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getRefundtime() {
		return refundtime;
	}

	public void setRefundtime(Date refundtime) {
		this.refundtime = refundtime;
	}

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OrderNo",getOrderNo())
			.append("ObjectType",getObjectType())
			.append("ObjectId",getObjectId())
			.append("Channel",getChannel())
			.append("TotalFee",getTotalFee())
			.append("UserId",getUserId())
			.append("PayStatus",getPayStatus())
			.append("Paytime",getPaytime())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcPayOrder == false) return false;
		if(this == obj) return true;
		ZcPayOrder other = (ZcPayOrder)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

