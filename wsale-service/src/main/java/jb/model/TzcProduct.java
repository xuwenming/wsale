
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
@Table(name = "zc_product")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcProduct implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcProduct";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_PNO = "拍品编号";
	public static final String ALIAS_CATEGORY_ID = "分类id";
	public static final String ALIAS_DEADLINE = "截止时间";
	public static final String ALIAS_REAL_DEADLINE = "实际截止时间";
	public static final String ALIAS_STARTING_PRICE = "起拍价";
	public static final String ALIAS_APPROVAL_DAYS = "几天包退,3:3天包退；7:7天包退；NULL：不包退";
	public static final String ALIAS_IS_FREE_SHIPPING = "是否包邮,1：包邮 0：不包邮";
	public static final String ALIAS_MARGIN = "保证金";
	public static final String ALIAS_IS_NEED_REAL_ID = "是否需要实名,1：需要 0：不需要";
	public static final String ALIAS_IS_NEED_PROTECTION_PRICE = "是否需要保证金,1：需要 0：不需要";
	public static final String ALIAS_STARTING_TIME = "开拍时间";
	public static final String ALIAS_FIXED_PRICE = "一口价";
	public static final String ALIAS_REFERENCE_PRICE = "参考价";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_STATUS = "状态，0草稿10未开始20竞拍中30已成交40已流拍50已失败";
	public static final String ALIAS_IS_CLOSE = "封存，1封存0未封存";
	public static final String ALIAS_READ_COUNT = "围观数量";
	public static final String ALIAS_LIKE_COUNT = "点赞数量";
	public static final String ALIAS_SHARE_COUNT = "分享数量";
	public static final String ALIAS_CURRENT_PRICE = "当前价";
	public static final String ALIAS_USER_ID = "成交人ID";
	public static final String ALIAS_HAMMER_PRICE = "成交金额";
	public static final String ALIAS_HAMMER_TIME = "成交时间";
	public static final String ALIAS_IS_DELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_ADD_USER_ID = "创建人ID";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_UPDATE_USER_ID = "更新人ID";
	public static final String ALIAS_UPDATETIME = "更新时间";
	
	//date formats
	public static final String FORMAT_DEADLINE = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_REAL_DEADLINE = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_STARTING_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_HAMMER_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=255)
	private String pno;
	//@Length(max=36)
	private String categoryId;
	//
	private Date deadline;
	//
	private Date realDeadline;
	//
	private Double startingPrice;
	//@Length(max=4)
	private String approvalDays;
	//
	private Boolean isFreeShipping;
	//
	private Double margin;
	//
	private Boolean isNeedRealId;
	//
	private Boolean isNeedProtectionPrice;
	//
	private Date startingTime;
	//
	private Double fixedPrice;
	//
	private Double referencePrice;
	//@Length(max=65535)
	private String content;
	//@Length(max=4)
	private String status;
	//
	private Boolean isClose;
	//
	private Integer readCount;
	//
	private Integer likeCount;
	//
	private Integer shareCount;
	//
	private Double currentPrice;
	//@Length(max=36)
	private String userId;
	//
	private Double hammerPrice;
	//
	private Date hammerTime;
	//
	private Boolean isDeleted;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	//@Length(max=36)
	private String updateUserId;
	//
	private Date updatetime;
	//columns END


		public TzcProduct(){
		}
		public TzcProduct(String id) {
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
	
	@Column(name = "p_no", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getPno() {
		return this.pno;
	}
	
	public void setPno(String pno) {
		this.pno = pno;
	}
	
	@Column(name = "category_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	

	@Column(name = "deadline", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDeadline() {
		return this.deadline;
	}
	
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	

	@Column(name = "real_deadline", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getRealDeadline() {
		return this.realDeadline;
	}
	
	public void setRealDeadline(Date realDeadline) {
		this.realDeadline = realDeadline;
	}
	
	@Column(name = "starting_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getStartingPrice() {
		return this.startingPrice;
	}
	
	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}
	
	@Column(name = "approval_days", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getApprovalDays() {
		return this.approvalDays;
	}
	
	public void setApprovalDays(String approvalDays) {
		this.approvalDays = approvalDays;
	}
	
	@Column(name = "is_free_shipping", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsFreeShipping() {
		return this.isFreeShipping;
	}
	
	public void setIsFreeShipping(Boolean isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}
	
	@Column(name = "margin", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getMargin() {
		return this.margin;
	}
	
	public void setMargin(Double margin) {
		this.margin = margin;
	}
	
	@Column(name = "is_need_real_id", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsNeedRealId() {
		return this.isNeedRealId;
	}
	
	public void setIsNeedRealId(Boolean isNeedRealId) {
		this.isNeedRealId = isNeedRealId;
	}
	
	@Column(name = "is_need_protection_price", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsNeedProtectionPrice() {
		return this.isNeedProtectionPrice;
	}
	
	public void setIsNeedProtectionPrice(Boolean isNeedProtectionPrice) {
		this.isNeedProtectionPrice = isNeedProtectionPrice;
	}
	

	@Column(name = "starting_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getStartingTime() {
		return this.startingTime;
	}
	
	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}
	
	@Column(name = "fixed_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getFixedPrice() {
		return this.fixedPrice;
	}
	
	public void setFixedPrice(Double fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	
	@Column(name = "reference_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getReferencePrice() {
		return this.referencePrice;
	}
	
	public void setReferencePrice(Double referencePrice) {
		this.referencePrice = referencePrice;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "is_close", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsClose() {
		return this.isClose;
	}
	
	public void setIsClose(Boolean isClose) {
		this.isClose = isClose;
	}
	
	@Column(name = "read_count", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getReadCount() {
		return this.readCount;
	}
	
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	
	@Column(name = "like_count", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getLikeCount() {
		return this.likeCount;
	}
	
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	
	@Column(name = "share_count", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShareCount() {
		return this.shareCount;
	}
	
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	
	@Column(name = "current_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getCurrentPrice() {
		return this.currentPrice;
	}
	
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "hammer_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getHammerPrice() {
		return this.hammerPrice;
	}
	
	public void setHammerPrice(Double hammerPrice) {
		this.hammerPrice = hammerPrice;
	}
	

	@Column(name = "hammer_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getHammerTime() {
		return this.hammerTime;
	}
	
	public void setHammerTime(Date hammerTime) {
		this.hammerTime = hammerTime;
	}
	
	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Pno",getPno())
			.append("CategoryId",getCategoryId())
			.append("Deadline",getDeadline())
			.append("RealDeadline",getRealDeadline())
			.append("StartingPrice",getStartingPrice())
			.append("ApprovalDays",getApprovalDays())
			.append("IsFreeShipping",getIsFreeShipping())
			.append("Margin",getMargin())
			.append("IsNeedRealId",getIsNeedRealId())
			.append("IsNeedProtectionPrice",getIsNeedProtectionPrice())
			.append("StartingTime",getStartingTime())
			.append("FixedPrice",getFixedPrice())
			.append("ReferencePrice",getReferencePrice())
			.append("Content",getContent())
			.append("Status",getStatus())
			.append("IsClose",getIsClose())
			.append("ReadCount",getReadCount())
			.append("LikeCount",getLikeCount())
			.append("ShareCount",getShareCount())
			.append("CurrentPrice",getCurrentPrice())
			.append("UserId",getUserId())
			.append("HammerPrice",getHammerPrice())
			.append("HammerTime",getHammerTime())
			.append("IsDeleted",getIsDeleted())
			.append("AddUserId",getAddUserId())
			.append("Addtime",getAddtime())
			.append("UpdateUserId",getUpdateUserId())
			.append("Updatetime",getUpdatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcProduct == false) return false;
		if(this == obj) return true;
		ZcProduct other = (ZcProduct)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

