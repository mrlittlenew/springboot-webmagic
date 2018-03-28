package online.mrlittlenew.webmagic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.mrlittlenew.webmagic.service.ProxyService;

@RestController
@RequestMapping("/proxy")
public class ProxyInfoController {

	@Autowired
	private ProxyService proxyService;

	@RequestMapping("/getKuaiDaiLi")
	public String getKuaiDaiLi(@RequestParam("pageNum") Integer pageNum,@RequestParam(value="proxyIP",required=false) String proxyIP,@RequestParam(value="proxyPort",required=false)Integer proxyPort,@RequestParam(value="threadNum",required=false)Integer threadNum) {
		proxyService.getKuaiDaiLi(pageNum,proxyIP,proxyPort,threadNum);
		
		return "开始计划.";
	}
	
	@RequestMapping("/testProxy")
	public String testProxy() {
		proxyService.testProxy();
		
		return "开始测试.";
	}
	

}
