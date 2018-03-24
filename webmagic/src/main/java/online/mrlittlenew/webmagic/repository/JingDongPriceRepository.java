package online.mrlittlenew.webmagic.repository;

import java.util.List;

import online.mrlittlenew.webmagic.domain.JingDongPrice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JingDongPriceRepository extends JpaRepository<JingDongPrice, Long> {
	public List<JingDongPrice> findBySkuOrderByLastUpdateDateDesc(Long sku);
}