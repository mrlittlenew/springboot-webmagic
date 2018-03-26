package online.mrlittlenew.webmagic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import online.mrlittlenew.webmagic.domain.JingDongProductInfo;

public interface JingDongProductInfoRepository extends JpaRepository<JingDongProductInfo, Long> {
	public List<JingDongProductInfo> findBySku(Long sku);
	public JingDongProductInfo findBySkuAndCategoriesAndUnit(Long sku,String categories,String unit);
	@Query(value="select sku,name from t_jing_dong_product", nativeQuery = true)
	public List<Object[]> findProductDtoList();
}