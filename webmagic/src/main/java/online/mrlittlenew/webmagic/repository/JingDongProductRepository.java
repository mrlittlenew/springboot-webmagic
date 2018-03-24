package online.mrlittlenew.webmagic.repository;

import java.util.List;
import java.util.Map;

import online.mrlittlenew.webmagic.domain.JingDongProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JingDongProductRepository extends JpaRepository<JingDongProduct, Long> {

	@Query(value="select p.sku, p.name,p.LAST_UPDATE_DATE,price.price from t_jing_dong_product p join (select sku,max(price) price from t_jing_dong_price group by sku) price on p.sku=price.sku", nativeQuery = true)
	public List<Map> findProductList();
}