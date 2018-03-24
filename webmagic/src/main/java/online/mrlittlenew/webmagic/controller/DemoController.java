package online.mrlittlenew.webmagic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


	@RequestMapping("/hello")
	String hello() {

		return "Hello Spring Boot test";
	}

}
