package online.mrlittlenew.webmagic.dao;

import java.util.List;

import online.mrlittlenew.webmagic.dto.JingDongProductDto;

public interface JingDongDao {
	public List<JingDongProductDto> findProductList();

}
