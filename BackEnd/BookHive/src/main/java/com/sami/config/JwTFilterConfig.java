package com.cgi.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class JwTFilterConfig {
	@Bean
	public FilterRegistrationBean<JWTAuthTokenFilter> getFilterRegistrationBean(){
		FilterRegistrationBean<JWTAuthTokenFilter> filterRegistrationBean  = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new JWTAuthTokenFilter());
		filterRegistrationBean.addUrlPatterns("/dashboard/*");
		filterRegistrationBean.addUrlPatterns("/admin/check/isAuthenticated");
		return filterRegistrationBean;
		
	}

}
