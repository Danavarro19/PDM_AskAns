# Ask&Answer

Aplicación móvil para crear encuestas.

## Getting Started

La aplicación fue diseñada para crear toda la base de datos dinámicamente, así que el usuario que se vaya a crear en ambas bases de datos, deben tener todos los permisos necesarios.
El sistema funciona en Windows, debido a que usa lectura de archivos del sistema par crear las tablas del modelo transaccional en Postgress, no estamos seguros de su funcionamiento 
en Linux o Mac.

### Prerequisites
Para asegurar el buen funcionamiento de la api, seguir los siguientes pasos:

(Esto de debe de aplicar al documento .envExample)

APP=app (Nombre de la app, esto no se debe de cambiar, dejarlo como esta)

APP_ENV=development (Como la api sigue en desarrollo, por favor dejarlo como se muestra en este ejemplo)

APP_KEY= (esto es la llave de la seguridad encriptada, usar una llave segura y de 24 de caracteres)

DB_URL= (URL de la base de Postgress, se ha probado para Heroku y funciona)

MONGO_URL= (URI DE MONGO, procurar que la contraseña de Mongo, si es local o en atlas, esta solo se ha probado con atlas, no usar carácteres especiales para contraseña, estos no funcionarán)

PORT= (puerto que ocupará la app)

DB_POSTGRES_DEVELOPMENT= (Esta variable debe de ir en true la primera vez que se corre todo el server, luego se tiene que reiniciar el server con la variable en false, 
esperar a que ejecute absolutamente todas las operaciones, el sistema funciona en Windows, no estamos seguros que funcione en Mac o Linux. 

NODE_TLS_REJECT_UNAUTHORIZED=0  (esto es necesario por un error en la fase de instalación, no se ha probado con quitarlo, así que por motivos de desarrollo, dejarla así como aparece
en este ejemplo, o sea, dejarla habilitada) 


- Si el server se reinicia y no se le cambia la variable de desarrollo de Postgress a false, este eliminará y volverá a crear todas las tablas sin importar el contenido de estas.
- Una vez creadas las tablas, se tiene que crear un usuario y con su respectiva contraseña en la tabla "adminusers"
- Luego la URL admin/module llevará a un login en donde se pueden gestionar todas las tablas. (Es el módulo administrativo).


### Installing

Ahora bien, para poder levantar el server, se tiene que ejecutar el comando "npm install", pero tienes que asegurarte de estar 
dentro de la carpeta que contiene todo el proyecto (no una carpeta antes), porque si no esta de esa forma y ejecutas "npm install", dará error.

Una vez ejecutado el comando "npm install", procedemos a ejecutar el siguiente comando "npm start" para poder levantar el server. 


Una vez hechos los pasos anteriores, hay que ir al proyecto de la aplicación, aquí el documento que nos interesa tiene como nombre
"serviceLoginResponse.kt", está dentro de la carpeta "retrofit".

En este documento, lo que tenemos que cambiar es la parte URL: String = "http://192.168.1.15:3001/movil/" (PARTE A CAMBIAR 192.168.1.15) por tu ip de computadora o laptop.

En caso de no ocupar emulador de Android Studio, si no que un teléfono, asegurarse que estos estén conectados a la misma red Wi-fi. 
(como la computadora y el teléfono).


## Running the tests

Primero correr el server, después la aplicación móvil.

MÓDULO ADMINISTRATIVO.

Para poder entrar al módulo administrativo, con la url http://localhost:3001/admin/module, pedirá ingresar credenciales.


APLICACIÓN MÓVIL.

Ahora, cuando corres la aplicación móvil, puedes loguearte o bien crear un nuevo usuario


## Versioning

V 0.1.0

