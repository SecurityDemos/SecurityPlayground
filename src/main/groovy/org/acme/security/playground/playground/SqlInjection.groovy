package org.acme.security.playground.playground

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SqlInjection {
    @RequestMapping("/test")
    String home() {
        return "Hello World!"
    }
}
