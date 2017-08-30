package com.qianlixy.framework.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.qianlixy.framework.upload.config.FileUploadConfig;
import com.qianlixy.framework.upload.intercepter.CommonIntercepter;
import com.qianlixy.framework.upload.intercepter.ValidateIntercepter;

@SpringBootApplication
public class UploadApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class, args);
	}
	
	@Bean
	public FileUploadConfig fileUploadConfig() {
		return new FileUploadConfig();
	}
	
	/**
	 * 映射静态资源
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}
	
	/*
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//验证拦截器
		registry.addInterceptor(new CommonIntercepter());
		registry.addInterceptor(new ValidateIntercepter()).addPathPatterns("/upload");
		super.addInterceptors(registry);
	}

	/*
	 * 允许跨域
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/upload")
			.allowedOrigins(fileUploadConfig().getAllowedOrigins().toArray(new String[]{}))
			.allowedMethods("GET", "POST", "OPTIONS");
		super.addCorsMappings(registry);
	}
	
}
