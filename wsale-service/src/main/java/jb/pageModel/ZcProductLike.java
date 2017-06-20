package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcProductLike implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String productId;
	private String userId;
	private Date addtime;

	private User user;
	private ZcProduct zcProduct;
	private String pname;
	private String userName;



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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
