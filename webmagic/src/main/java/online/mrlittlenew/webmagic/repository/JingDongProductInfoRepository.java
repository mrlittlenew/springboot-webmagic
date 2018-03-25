package online.mrlittlenew.webmagic.repository;

import java.util.List;

import online.mrlittlenew.webmagic.domain.JingDongProductInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JingDongProductInfoRepository extends JpaRepository<JingDongProductInfo, Long> {
	public List<JingDongProductInfo> findBySku(Long sku);
	public JingDongProductInfo findBySkuAndCategoriesAndUnit(Long sku,String categories,String unit);
}