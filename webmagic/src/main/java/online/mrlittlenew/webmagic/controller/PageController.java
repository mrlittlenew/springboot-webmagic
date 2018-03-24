package online.mrlittlenew.webmagic.controller;

import java.util.ArrayList;
import java.util.List;

import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {

	@Autowired
	private JingDongProductRepository productRep;
	@RequestMapping("/")
	String index() {
		return "index";
	}
	@RequestMapping("/test")
	String test() {
		return "test";
	}
	@RequestMapping("/test2")
	String test2(Model model) {
		model.addAttribute("name","seawater");  
		return "test";
	}
	@RequestMapping("/boot")
	String bootstrap(Model model) {
		model.addAttribute("name","seawater");  
		return "bootstrap";
	}
	@RequestMapping("/product")
	String product(Model model) {
		Sort sort = new Sort(Direction.DESC, "lastUpdateDate");
		Pageable pageable = new PageRequest(0, 100, sort);
		Page<JingDongProduct> list = productRep.findAll(pageable);
		List<JingDongProduct> data = list.getContent();
		model.addAttribute("data",data);  
		return "product";
	}
}