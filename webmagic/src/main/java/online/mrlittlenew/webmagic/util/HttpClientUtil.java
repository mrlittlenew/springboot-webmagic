package online.mrlittlenew.webmagic.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static HttpClientDownloader downloader = new HttpClientDownloader();

	public static void main(String[] args) {
		getJson("https://cd.jd.com/promotion/v2?skuId=24436869160&area=1_72_2799_0&shopId=117534&venderId=117534&cat=652,828,13662");
	}
	
	
	public static String getJson(String url){
		Html result = downloader.download(url);
		String jsonStr = result.xpath("//body/text()").get();
    	logger.debug("jsonStr:"+jsonStr);
    	return jsonStr;
	}
	
}
