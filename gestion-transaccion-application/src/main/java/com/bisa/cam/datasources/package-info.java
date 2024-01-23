/**
 *
 * Las fuentes de datos (data sources) son adaptadores de diferentes implementaciones de almacenamiento.
 *
 * Una fuente de datos puede ser un adaptador a una base de datos SQL (una clase Active Record en Rails o JPA en Java),
 * un adaptador de búsqueda elástica, una API REST, o incluso un adaptador algo sencillo como un archivo CSV o un Hash.
 *
 * Una fuente de datos implementa los métodos definidos en el repositorio y se enccarga de la implementación de la
 * obtención y persistencia de los datos.
 *
 */
package com.bisa.cam.datasources;