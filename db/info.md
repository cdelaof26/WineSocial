# Información sobre la base de datos

La base de datos en la versión **v0.0.1** contiene
9 tablas,

### user

| Campo       | Tipo           | Descripción         | Constrains |
|-------------|----------------|---------------------|------------|
| `id`        | `INT`          | Llave primaria      | PK, NN, AI |
| `username`  | `VARCHAR(128)` | Nombre de usuario   | NN         |
| `birthdate` | `DATE`         | Fecha de nacimiento | NN         |
| `email`     | `VARCHAR(128)` | Correo electrónico  | NN, UQ     |


### follower

_Depends on `user`_

| Campo               | Tipo      | Descripción                                                    | Constrains |
|---------------------|-----------|----------------------------------------------------------------|------------|
| `user_id`           | `INT`     | Identificador del usuario que realiza la solicitud             | NN         |
| `to_follow_user_id` | `INT`     | Identificador del usuario a seguir                             | NN         |
| `approved`          | `BOOLEAN` | Bandera que indica si el usuario a seguir aprueba la solicitud | NN         |
| `answered`          | `BOOLEAN` | Bandera que indica si se ha contestado a la solicitud          | NN         |
| `asked_on`          | `DATE`    | Fecha en la que se realizó la solicitud                        | NN         |


### winery

| Campo            | Tipo           | Descripción         | Constrains |
|------------------|----------------|---------------------|------------|
| `id`             | `INT`          | Llave primaria      | PK, NN, AI |
| `name`           | `VARCHAR(128)` | Nombre de la bodega | NN, UQ     |
| `postal_address` | `TEXT(512)`    | Dirección postal    |            |


### pdo

| Campo            | Tipo           | Descripción                                                           | Constrains |
|------------------|----------------|-----------------------------------------------------------------------|------------|
| `id`             | `INT`          | Llave primaria                                                        | PK, NN, AI |
| `name`           | `VARCHAR(128)` | Nombre de la denominación de origen (Protected Designation of Origin) | NN, UQ     |
| `postal_address` | `TEXT(512)`    | Dirección postal                                                      |            |


### wine_type

| Campo         | Tipo           | Descripción             | Constrains |
|---------------|----------------|-------------------------|------------|
| `id`          | `INT`          | Llave primaria          | PK, NN, AI |
| `name`        | `VARCHAR(128)` | Nombre del tipo de vino | NN, UQ     |
| `description` | `TEXT(1024)`   | Descripción del vino    |            |


### wine

_Depends on `winery`, `pdo` & `wine_type`_

| Campo          | Tipo           | Descripción                                   | Constrains |
|----------------|----------------|-----------------------------------------------|------------|
| `id`           | `INT`          | Llave primaria                                | PK, NN, AI |
| `name`         | `VARCHAR(128)` | Nombre del vino                               | NN         |
| `winery_id`    | `INT`          | Identificador de la bodega a la que pertenece | FK, NN     |
| `vintage`      | `DATE`         | Añada de la botella                           | NN         |
| `pdo_id`       | `INT`          | Identificador de la denominación de origen    | FK, NN     |
| `wine_type_id` | `INT`          | Identificador del tipo de vino                | FK, NN     |


### wine_list

_Depends on `user` & `wine`_

| Campo         | Tipo          | Descripción                                                   | Constrains |
|---------------|---------------|---------------------------------------------------------------|------------|
| `user_id`     | `INT`         | Identificador del usuario que puntua el vino                  | NN         |
| `wine_id`     | `INT`         | Identificador del vino que se esta puntuando                  | NN         |
| `score`       | `TINYINT(10)` | Puntuación que se le da a un vino de 0 a 10                   | NN, UN     |
| `modified_on` | `DATE`        | Fecha en la que se puntua o modifica la puntuación de un vino | NN         |


### grape_type

| Campo         | Tipo           | Descripción            | Constrains |
|---------------|----------------|------------------------|------------|
| `id`          | `INT`          | Llave primaria         | PK, NN, AI |
| `name`        | `VARCHAR(128)` | Nombre del tipo de uva | NN, UQ     |


### wine_grape_composition

_Depends on `grape_type` & `wine`_

| Campo           | Tipo           | Descripción                               | Constrains |
|-----------------|----------------|-------------------------------------------|------------|
| `grape_type_id` | `INT`          | Identificador del tipo de uva             | NN         |
| `wine_id`       | `INT`          | Identificador del vino                    | NN         |
| `percentage`    | `TINYINT(100)` | Porcentaje de contenido de uva de 0 a 100 | NN, UN     |
