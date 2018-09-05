package org.acme.security.playground.playground


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = "*")
class SqlInjection {

    @Autowired
    BadDatasource badDatasource


    @RequestMapping('/login/bad')
    loginWithInjection(@RequestParam('username') String username, @RequestParam('password') String password) {
        badDatasource.doLogin(username, password)
    }
}
