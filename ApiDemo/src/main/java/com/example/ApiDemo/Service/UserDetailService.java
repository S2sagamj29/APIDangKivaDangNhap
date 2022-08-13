package com.example.ApiDemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ApiDemo.Enity.CustomUserDetails;
import com.example.ApiDemo.Enity.User;
import com.example.ApiDemo.Mapper.UserMapper;

@Service
public class UserDetailService implements UserDetailsService {
    
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.getData(username);
		if(user==null) {
			 throw new UsernameNotFoundException(username);
		}
		
		return new CustomUserDetails(user);
	}

}
