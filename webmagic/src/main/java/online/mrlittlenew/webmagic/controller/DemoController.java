package online.mrlittlenew.webmagic.controller;

import javax.management.JMException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.mrlittlenew.webmagic.processer.JingdongPageProcesser;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import online.mrlittlenew.webmagic.service.EmployeeService;

@RestController
public class DemoController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private JingDongProductRepository jingDongProductRepository;
	
	@RequestMapping("/test")
	String test() {

		employeeService.test();
		
		return "Hello Spring Boot test";
	}
	
	@RequestMapping("/jingdong")
	String jingdong() {

		try {
			//"https://item.jd.com/836068.html"
			String startUrl="https://list.jd.com/list.html?cat=1316,1625,1671";
			JingdongPageProcesser.main(startUrl,jingDongProductRepository);
		} catch (JMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Hello Spring Boot jingdong";
	}
	
	@RequestMapping("/")
	String index() {
		return "Hello Spring Boot";
	}
	
}
