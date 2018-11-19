package org.acme.security.playground.playground

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/secure")
                    .authenticated()
                .anyRequest().permitAll()

        // For XXE test
        http.csrf().disable()

        http.formLogin()
                .loginPage('/login2')
                .permitAll()

        http.logout()
                .permitAll()

    }

    @Bean
    UserDetailsService userDetailsService() {
        new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username('mario')
                        .password('rossi')
                        .roles('USER')
                        .build())
    }
}
