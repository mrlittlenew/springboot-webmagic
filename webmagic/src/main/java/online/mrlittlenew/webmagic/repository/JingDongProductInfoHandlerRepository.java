package online.mrlittlenew.webmagic.repository;

import java.util.List;

import online.mrlittlenew.webmagic.domain.JingDongProductInfoHandler;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JingDongProductInfoHandlerRepository extends JpaRepository<JingDongProductInfoHandler, Long> {
	List<JingDongProductInfoHandler> findByActive(String active);
}