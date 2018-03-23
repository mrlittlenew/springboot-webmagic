package online.mrlittlenew.webmagic.processer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.repository.JingDongProductRepository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class SaveToDataBasePipeline implements Pipeline{

	private static Logger logger = LoggerFactory.getLogger(SaveToDataBasePipeline.class);
	
	private JingDongProductRepository jingDongProductRepository;
	public SaveToDataBasePipeline(JingDongProductRepository jingDongProductRepository) {
		this.jingDongProductRepository=jingDongProductRepository;
	}
	@Override
	public void process(ResultItems resultItems, Task task) {
		JingDongProduct item=resultItems.get("item");
		item.setLastUpdateDate(new Date());
		jingDongProductRepository.save(item);	
	}

}
