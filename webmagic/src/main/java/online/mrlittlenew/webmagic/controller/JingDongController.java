package online.mrlittlenew.webmagic.controller;

import java.util.HashMap;
import java.util.Map;

import javax.management.JMException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.mrlittlenew.webmagic.dto.JobStatus;
import online.mrlittlenew.webmagic.service.JingDongService;
import online.mrlittlenew.webmagic.service.impl.JingDongServiceImpl;
import us.codecraft.webmagic.Spider;

@RestController
public class JingDongController {

	@Autowired
	private JingDongService jingDongService;
	private Map<String,Spider> spiderMap=new HashMap<String,Spider>();

	public static void main(String[] args) throws JMException {
		new JingDongServiceImpl().process("5327329");
	}
	@RequestMapping("/jingdong")
	public String jingdong(@RequestParam("jobName") String jobName,@RequestParam("action") String action) {

		try {
			Spider spider=jingDongService.process(action);
			spiderMap.put(jobName, spider);
		} catch (JMException e) {
			e.printStackTrace();
		}

		return "开始计划，jobName="+jobName;
	}
	@RequestMapping("/getStatus")
	public JobStatus getStatus(@RequestParam("jobName") String jobName) {
		Spider spider=spiderMap.get(jobName);
		return JobStatus.build(spider);
	}
	
	@RequestMapping("/updatePrice")
	public JobStatus updatePrice() {
		return jingDongService.updatePrice();
	}

}
