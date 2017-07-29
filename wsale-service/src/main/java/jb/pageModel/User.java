package jb.pageModel;

import java.util.Date;
import java.util.Map;

import jb.util.PathUtil;

@SuppressWarnings("serial")
public class User implements java.io.Serializable {

	private java.lang.String id;
	private java.lang.String name;
	private java.lang.String pwd;
	private java.lang.String nickname;
	private java.lang.String headImage;
	private java.lang.String mobile;
	private java.lang.String wechatNo;
	private java.lang.Integer sex;
	private java.lang.String area;
	private java.lang.String bardian;
	private java.lang.String contact;
	private java.lang.String buyerV;
	private java.lang.String sellerV;
	private java.lang.Boolean isAuth;
	private java.lang.Boolean isPayBond;
	private java.lang.String utype;
	private java.lang.Boolean isDeleted;
	private Date blocktime;
	private Date createdatetime;
	private Date modifydatetime;
	private java.lang.Boolean isGag;
	private java.lang.String hxPassword;
	private java.lang.Boolean hxStatus;
	private Integer serviceFeePer;
	private Integer shopSeq;

	private Date createdatetimeStart;
	private Date createdatetimeEnd;
	private Date modifydatetimeStart;
	private Date modifydatetimeEnd;

	private String roleIds;
	private String roleNames;

	private String positionId;
	private String position;
	private int bbsNums; // 发帖数
	private int fans; // 粉丝数
	private int shieldors; // 屏蔽数
	private boolean attred; // 是否关注
	private boolean self; // 是否本人自己
	private int credit; // 信誉
	private String q; // combogrid查询条件

	private java.lang.Double walletAmount;
	private java.lang.Double protection;
	private int biddingNums;
	private Map<String, Double> turnover;

	public String getUtypeZh() {
		if("UT01".equals(utype)) return "后端";
		else if("UT02".equals(utype)) return "前端";
		else return "模拟";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBardian() {
		return bardian;
	}

	public void setBardian(String bardian) {
		this.bardian = bardian;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getBuyerV() {
		return buyerV;
	}

	public void setBuyerV(String buyerV) {
		this.buyerV = buyerV;
	}

	public String getSellerV() {
		return sellerV;
	}

	public void setSellerV(String sellerV) {
		this.sellerV = sellerV;
	}

	public Boolean getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Boolean isAuth) {
		this.isAuth = isAuth;
	}

	public Boolean getIsPayBond() {
		return isPayBond;
	}

	public void setIsPayBond(Boolean isPayBond) {
		this.isPayBond = isPayBond;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getBlocktime() {
		return blocktime;
	}

	public void setBlocktime(Date blocktime) {
		this.blocktime = blocktime;
	}

	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Date getModifydatetime() {
		return modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	public Boolean getIsGag() {
		return isGag;
	}

	public void setIsGag(Boolean isGag) {
		this.isGag = isGag;
	}

	public String getHxPassword() {
		return hxPassword;
	}

	public void setHxPassword(String hxPassword) {
		this.hxPassword = hxPassword;
	}

	public Boolean getHxStatus() {
		return hxStatus;
	}

	public void setHxStatus(Boolean hxStatus) {
		this.hxStatus = hxStatus;
	}

	public Integer getServiceFeePer() {
		return serviceFeePer;
	}

	public void setServiceFeePer(Integer serviceFeePer) {
		this.serviceFeePer = serviceFeePer;
	}

	public Integer getShopSeq() {
		return shopSeq;
	}

	public void setShopSeq(Integer shopSeq) {
		this.shopSeq = shopSeq;
	}

	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	public Date getModifydatetimeStart() {
		return modifydatetimeStart;
	}

	public void setModifydatetimeStart(Date modifydatetimeStart) {
		this.modifydatetimeStart = modifydatetimeStart;
	}

	public Date getModifydatetimeEnd() {
		return modifydatetimeEnd;
	}

	public void setModifydatetimeEnd(Date modifydatetimeEnd) {
		this.modifydatetimeEnd = modifydatetimeEnd;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getBbsNums() {
		return bbsNums;
	}

	public void setBbsNums(int bbsNums) {
		this.bbsNums = bbsNums;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getShieldors() {
		return shieldors;
	}

	public void setShieldors(int shieldors) {
		this.shieldors = shieldors;
	}

	public boolean isAttred() {
		return attred;
	}

	public void setAttred(boolean attred) {
		this.attred = attred;
	}

	public boolean isSelf() {
		return self;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Double getProtection() {
		return protection;
	}

	public void setProtection(Double protection) {
		this.protection = protection;
	}

	public Double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}

	public int getBiddingNums() {
		return biddingNums;
	}

	public void setBiddingNums(int biddingNums) {
		this.biddingNums = biddingNums;
	}

	public Map<String, Double> getTurnover() {
		return turnover;
	}

	public void setTurnover(Map<String, Double> turnover) {
		this.turnover = turnover;
	}
}
