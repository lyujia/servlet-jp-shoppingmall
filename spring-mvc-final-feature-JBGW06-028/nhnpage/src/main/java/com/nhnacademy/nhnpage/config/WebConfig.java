package com.nhnacademy.nhnpage.config;

import com.nhnacademy.nhnpage.exception.NotLoginException;
import com.nhnacademy.nhnpage.interceptor.DbConnectionInterceptor;
import com.nhnacademy.nhnpage.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DbConnectionInterceptor());
        //registry.addInterceptor(new LoginCheckInterceptor()).excludePathPatterns("/cs/login");

    }
    @ExceptionHandler
    public String handelException(NotLoginException e, Model model) {
        return "redirect:/login";
    }
}
