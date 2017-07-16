package jb.pageModel;

import jb.absx.F;
import jb.listener.Application;

import java.util.Date;

/**
 * 
 *
 * 订单商品信息
 * 
 * @author John
 * 
 */
@SuppressWarnings("serial")
public class OrderProductInfo implements java.io.Serializable {

	private String id;
	private String pno;
	private String icon;
	private String content;
	private String sellerUserId;
	private String buyerUserId;
	private Date startingTime;
	private Date realDeadline;
	private Date hammerTime;
	private Double totalPrice;
	private Double margin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContentLine() {
		if(!F.empty(this.content))
			return this.content.replaceAll(Application.getSWordReg(), "***").replaceAll("[\\r\\n]", "");
		return this.content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public Date getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}

	public Date getRealDeadline() {
		return realDeadline;
	}

	public void setRealDeadline(Date realDeadline) {
		this.realDeadline = realDeadline;
	}

	public Date getHammerTime() {
		return hammerTime;
	}

	public void setHammerTime(Date hammerTime) {
		this.hammerTime = hammerTime;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}
}
