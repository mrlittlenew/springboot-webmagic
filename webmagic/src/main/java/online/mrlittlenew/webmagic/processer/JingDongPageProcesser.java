package online.mrlittlenew.webmagic.processer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import online.mrlittlenew.webmagic.util.HttpClientUtil;
import online.mrlittlenew.webmagic.util.JingDongUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class JingDongPageProcesser implements PageProcessor {
	
	private JingDongProductRepository productRep;
	
	private static Logger logger = LoggerFactory.getLogger(JingDongPageProcesser.class);
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(6000).addHeader("Accept-Encoding", "/")
			.setUserAgent(HttpClientUtil.USER_AGENT);

	public JingDongPageProcesser(JingDongProductRepository productRep) {
		this.productRep=productRep;
	}

	public void process(Page page) {
		logger.info("Page Url:" + page.getUrl());
		//logger.info(page.getRawText());
		boolean updatePrice = false;
		boolean updateProduct = true;
		JingDongUtil.handleProductPage(page, updatePrice);
		JingDongUtil.handleListPage(page,productRep,updateProduct );
	}

	public Site getSite() {
		return site;

	}

}
