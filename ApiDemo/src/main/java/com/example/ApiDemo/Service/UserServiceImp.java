package com.example.ApiDemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ApiDemo.Enity.User;
import com.example.ApiDemo.Mapper.UserMapper;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public User authen(String userName) {
		User user = userMapper.getData(userName);
		return user;
	}

	@Override
	public void register(User user) {
		 userMapper.InsertData(user);
		
	}

}
