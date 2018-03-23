package online.mrlittlenew.webmagic.dto;

import java.io.Serializable;
import java.util.Date;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;

public class JobStatus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5922444906591669633L;
	public static JobStatus build(Spider spider){
		JobStatus status=new JobStatus();
		if(spider==null){
			status.setMessage("没有该Job！");
			return status;
		}
		if (spider.getScheduler() instanceof MonitorableScheduler) {
			int totalRequestsCount = ((MonitorableScheduler) spider.getScheduler()).getTotalRequestsCount(spider);
			int leftRequestsCount=((MonitorableScheduler) spider.getScheduler()).getLeftRequestsCount(spider);
			
			status.setStartTime(spider.getStartTime());
			status.setRunning(spider.getStatus().toString());
			status.setLeftRequestsCount(leftRequestsCount);
			status.setTotalRequestsCount(totalRequestsCount);
			return status;
        }
		status.setMessage("获取状态失败!");
		return status;
	}
	private String message;
	private Date startTime;
	private String running;
	private int totalRequestsCount;
	private int leftRequestsCount;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getTotalRequestsCount() {
		return totalRequestsCount;
	}
	public void setTotalRequestsCount(int totalRequestsCount) {
		this.totalRequestsCount = totalRequestsCount;
	}
	public int getLeftRequestsCount() {
		return leftRequestsCount;
	}
	public void setLeftRequestsCount(int leftRequestsCount) {
		this.leftRequestsCount = leftRequestsCount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRunning() {
		return running;
	}
	public void setRunning(String running) {
		this.running = running;
	}

}
