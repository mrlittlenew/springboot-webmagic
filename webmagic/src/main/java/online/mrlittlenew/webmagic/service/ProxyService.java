package online.mrlittlenew.webmagic.service;

import us.codecraft.webmagic.Spider;

public interface ProxyService {
	public Spider getKuaiDaiLi(Integer pageNum, String proxyIP, Integer proxyPort, Integer threadNum);
	public String testProxy();
}
