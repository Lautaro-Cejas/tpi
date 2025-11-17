## Trabajo Práctico Integrador: Proyecto Empresa

## Link: https://drive.google.com/file/d/1F88fekAeF8Y0xy4Jv5DYID-RrxV9sEsP/view

El dominio del proyecto se centra en la gestión de empleados y sus legajos administrativos dentro de una organización. 
El sistema permite registrar, consultar y actualizar información básica de cada empleado, 
junto con los datos específicos asociados a su legajo.

---

## Requisitos
- Java 17+  
- MySQL 8+  
- MySQL JDBC Driver: mysql-connector-j-9.5.0

---

## Configuración y ejecución

1. Correr "tablas_inserciones.sql". Esto crea la base de datos "empresa" y carga las tablas "empleado"
y "legajo" con 2 registros.

2. Duplicar el archivo ubicado en utn.tfi.grupo7.config llamado "config.example.properties" y
renombrarlo como "config.properties".
Luego, editar dentro del duplicado recien hecho:
	db.url=jdbc:mysql://localhost:3306/empresa (cambiar el nro de puerto si usas otro)
	db.user=root (tu usuario del servidor)
	db.password= (tu contraseña, si usas. Sino, se deja en blanco)
IMPORTANTE! No se deben dejar espacios ni usar comillas despues de "=".
Es necesario tener el conector JDBC de MySQL agregado al proyecto a partir de este momento.

3. Ejecutar test de conexión:

En la carpeta "testParteKevin", correr "TestConexion.java". Si todo esta bien configurado,
deberías ver en consola los empleados y sus legajos asociados que se cargaron en el paso 1.
