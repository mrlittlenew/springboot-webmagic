package online.mrlittlenew.webmagic.service.impl;

import javax.management.JMException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.mrlittlenew.webmagic.pipeline.SaveToDataBasePipeline;
import online.mrlittlenew.webmagic.processer.JingdongPageProcesser;
import online.mrlittlenew.webmagic.repository.JingDongPriceRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import online.mrlittlenew.webmagic.service.JingDongService;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@Service
public class JingDongServiceImpl implements JingDongService{
	
	@Autowired
	private JingDongProductRepository productRep;
	@Autowired
	private JingDongPriceRepository priceRep;

	@Override
	public void process(String action) throws JMException {
		String startUrl="https://item.jd.com/2877592.html";
		if("all".equals(action)){
			startUrl="https://list.jd.com/list.html?cat=1316,1625,1671";
		}else{
			startUrl="https://item.jd.com/"+action+".html";
		}
		//String startUrl="https://list.jd.com/list.html?cat=1316,1625,1671";
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
    	httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("10.12.251.1",8080,"","")));

    	Spider spider=Spider.create(new JingdongPageProcesser());
    	spider.addUrl(startUrl);
    	//spider.thread(50);
    	spider.addPipeline(new ConsolePipeline());
    	spider.addPipeline(new SaveToDataBasePipeline(productRep,priceRep));
    	//spider.setDownloader(httpClientDownloader);
    	SpiderMonitor.instance().register(spider);
    	spider.start();
		
	}
	
	
	
	
}
