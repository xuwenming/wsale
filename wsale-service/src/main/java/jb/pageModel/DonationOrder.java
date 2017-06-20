package jb.pageModel;

import jb.listener.Application;

import java.util.Date;

@SuppressWarnings("serial")
public class DonationOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String openid;
	private String nickname;
	private Integer sex;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private Date addtime;			
	private String payStatus;
	private Date paytime;			
	private String orderNo;
	private Float totalFee;

	private Date paytimeBegin;
	private Date paytimeEnd;

	public String getPayStatusZh() {
		return Application.getString(this.payStatus);
	}

	public String getSexZh() {
		if(this.sex == null) return "未知";
		return this.sex == 1 ? "男" : (this.sex == 2 ? "女" : "未知");
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getOpenid() {
		return this.openid;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	public Integer getSex() {
		return this.sex;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getProvince() {
		return this.province;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountry() {
		return this.country;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public String getHeadimgurl() {
		return this.headimgurl;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
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
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}
	
	public Float getTotalFee() {
		return this.totalFee;
	}

	public Date getPaytimeBegin() {
		return paytimeBegin;
	}

	public void setPaytimeBegin(Date paytimeBegin) {
		this.paytimeBegin = paytimeBegin;
	}

	public Date getPaytimeEnd() {
		return paytimeEnd;
	}

	public void setPaytimeEnd(Date paytimeEnd) {
		this.paytimeEnd = paytimeEnd;
	}
}
