package org.acme.security.playground.playground

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate

import javax.sql.DataSource

@SpringBootApplication
class PlaygroundApplication {

    @Bean
    @Primary
    DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("root")
                .password("ciccio")
                .url("jdbc:mysql://localhost:3306/playground")
                .driverClassName("com.mysql.jdbc.Driver")
                .build()
    }

    @Bean
    @Primary
    JdbcTemplate jdbcTemplate() {
        new JdbcTemplate(dataSource())
    }


    static void main(String[] args) {
        SpringApplication.run PlaygroundApplication, args
    }
}
