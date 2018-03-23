package online.mrlittlenew.webmagic.processer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.mrlittlenew.webmagic.util.HttpClientUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class JingDongUpdatePriceProcesser implements PageProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(JingDongUpdatePriceProcesser.class);
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(6000).addHeader("Accept-Encoding", "/")
			.setUserAgent(HttpClientUtil.USER_AGENT);

	public void process(Page page) {
		logger.info("Page Url:" + page.getUrl());
		logger.info(page.getRawText());
		//TODO 获取价格，保存价格
	}

	public Site getSite() {
		return site;

	}

}
