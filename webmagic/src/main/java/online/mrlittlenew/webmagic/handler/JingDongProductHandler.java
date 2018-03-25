package online.mrlittlenew.webmagic.handler;

import java.util.Date;
import java.util.List;

import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.domain.JingDongProductInfo;
import online.mrlittlenew.webmagic.domain.JingDongProductInfoHandler;
import online.mrlittlenew.webmagic.repository.JingDongProductInfoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JingDongProductHandler{
	private static Logger logger = LoggerFactory.getLogger(JingDongProductHandler.class);
	private JingDongProductInfoRepository productInfoRep;
	private List<JingDongProductInfoHandler> handlerList;
	

	public JingDongProductHandler(JingDongProductInfoRepository productInfoRep,List<JingDongProductInfoHandler> handlerList){
		this.productInfoRep=productInfoRep;
		this.handlerList=handlerList;
	}
	
	public void handleInfo(JingDongProduct product) {
		logger.info("product.getCatId():"+product.getCatId());
		for(JingDongProductInfoHandler handler:handlerList){
			if(product.getCatId()!=null&&product.getCatId().contains(handler.getCatId())){
				handleInfo( product,handler.getCategories(),handler.getUnit(),handler.getKeyWord(),handler.getHasNumber());
			}
		}
	}
	
	private void handleInfo(JingDongProduct product,String categories,String unit,String keyword,String hasNum) {
		logger.info("handleInfo:"+product.getName()+","+categories+","+unit+","+keyword);
		JingDongProductInfo info =productInfoRep.findBySkuAndCategoriesAndUnit(product.getSku(), categories, unit);
		if(info==null){
			info = new JingDongProductInfo();
		}
		info.setLastUpdateDate(new Date());
		String name= product.getName();
		info.setSku(product.getSku());
		info.setCategories(categories);
		if(name!=null&&name.contains(keyword)){
			info.setUnit(unit);
		}else{
			return;
		}
		//处理数字
		if("Y".equals(hasNum)){
			
		}else{
			info.setNum(0d);
		}
		
		//保存到数据库
		productInfoRep.save(info);
	}

	
	
}
