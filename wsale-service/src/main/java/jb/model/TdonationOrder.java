
/*
 * @author John
 * @date - 2016-07-09
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "donation_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TdonationOrder implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "DonationOrder";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_OPENID = "微信用户唯一标识";
	public static final String ALIAS_NICKNAME = "昵称";
	public static final String ALIAS_SEX = "性别1：男 2：女 0：未知";
	public static final String ALIAS_CITY = "城市";
	public static final String ALIAS_PROVINCE = "省份";
	public static final String ALIAS_COUNTRY = "国家";
	public static final String ALIAS_HEADIMGURL = "头像";
	public static final String ALIAS_ADDTIME = "创建时间";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAYTIME = "支付时间";
	public static final String ALIAS_ORDER_NO = "订单编号";
	public static final String ALIAS_TOTAL_FEE = "捐款金额";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PAYTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String openid;
	//@Length(max=256)
	private String nickname;
	//
	private Integer sex;
	//@Length(max=36)
	private String city;
	//@Length(max=36)
	private String province;
	//@Length(max=36)
	private String country;
	//@Length(max=255)
	private String headimgurl;
	//
	private Date addtime;
	//@Length(max=4)
	private String payStatus;
	//
	private Date paytime;
	//@Length(max=64)
	private String orderNo;
	//
	private Float totalFee;
	//columns END


		public TdonationOrder(){
		}
		public TdonationOrder(String id) {
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
	
	@Column(name = "openid", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getOpenid() {
		return this.openid;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Column(name = "nickname", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Column(name = "sex", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSex() {
		return this.sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Column(name = "city", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "province", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getProvince() {
		return this.province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "country", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(name = "headimgurl", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getHeadimgurl() {
		return this.headimgurl;
	}
	
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
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
	
	@Column(name = "order_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "total_fee", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	public Float getTotalFee() {
		return this.totalFee;
	}
	
	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Openid",getOpenid())
			.append("Nickname",getNickname())
			.append("Sex",getSex())
			.append("City",getCity())
			.append("Province",getProvince())
			.append("Country",getCountry())
			.append("Headimgurl",getHeadimgurl())
			.append("Addtime",getAddtime())
			.append("PayStatus",getPayStatus())
			.append("Paytime",getPaytime())
			.append("OrderNo",getOrderNo())
			.append("TotalFee",getTotalFee())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DonationOrder == false) return false;
		if(this == obj) return true;
		DonationOrder other = (DonationOrder)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

