package online.mrlittlenew.webmagic.pipeline;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.mrlittlenew.webmagic.domain.JingDongPrice;
import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.repository.JingDongPriceRepository;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class SaveToDataBasePipeline implements Pipeline{

	private static Logger logger = LoggerFactory.getLogger(SaveToDataBasePipeline.class);
	
	@Autowired
	private JingDongProductRepository productRep;
	@Autowired
	private JingDongPriceRepository priceRep;
	/*public SaveToDataBasePipeline(JingDongProductRepository productRep, JingDongPriceRepository priceRep) {
		this.productRep=productRep;
		this.priceRep=priceRep;
	}*/
	@Override
	public void process(ResultItems resultItems, Task task) {
		Date now=new Date();
		JingDongProduct item=resultItems.get("item");
		item.setLastUpdateDate(now);
		JingDongPrice price=resultItems.get("price");
		price.setLastUpdateDate(now);
		productRep.save(item);	
		priceRep.save(price);	
	}

}
