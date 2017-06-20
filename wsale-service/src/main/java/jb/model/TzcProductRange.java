
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
@Table(name = "zc_product_range")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcProductRange implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "ZcProductRange";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_PRODUCT_ID = "拍品id";
	public static final String ALIAS_START_PRICE = "起始价格";
	public static final String ALIAS_END_PRICE = "结束价格";
	public static final String ALIAS_PRICE = "加价幅度";
	public static final String ALIAS_ADDTIME = "创建时间";

	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;


	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String productId;
	//
	private java.lang.Double startPrice;
	//
	private java.lang.Double endPrice;
	//
	private java.lang.Double price;
	//@Length(max=36)
	private java.lang.String addUserId;
	//
	private java.util.Date addtime;
	//columns END


	public TzcProductRange(){
	}
	public TzcProductRange(String id) {
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

	@Column(name = "product_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getProductId() {
		return this.productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}

	@Column(name = "start_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.lang.Double getStartPrice() {
		return this.startPrice;
	}

	public void setStartPrice(java.lang.Double startPrice) {
		this.startPrice = startPrice;
	}

	@Column(name = "end_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.lang.Double getEndPrice() {
		return this.endPrice;
	}

	public void setEndPrice(java.lang.Double endPrice) {
		this.endPrice = endPrice;
	}

	@Column(name = "price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.lang.Double getPrice() {
		return this.price;
	}

	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	@Column(name = "addUserId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductId",getProductId())
			.append("StartPrice",getStartPrice())
			.append("EndPrice",getEndPrice())
			.append("Price",getPrice())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcProductRange == false) return false;
		if(this == obj) return true;
		ZcProductRange other = (ZcProductRange)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

