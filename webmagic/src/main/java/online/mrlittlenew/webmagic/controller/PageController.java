package online.mrlittlenew.webmagic.controller;

import java.util.ArrayList;
import java.util.List;

import online.mrlittlenew.webmagic.domain.JingDongPrice;
import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.domain.JingDongProductInfoHandler;
import online.mrlittlenew.webmagic.dto.JingDongProductDto;
import online.mrlittlenew.webmagic.repository.JingDongPriceRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import online.mrlittlenew.webmagic.service.JingDongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {

	@Autowired
	private JingDongProductRepository productRep;
	@Autowired
	private JingDongPriceRepository priceRep;
	@Autowired
	private JingDongService jingDongService;
	
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
		Pageable pageable = new PageRequest(0, 30, sort);
		Page<JingDongProduct> list = productRep.findAll(pageable);
		List<JingDongProductDto> data=new ArrayList<JingDongProductDto>(); 
		for(JingDongProduct p:list){
			JingDongProductDto dto=new JingDongProductDto();
			List<JingDongPrice> price = priceRep.findBySkuOrderByLastUpdateDateDesc(p.getSku());
			dto.setSku(p.getSku());
			dto.setName(p.getName());
			dto.setShopName(p.getShopName());
			if(price!=null&&price.size()>0){
				dto.setPrice(price.get(0).getPrice());
			}
			data.add(dto);
		}

		model.addAttribute("data",data);  
		return "product";
	}
	@RequestMapping("/handlerList")
	String handlerList(Model model) {
		
		List<JingDongProductInfoHandler> data= jingDongService.getHandlerList();
		model.addAttribute("data",data);  
		return "handlerList";
	}
	@RequestMapping("/handlerForm")
	String handlerForm(Model model,@RequestParam(value="id",required=false) Long id) {
		JingDongProductInfoHandler handler= jingDongService.getHandlerById(id);
		model.addAttribute("handler",handler);  
		return "handlerForm";
	}
}
