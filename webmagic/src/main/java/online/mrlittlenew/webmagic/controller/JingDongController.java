package online.mrlittlenew.webmagic.controller;

import javax.management.JMException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.mrlittlenew.webmagic.service.JingDongService;

@RestController
public class JingDongController {

	@Autowired
	private JingDongService jingDongService;

	@RequestMapping("/jingdong")
	String jingdong(@RequestParam("action") String action) {

		try {
			jingDongService.process(action);
		} catch (JMException e) {
			e.printStackTrace();
		}

		return "Hello Spring Boot jingdong";
	}

}
