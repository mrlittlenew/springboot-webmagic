package online.mrlittlenew.webmagic.service;

import javax.management.JMException;

import us.codecraft.webmagic.Spider;

public interface JingDongService {
	public Spider process(String action) throws JMException;

	public Spider updatePrice() throws JMException;
}
