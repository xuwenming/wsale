
/*
 * @author John
 * @date - 2016-09-21
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_address")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcAddress implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcAddress";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "所有者Id";
	public static final String ALIAS_ATYPE = "类型1-收货地址 2-退货地址";
	public static final String ALIAS_USER_NAME = "收货人姓名";
	public static final String ALIAS_TEL_NUMBER = "收货人手机号码";
	public static final String ALIAS_PROVINCE_NAME = "国标收货地址第一级地址（省）";
	public static final String ALIAS_CITY_NAME = "国标收货地址第二级地址（市）";
	public static final String ALIAS_COUNTY_NAME = "国标收货地址第三级地址（县/区）";
	public static final String ALIAS_DETAIL_INFO = "详细收货地址信息";
	public static final String ALIAS_POSTAL_CODE = "邮政编码";
	public static final String ALIAS_IS_DEFAULT = "是否默认，1默认0不是默认";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String userId;
	private String orderId;
	//
	private Integer atype;
	//@Length(max=36)
	private String userName;
	//@Length(max=36)
	private String telNumber;
	//@Length(max=128)
	private String provinceName;
	//@Length(max=128)
	private String cityName;
	//@Length(max=128)
	private String countyName;
	//@Length(max=256)
	private String detailInfo;
	//@Length(max=36)
	private String postalCode;
	//
	private Boolean isDefault;
	//
	private Date addtime;
	//columns END


		public TzcAddress(){
		}
		public TzcAddress(String id) {
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
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "order_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "atype", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getAtype() {
		return this.atype;
	}
	
	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	
	@Column(name = "userName", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "telNumber", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getTelNumber() {
		return this.telNumber;
	}
	
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	
	@Column(name = "provinceName", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getProvinceName() {
		return this.provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	@Column(name = "cityName", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Column(name = "countyName", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getCountyName() {
		return this.countyName;
	}
	
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	@Column(name = "detailInfo", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getDetailInfo() {
		return this.detailInfo;
	}
	
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	
	@Column(name = "postalCode", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getPostalCode() {
		return this.postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@Column(name = "is_default", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDefault() {
		return this.isDefault;
	}
	
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
			.append("UserId",getUserId())
			.append("Atype",getAtype())
			.append("UserName",getUserName())
			.append("TelNumber",getTelNumber())
			.append("ProvinceName",getProvinceName())
			.append("CityName",getCityName())
			.append("CountyName",getCountyName())
			.append("DetailInfo",getDetailInfo())
			.append("PostalCode",getPostalCode())
			.append("IsDefault",getIsDefault())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcAddress == false) return false;
		if(this == obj) return true;
		ZcAddress other = (ZcAddress)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

