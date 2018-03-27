package online.mrlittlenew.webmagic.runnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import online.mrlittlenew.webmagic.domain.ProxyInfo;
import online.mrlittlenew.webmagic.util.HttpClientUtil;

public class Test {
	// TODO: queue是LinkedList对象时，程序会出错。
    //private static Queue<String> queue = new LinkedList<String>();
    private static Queue<ProxyInfo> queue = new ConcurrentLinkedQueue<ProxyInfo>();
    public static void main(String[] args) {
        
		String remoteAddr = HttpClientUtil.getIpInfo().getRemoteAddr();
		for(int i=1;i<=100;i++){
			ProxyInfo proxy=new ProxyInfo();
			proxy.setIp("114.249.115."+i);
			proxy.setPort(9000);
			queue.add(proxy);
		}
		System.out.println(queue.size());
		int threadNum=50;
		for(int i=1;i<=threadNum;i++){
			ProxyTestRunnable r = new ProxyTestRunnable( "Thread-"+i,queue,remoteAddr,null);
		    r.start();
		}
    }
}
