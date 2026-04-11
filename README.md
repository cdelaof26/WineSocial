# WineSocial

WineSocial es un prototipo de una API REST escrito 
en Java con SpringBoot.


# Configuración

Es necesario cambiar ```server-domain``` por la IP o dirección del servidor 
ejecutando MariaDB en el archivo [```application-template.properties```](src/main/resources/application-template.properties)
y agregar el nombre de usuario y contraseña.


# Historial de versiones

#### v0.0.6 CRUD de vinos

#### v0.0.5 CRUD tipos de vino

#### v0.0.4 CRUD de denominaciones de origen
- Implementada validación de logitudes en campos
- Exceptions refactory
- ~~TODO: delete ```WineryExceptionAdvice``` and refactor ```UserExceptionAdvice```~~

#### v0.0.3 CRUD de bodegas

#### v0.0.2_1 CRUD de usuarios
- Agregadas operaciones de creación, actualización y eliminación

#### v0.0.2 Listar usuarios
- Conexión con la base de datos
- Cambio de MySQL a MariaDB

#### v0.0.1 Proyecto inicial
- Modelo de base de datos inicial
- Estructura de proyecto inicial
