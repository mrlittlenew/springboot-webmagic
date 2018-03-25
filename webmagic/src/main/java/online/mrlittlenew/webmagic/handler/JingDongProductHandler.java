package online.mrlittlenew.webmagic.handler;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			if(matchHandler(product,handler)){
				handleInfo( product,handler.getCategories(),handler.getUnit(),handler.getKeyWord(),handler.getHasNumber());
			}
		}
	}
	
	private boolean matchHandler(JingDongProduct product,JingDongProductInfoHandler handler) {
		if(product.getCatId()==null){
			return false;
		}
		if(product.getName()==null){
			return false;
		}
		if(!product.getCatId().contains(handler.getCatId())){
			return false;
		}
		if(!product.getName().contains(handler.getKeyWord())){
			return false;
		}
		
		String otherKeyword = handler.getKeyWordOther();
		if(otherKeyword!=null){
			String[] keywordArr = otherKeyword.split(",");
			for(String kw:keywordArr){
				if(!product.getName().contains(kw)){
					return false;
				}
			}
		}
		return true;
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
			Pattern p = Pattern.compile("[0-9]+"+keyword);
			Matcher m = p.matcher(name);
			if (m.find()) {
			  String numStr=m.group().replace(keyword, "");
			  logger.info("numStr:"+numStr);
			  info.setNum(Double.valueOf(numStr));
			}else{
				logger.info("notfound匹配错误");
				return;
			}
		}else{
			info.setNum(0d);
		}
		
		//保存到数据库
		productInfoRep.save(info);
	}
	public static void main(String[] args) {
		String name="舒洁（Kleenex）湿厕纸 旅行装10片 私处清洁湿纸巾湿巾 可搭配卷纸卫生纸使用";
		String keyword="卷";
		Pattern p = Pattern.compile("[0-9]+"+keyword);
		Matcher m = p.matcher(name);
		if (m.find()) {
		  String numStr=m.group().replace(keyword, "");
		  logger.info("numStr:"+numStr);
		}else{
			logger.info("notfound");
		}
	}
	
}
