package online.mrlittlenew.webmagic.runnable;

import online.mrlittlenew.webmagic.domain.ProxyInfo;
import online.mrlittlenew.webmagic.util.HttpClientUtil;

public class ProxyTestThread {
	 
	   public static void test(String args[]) {
		   String remoteAddr = HttpClientUtil.getIpInfo().getRemoteAddr();
		   ProxyInfo proxy=new ProxyInfo();
		   proxy.setIp("114.249.115.84");
		   proxy.setPort(9000);
		  int threadNum=1;
		  for(int i=1;i<=threadNum;i++){
			  System.out.println(i);
			  //ProxyTestRunnable r = new ProxyTestRunnable( "Thread-"+i,remoteAddr,proxy);
		      //r.start();
		  }
	   }   
	}
