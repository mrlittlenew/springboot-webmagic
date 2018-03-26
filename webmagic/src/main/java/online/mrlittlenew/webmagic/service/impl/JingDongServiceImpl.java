package online.mrlittlenew.webmagic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import online.mrlittlenew.webmagic.dao.JingDongDao;
import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.domain.JingDongProductInfo;
import online.mrlittlenew.webmagic.domain.JingDongProductInfoHandler;
import online.mrlittlenew.webmagic.dto.JingDongProductDto;
import online.mrlittlenew.webmagic.dto.JingDongProductInfoDto;
import online.mrlittlenew.webmagic.handler.JingDongProductHandler;
import online.mrlittlenew.webmagic.pipeline.SaveToDataBasePipeline;
import online.mrlittlenew.webmagic.processer.JingDongPageProcesser;
import online.mrlittlenew.webmagic.processer.JingDongUpdatePriceProcesser;
import online.mrlittlenew.webmagic.repository.JingDongPriceRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductInfoHandlerRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductInfoRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import online.mrlittlenew.webmagic.service.JingDongService;
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
	@Autowired
	private JingDongDao jingDongDao;
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

	@Override
	public List<JingDongProductInfoHandler> getHandlerList() {
		return productInfoHandlerRep.findAll();
	}

	@Override
	public JingDongProductInfoHandler getHandlerById(Long id) {
		if(id==null){
			return new JingDongProductInfoHandler();
		}
		return productInfoHandlerRep.getOne(id);
	}

	@Override
	public void delHandler(Long id) {
		productInfoHandlerRep.delete(id);
		
	}

	@Override
	public JingDongProductInfoHandler updateHandler(
			JingDongProductInfoHandler handler) {
		JingDongProductInfoHandler po=null;
		if(handler.getId()!=null){
			po = productInfoHandlerRep.findOne(handler.getId());
		}
		if(po==null){
			po= new JingDongProductInfoHandler();
			po.setId(handler.getId());
		}
		po.setActive(handler.getActive());
		po.setCategories(handler.getCategories());
		po.setCatId(handler.getCatId());
		po.setHasNumber(handler.getHasNumber());
		po.setKeyWord(handler.getKeyWord());
		po.setKeyWordOther(handler.getKeyWordOther());
		po.setUnit(handler.getUnit());
		po.setLastUpdateDate(new Date());

		return productInfoHandlerRep.save(po);
	}
	

	@Override
	public void test() {
		List<Object[]> list =  productInfoRep.findProductDtoList();
		
		for(Object[] item:list){
			JingDongProductDto dto=new JingDongProductDto();
			dto.setSku(Long.valueOf(getValue(item[0])));
			dto.setName(getValue(item[1]));
			System.out.println(dto);
		}

		System.out.println(list.size());
	}
	
	private String getValue(Object obj){
		return obj==null?"":obj.toString();
	}

	@Override
	public List<JingDongProductDto> getProductList() {
		Sort sort = new Sort(Direction.DESC, "lastUpdateDate");
		Pageable pageable = new PageRequest(0, 30, sort);
		Page<JingDongProduct> list = productRep.findAll(pageable);
		List<JingDongProductDto> data=new ArrayList<JingDongProductDto>(); 
		for(JingDongProduct p:list){
			JingDongProductDto dto=new JingDongProductDto();
			dto.setSku(p.getSku());
			dto.setName(p.getName());
			dto.setShopName(p.getShopName());
			dto.setPrice(p.getLastPrice());
			dto.setLastUpdateDate(p.getLastUpdateDate());
			data.add(dto);
		}
		return data;
	}

	@Override
	public List<JingDongProductInfoDto> getProductInfoList() {
		List<JingDongProductInfoDto> dataList=new ArrayList<JingDongProductInfoDto>();
		List<Object[]> list = productRep.findProductInfoList();
		for(Object[] obj:list){
			JingDongProductInfoDto dto= new JingDongProductInfoDto();
			dto.setSku(Long.valueOf(nullToEmpty(obj[0])));
			dto.setName(nullToEmpty(obj[1]));
			dto.setPrice(Double.valueOf(nullToEmpty(obj[2])));
			dto.setUnit(nullToEmpty(obj[3]));
			dto.setNum(Double.valueOf(nullToEmpty(obj[4])));
			dto.setPriceOfUnit(Double.valueOf(nullToEmpty(obj[5])));
			dataList.add(dto);
		}
		
		return dataList;
	}
	
	private String nullToEmpty(Object obj){
		return obj==null?"":obj.toString();
	}
	
	
	
	
}
