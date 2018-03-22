package com.example.demo;

import javax.management.JMException;

import online.mrlittlenew.webmagic.JingdongPageProcesser;

public class Test {
public static void main(String[] args) throws JMException {
	//spider.addUrl("https://list.jd.com/list.html?cat=1316,1625,1671");
	String startUrl="https://item.jd.com/836068.html";
	JingdongPageProcesser.main(startUrl);
}
}
