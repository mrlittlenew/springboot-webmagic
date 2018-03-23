package online.mrlittlenew.webmagic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.mrlittlenew.webmagic.service.EmployeeService;

@RestController
public class DemoController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/test")
	String test() {

		employeeService.test();

		return "Hello Spring Boot test";
	}

	@RequestMapping("/")
	String index() {
		return "Hello Spring Boot";
	}

}
