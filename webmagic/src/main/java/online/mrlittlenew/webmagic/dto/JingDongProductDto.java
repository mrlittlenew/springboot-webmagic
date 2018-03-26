package online.mrlittlenew.webmagic.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;


public class JingDongProductDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6060079035275912614L;
	@Id
	private Long sku;
    private String name;
    private String shopName;
    private String seller;
    private String catName;
    private String description;
    private String catId;
    private String shopId;
    private String venderId;
    private Double price;
    private Date lastUpdateDate;
	public Long getSku() {
		return sku;
	}
	public void setSku(Long sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getVenderId() {
		return venderId;
	}
	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "JingDongProductDto [sku=" + sku + ", name=" + name + ", shopName=" + shopName + ", seller=" + seller
				+ ", catName=" + catName + ", description=" + description + ", catId=" + catId + ", shopId=" + shopId
				+ ", venderId=" + venderId + ", price=" + price + ", lastUpdateDate=" + lastUpdateDate + "]";
	}
	
}
