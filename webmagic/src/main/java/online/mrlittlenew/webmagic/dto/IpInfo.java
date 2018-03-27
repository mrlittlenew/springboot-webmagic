package online.mrlittlenew.webmagic.dto;

import java.io.Serializable;

public class IpInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5922444906591669633L;
	
	private String remoteAddr;
	private String httpVia;
	private String httpForwarded;
	
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getHttpVia() {
		return httpVia;
	}
	public void setHttpVia(String httpVia) {
		this.httpVia = httpVia;
	}
	public String getHttpForwarded() {
		return httpForwarded;
	}
	public void setHttpForwarded(String httpForwarded) {
		this.httpForwarded = httpForwarded;
	}
	

}
