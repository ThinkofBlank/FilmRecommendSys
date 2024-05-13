package com.example.demo.handler;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//拦截器
@SpringBootConfiguration
public class MyInterceptorConfig  extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //配置拦截所有请求
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/auth/login","/auth/save","/auth/resetPassword","/film/list","/film/getFilmResource");
        super.addInterceptors(registry);
    }
}
