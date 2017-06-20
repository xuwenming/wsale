package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcAddress implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userId;
	private String orderId;
	private Integer atype;
	private String userName;
	private String telNumber;
	private String provinceName;
	private String cityName;
	private String countyName;
	private String detailInfo;
	private String postalCode;
	private Boolean isDefault;
	private Date addtime;			

	

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	
	public Integer getAtype() {
		return this.atype;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	
	public String getTelNumber() {
		return this.telNumber;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getProvinceName() {
		return this.provinceName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	public String getCountyName() {
		return this.countyName;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	
	public String getDetailInfo() {
		return this.detailInfo;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getPostalCode() {
		return this.postalCode;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public Boolean getIsDefault() {
		return this.isDefault;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}
