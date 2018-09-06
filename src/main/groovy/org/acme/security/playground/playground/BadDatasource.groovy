package org.acme.security.playground.playground

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCallback
import org.springframework.stereotype.Component

import java.sql.ResultSet

/**
 * Una datasource VOLUTAMENTE piena di falle per dimostrare i pericoli legati al sql-injection.
 */
@Component
class BadDatasource {
    @Autowired
    JdbcTemplate jdbcTemplate

    def doLogin(String username, String password) {
        jdbcTemplate.execute("SELECT count(*) FROM users WHERE username = '" + username + "' AND  password='" + password + "'", (PreparedStatementCallback) {
            ps ->
                ResultSet res = ps.executeQuery()
                try {
                    while (res.next()) {
                        return res.getInt(1) > 0
                    }
                } finally {
                    res.close()
                }

                return false
        })
    }

    LoginData queryLoginDataByUsername(String username) {
        jdbcTemplate.execute("SELECT * FROM users WHERE username = '" + username + "'", (PreparedStatementCallback) {
            ps ->
                ResultSet res = ps.executeQuery()
                try {
                    while (res.next()) {
                        return new LoginData(res.getInt('id'), res.getString('display_name'))
                    }
                } finally {
                    res.close()
                }

                return false
        })
    }
}
