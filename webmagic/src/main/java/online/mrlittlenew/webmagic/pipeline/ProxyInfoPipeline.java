package online.mrlittlenew.webmagic.pipeline;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.mrlittlenew.webmagic.domain.ProxyInfo;
import online.mrlittlenew.webmagic.repository.ProxyInfoRepository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class ProxyInfoPipeline implements Pipeline{

	private static Logger logger = LoggerFactory.getLogger(ProxyInfoPipeline.class);
	
	@Autowired
	private ProxyInfoRepository proxyInfoRep;

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<ProxyInfo> proxyList=resultItems.get("proxyList");
		proxyInfoRep.save(proxyList);
		/*for(ProxyInfo info:proxyList){
			List<ProxyInfo> list = proxyInfoRep.findByIpAndPort(info.getIp(), info.getPort());
			if(list.size()==0){
				proxyInfoRep.save(info);
			}
		}*/
		
	}

}
