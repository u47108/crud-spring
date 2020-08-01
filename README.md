# crud-spring
Haciendo uso de Spring Conectarse a una base de datos RELACIONAL e insertar los datos obtenidos al hacer una llamada GET a https://arsene.azurewebsites.net/LicensePlate " indicar mediante comentarios base de datos utilizada ,Hacer una llamada a un Controller que se realice el request a la API y los datos se inserten en tu base de datos local.Crear proyecto de pruebas usando jUnit y Mockito, el coverage debe ser superior al 70% o no lo intente. Esta revisión de coverage se debe validar utilizando JACOCO.

> sudo docker pull mysql:5.7.17

> docker run --name container-name -e MYSQL_ROOT_PASSWORD=secret -p 3306:3306 -d mysql:5.7.17

> docker exec -it container-name mysql -uroot -p

>
```
CREATE TABLE vehiculos.movil (
	`key` BIGINT auto_increment NULL,
	id varchar(200) NULL,
	patente varchar(10) NULL,
	tipoAuto varchar(100) NULL,
	color varchar(100) NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
AUTO_INCREMENT=1;
```
> sudo docker ps -a

> sudo docker start %ID%

