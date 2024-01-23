

# gestion-transaccion
API - GESTION DE TRANSACCIONES

### Stack Tecnológico 
* Java 17
* SpringBoot 3.1.2
* Maven 3.9.0


### Documentación de la API (Swagger)

La especificación del servicio a través de la siguiente ruta:[http://localhost:9980/swagger-ui/index.html](http://localhost:9980/swagger-ui/index.html)

###  MEDIANTE DOCKER COMPOSE (integrado con la BD MySql)

Desde la ruta del proyecto, una vez construido el jar, ejecutar:

```shell
docker-compose -f docker-compose.yml up
```

Si todo esta bien debería poder acceder al contenedor desde el
navegador con [http://localhost:9980/swagger-ui/index.html](http://localhost:9980/swagger-ui/index.html)


### ACCESO A LA BD MYSQL

Para el ejercicio se utiliza la BD MySql
```shell
jdbc:mysql://localhost:4406/mysql
root/desa
```
Se debe correr el script de creacion de BD y Tablas
```shell
/gestion-transaccion/assets/sql/DB_Mysql_Transaccion.sql
```

### CONTENEDOR USANDO DOCKER MANUALMENTE
--
Para construir el contenedor, se debe construir la imagen en Docker.

```bash
docker build . -t gestion-transaccion
```

A continuación se muestra como construir la imagen utilizando el nombre raíz del proyecto como nombre de la imagen.

```bash
docker build -f Dockerfile -t gestion-transaccion --network host .
```

--
Para levantar el contenedor

```bash
docker run -it --rm --name gestion-transaccion -p 9980:9980 -d gestion-transaccion --network=host
```
Para comprobar que el contenedor levanto correctamente

```bash
docker ps -a
```

Esto listara los contenedores corriendo en el equipo, si todo esta bien debería poder acceder al contenedor desde su
navegador con [http://localhost:9980/swagger-ui/index.html](http://localhost:9980/swagger-ui/index.html)

--
Ver logs del contenedor:
```shell
docker logs gestion-transaccion
```

--
Para acceder a través de TTY al contenedor:
```shell
docker exec -it gestion-transaccion /bin/sh
```

### DOCUMENTACION DEL SERVICIO

[Cajero_Automatico_Multi_Moneda.pdf](assets/Cajero_Automatico_Multi_Moneda.pdf)



