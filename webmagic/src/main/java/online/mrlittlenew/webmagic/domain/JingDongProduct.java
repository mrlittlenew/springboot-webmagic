package online.mrlittlenew.webmagic.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_JING_DONG_PRODUCT")
public class JingDongProduct {
    private static final long serialVersionUID = 1L;

    @Id
    private Long sku;
    private String name;
    private double price;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "JingDongProduct [sku=" + sku + ", name=" + name + ", price=" + price + "]";
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
   
    
    
}