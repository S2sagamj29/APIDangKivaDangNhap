package com.example.ApiDemo.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiDemo.Config.JwtTokenProvider;
import com.example.ApiDemo.Enity.CustomUserDetails;
import com.example.ApiDemo.Enity.User;
import com.example.ApiDemo.Service.UserService;

@RestController
@RequestMapping(value = "/api")
public class ApiController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	/* @Autowired AuthenticationManager authenticationManager; */
	 
	
	@Autowired
	JwtTokenProvider tokenProvider;

	@PostMapping(value = "/register")
	public void RegisterApi(@RequestBody User user) {
		User register = new User();
		register.setUsername(user.getUsername());
		register.setPassword(passwordEncoder.encode(user.getPassword()));
		register.setEmail(user.getEmail());
		register.setPhone(user.getPhone());
		
		
		 userService.register(register);
	}
	
	
	
    @PostMapping("/login")
    public String authenticateUser(@Validated @RequestBody User loginRequest) {

        // Xác thực từ username và password.
    	 Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         loginRequest.getUsername(),
                         loginRequest.getPassword()
                 )
         );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken(loginRequest.getUsername());
        return jwt;
    }
	
	}
	

