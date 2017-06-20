package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcAuction implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String productId;
	private String buyerId;
	private Double bid;
	private String status;
	private Boolean isAuto;
	private String addUserId;
	private Date addtime;			
	private String updateUserId;
	private Date updatetime;			
	private String pname;

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	private User user;
	private ZcProduct zcProduct;
	private ZcOrder zcOrder;
	private String buyerName;

	public ZcOrder getZcOrder() {
		return zcOrder;
	}

	public void setZcOrder(ZcOrder zcOrder) {
		this.zcOrder = zcOrder;
	}

	public ZcProduct getZcProduct() {
		return zcProduct;
	}

	public void setZcProduct(ZcProduct zcProduct) {
		this.zcProduct = zcProduct;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductId() {
		return this.productId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getBuyerId() {
		return this.buyerId;
	}
	public void setBid(Double bid) {
		this.bid = bid;
	}
	
	public Double getBid() {
		return this.bid;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setIsAuto(Boolean isAuto) {
		this.isAuto = isAuto;
	}
	
	public Boolean getIsAuto() {
		return this.isAuto;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
}
