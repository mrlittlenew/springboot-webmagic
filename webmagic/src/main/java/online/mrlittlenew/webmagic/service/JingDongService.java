package online.mrlittlenew.webmagic.service;

import java.util.List;

import javax.management.JMException;

import online.mrlittlenew.webmagic.domain.JingDongProductInfoHandler;

import us.codecraft.webmagic.Spider;

public interface JingDongService {
	public Spider process(String action) throws JMException;

	public Spider updatePrice() throws JMException;
	
	public void productInfoHandle();

	public List<JingDongProductInfoHandler> getHandlerList();

	public JingDongProductInfoHandler getHandlerById(Long id);

	public void delHandler(Long id);

	public JingDongProductInfoHandler updateHandler(JingDongProductInfoHandler handler);
}
