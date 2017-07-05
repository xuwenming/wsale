package jb.pageModel;

import jb.listener.Application;
import jb.util.EnumConstants;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcBestProduct implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String channel;
	private String productId;
	private String auditStatus;
	private Date auditTime;			
	private String auditUserId;
	private String auditRemark;
	private Date startTime;			
	private Date endTime;			
	private String payStatus;
	private Date paytime;			
	private String addUserId;
	private Date addtime;

	private String pno;
	private String addUserName;
	private String auditUserName;
	private String categoryName;

	private ZcProduct zcProduct;
	private String categoryId;
	private String group;
	private String productStatus;

	public int getStatus() {
		int status = 0;
		if(endTime != null) {
			if(endTime.getTime() > new Date().getTime()) status = 1;
			else status = 2;
		}
		return status;
	}

	public String getChannelZh() {
		return EnumConstants.BEST_CHANNEL.getCnName(this.channel);
	}

	public String getAuditStatusZh() {
		return Application.getString(this.auditStatus);
	}

	public String getPayStatusZh() {
		return Application.getString(this.payStatus);
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

	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getChannel() {
		return this.channel;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductId() {
		return this.productId;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getAuditStatus() {
		return this.auditStatus;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public Date getAuditTime() {
		return this.auditTime;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	public String getAuditUserId() {
		return this.auditUserId;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	
	public String getAuditRemark() {
		return this.auditRemark;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
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

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
}
