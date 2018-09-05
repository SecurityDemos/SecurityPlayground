package org.acme.security.playground.playground

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCallback
import org.springframework.stereotype.Component

import java.sql.ResultSet

@Component
class BadDatasource {
    @Autowired
    JdbcTemplate jdbcTemplate

    def doLogin(String username, String password) {
        jdbcTemplate.execute("SELECT * FROM `users` WHERE `username` = '" + username + "' AND  `password`='" + password + "'", (PreparedStatementCallback) {
            ps ->
                ResultSet res = ps.executeQuery()
                try {
                    while (res.next()) {
                        return res.getString('display_name')
                    }
                } finally {
                    res.close()
                }

                return null

        })
    }
}
