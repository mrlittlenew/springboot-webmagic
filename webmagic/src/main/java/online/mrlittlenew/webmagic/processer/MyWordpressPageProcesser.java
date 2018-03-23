package online.mrlittlenew.webmagic.processer;

import java.util.List;

import javax.management.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class MyWordpressPageProcesser implements PageProcessor {

	private static Logger logger = LoggerFactory.getLogger(MyWordpressPageProcesser.class);
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    public void process(Page page) {
    	
        List<String> links = page.getHtml().links().regex("http://www\\.mrlittlenew\\.online/\\w+").all();
        
        //System.out.println("-------------------links size:"+links.size());
        
        for(String link:links){
        	//System.out.println("------------------link:"+link);
        }
        page.addTargetRequests(links);
        
        page.putField("title", page.getHtml().xpath("//div[@class='entry-header']/h1/text()").toString());
        //page.putField("content", "");
        //page.putField("tags",page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }


    public Site getSite() {
        return site;

    }

    public static void main(String[] args) throws JMException {
    	HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
    	httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("10.12.251.1",8080,"","")));
    	    
    	
    	Spider spider=Spider.create(new MyWordpressPageProcesser());
    	spider.addUrl("http://www.mrlittlenew.online/");
    	spider.addUrl("http://my.oschina.net/flashsword/blog");
    	spider.thread(50);
    	spider.addPipeline(new ConsolePipeline());
    	spider.addPipeline(new FilePipeline());
    	spider.setDownloader(httpClientDownloader);
    	
    	SpiderMonitor.instance().register(spider);
    	spider.start();
    }
}
