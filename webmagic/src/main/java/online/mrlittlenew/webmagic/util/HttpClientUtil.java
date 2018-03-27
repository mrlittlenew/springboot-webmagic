package online.mrlittlenew.webmagic.util;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import online.mrlittlenew.webmagic.dto.IpInfo;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static HttpClientDownloader downloader = new HttpClientDownloader();
	public static final String USER_AGENT="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
	public static final String HEADER="";

	public static void main(String[] args) {
		getJson("http://mrlittlenew.online/proxytest.php");
	}
	
	public static IpInfo getIpInfo(){
		String jsonStr = getJson("http://mrlittlenew.online/proxytest.php");
		ObjectMapper mapper = new ObjectMapper();  
		IpInfo ipInfo=new IpInfo();
		try {
			ipInfo = mapper.readValue(jsonStr, IpInfo.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ipInfo;
	}
	
	public static String getJson(String url){
		Html result = downloader.download(url);
		String jsonStr = result.xpath("//body/text()").get();
    	logger.debug("jsonStr:"+jsonStr);
    	return jsonStr;
	}
	
	
	
}
