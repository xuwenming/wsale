package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcShop implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userId;
	private String name;
	private String logoUrl;
	private String notice;
	private String introduction;
	private Double protectionPrice;
	private Boolean isNeedPhone;
	private Boolean isNeedReelId;
	private String shopUrl;
	private String shopQrcodeUrl;
	private Float grade;
	private String addUserId;
	private Date addtime;			
	private String updateUserId;
	private Date updatetime;			

	

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
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	public String getLogoUrl() {
		return this.logoUrl;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	public String getNotice() {
		return this.notice;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getIntroduction() {
		return this.introduction;
	}
	public void setProtectionPrice(Double protectionPrice) {
		this.protectionPrice = protectionPrice;
	}
	
	public Double getProtectionPrice() {
		return this.protectionPrice;
	}
	public void setIsNeedPhone(Boolean isNeedPhone) {
		this.isNeedPhone = isNeedPhone;
	}
	
	public Boolean getIsNeedPhone() {
		return this.isNeedPhone;
	}
	public void setIsNeedReelId(Boolean isNeedReelId) {
		this.isNeedReelId = isNeedReelId;
	}
	
	public Boolean getIsNeedReelId() {
		return this.isNeedReelId;
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	
	public String getShopUrl() {
		return this.shopUrl;
	}
	public void setShopQrcodeUrl(String shopQrcodeUrl) {
		this.shopQrcodeUrl = shopQrcodeUrl;
	}
	
	public String getShopQrcodeUrl() {
		return this.shopQrcodeUrl;
	}
	public void setGrade(Float grade) {
		this.grade = grade;
	}
	
	public Float getGrade() {
		return this.grade;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	
	public String getAddUserId() {
		return this.addUserId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public String getUpdateUserId() {
		return this.updateUserId;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime() {
		return this.updatetime;
	}

}
