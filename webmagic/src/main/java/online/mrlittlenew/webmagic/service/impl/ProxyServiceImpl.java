package online.mrlittlenew.webmagic.service.impl;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.mrlittlenew.webmagic.domain.ProxyInfo;
import online.mrlittlenew.webmagic.pipeline.ProxyInfoPipeline;
import online.mrlittlenew.webmagic.processer.KuaiDaiLiPageProcesser;
import online.mrlittlenew.webmagic.repository.ProxyInfoRepository;
import online.mrlittlenew.webmagic.runnable.ProxyTestRunnable;
import online.mrlittlenew.webmagic.service.ProxyService;
import online.mrlittlenew.webmagic.util.HttpClientUtil;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

@Service
public class ProxyServiceImpl implements ProxyService{
	private static Logger logger = LoggerFactory.getLogger(ProxyServiceImpl.class);
	@Autowired
	private ProxyInfoPipeline proxyInfoPipeline;
	@Autowired
	private ProxyInfoRepository proxyInfoRepository;

	@Override
	public Spider getKuaiDaiLi(Integer pageNum) {
		String startUrl="https://www.kuaidaili.com/free/inha/";
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
    	httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("114.215.102.168",8081,"","")));
    	Spider spider=Spider.create(new KuaiDaiLiPageProcesser());
    	for(int i=1; i<=pageNum;i++){
    		spider.addUrl("https://www.kuaidaili.com/free/inha/"+i+"/");
    	}
    	
    	//spider.setScheduler(new FileCacheQueueScheduler("/data/webmagic/scheduler"));
    	//spider.thread(50);
    	spider.addPipeline(new ConsolePipeline());
    	if(proxyInfoPipeline!=null){
    		spider.addPipeline(proxyInfoPipeline);
    	}
    	spider.setDownloader(httpClientDownloader);
    	spider.start();
    	return spider;
	}
	
	
	
	public static void main(String[] args) {
		new ProxyServiceImpl().getKuaiDaiLi(10);
	}



	@Override
	public String testProxy() {
		String remoteAddr = HttpClientUtil.getIpInfo().getRemoteAddr();
		
		
		Queue<ProxyInfo> queue = new ConcurrentLinkedQueue<ProxyInfo>();
		List<ProxyInfo> list = proxyInfoRepository.findAll();
		queue.addAll(list);
		
		logger.info("queue list:"+queue.size());
		int threadNum=200;
		for(int i=1;i<=threadNum;i++){
			ProxyTestRunnable r = new ProxyTestRunnable( "Thread-"+i,queue,remoteAddr,proxyInfoRepository);
		    r.start();
		}

		return null;
	}
	
	
	
	
	
}
