package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "tuser")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tuser implements java.io.Serializable {

	public static final String ALIAS_UTYPE = "用户类型";
	public static final String ALIAS_HEAD_IMAGE = "头像";
	public static final String ALIAS_NICKNAME = "昵称";
	public static final String ALIAS_AREA_CODE = "地区";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_BARDIAN = "个性签名";

	private String id;
	private Date createdatetime;
	private Date modifydatetime;
	private String name;
	private String pwd;
	private Set<Trole> troles = new HashSet<Trole>(0);

	//@Length(max=36)
	private String nickname;
	//@Length(max=256)
	private String headImage;
	//@Length(max=36)
	private String mobile;

	//@Length(max=36)
	private java.lang.String wechatNo;
	private java.lang.Integer sex;
	//@Length(max=256)
	private java.lang.String area;
	//@Length(max=128)
	private String bardian;
	//@Length(max=36)
	private java.lang.String contact;
	//@Length(max=4)
	private java.lang.String buyerV;
	//@Length(max=4)
	private java.lang.String sellerV;
	//
	private java.lang.Boolean isAuth;
	//
	private java.lang.Boolean isPayBond;
	private String utype;
	private java.lang.Boolean isDeleted;
	private Date blocktime;
	private java.lang.Boolean isGag;
	private java.lang.String hxPassword;
	private java.lang.Boolean hxStatus;
	private Integer serviceFeePer;
	private Integer shopSeq;

	public Tuser() {
	}

	public Tuser(String id, String name, String pwd) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	public Tuser(String id, Date createdatetime, Date modifydatetime, String name, String pwd, Set<Trole> troles) {
		this.id = id;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
		this.name = name;
		this.pwd = pwd;
		this.troles = troles;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 19)
	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME", length = 19)
	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PWD", nullable = true, length = 100)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "utype", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getUtype() {
		return this.utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	@Column(name = "head_image", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getHeadImage() {
		return this.headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@Column(name = "nickname", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "bardian", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getBardian() {
		return this.bardian;
	}

	public void setBardian(String bardian) {
		this.bardian = bardian;
	}


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tuser_trole", joinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) })
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

	@Column(name = "mobile", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "wechat_no", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getWechatNo() {
		return this.wechatNo;
	}

	public void setWechatNo(java.lang.String wechatNo) {
		this.wechatNo = wechatNo;
	}

	@Column(name = "sex", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getSex() {
		return this.sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	@Column(name = "area", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public java.lang.String getArea() {
		return this.area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	@Column(name = "contact", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getContact() {
		return this.contact;
	}

	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	@Column(name = "buyer_v", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getBuyerV() {
		return this.buyerV;
	}

	public void setBuyerV(java.lang.String buyerV) {
		this.buyerV = buyerV;
	}

	@Column(name = "seller_v", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getSellerV() {
		return this.sellerV;
	}

	public void setSellerV(java.lang.String sellerV) {
		this.sellerV = sellerV;
	}

	@Column(name = "isDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "blocktime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getBlocktime() {
		return blocktime;
	}

	public void setBlocktime(Date blocktime) {
		this.blocktime = blocktime;
	}

	@Column(name = "isAuth", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsAuth() {
		return this.isAuth;
	}

	public void setIsAuth(java.lang.Boolean isAuth) {
		this.isAuth = isAuth;
	}

	@Column(name = "isPayBond", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsPayBond() {
		return this.isPayBond;
	}

	public void setIsPayBond(java.lang.Boolean isPayBond) {
		this.isPayBond = isPayBond;
	}

	@Column(name = "isGag", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsGag() {
		return isGag;
	}

	public void setIsGag(Boolean isGag) {
		this.isGag = isGag;
	}
	@Column(name = "hx_password", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getHxPassword() {
		return hxPassword;
	}

	public void setHxPassword(String hxPassword) {
		this.hxPassword = hxPassword;
	}
	@Column(name = "hx_status", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getHxStatus() {
		return hxStatus;
	}

	public void setHxStatus(Boolean hxStatus) {
		this.hxStatus = hxStatus;
	}

	@Column(name = "service_fee_per", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getServiceFeePer() {
		return serviceFeePer;
	}

	public void setServiceFeePer(Integer serviceFeePer) {
		this.serviceFeePer = serviceFeePer;
	}

	@Column(name = "shop_seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getShopSeq() {
		return shopSeq;
	}

	public void setShopSeq(Integer shopSeq) {
		this.shopSeq = shopSeq;
	}
}
