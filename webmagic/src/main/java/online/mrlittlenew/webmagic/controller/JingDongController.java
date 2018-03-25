package online.mrlittlenew.webmagic.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
		//new JingDongServiceImpl().process("5327329");
		new JingDongServiceImpl().updatePrice();
		//https://www.jd.com/allSort.aspx
	}
	@RequestMapping("/jingdong")
	public String jingdong(@RequestParam("jobName") String jobName) {
		Spider spiderFromMap=spiderMap.get(jobName);
		if(spiderFromMap!=null){
			return "已经存在，jobName="+jobName;
		}
		try {
			Spider spider=jingDongService.process("all");
			spiderMap.put(jobName, spider);
		} catch (JMException e) {
			e.printStackTrace();
		}

		return "开始计划，jobName="+jobName;
	}
	@RequestMapping("/list")
	public String list() {
		String keys="";
		 Set<String> keySet = spiderMap.keySet();
		 for(String key:keySet){
			 keys+=key+",";
		 }
		return keys;
	}
	
	@RequestMapping("/getStatus")
	public JobStatus getStatus(@RequestParam("jobName") String jobName) {
		Spider spider=spiderMap.get(jobName);
		return JobStatus.build(spider);
	}
	
	@RequestMapping("/stop")
	public JobStatus stop(@RequestParam("jobName") String jobName) {
		Spider spider=spiderMap.get(jobName);
		if(spider!=null){
			spider.stop();
		}
		return JobStatus.build(spider);
	}
	@RequestMapping("/start")
	public JobStatus start(@RequestParam("jobName") String jobName) {
		Spider spider=spiderMap.get(jobName);
		if(spider!=null){
			spider.start();
		}
		return JobStatus.build(spider);
	}
	@RequestMapping("/wait")
	public JobStatus wait(@RequestParam("jobName") String jobName) throws InterruptedException {
		Spider spider=spiderMap.get(jobName);
		if(spider!=null){
			spider.wait();
		}
		return JobStatus.build(spider);
	}
	
	@RequestMapping("/updatePrice")
	public String updatePrice(@RequestParam("jobName") String jobName) {
		Spider spiderFromMap=spiderMap.get(jobName);
		if(spiderFromMap!=null){
			spiderFromMap.close();
			spiderMap.remove(jobName);
		}
		try {
			Spider spider=jingDongService.updatePrice();
			spiderMap.put(jobName, spider);
		} catch (JMException e) {
			e.printStackTrace();
		}

		return "开始计划更新价格，jobName="+jobName;
	}
	
	
	@RequestMapping("/info")
	public String info() {
		jingDongService.productInfoHandle();

		return "开始更新";
	}

}
