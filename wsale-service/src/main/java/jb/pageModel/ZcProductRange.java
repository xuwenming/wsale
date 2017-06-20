package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcProductRange implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String productId;
	private Double startPrice;
	private Double endPrice;
	private Double price;
	private String addUserId;
	private Date addtime;

	private Double[] startPrices;
	private Double[] endPrices;
	private Double[] prices;

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

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public Double[] getStartPrices() {
		return startPrices;
	}

	public void setStartPrices(Double[] startPrices) {
		this.startPrices = startPrices;
	}

	public Double[] getEndPrices() {
		return endPrices;
	}

	public void setEndPrices(Double[] endPrices) {
		this.endPrices = endPrices;
	}

	public Double[] getPrices() {
		return prices;
	}

	public void setPrices(Double[] prices) {
		this.prices = prices;
	}

	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
}
