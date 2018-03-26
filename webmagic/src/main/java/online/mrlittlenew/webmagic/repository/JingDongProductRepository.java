package online.mrlittlenew.webmagic.repository;

import java.util.List;
import java.util.Map;

import online.mrlittlenew.webmagic.domain.JingDongProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JingDongProductRepository extends JpaRepository<JingDongProduct, Long> {

	@Query(value="select p.sku, p.name,p.LAST_UPDATE_DATE,price.price from t_jing_dong_product p join (select sku,max(price) price from t_jing_dong_price group by sku) price on p.sku=price.sku", nativeQuery = true)
	public List<Map> findProductList();
	
	@Query(value="select price.sku,price.name,price.last_price,info.unit,info.num,cast(price.last_price/info.num as decimal(10,2)) as priceOfUnit from t_jing_dong_product price "
			+ "join ("
			+ "select sku,unit,num from t_jing_dong_product_info "
			+ "where sku in (select sku from t_jing_dong_product_info where categories='种类' and unit='卷纸') "
			+ "and id not in (select id from t_jing_dong_product_info where categories='种类' and unit='卷纸')"
			+ ") info "
			+ "on price.sku=info.sku  "
			+ "where price.last_price/info.num>0.2 "
			+ "order by price.last_price/info.num", nativeQuery = true)
	public List<Object[]> findProductInfoList();
}