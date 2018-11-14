package org.acme.security.playground.playground

import org.acme.security.playground.bad_auth.BadAuthFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@SpringBootApplication
class PlaygroundApplication {

    static void main(String[] args) {
        SpringApplication.run PlaygroundApplication, args
    }

    @Bean
    FilterRegistrationBean<BadAuthFilter> badAuthFilter() {
        FilterRegistrationBean<BadAuthFilter> registrationBean = new FilterRegistrationBean()

        registrationBean.setFilter(new BadAuthFilter())
        registrationBean.addUrlPatterns("/static/bad-auth/*")

        registrationBean
    }
}
