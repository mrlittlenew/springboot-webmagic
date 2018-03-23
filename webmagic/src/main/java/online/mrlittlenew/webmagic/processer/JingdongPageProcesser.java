package online.mrlittlenew.webmagic.processer;

import javax.management.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.mrlittlenew.webmagic.util.JingDongUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class JingdongPageProcesser implements PageProcessor {
	private static Logger logger = LoggerFactory.getLogger(JingdongPageProcesser.class);
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(6000).addHeader("Accept-Encoding", "/")
			.setUserAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");

	public void process(Page page) {
		logger.info("Page Url:" + page.getUrl());
		logger.info(page.getRawText());
		
		boolean updatePrice = false;
		JingDongUtil.handleProductPage(page, updatePrice);
		JingDongUtil.handleListPage(page);
	}

	public Site getSite() {
		return site;

	}

	public static void main(String[] args) throws JMException {
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("10.12.251.1", 8080, "", "")));

		Spider spider = Spider.create(new JingdongPageProcesser());
		String startUrl = "https://item.jd.com/24436869160.html";
		// String startUrl="https://list.jd.com/list.html?cat=1316,1625,1671";
		spider.addUrl(startUrl);
		// spider.thread(50);
		spider.addPipeline(new ConsolePipeline());
		spider.setDownloader(httpClientDownloader);
		SpiderMonitor.instance().register(spider);
		spider.start();
	}

}
