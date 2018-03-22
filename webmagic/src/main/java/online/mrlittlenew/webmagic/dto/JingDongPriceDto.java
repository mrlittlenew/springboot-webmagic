package online.mrlittlenew.webmagic.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JingDongPriceDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6060079035275912613L;
	
	@JsonProperty("op")
	private double originalPrice;
	@JsonProperty("m")
	private double officialPrice;
	@JsonProperty("id")
	private String sku;
	@JsonProperty("p")
	private double price;
	public double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public double getOfficialPrice() {
		return officialPrice;
	}
	public void setOfficialPrice(double officialPrice) {
		this.officialPrice = officialPrice;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
	
}
