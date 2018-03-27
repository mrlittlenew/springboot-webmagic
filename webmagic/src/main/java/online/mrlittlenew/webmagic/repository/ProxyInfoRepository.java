package online.mrlittlenew.webmagic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.mrlittlenew.webmagic.domain.ProxyInfo;

public interface ProxyInfoRepository extends JpaRepository<ProxyInfo, Long> {
}