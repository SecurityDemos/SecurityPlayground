curl -v -H "Content-Type: text/plain" -X POST http://localhost:8080/xxe -d '
<login><username>mario</username><password>password</password></login>
'
