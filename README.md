# Seguimiento del proyecto 
A continuación se presenta el seguimiento del proyecto, 
en el cual se detallan los avances realizados y los 
resultados obtenidos semanalmente.

## Seguimiento_00_23
Durante la semana 0 hemos pensado la idea general del proyecto,
y también realizado los siguientes puntos:
1. Diseño del diagrama de clases.
2. Realización del documento inicial del proyecto.
3. División y organización del trabajo.

## Seguimiento_01_23
Durante la primera semana del proyecto hemos estado trabajando
en la parte gráfica de la **interfaz de inicio de sesión** y en las
**principales funcionalidades** del programa.
Más concretamente, en los siguientes aspectos:
1. Creación de clases principales.
2. Búsqueda de las imágenes y png utilizados en el programa
3. Creadas clases Servicios y ObjRecursos para facilitar la creación de Swing sin Layouts.
4. Hecha la interfaz de inicio de sesión.
5. Métodos de usuario (Registrar y Conectar).
6. Métodos de las tareas (Crear, Modificar y Eliminar).
7. Métodos para añadir **restricciones en el nombre y la contraseña** del usuario.
8. Métodos para **comprobar que el nombre de usuario y la contraseña** sean correctos al registrarse o conectarse.
9. El usuario puede **registrarse y conectarse** desde la interfaz.
10. El usuario puede **trabajar con tareas** (crearlas, modificarlas y eliminarlas) desde un Menú temporal de pruebas.
11. La interfaz lanza **avisos cuando hubo errores** a la hora de registrarse o conectarse.
12. Cambiada la **fuente principal** del programa.

![Avance interfaz](/img/interfaz1.png)

## Seguimiento_02_23
Durante la segunda semana del proyecto hemos hecho todo lo relacionado con
la **base de datos** y hemos avanzado en la **interfaz principal**.
Los principales cambios realizados hna sido:
1. Cambiado el proyecto de IntelIJ a **Maven**
2. Creación de la **base de datos** (Base, esquema, tablas, roles, permisos, etc).
3. Creación de una **VPS** en la que poder subir la base de datos para que el usuario tenga su progreso en cualquier ordenador.
4. Implementación de la base de datos en el programa Java.
5. Modificados los métodos para que funcionen a través de la base de datos.
6. **Encriptadas las contraseñas** del Usuario para que nadie pueda verla, ni si quiera en la base de datos.
7. Empezada la pestaña Tareas y terminada la parte visual de la pestaña Pomodoro con algunas de sus funciones.
8. Implementado contador de usuarios automático en la interfaz login.
9. Empezada la Interfaz Principal con layouts.
10. Cambiado icono de la aplicación.

## Seguimiento_03_23
Esta semana hemos trabajado en las **pestañas de la interfaz principal**, **rehecho partes** del programa,
y creadas **nuevas funciones**. Aparte, se han arreglado diversos errores.
Cambios detallados de esta semana:
1. Rehechas todas las pestañas para que funcionen con layouts.
2. Rehechos los métodos de usuario y de tareas para que funcionen con la base de datos.
3. **Implementadas las pestañas** en la Interfaz Principal.
4. Se puede **cambiar de pestaña desde el menú** principal.
5. Cambios estéticos en los botones (Cambian de color cuando pasa el ratón encima y/o se seleccionan, y se expanden )
6. Avance en la parte visual de VistaTareas y VistaMatrix.
7. Implementadas funcionalidades en VistaTareas (Crear y completar tareas, se cambian de columna)
8. Arreglado y **perfeccionado el funcionamiento del panel superior** de la ventana.
9. Dibujados los botones de Cerrar, Maximizar y Minimizar con G2Graphics
10. La interfaz login lanza **avisos cuando no se pudo conectar** a la base de datos.
11. Ahora la **aplicación se abre rápidamente cuando se inicia sin conexión**.
12. Creado e implementado en el menú método para **desconectar al usuario**.