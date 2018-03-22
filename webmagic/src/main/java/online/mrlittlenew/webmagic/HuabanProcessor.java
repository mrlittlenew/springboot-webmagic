package online.mrlittlenew.webmagic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
* 花瓣网抽取器。<br>
* 使用Selenium做页面动态渲染。<br>
*/
public class HuabanProcessor implements PageProcessor {

private Site site;

public void process(Page page) {
    page.addTargetRequests(page.getHtml().links().regex("http://huaban\\.com/.*").all());
    if (page.getUrl().toString().contains("pins")) {
        page.putField("img", page.getHtml().xpath("//div[@id='pin_img']/img/@src").toString());
    } else {
        page.getResultItems().setSkip(true);
    }
}


public Site getSite() {
    if (site == null) {
        site = Site.me().setDomain("huaban.com").setSleepTime(1000);
    }
    return site;
}

public static void main(String[] args) {
	/*Spider spider=Spider.create(new HuabanProcessor());
	spider.addUrl("http://huaban.com/");
	spider.thread(50);
	spider.addPipeline(new ConsolePipeline());
	spider.addPipeline(new FilePipeline());
	spider.setDownloader(new SeleniumDownloader("/Users/yihua/Downloads/chromedriver"));
	spider.run();*/
	/*System.getProperties().setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
    WebDriver webDriver = new ChromeDriver();
    webDriver.get("http://huaban.com/");
    WebElement webElement = webDriver.findElement(By.xpath("/html"));
    System.out.println(webElement.getAttribute("outerHTML"));
    webDriver.close();*/
}
}