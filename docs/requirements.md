# Requisitos

Se trata de diseñar e implementar una API REST y un prototipo funcional de un servicio
sencillo que simule una aplicación de una comunidad online para la gestión y
recomendación de vinos. Donde los usuarios de esta comunidad puedan interactuar
entre ellos de diversas formas. _Se asume que la autenticación y autorización de la
herramienta está realizada y, por tanto, no hay que implementarla._

En este servicio los usuarios pueden puntuar vinos, seguir a otros usuarios, realizar
búsquedas de vinos concretos o descubrir recomendaciones basadas en sus gustos
personales. Para ello se debe tener en cuenta que los vinos deben estar representados
al menos por el nombre, bodega a la que pertenece, añada de la botella, denominación
u origen (país o región), tipo de vino (tinto, blanco, rosado…etc.), tipos de uva (en
porcentaje, un vino puede estar formado por varios tipos de uva).
El servicio debe implementar a través de la API las siguientes operaciones:

* [x] Añadir un usuario nuevo a la comunidad. Los datos básicos de un usuario son:
nombre de usuario, fecha de nacimiento para comprobar que es mayor de edad
y correo electrónico. Si el usuario no es mayor de edad no podrá ser dado de alta
en el servicio y se deberá devolver un mensaje de error.

* [x] Ver los datos básicos del usuario.

* [x] Cambiar los datos básicos de nuestro perfil de usuario.

* [x] Borrar nuestro perfil.

* [x] Obtener una lista de todos los usuarios en la aplicación. Esta lista debe permitir ser
filtrada por patrón de nombre (eg. Buscar todos los usuarios que contengan “Mar”
en su nombre, “Mario”, “Maria”…etc.).1

* [x] Obtener una lista de todos los usuarios en la aplicación permitiendo limitar la
cantidad de información obtenida por cantidad (e.g. los X primeros, los elementos
entre X e Y, etc.).

* [ ] Un usuario puede añadir un vino a su lista y agregarle una puntuación (0 a 10).

* [ ] Un usuario puede eliminar vinos de su lista.

* [ ] Un usuario puede modificar la puntuación de un vino concreto de su lista.

* [ ] Obtener una lista con todos los vinos de un usuario. Esta lista debe permitir la
opción de ser filtrada por fecha en la que se añadió a la lista o limitar la cantidad
de información devuelta (e.g. los X primeros, los elementos entre X e Y, etc.).

* [ ] Se debe dar la opción de realizar filtros en la lista de vinos de un usuario para
poder buscar vinos de un usuario por característica/s de vinos: tipo de vino, uva,
origen, año…etc, o la combinación de varios de estos filtros.

* [ ] Un usuario puede comenzar a seguir otros usuarios. Esta operación NO será
inmediata; debe generar una solicitud de seguimiento que el usuario a seguir
debe aceptar o rechazar.

* [ ] Un usuario puede dejar de seguir a usuarios que siga.

* [ ] Un usuario puede consultar sus solicitudes de seguimiento pendientes
(solicitudes recibidas).

* [ ] Un usuario puede aceptar una solicitud de seguimiento.

* [ ] Un usuario puede rechazar una solicitud de seguimiento.

* [ ] Obtener un listado de todos los seguidos por un usuario. Además, esta lista debe
permitir la opción de ser filtrada por patrón de nombre y/o limitar la cantidad de
información obtenida.

* [ ] Un usuario puede obtener la lista de vinos de un usuario concreto que siga,
pudiendo filtrar este listado por las características de los vinos (bodega, año,
origen, tipo de vino…etc.). Esta lista debe permitir la opción de ser filtrada por
fecha en la que se añadió el vino o limitar la cantidad de información obtenida
(e.g. los X primeros, los elementos entre X e Y, etc.).

* [ ] Se debe permitir consultar fácilmente las recomendaciones personalizadas para
un usuario. Esta operación devuelve toda la información del usuario (datos
personales), un listado con sus 5 últimos vinos añadidos, un listado con sus 5 vinos
con mayor puntuación y otro listado con los 5 mejores vinos (los de mayor
puntación) de todos sus amigos.

* [ ] Consultar estadísticas de puntuaciones de un usuario, devolviendo la nota media
de los vinos puntuados. Esta operación debe permitir aplicar los mismos filtros
que la operación de obtención del listado de vinos del usuario. En concreto, debe
soportar un rango de fechas de adición (desde/hasta) y filtros por bodega, añada,
origen, tipo de vino y uva, pudiendo combinar varios filtros.
