package online.mrlittlenew.webmagic.pipeline;

import java.util.Date;

import online.mrlittlenew.webmagic.domain.JingDongPrice;
import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.domain.JingDongProductHtml;
import online.mrlittlenew.webmagic.repository.JingDongPriceRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductHtmlRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class JingDongPipeline implements Pipeline{

	private static Logger logger = LoggerFactory.getLogger(JingDongPipeline.class);
	
	@Autowired
	private JingDongProductRepository productRep;
	@Autowired
	private JingDongProductHtmlRepository htmlRep;
	@Autowired
	private JingDongPriceRepository priceRep;

	@Override
	public void process(ResultItems resultItems, Task task) {
		Date now=new Date();
		JingDongProduct item=resultItems.get("item");
		JingDongPrice price=resultItems.get("price");
		if(price!=null){
			if(item==null){
				item=productRep.findOne(price.getSku());
			}
			if(item!=null){
				item.setLastPrice(price.getPrice());
			}
			price.setLastUpdateDate(now);
			priceRep.save(price);	
		}
		
		if(item!=null){
			item.setLastUpdateDate(now);
			productRep.save(item);	
		}
		
		JingDongProductHtml html=resultItems.get("html");
		if(html!=null){
			html.setLastUpdateDate(now);
			htmlRep.save(html);	
		}

		
	
	}

}
