package online.mrlittlenew.webmagic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.mrlittlenew.webmagic.service.ProxyService;

@RestController
@RequestMapping("/proxy")
public class ProxyInfoController {

	@Autowired
	private ProxyService proxyService;

	@RequestMapping("/getKuaiDaiLi")
	public String getKuaiDaiLi() {
		proxyService.getKuaiDaiLi();
		
		return "开始计划.";
	}
	
	@RequestMapping("/testProxy")
	public String testProxy() {
		proxyService.testProxy();
		
		return "开始测试.";
	}
	

}
