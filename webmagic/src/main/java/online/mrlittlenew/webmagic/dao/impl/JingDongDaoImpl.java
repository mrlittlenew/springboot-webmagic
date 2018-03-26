package online.mrlittlenew.webmagic.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import online.mrlittlenew.webmagic.dao.JingDongDao;
import online.mrlittlenew.webmagic.dto.JingDongProductDto;

@Repository
public class JingDongDaoImpl implements JingDongDao {
	//@Autowired
    private SessionFactory sessionFactory;
	
	public Session getSession() {    
        return sessionFactory.getCurrentSession();    
    }  

	@Override
	public List<JingDongProductDto> findProductList() {
		
		String sql="select * from t_jing_dong_product";
		List<JingDongProductDto> list = getSession().createSQLQuery(sql).list(); 
		
        return null; 
	}

}
