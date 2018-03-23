package online.mrlittlenew.webmagic.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JingDongPriceDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6060079035275912614L;
	
	@JsonProperty("op")
	private double originalPrice;
	@JsonProperty("m")
	private double officialPrice;
	@JsonProperty("id")
	private String id;
	@JsonProperty("p")
	private double price;
	@JsonProperty("tpp")
	private double tpp;
	@JsonProperty("up")
	private String up;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getTpp() {
		return tpp;
	}
	public void setTpp(double tpp) {
		this.tpp = tpp;
	}
	public String getUp() {
		return up;
	}
	public void setUp(String up) {
		this.up = up;
	}
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

	
	
	
}
