package org.acme.security.playground.playground

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig implements WebMvcConfigurer {
    void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login2").setViewName("login2")
        registry.addViewController("/error").setViewName("error")
        registry.addViewController("/secure").setViewName("secure")
    }
}
