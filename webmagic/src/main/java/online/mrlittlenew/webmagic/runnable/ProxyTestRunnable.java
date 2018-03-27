package online.mrlittlenew.webmagic.runnable;

import java.io.IOException;
import java.util.Date;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import online.mrlittlenew.webmagic.domain.ProxyInfo;
import online.mrlittlenew.webmagic.dto.IpInfo;
import online.mrlittlenew.webmagic.repository.ProxyInfoRepository;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;

public class ProxyTestRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ProxyTestRunnable.class);
	private Thread t;
	private String threadName;
	private String remoteAddr;
	private Queue<ProxyInfo> queue;
	private ProxyInfoRepository proxyInfoRepository;

	public ProxyTestRunnable(String name,Queue<ProxyInfo> queue,String remoteAddr, ProxyInfoRepository proxyInfoRepository) {
		threadName = name;
		this.queue=queue;
		this.remoteAddr=remoteAddr;
		this.proxyInfoRepository=proxyInfoRepository;
		logger.debug("Creating " + threadName);
	}

	@Override
	public void run() {
		logger.debug("Running " + threadName);
		while(!queue.isEmpty()){
			ProxyInfo proxyInfo=queue.poll();
			try{
				HttpClientDownloader downloader = new HttpClientDownloader();
				downloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(proxyInfo.getIp(),proxyInfo.getPort(),"","")));
				
				Html result = downloader.download("http://mrlittlenew.online/proxytest.php");
				String jsonStr = result.xpath("//body/text()").get();
		    	logger.debug("jsonStr:"+jsonStr);
		 
		    	IpInfo ipInfo=new IpInfo();
				try {
					ObjectMapper mapper = new ObjectMapper(); 
					ipInfo = mapper.readValue(jsonStr, IpInfo.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(ipInfo.getRemoteAddr().contains(remoteAddr)){
		    		proxyInfo.setPublicness("失效");
		    	}else if(ipInfo.getHttpForwarded().contains(remoteAddr)){
		    		proxyInfo.setPublicness("透明");
		    	}else if(ipInfo.getHttpVia().contains(remoteAddr)){
		    		proxyInfo.setPublicness("透明");
		    	}else{
		    		proxyInfo.setPublicness("高匿");
		    	}
				proxyInfo.setResult(jsonStr);
			}catch (Exception e) {
				proxyInfo.setPublicness("超时");
				proxyInfo.setResult("timeout");
			}
			logger.debug("hahahahahahahahahahahahahaha");
			logger.debug("Thread " + threadName + " exiting.#"+remoteAddr+"->"+proxyInfo.getIp()+":"+proxyInfo.getPort()+"->"+proxyInfo.getPublicness()+" ##jsonStr:"+proxyInfo.getResult());
			proxyInfo.setLastUpdateDate(new Date());
			proxyInfoRepository.save(proxyInfo);
		}
		
		
	}

	public void start() {
		logger.debug("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
