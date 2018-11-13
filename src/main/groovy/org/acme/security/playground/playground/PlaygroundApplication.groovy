package org.acme.security.playground.playground

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate

import javax.sql.DataSource

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.acme.security.playground.bad_auth.BadAuthFilter

@SpringBootApplication
class PlaygroundApplication {

    static void main(String[] args) {
        SpringApplication.run PlaygroundApplication, args
    }

    @Bean
    public FilterRegistrationBean<BadAuthFilter> badAuthFilter(){
        FilterRegistrationBean<BadAuthFilter> registrationBean = new FilterRegistrationBean();
            
        registrationBean.setFilter(new BadAuthFilter());
        registrationBean.addUrlPatterns("/static/bad-auth/*");
            
        return registrationBean;    
    }
}
