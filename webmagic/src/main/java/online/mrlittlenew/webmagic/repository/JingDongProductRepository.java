package online.mrlittlenew.webmagic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.mrlittlenew.webmagic.domain.JingDongProduct;

public interface JingDongProductRepository extends JpaRepository<JingDongProduct, Long> {

	JingDongProduct findBySku(Long sku);
}