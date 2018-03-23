package online.mrlittlenew.webmagic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.mrlittlenew.webmagic.domain.User;
import online.mrlittlenew.webmagic.repository.UserRepository;
import online.mrlittlenew.webmagic.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void test() {
		User e=new User();
		e.setName("hihi");
		e.setAge(12);
		e.setGender("ni");
		userRepository.saveAndFlush(e);
		System.out.println("dsdasd");

	}
	
	
}
