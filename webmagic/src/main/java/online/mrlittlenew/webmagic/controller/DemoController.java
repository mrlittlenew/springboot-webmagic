package online.mrlittlenew.webmagic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


	@RequestMapping("/test")
	String test() {

		return "Hello Spring Boot test";
	}

	@RequestMapping("/")
	String index() {
		return "Hello Spring Boot";
	}

}
