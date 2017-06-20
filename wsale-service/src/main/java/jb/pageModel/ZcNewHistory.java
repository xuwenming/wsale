package jb.pageModel;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ZcNewHistory implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String openid;
	private String userId;
	private String productIds;
	private Boolean isRead;
	private Date addtime;
	private Date updatetime;

	private Date startTime;
	private Date endTime;
	private Boolean sameDay; // 是否当天
	private User user;
	private List<ZcProduct> products;

	
	public void setId(String id) {
		this.id = id;
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
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	
	public String getProductIds() {
		return this.productIds;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getSameDay() {
		return sameDay;
	}

	public void setSameDay(Boolean sameDay) {
		this.sameDay = sameDay;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ZcProduct> getProducts() {
		return products;
	}

	public void setProducts(List<ZcProduct> products) {
		this.products = products;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
