package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcComment implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String orderId;
	private String productId;
	private Float grade;
	private String content;
	private String addUserId;
	private Date addtime;			
	private String pname;

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderId() {
		return this.orderId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductId() {
		return this.productId;
	}
	public void setGrade(Float grade) {
		this.grade = grade;
	}
	
	public Float getGrade() {
		return this.grade;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
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

}
