package com.example.ApiDemo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ApiDemo.Filter.JwtAuthenticationFilter;
import com.example.ApiDemo.Service.UserDetailService;

@EnableWebSecurity
@Configuration
public class ConfigSecurity {

	@Autowired
	UserDetailService userService;
	
	
	 @Bean
	    public JwtAuthenticationFilter jwtAuthenticationFilter() {
	        return new JwtAuthenticationFilter();
	    }
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	     
	        http.cors().and()
	                .authorizeRequests()
	                .antMatchers("/login").permitAll()
					.antMatchers("/api/*").permitAll()
					.anyRequest().authenticated()
					.and()
					.csrf().disable();
	                /*
					 * .hasAnyAuthority("Admin", "Editor", "Salesperson") .hasAnyAuthority("Admin",
					 * "Editor", "Salesperson", "Shipper")
					 */
	                /*.anyRequest().authenticated()
	                .and().formLogin()
	                .loginPage("/login")
	                    .usernameParameter("email")
	                    .permitAll()
	                .and()
	                .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
	                .and()
	                .logout().permitAll();*/
	        
	        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);	 
	        return http.build();
	    }
	 
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
	        return new BCryptPasswordEncoder();
	    }
	 
	 
	    protected void configure(AuthenticationManagerBuilder auth)
	            throws Exception {
	        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
	            .passwordEncoder(passwordEncoder()); // cung cấp password encoder
	    }
	    
	    @Bean
	    public AuthenticationManager authenticationManager(
	            AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }
	
}
