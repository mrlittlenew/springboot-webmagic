package online.mrlittlenew.webmagic.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import online.mrlittlenew.webmagic.domain.JingDongPrice;
import online.mrlittlenew.webmagic.domain.JingDongProduct;
import online.mrlittlenew.webmagic.dto.JingDongPriceDto;
import us.codecraft.webmagic.Page;

public class JingDongUtil {
	private static Logger logger = LoggerFactory.getLogger(JingDongUtil.class);
	public static void main(String[] args) {
		getComment(24436869160l);
	}
	
	
	public static void getComment(Long sku){
		String url="https://club.jd.com/comment/productPageComments.action?productId="+sku+"&score=0&sortType=5&page=0&pageSize=10";
		//url="https://club.jd.com/comment/productPageComments.action?productId=24436869160&score=0&sortType=5&page=0&pageSize=10";
		String jsonStr = HttpClientUtil.getJson(url);
	}
	public static void getPromotion(Long sku,String shopId,String venderId,String catId){
		String url="https://cd.jd.com/promotion/v2?skuId="+sku+"&area=1_72_2799_0&shopId="+shopId+"&venderId="+venderId+"&cat="+catId;
		//url="https://cd.jd.com/promotion/v2?skuId=24436869160&area=1_72_2799_0&shopId=117534&venderId=117534&cat=652,828,13662";
		String jsonStr = HttpClientUtil.getJson(url);
	}
	
	public static JingDongPriceDto getPriceBySku(Long sku){
		String price_url = "https://p.3.cn/prices/mgets?skuIds=J_";
		JingDongPriceDto priceDto = new JingDongPriceDto();
		String jsonStr = HttpClientUtil.getJson(price_url+sku);
    	ObjectMapper mapper = new ObjectMapper();  
    	
		try {
			List<JingDongPriceDto> priceDtos = mapper.readValue(jsonStr, new TypeReference<List<JingDongPriceDto>>() { });
			priceDto=priceDtos.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priceDto;
	}
	
	public static void handleProductPage(Page page,boolean updatePrice){
		boolean match = page.getUrl().regex("https://item\\.jd\\.com/\\d+.html").match();
        if(!match){
        	return;
        }
        logger.debug("handleProductPage:"+page.getUrl());
        
    	String url = page.getUrl().get();
    	String skuStr = url.split("/")[3].replace(".html", "");
    	Long sku=Long.valueOf(skuStr);
    	String name=page.getHtml().xpath("//div[@class='product-intro']/div[@class='itemInfo-wrap']/div[@class='sku-name']/text()").get();
    	
    	
    	//店名
		String shopName=page.getHtml().xpath("//div[@class=contact]/div[@class=J-hove-wrap]/div[@class=item]/div[@class=name]/a/text()").get();
		logger.debug("shopName:"+shopName);
		//自营
		String seller=page.getHtml().xpath("//div[@class=contact]/div[@class=name]/em/text()").get();
		logger.debug("seller:"+seller);
		//分类名
		String catName="";
		List<String>  catNameList=page.getHtml().xpath("//div[@class=crumb-wrap]//div[@class=crumb]/div[@class=item]/a/text()").all();
		for(String info:catNameList){
			catName+="> "+info;
		}
		logger.debug("catName:"+catName);
		//description
		List<String> descList = page.getHtml().xpath("//ul[@class=parameter2]/li/text()").all();
		String description="";
		for(String info:descList){
			description+="> "+info;
		}
		//String description=page.getHtml().xpath("//ul[@class=parameter2]/li/text()").get();
		logger.debug("description:"+description);
		//分类id
		String catId = "";
		Pattern patternCatId = Pattern.compile("cat: \\[.*\\]");
		Matcher matcherCatId = patternCatId.matcher(page.getRawText());
		if (matcherCatId.find()) {
			catId=matcherCatId.group().replace("cat: [", "").replace("]", "");
	
		}
		//shopId  +  venderId
		String shopId = "";
		String venderId = "";
		
		//优惠
		//JingDongUtil.getJingDongPromotion(sku, shopId, venderId, catId);
		
		Pattern patternShopId = Pattern.compile("shopId\\:\\'\\d+\\'");
		Pattern patternVenderId = Pattern.compile("venderId\\:\\d+");
		Pattern patternNum = Pattern.compile("\\d+");
		Matcher matcher = patternShopId.matcher(page.getRawText());
		if (matcher.find()) {
			Matcher matcherNo = patternNum.matcher(matcher.group());
			if (matcherNo.find()) {
				shopId = matcherNo.group();
			}
		}
		Matcher matcher2 = patternVenderId.matcher(page.getRawText());
		if (matcher2.find()) {
			Matcher matcherNo = patternNum.matcher(matcher.group());
			if (matcherNo.find()) {
				venderId = matcherNo.group();
			}
		}
		logger.debug("catId:" + catId);
		logger.debug("shopId:" + shopId);
		logger.debug("venderId:" + venderId);
    	
		
		//=================================================================
    	double priceNum=0;
    	if(updatePrice){
    		JingDongPriceDto priceDto = JingDongUtil.getPriceBySku(sku);
    		priceNum = priceDto.getPrice();
    	}
    	
		//保存数据到PO
    	JingDongProduct item=new JingDongProduct();
    	item.setSku(sku);
    	item.setName(name);
    	item.setHtml(page.getRawText());
    	item.setShopName(shopName);
    	item.setSeller(seller);
    	item.setCatName(catName);
    	item.setDescription(description);
    	item.setCatId(catId);
    	item.setShopId(shopId);
    	item.setVenderId(venderId);
    	
    	JingDongPrice price=new JingDongPrice();
    	price.setSku(sku);
    	price.setPrice(priceNum);
    	
    	page.putField("item", item);
    	page.putField("price", price);
    }
	
    public static void handleListPage(Page page){
    	boolean match = page.getUrl().regex("https://list\\.jd\\.com/list\\.html?\\w+").match();
	    if(!match){
	    	return;
	    }
	    logger.debug("handleListPage:"+page.getUrl());
    	//获取产品链接
    	List<String> productLinks = page.getHtml().xpath("//div[@id='plist']//li//div[@class='p-name']/a").links().all();
    	page.addTargetRequests(productLinks);
    	//获取列表页翻页
    	List<String> listPageLinks = page.getHtml().xpath("//div[@class='page']/div[@id='J_bottomPage']//a").links().all();
    	page.addTargetRequests(listPageLinks);
    	page.setSkip(true);
    }
}
