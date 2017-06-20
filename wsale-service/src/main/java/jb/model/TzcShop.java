
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
@Table(name = "zc_shop")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcShop implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcShop";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "所有者Id";
	public static final String ALIAS_NAME = "店铺名称";
	public static final String ALIAS_LOGO_URL = "店铺LOGO";
	public static final String ALIAS_NOTICE = "店铺公告";
	public static final String ALIAS_INTRODUCTION = "店铺介绍";
	public static final String ALIAS_PROTECTION_PRICE = "消保金";
	public static final String ALIAS_IS_NEED_PHONE = "首次出价需要手机号，1需要0不需要";
	public static final String ALIAS_IS_NEED_REEL_ID = "首次出价需实名认证，1需要0不需要";
	public static final String ALIAS_SHOP_URL = "店铺链接";
	public static final String ALIAS_SHOP_QRCODE_URL = "店铺二维码";
	public static final String ALIAS_GRADE = "店铺评分";
	public static final String ALIAS_ADD_USER_ID = "创建人ID";
	public static final String ALIAS_ADDTIME = "创建时间";
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
	private String userId;
	//@Length(max=255)
	private String name;
	//@Length(max=255)
	private String logoUrl;
	//@Length(max=255)
	private String notice;
	//@Length(max=255)
	private String introduction;
	//
	private Double protectionPrice;
	//
	private Boolean isNeedPhone;
	//
	private Boolean isNeedReelId;
	//@Length(max=255)
	private String shopUrl;
	//@Length(max=255)
	private String shopQrcodeUrl;
	//
	private Float grade;
	//@Length(max=36)
	private String addUserId;
	//
	private Date addtime;
	//@Length(max=36)
	private String updateUserId;
	//
	private Date updatetime;
	//columns END


		public TzcShop(){
		}
		public TzcShop(String id) {
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
	
	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "logo_url", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getLogoUrl() {
		return this.logoUrl;
	}
	
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	@Column(name = "notice", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getNotice() {
		return this.notice;
	}
	
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	@Column(name = "introduction", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getIntroduction() {
		return this.introduction;
	}
	
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	@Column(name = "protection_price", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getProtectionPrice() {
		return this.protectionPrice;
	}
	
	public void setProtectionPrice(Double protectionPrice) {
		this.protectionPrice = protectionPrice;
	}
	
	@Column(name = "is_need_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsNeedPhone() {
		return this.isNeedPhone;
	}
	
	public void setIsNeedPhone(Boolean isNeedPhone) {
		this.isNeedPhone = isNeedPhone;
	}
	
	@Column(name = "is_need_reel_id", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsNeedReelId() {
		return this.isNeedReelId;
	}
	
	public void setIsNeedReelId(Boolean isNeedReelId) {
		this.isNeedReelId = isNeedReelId;
	}
	
	@Column(name = "shop_url", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getShopUrl() {
		return this.shopUrl;
	}
	
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	
	@Column(name = "shop_qrcode_url", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getShopQrcodeUrl() {
		return this.shopQrcodeUrl;
	}
	
	public void setShopQrcodeUrl(String shopQrcodeUrl) {
		this.shopQrcodeUrl = shopQrcodeUrl;
	}
	
	@Column(name = "grade", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	public Float getGrade() {
		return this.grade;
	}
	
	public void setGrade(Float grade) {
		this.grade = grade;
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
			.append("UserId",getUserId())
			.append("Name",getName())
			.append("LogoUrl",getLogoUrl())
			.append("Notice",getNotice())
			.append("Introduction",getIntroduction())
			.append("ProtectionPrice",getProtectionPrice())
			.append("IsNeedPhone",getIsNeedPhone())
			.append("IsNeedReelId",getIsNeedReelId())
			.append("ShopUrl",getShopUrl())
			.append("ShopQrcodeUrl",getShopQrcodeUrl())
			.append("Grade",getGrade())
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
		if(obj instanceof ZcShop == false) return false;
		if(this == obj) return true;
		ZcShop other = (ZcShop)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

