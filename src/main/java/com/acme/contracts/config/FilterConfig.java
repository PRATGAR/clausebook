package com.acme.contracts.config;

import com.acme.contracts.support.SearchTermFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SearchTermFilter> searchTermFilter() {
        FilterRegistrationBean<SearchTermFilter> registration = new FilterRegistrationBean<>(new SearchTermFilter());
        registration.addUrlPatterns("/api/contracts/search");
        return registration;
    }
}
