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
esperar a que ejecute absolutamente todas las operaciones, el sistema funciona en Windows, no estamos seguros que funcione en Mac o Linux) 

NODE_TLS_REJECT_UNAUTHORIZED=0  (esto es necesario por un error en la fase de instalación, no se ha probado con quitarlo, así que por motivos de desarrollo, dejarla así como aparece
en este ejemplo, o sea, dejarla habilitada) 

Para llenar ambas variables (STRIPE_PUBLISHABLE_KEY y STRIPE_SECRET_KEY), es necesario crear o tener una cuenta en Stripe (se puede acceder a la página oficial de Stripe en siguiente link https://stripe.com/docs) y guardarlas según la documentación que la misma página ofrece.

STRIPE_PUBLISHABLE_KEY=

STRIPE_SECRET_KEY=

- Si el server se reinicia y no se le cambia la variable de desarrollo de Postgress a false, este eliminará y volverá a crear todas las tablas sin importar el contenido de estas.
- Una vez creadas las tablas, se tiene que crear un usuario y con su respectiva contraseña en la tabla "adminusers"
- Luego la URL admin/module llevará a un login en donde se pueden gestionar todas las tablas. (Es el módulo administrativo).


Realizados los pasos anteriores, se tendrá que crear un servidor HTTPS, para esto se necesitan dos cosas:
un certificado SSL y un módulo https Node.js incorporado.

Para poder crear estos archivos, tendremos que ejecutar los siguientes comandos (estos se ejecutan en la consola de Ubuntu 
y posteriormente se trasladaron al sistema operativo Windows donde se aloja la api), en el orden que 
se muestra a continuación:

- openssl genrsa -out key.pem
- openssl req -new -key key.pem -out csr.pem
- openssl x509 -req -days 9999 -in csr.pem -signkey key.pem -out cert.pem
- rm csr.pem

Una vez creados los archivos, (como se mencionó anteriormente, estos fueron creados en Ubuntu), ahora se tendrán que mover 
a la carpeta en donde se aloja la api (en nuestro caso, lo teníamos en una computadora con sistema operativo Windows), los dos 
archivos se guardarán en la carpeta bin (esta se encuentra dentro del proyecto de la api).

Los dos archivos que tienen que estar alojados en la carpeta bin, ubicada dentro del proyecto de la api, son:
- cert.pem
- key.pem

Ahora se tiene que crear la siguiente carpeta llamada "assets", detro de app/src/main, esto se debe de hacer en el proyecto
de Android Studio.

El archivo que pondremos dentro de la carpeta creada anteriormente tiene que ser cert.pem, en este caso solo basta con copiar el arvicho 
que fue creado con anterioridad.


### Installing

Ahora bien, para poder levantar el server, se tiene que ejecutar el comando "npm install", pero tienes que asegurarte de estar 
dentro de la carpeta que contiene todo el proyecto (no una carpeta antes), porque si no esta de esa forma y ejecutas "npm install", dará error.

Una vez ejecutado el comando "npm install", procedemos a ejecutar el siguiente comando "npm start" para poder levantar el server. 


Una vez hechos los pasos anteriores, hay que ir al proyecto de la aplicación, aquí el documento que nos interesa tiene como nombre
"serviceLoginResponse.kt", está dentro de la carpeta "retrofit".

En este documento hay tres variables que bien podemos dejar como están o podemos cambiarlas, la primera es private const val IP: String = "analyticsmg.herokuapp.com", private const val URL: String = "https://analyticsmg.herokuapp.com/movil/" y const val URI = "https://analyticsmg.herokuapp.com/"

Pero si se opta por usar la aplicación y la api de manera local, entonces la primera variable se tiene que cambiar por la ip de nuestra computadora o laptop, la segunda por "https://localhost:3001/movil/" y la tercera por "https://"+ IP + ":3001/", de igual manera se tiene que cambiar un apartado del mismo archivo, el cual es val caInput: InputStream = BufferedInputStream(context.assets.open("cert.cer")), cambiaremos cert.cer a cert.pem

En caso de no ocupar emulador de Android Studio, si no que un teléfono, asegurarse que estos estén conectados a la misma red Wi-fi. 
(como la computadora y el teléfono).


## Running the tests

Primero correr el server, después la aplicación móvil.

MÓDULO ADMINISTRATIVO.

Para poder entrar al módulo administrativo, con la url https://localhost:3001/admin/module, pedirá ingresar credenciales.


APLICACIÓN MÓVIL.

Ahora, cuando corres la aplicación móvil, puedes loguearte o bien crear un nuevo usuario


## Versioning

V 1.0.0

