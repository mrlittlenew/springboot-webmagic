package online.mrlittlenew.webmagic.processer;

import online.mrlittlenew.webmagic.domain.JingDongPrice;
import online.mrlittlenew.webmagic.dto.JingDongPriceDto;
import online.mrlittlenew.webmagic.util.HttpClientUtil;
import online.mrlittlenew.webmagic.util.JingDongUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class JingDongUpdatePriceProcesser implements PageProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(JingDongUpdatePriceProcesser.class);
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(6000).addHeader("Accept-Encoding", "/")
			.setUserAgent(HttpClientUtil.USER_AGENT);

	public void process(Page page) {
		logger.info("Page Url:" + page.getUrl());
		
		String price_url = "https://p.3.cn/prices/mgets?skuIds=J_";
		String skuStr = page.getUrl().get().replace(price_url, "");
		logger.info(page.getRawText());
		String jsonStr = page.getRawText();
		//TODO 获取价格，保存价格
		JingDongPriceDto priceDto = JingDongUtil.getPriceDtoFromJson(jsonStr);
		JingDongPrice price=null;
    	double priceNum = priceDto.getPrice();
    	price=new JingDongPrice();
        price.setSku(Long.valueOf(skuStr));
        price.setPrice(priceNum);
        page.putField("price", price);
	}

	public Site getSite() {
		return site;

	}

}
