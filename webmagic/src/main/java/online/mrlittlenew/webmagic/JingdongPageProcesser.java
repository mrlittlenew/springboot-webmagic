package online.mrlittlenew.webmagic;

import java.util.List;

import javax.management.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import online.mrlittlenew.webmagic.dto.JingDongPriceDto;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;

public class JingdongPageProcesser implements PageProcessor {
	private static int count=0;
	private static Logger logger = LoggerFactory.getLogger(JingdongPageProcesser.class);
    private Site site = Site.me()
    		.setRetryTimes(3)
    		.setSleepTime(1000)
    		.setTimeOut(6000)
    		.addHeader("Accept-Encoding", "/")
    		.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");  
    
    private String price_url = "https://p.3.cn/prices/mgets?skuIds=J_";
    
    private HttpClientDownloader downloader = new HttpClientDownloader();

    public void process(Page page) {
    	count++;
    	//logger.debug(page.getRawText());
    	logger.info("===================================== count:"+count);
    	boolean match = page.getUrl().regex("https://item\\.jd\\.com/\\d+.html").match();
        if(match){
        	handleProductPage(page);
        }else{
        	//handleListPage(page);
        }
    }
    public void handleListPage(Page page){
    	//获取产品链接
    	List<String> productLinks = page.getHtml().xpath("//div[@id='plist']//li//div[@class='p-name']/a").links().all();
    	//logger.info("productLinks size:"+productLinks.size());
    	//page.addTargetRequests(productLinks);
    	//获取列表页翻页
    	List<String> listPageLinks = page.getHtml().xpath("//div[@class='page']/div[@id='J_bottomPage']//a").links().all();
    	//logger.info("listPageLinks size:"+listPageLinks.size());
    	page.addTargetRequests(listPageLinks);
    }
    
    public void handleProductPage(Page page){
    	String url = page.getUrl().get();
    	logger.debug("match:"+url);
    	String sku = url.split("/")[3].replace(".html", "");
    	String name=page.getHtml().xpath("//div[@class='product-intro']/div[@class='itemInfo-wrap']/div[@class='sku-name']/text()").get();
    	Html result = downloader.download(price_url+sku);
    	String jsonStr = result.xpath("//body/text()").get();
    	logger.debug("jsonStr:"+jsonStr);
    	ObjectMapper mapper = new ObjectMapper();  
    	JingDongPriceDto priceDto = new JingDongPriceDto();
		try {
			List<JingDongPriceDto> priceDtos = mapper.readValue(jsonStr, new TypeReference<List<JingDongPriceDto>>() { });
			priceDto=priceDtos.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	logger.info("----------------------");
    	logger.info("sku:"+sku);
    	logger.info("name:"+name);
    	logger.info("price:"+priceDto.getPrice());
    	logger.info("----------------------");
    }


    public Site getSite() {
        return site;

    }
    
    
     

    public static void main(String startUrl) throws JMException {
    	HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
    	httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("10.12.251.1",8080,"","")));

    	Spider spider=Spider.create(new JingdongPageProcesser());
    	//spider.addUrl("https://list.jd.com/list.html?cat=1316,1625,1671");
    	//spider.addUrl("https://item.jd.com/836068.html");
    	spider.addUrl(startUrl);
    	//spider.thread(50);
    	spider.addPipeline(new ConsolePipeline());
    	spider.addPipeline(new FilePipeline());
    	//spider.setDownloader(httpClientDownloader);
    	
    	SpiderMonitor.instance().register(spider);
    	spider.start();
    }
}
