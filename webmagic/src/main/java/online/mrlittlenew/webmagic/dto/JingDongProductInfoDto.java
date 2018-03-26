package online.mrlittlenew.webmagic.dto;

import java.io.Serializable;

import javax.persistence.Id;


public class JingDongProductInfoDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6060079035275912614L;
	@Id
	private Long sku;
    private String name;
    private Double price;
    private String unit;
    private Double num;
    private Double priceOfUnit;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public Double getPriceOfUnit() {
		return priceOfUnit;
	}
	public void setPriceOfUnit(Double priceOfUnit) {
		this.priceOfUnit = priceOfUnit;
	}
	@Override
	public String toString() {
		return "JingDongProductInfoDto [sku=" + sku + ", name=" + name + ", price=" + price + ", unit=" + unit
				+ ", num=" + num + ", priceOfUnit=" + priceOfUnit + "]";
	}
    
    
    
	
}
