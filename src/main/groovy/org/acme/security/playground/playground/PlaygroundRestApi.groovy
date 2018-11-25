package org.acme.security.playground.playground

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.nio.charset.StandardCharsets

@RestController()
@CrossOrigin(origins = "*")
class PlaygroundRestApi {

    @Autowired
    BadDatasource badDatasource

    Map<String, String> authTokens = new HashMap<>()

    @RequestMapping('/login/bad1')
    def loginWithInjection(@RequestParam('username') String username, @RequestParam('password') String password) {
        if (badDatasource.doLogin(username, password)) {
            String token = UUID.randomUUID().toString()
            authTokens.put(token, username)    // <- QUESTA INFORMAZIONE ARRIVA DALL'UTENTE: NON E' AFFIDABILE!!!!
            return ResponseEntity.ok(token)
        }
        // Return 401 error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

    }

    @RequestMapping('/login/bad2')
    def loginWithInjection2(@RequestParam('username') String username, @RequestParam('password') String password) {
        String authName = badDatasource.doLogin2(username, password)
        if (authName != null) {
            String token = UUID.randomUUID().toString()
            authTokens.put(token, authName)
            return ResponseEntity.ok(token)
        }
        // Return 401 error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

    }

    @RequestMapping('/login')
    def login(@RequestParam('username') String username, @RequestParam('password') String password) {
        String authName = badDatasource.doLoginOk(username, password)
        if (authName != null) {
            String token = UUID.randomUUID().toString()
            authTokens.put(token, authName)
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

    @RequestMapping(value = '/xxe', method = RequestMethod.POST, consumes = [MediaType.TEXT_PLAIN_VALUE])
    LoginData loginByXML(@RequestBody String xmlRequest) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance()
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder()

        Document doc = documentBuilder.parse(new ByteArrayInputStream(xmlRequest.getBytes(StandardCharsets.UTF_8)))

        String username = ((Element) doc.getElementsByTagName("username").item(0)).getTextContent()
        String password = ((Element) doc.getElementsByTagName("password").item(0)).getTextContent()

        String authName = badDatasource.doLoginOk(username, password)
        if (authName != null) {
            return badDatasource.queryLoginDataByUsername(authName)
        } else {
            throw new RuntimeException("User " + username + " is not authenticated")
        }
    }

    @RequestMapping(path = '/deser', method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    def deserDemo(@RequestBody String encodedToken) {
        // Token is a b64 java object
        byte[] bin = Base64.decoder.decode(encodedToken)

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bin))
        LoginData token = ois.readObject()
        ois.close()

        // Do stuff with the token :

        return ResponseEntity.ok(token)
    }


    @RequestMapping('/loginSer')
    def loginSer(@RequestParam('username') String username, @RequestParam('password') String password) {

        // Login and return the auth token.
        String authName = badDatasource.doLoginOk(username, password)
        if (authName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        LoginData data = badDatasource.queryLoginDataByUsername(authName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ObjectOutputStream oos = new ObjectOutputStream(baos)
        oos.writeObject(data)
        oos.close()
        baos.close()

        ResponseEntity.ok(Base64.encoder.encodeToString(baos.toByteArray()))
    }
}
