package online.mrlittlenew.webmagic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.management.JMException;

import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.domain.JingDongProductInfo;
import online.mrlittlenew.webmagic.domain.JingDongProductInfoHandler;
import online.mrlittlenew.webmagic.handler.JingDongProductHandler;
import online.mrlittlenew.webmagic.pipeline.SaveToDataBasePipeline;
import online.mrlittlenew.webmagic.processer.JingDongPageProcesser;
import online.mrlittlenew.webmagic.processer.JingDongUpdatePriceProcesser;
import online.mrlittlenew.webmagic.repository.JingDongPriceRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductInfoHandlerRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductInfoRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import online.mrlittlenew.webmagic.service.JingDongService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

@Service
public class JingDongServiceImpl implements JingDongService{
	private static Logger logger = LoggerFactory.getLogger(JingDongServiceImpl.class);
	@Autowired
	private SaveToDataBasePipeline saveToDataBasePipeline;
	@Autowired
	private JingDongProductRepository productRep;
	@Autowired
	private JingDongProductInfoRepository productInfoRep;
	@Autowired
	private JingDongProductInfoHandlerRepository productInfoHandlerRep;
	@Autowired
	private JingDongPriceRepository priceRep;
	@Override
	public Spider process(String action) throws JMException {
		String startUrl="https://item.jd.com/2877592.html";
		if("all".equals(action)){
			startUrl="https://list.jd.com/list.html?cat=1316,1625,1671";
		}else{
			startUrl="https://item.jd.com/"+action+".html";
		}
		//String startUrl="https://list.jd.com/list.html?cat=1316,1625,1671";
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
    	httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("180.118.32.100",9000,"","")));

    	Spider spider=Spider.create(new JingDongPageProcesser(productRep));
    	spider.addUrl(startUrl);
    	spider.setScheduler(new FileCacheQueueScheduler("/data/webmagic/scheduler"));
    	//spider.thread(50);
    	spider.addPipeline(new ConsolePipeline());
    	if(saveToDataBasePipeline!=null){
    		spider.addPipeline(saveToDataBasePipeline);
    	}
    	//spider.setDownloader(httpClientDownloader);
    	SpiderMonitor monitor = SpiderMonitor.instance();

    	monitor.register(spider);
    	spider.start();
    	return spider;
		
	}

	@Override
	public Spider updatePrice()  throws JMException{
		Spider spider=Spider.create(new JingDongUpdatePriceProcesser());
    	spider.addPipeline(new ConsolePipeline());
    	List<JingDongProduct> list = productRep.findAll();
    	for(JingDongProduct item:list){
    		String price_url = "https://p.3.cn/prices/mgets?skuIds=J_"+item.getSku();
    		spider.addUrl(price_url);
    	}
    	//for test
    	//String price_url = "https://p.3.cn/prices/mgets?skuIds=J_13517587277";
		//spider.addUrl(price_url);
    	if(saveToDataBasePipeline!=null){
    		spider.addPipeline(saveToDataBasePipeline);
    	}
    	spider.thread(200);
    	spider.start();
    	return spider;
	}

	@Override
	public void productInfoHandle() {
		List<JingDongProduct> list = productRep.findAll();
		List<JingDongProductInfoHandler> handlerList = productInfoHandlerRep.findByActive("Y");
		//handleInfo(p,"种类","抽纸","抽纸",false);
		//handleInfo(p,"种类","卷纸","卷纸",false);
		JingDongProductHandler handler=new JingDongProductHandler(productInfoRep,handlerList);

		List<String> namelist =new ArrayList<String>();
		for(JingDongProduct p:list){
			handler.handleInfo(p);
			List<JingDongProductInfo> infoList = productInfoRep.findBySku(p.getSku());
			if(infoList.size()==0){
				namelist.add(p.getName());
			}
		}
		for(String name:namelist){
			logger.info("====="+name);
		}
		logger.info("=====ending=========");
	}


	
	
	
	
}
