
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
@Table(name = "zc_auction")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcAuction implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcAuction";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_PRODUCT_ID = "拍品id";
	public static final String ALIAS_BUYER_ID = "出价人";
	public static final String ALIAS_BID = "出价金额";
	public static final String ALIAS_STATUS = "成交状态 0未成交1已成交";
	public static final String ALIAS_IS_AUTO = "是否自动出价";
	public static final String ALIAS_ADD_USER_ID = "创建人ID";
	public static final String ALIAS_ADDTIME = "出价时间";
	public static final String ALIAS_UPDATE_USER_ID = "更新人ID";
	public static final String ALIAS_UPDATETIME = "更新时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String productId;
	//@Length(max=36)
	private String buyerId;
	//
	private Double bid;
	//@Length(max=4)
	private String status;
	//
	private Boolean isAuto;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	//@Length(max=36)
	private String updateUserId;
	//
	private Date updatetime;
	//columns END


		public TzcAuction(){
		}
		public TzcAuction(String id) {
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
	
	@Column(name = "product_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getProductId() {
		return this.productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Column(name = "buyer_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getBuyerId() {
		return this.buyerId;
	}
	
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	@Column(name = "bid", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getBid() {
		return this.bid;
	}
	
	public void setBid(Double bid) {
		this.bid = bid;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "is_auto", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsAuto() {
		return this.isAuto;
	}
	
	public void setIsAuto(Boolean isAuto) {
		this.isAuto = isAuto;
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
			.append("ProductId",getProductId())
			.append("BuyerId",getBuyerId())
			.append("Bid",getBid())
			.append("Status",getStatus())
			.append("IsAuto",getIsAuto())
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
		if(obj instanceof ZcAuction == false) return false;
		if(this == obj) return true;
		ZcAuction other = (ZcAuction)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

