package org.acme.security.playground.playground


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController()
@CrossOrigin(origins = "*")
class SqlInjection {

    @Autowired
    BadDatasource badDatasource

    Map<String,String> authTokens = new HashMap<>()

    @RequestMapping('/login/bad')
    def loginWithInjection(@RequestParam('username') String username, @RequestParam('password') String password) {
        badDatasource.doLogin(username, password)
    }

    @RequestMapping('/user/')
    LoginData getUserInfo() {

    }
}
