package org.acme.security.playground.playground

import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest
class PlaygroundApplicationTest {

    @Autowired
    private PlaygroundRestApi api

    @Autowired
    private BadDatasource dao


    @Test
    void contextLoads() {
        Assert.assertThat(api, IsNull.notNullValue(PlaygroundRestApi.class))
    }

    @Test
    void checkLogin() {
        boolean loggedin = dao.doLogin('mario', 'password')
        Assert.assertThat(loggedin, IsEqual.equalTo(true))
    }

}
