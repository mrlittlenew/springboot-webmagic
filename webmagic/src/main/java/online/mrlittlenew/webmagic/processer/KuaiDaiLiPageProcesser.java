package online.mrlittlenew.webmagic.processer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.mrlittlenew.webmagic.domain.ProxyInfo;
import online.mrlittlenew.webmagic.util.HttpClientUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class KuaiDaiLiPageProcesser implements PageProcessor {

	
	private static Logger logger = LoggerFactory.getLogger(KuaiDaiLiPageProcesser.class);
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(6000).addHeader("Accept-Encoding", "/")
			.setUserAgent(HttpClientUtil.USER_AGENT);

	public KuaiDaiLiPageProcesser() {

	}

	public void process(Page page) {

		logger.info("Page Url:" + page.getUrl());
		//代理信息
		
		//List<Selectable> proxyRows = page.getHtml().xpath("//div[@id='list']/table/tbody/html()").selectList(selector)

		List<Selectable> proxyRows = page.getHtml().css("#list table tbody tr").nodes();
		List<ProxyInfo> proxyList=new ArrayList<ProxyInfo>();
		for(Selectable row:proxyRows){
			String ip=row.css("td[data-title='IP']").replace("<td data-title=\"IP\">", "").replace("</td>", "").get();
			String port=row.css("td[data-title='PORT']").replace("<td data-title=\"PORT\">", "").replace("</td>", "").get();
			String publicness=row.css("td[data-title='匿名度']").replace("<td data-title=\"匿名度\">", "").replace("</td>", "").get();
			String type=row.css("td[data-title='类型']").replace("<td data-title=\"类型\">", "").replace("</td>", "").get();
			String local=row.css("td[data-title='位置']").replace("<td data-title=\"位置\">", "").replace("</td>", "").get();
			String speed=row.css("td[data-title='响应速度']").replace("<td data-title=\"响应速度\">", "").replace("</td>", "").replace("秒", "").get();
			String recordDate=row.css("td[data-title='最后验证时间']").replace("<td data-title=\"最后验证时间\">", "").replace("</td>", "").get();
			
			logger.info(ip+":"+port);
			ProxyInfo proxy=new ProxyInfo();
			proxy.setIp(ip);
			try{
				proxy.setPort(Integer.valueOf(port));	
			}catch (Exception e) {
				proxy.setPort(0);
			}
			proxy.setPublicness(publicness);
			proxy.setType(type);
			proxy.setLocal(local);
			try{
				proxy.setSpeed(Double.valueOf(speed));
			}catch (Exception e) {
				proxy.setSpeed(0d);
			}
			proxy.setActive("");
			proxy.setAnonymous("Y");
			proxy.setUserName("");
			proxy.setPassword("");
			proxy.setResult("");
			proxy.setRecordDate(recordDate);
			proxy.setLastUpdateDate(new Date());
			proxyList.add(proxy);
		}
		page.putField("proxyList", proxyList);
		//代理翻页
		//List<String> links = page.getHtml().xpath("//div[@id=listnav]").links().all();
		//page.addTargetRequests(links);
		
	}

	public Site getSite() {
		return site;

	}

}
