# Bad Login 1

Esempio di bad practice che riguarda l'utilizzo di informazioni "non autenticate"
insieme al sql-injection.

## Prova n.1 : normale login

Effettuare il normale login per verficare che il funzionamento normale sia corretto.

 - Username : `mario`
 - Password : `password`

## Prova n.2: login senza password

E' possibile loggarsi senza conoscere la password dell'utente ma solo la username:

 - Username: `mario`
 - Password: `qualsiasi' OR 'a'='a`
 
## Prova n.3: login senza conoscere ne lo username ne la password

E' possibile loggersi anche senza conoscere lo username:

 - Username: `qualsiasi' OR 'a'='a`
 - Password: `qualsiasi' OR 'a'='a`