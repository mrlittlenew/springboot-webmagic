package online.mrlittlenew.webmagic.service;

import javax.management.JMException;

import online.mrlittlenew.webmagic.dto.JobStatus;
import us.codecraft.webmagic.Spider;

public interface JingDongService {
	public Spider process(String action) throws JMException;

	public JobStatus updatePrice();
}
