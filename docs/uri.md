# Definición reducida de URI

Tabla de identificadores

| Nombre | Significado |
|--------|-------------|
| uidX   | user idX    |
| bidX   | winery idX  |
| widX   | wine idX    |
| ridX   | request idX |

### user

<pre>
 [GET] /users - Obtener todos los usuarios
[POST] /users - Crear un usuario

[GET] /users/{uid} - Obtener un usuario
[PUT] /users/{uid} - Actualizar un usuario
[DEL] /users/{uid} - Eliminar un usuario
</pre>

### winery
<pre>
 [GET] /wineries - Obtener todas las bodegas
[POST] /wineries - Crear una bodega

[GET] /wineries/{uid} - Obtener una bodega
[PUT] /wineries/{uid} - Actualizar una bodega
[DEL] /wineries/{uid} - Eliminar una bodega
</pre>

### wine

<pre>
 [GET] /wines - Obtener todos los vinos
[POST] /wines - Crear un vino

[GET] /wines/{uid} - Obtener un vino
[PUT] /wines/{uid} - Actualizar un vino
[DEL] /wines/{uid} - Eliminar un vino
</pre>

### user-wine

<pre>
 [GET] /users/{uid}/wines - Obtener los vinos puntuados por un usuario
[POST] /users/{uid}/wines - Puntuar un vino como un usuario especifico
</pre>

### user-user

TODO: Review

<pre>
[GET] /users/{uid}/following - Obtener los usuarios seguidos por `uid`

[GET] /users/{uid}/following/{uid1} - Obtener el usuario `uid1` que es seguido por `uid`
[DEL] /users/{uid}/following/{uid1} - Quitar el usuario `uid1` como seguido por `uid`

 [GET] /users/{uid}/requests - Obtener las solicitudes para seguir sin responder
[POST] /users/{uid}/requests - Solicitar seguir a un usuario

 [GET] /users/{uid}/requests/{rid} - Ver el estado de una solicitud sin responder 
 [PUT] /users/{uid}/requests/{rid} - Contestar a una solicitud
</pre>
