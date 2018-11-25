package org.acme.security.playground.playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class AnotherDao {
    @Autowired
    private DataSource jdbc;

    public boolean doLogin(String username, String password) throws SQLException {

        try (Connection cnx = jdbc.getConnection()) {
            Statement st = cnx.createStatement();


            try (ResultSet res = st.executeQuery("SELECT count(*) FROM users WHERE username = '" + username + "' AND  password='" + password + "'")) {
                while (res.next()) {
                    return res.getInt(1) > 0;
                }
            }

            return false;

        }
    }
}
