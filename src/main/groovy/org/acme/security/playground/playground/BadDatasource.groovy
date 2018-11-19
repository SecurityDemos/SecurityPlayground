package org.acme.security.playground.playground

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCallback
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.nio.charset.StandardCharsets
import java.sql.ResultSet

/**
 * Una datasource VOLUTAMENTE piena di falle per dimostrare i pericoli legati al sql-injection.
 */
@Component
class BadDatasource {
    @Autowired
    JdbcTemplate jdbcTemplate

    /**
     * In questa versione lo username autenticato è quello fornito dall'utente; è possibile assumere l'identità di chiunque.
     * @param username
     * @param password
     * @return
     */
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

    /**
     * In questa versione lo username autenticato è quello restituito dalla query e si ha meno controllo.
     * @param username
     * @param password
     * @return
     */
    def doLogin2(String username, String password) {
        jdbcTemplate.execute("SELECT username FROM users WHERE username = '" + username + "' AND  password='" + password + "'", (PreparedStatementCallback) {
            ps ->
                ResultSet res = ps.executeQuery()
                try {
                    while (res.next()) {
                        return res.getString('username')
                    }
                } finally {
                    res.close()
                }

                return null
        })
    }

    /**
     * Qui non è più possibile utilizzare sql-injection.
     * @param username
     * @param password
     * @return
     */
    def doLoginOk(String username, String password) {
        jdbcTemplate.execute("SELECT username FROM users WHERE username = ? AND  password= ?", (PreparedStatementCallback) {
            ps ->
                ps.setString(1, username)
                ps.setString(2, password)
                ResultSet res = ps.executeQuery()
                try {
                    while (res.next()) {
                        return res.getString('username')
                    }
                } finally {
                    res.close()
                }

                return null
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
