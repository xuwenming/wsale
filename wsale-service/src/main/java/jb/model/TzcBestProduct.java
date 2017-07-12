
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
@Table(name = "zc_best_product")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcBestProduct implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcBestProduct";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CHANNEL = "精拍频道 0首页1分类";
	public static final String ALIAS_PRODUCT_ID = "拍品id";
	public static final String ALIAS_AUDIT_STATUS = "审核状态";
	public static final String ALIAS_AUDIT_TIME = "审核时间";
	public static final String ALIAS_AUDIT_USER_ID = "审核人";
	public static final String ALIAS_AUDIT_REMARK = "审核备注";
	public static final String ALIAS_START_TIME = "起始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAYTIME = "支付时间";
	public static final String ALIAS_ADD_USER_ID = "创建人ID";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_AUDIT_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_START_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_END_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PAYTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=18)
	private String channel;
	//@Length(max=36)
	private String productId;
	//@Length(max=4)
	private String auditStatus;
	//
	private Date auditTime;
	//@Length(max=36)
	private String auditUserId;
	//@Length(max=500)
	private String auditRemark;
	//
	private Date startTime;
	//
	private Date endTime;
	//@Length(max=4)
	private String payStatus;
	//
	private Date paytime;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	private Integer shopSeq;
	private Integer productSeq;
	//columns END


		public TzcBestProduct(){
		}
		public TzcBestProduct(String id) {
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
	
	@Column(name = "channel", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getChannel() {
		return this.channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Column(name = "product_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getProductId() {
		return this.productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Column(name = "audit_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getAuditStatus() {
		return this.auditStatus;
	}
	
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	

	@Column(name = "audit_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAuditTime() {
		return this.auditTime;
	}
	
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@Column(name = "audit_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAuditUserId() {
		return this.auditUserId;
	}
	
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	@Column(name = "audit_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getAuditRemark() {
		return this.auditRemark;
	}
	
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	

	@Column(name = "start_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	

	@Column(name = "end_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	@Column(name = "shop_seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopSeq() {
		return shopSeq;
	}

	public void setShopSeq(Integer shopSeq) {
		this.shopSeq = shopSeq;
	}

	@Column(name = "product_seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getProductSeq() {
		return productSeq;
	}

	public void setProductSeq(Integer productSeq) {
		this.productSeq = productSeq;
	}
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Channel",getChannel())
			.append("ProductId",getProductId())
			.append("AuditStatus",getAuditStatus())
			.append("AuditTime",getAuditTime())
			.append("AuditUserId",getAuditUserId())
			.append("AuditRemark",getAuditRemark())
			.append("StartTime",getStartTime())
			.append("EndTime",getEndTime())
			.append("PayStatus",getPayStatus())
			.append("Paytime",getPaytime())
			.append("AddUserId",getAddUserId())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcBestProduct == false) return false;
		if(this == obj) return true;
		ZcBestProduct other = (ZcBestProduct)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

