package org.acme.security.playground.playground


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController()
@CrossOrigin(origins = "*")
class SqlInjection {

    @Autowired
    BadDatasource badDatasource

    Map<String, String> authTokens = new HashMap<>()

    @RequestMapping('/login/bad')
    def loginWithInjection(@RequestParam('username') String username, @RequestParam('password') String password) {
        if (badDatasource.doLogin(username, password)) {
            String token = UUID.randomUUID().toString()
            authTokens.put(token, username)    // <- QUESTA INFORMAZIONE ARRIVA DALL'UTENTE: NON E' AFFIDABILE!!!!
            return ResponseEntity.ok(token)
        }
        // Return 401 error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

    }

    @RequestMapping('/user/me')
    LoginData getUserInfo(@RequestHeader('X-AUTH') String authToken) {
        checkToken(authToken)
        return badDatasource.queryLoginDataByUsername(authTokens.get(authToken))
    }


    private def checkToken(String token) {
        if (!authTokens.containsKey(token)) {
            throw new NotAuthenticatedException()
        }
    }
}
