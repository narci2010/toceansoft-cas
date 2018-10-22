package com.toceansoft.cas.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ComponentScan("com.toceansoft.cas")
@Slf4j
public class ToceanCasConfig extends WebMvcConfigurerAdapter {
	@Value("${cros.allow.headers}")
	private String corsAllowHeader = "Set-Cookie,x-auth-token,content-type,X-Requested-With,XMLHttpRequest,Authorization,Content-Disposition,Cookie,Content-Length,Accept,Accept-Encoding,Accept-Language,Cache-Control,Connection,Content-Type,Host,Origin,Referer,Upgrade-Insecure-Requests,User-Agent,If-Modified-Since,If-None-Match";
	@Value("${cros.expose.headers}")
	private String corsExposeHeader = "Set-Cookie,x-auth-token,content-type,X-Requested-With,XMLHttpRequest,Authorization,Content-Disposition,Cookie,Content-Length,Accept,Accept-Encoding,Accept-Language,Cache-Control,Connection,Content-Type,Host,Origin,Referer,Upgrade-Insecure-Requests,User-Agent,If-Modified-Since,If-None-Match";
	@Value("${cors.allow.methods}")
	private String corsMethod = "GET,POST,HEAD,OPTIONS,PUT,DELETE,TRACE,CONNECT";
	@Value("${cors.allow.domains}")
	private String corsDomain = "http://localhost:8080,http://localhost:8083";
	// @Bean
	// public MyServletContextListener getContext() {
	// return new MyServletContextListener();
	// }

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 保留空的字段
				SerializerFeature.WriteNullStringAsEmpty, // String null -> ""
				SerializerFeature.WriteNullNumberAsZero);// Number null -> 0
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		converters.add(converter);
	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		LOGGER.debug("setup cors filter...");
		LOGGER.debug("cros.allow.headers:" + corsAllowHeader);
		LOGGER.debug("cros.expose.headers:" + corsExposeHeader);
		UrlBasedCorsConfigurationSource source = configurationSource();
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

	private UrlBasedCorsConfigurationSource configurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		List<String> allowedHeaders = initParam(corsAllowHeader);
		List<String> exposedHeaders = initParam(corsExposeHeader);
		List<String> allowedMethods = initParam(corsMethod);
		List<String> allowedOrigins = initParam(corsDomain);
		corsConfig.setAllowedHeaders(allowedHeaders);
		corsConfig.setAllowedMethods(allowedMethods);
		corsConfig.setAllowedOrigins(allowedOrigins);
		corsConfig.setExposedHeaders(exposedHeaders);
		corsConfig.setMaxAge(36000L);
		corsConfig.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	private List<String> initParam(String name) {
		String[] tmps = name.split(",");
		List<String> params = Arrays.asList(tmps);
		return params;
	}

}
