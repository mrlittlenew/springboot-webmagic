package online.mrlittlenew.webmagic.repository;

import java.util.List;

import online.mrlittlenew.webmagic.domain.ProxyInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProxyInfoRepository extends JpaRepository<ProxyInfo, Long> {


	List<ProxyInfo> findByIpAndPort(String ip, Integer port);
}