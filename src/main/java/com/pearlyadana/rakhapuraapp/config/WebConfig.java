package com.pearlyadana.rakhapuraapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@PropertySource({ "classpath:application.properties" })
public class WebConfig implements WebMvcConfigurer {

	@Value("${content.data.dir}")
	public String contentDir;

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		// file upload
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(-1);// 52428800 max 50 MB
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// ref: https://www.baeldung.com/spring-mvc-static-resources
		registry.setOrder(0).addResourceHandler("/profile/**")
				.addResourceLocations("file://MerchantService", "file://" + contentDir)
				/*
				 * file:/// means the sys read %SYSTEMROOT% dir in filesys DON'T CHANGE THE PATH
				 * "file:///u01/intercom/public/"
				 * 
				 */
				.setCachePeriod(0)// 1 year max cache = 31556926
				.resourceChain(true).addResolver(new PathResourceResolver());
				System.out.println("Content DIR is:"+contentDir);
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
			source.registerCorsConfiguration("/api/**", config);
		}
		return new CorsFilter(source);
	}

	/*
	* To enable Url, PathVariable validation in controller methods
	*/
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}
