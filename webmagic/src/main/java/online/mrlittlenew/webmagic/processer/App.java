package online.mrlittlenew.webmagic.processer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = LoggerFactory.getLogger(App.class);
	public static void main( String[] args )
    {
    	String url="https://item\\.jd\\.com/12345678.html";
    	System.out.println(url.split("/")[3].replace(".html", ""));
    }
}
