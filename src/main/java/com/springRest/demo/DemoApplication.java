package com.springRest.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public ViewResolver xsltViewResolver() {
		XsltViewResolver viewResolver = new XsltViewResolver();
		viewResolver.setPrefix("/WEB-INF/xslt/");
		viewResolver.setSuffix(".xslt");
		return viewResolver;
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
