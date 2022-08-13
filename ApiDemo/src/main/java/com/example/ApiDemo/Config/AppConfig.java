package com.example.ApiDemo.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = { "com.example.ApiDemo.Service" })
public class AppConfig {
	// Tạo bean dataSource
	@Bean
	public DriverManagerDataSource dataSource() throws IOException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		Properties properties = new Properties();
		InputStream user_props = this.getClass().getResourceAsStream("/application.properties");
		properties.load(user_props);
		dataSource.setDriverClassName(properties.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(properties.getProperty("spring.datasource.url"));
		dataSource.setUsername(properties.getProperty("spring.datasource.username"));
		dataSource.setPassword(properties.getProperty("spring.datasource.password"));
		return dataSource;
	}

	// đọc thông tin file cấu hình MyBatis
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
		Resource resource = new ClassPathResource("SqlMapConfig.xml");
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setConfigLocation(resource);
		return sqlSessionFactory;
	}

	// scan tất cả những mapper package vn.viettuts.mapper
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.example.ApiDemo.Mapper");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}

	// Có thể tạo @Bean cho mỗi interface mapper thay vì scan toàn bộ package
	// vn.viettuts.mapper
	/*
	 * @Bean (name = "studentMapper") public StudentMapper studentMapper() throws
	 * Exception { SqlSessionTemplate sessionTemplate = new
	 * SqlSessionTemplate(sqlSessionFactory().getObject()); return
	 * sessionTemplate.getMapper(StudentMapper.class); }
	 */
}
