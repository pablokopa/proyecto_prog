# Índice
- [Aplicación](#aplicación)
  - [Descripción de la aplicación](#descripción-de-la-aplicación)
  - [Ventanas de la aplicación](#ventanas-de-la-aplicación)
  - [Bibliotecas utilizadas](#bibliotecas-utilizadas)
  - [Diagramas](#diagramas)
    - [Diagrama de clases](#diagrama-de-clases)
    - [Diagrama de secuencia](#diagrama-de-secuencia)
    - [Diagrama E/R](#diagrama-er)
- [COD](#cod)
   - [Arquitectura MVC](#arquitectura-mvc)
   - [Patrones de diseño](#patrones-de-diseño)
   - [Tests unitarios](#tests-unitarios)
   - [Uso de GitHub](#uso-de-github)
- [Seguimiento del proyecto](#seguimiento-del-proyecto)
  - [Seguimiento resumido](#seguimiento-resumido)
  - [Seguimiento detallado](#seguimiento-detallado)

   
# Aplicación
## Descripción de la aplicación
Aplicación de gestión de tareas y tiempo, con la que el usuario puede organizar su día a día y mejorar su productividad.  
Se requiere de conexión a internet para poder utilizar la aplicación, ya que los datos se guardan en una base de datos en la nube tras cada operación.

## Ventanas de la aplicación
### Ventana de inicio de sesión
En esta ventana el usuario puede registrarse o conectarse a la aplicación.  

<img src="/img/interfazLogin.png" alt="Interfaz Login" width="996"/>

### Ventana principal
Esta ventana es el frame principal del programa, en ella se incluyen las diferentes pestañas de la aplicación.  

<img src="/img/interfazPrincipal.png" alt="Interfaz Principal" width="996"/>

### Pestaña de tareas
Esta pestaña es la más importante del programa, la mayoría de las funcionalidades del programa se realizan desde aquí.  
En ella el usuario puede trabajar con las tareas, crearlas, modificarlas, eliminarlas, completarlas, etc.  
Los cambios también se verán en la pestaña de matrix.  

<img src="/img/vistaTareas.png" alt="Vista Tareas" width="996"/>

### Pestaña de matrix
Esta pestaña está basada en la Matriz de Eisenhower, en ella el usuario puede ver sus tareas organizadas según su importancia y urgencia.  
Se añaden a la matriz cuando se crean o modifican tareas en la pestaña de tareas.  
Las tareas también pueden ser completadas desde aquí.  

<img src="/img/vistaMatrix.png" alt="Vista Matrix" width="996"/>

### Pestaña de pomodoro
En esta pestaña el usuario se puede utilizar la técnica Pomodoro para mejorar la productividad.
Tiene diferentes temporizadores, uno para concentración, otro para descanso y otro para descanso largo, y todos ellos pueden ser modificados.  
Tras acabar uno de los tiempos, se lanza una notificación al sistema.  

<img src="/img/vistaPomodoro.png" alt="Vista Pomodoro" width="996"/>

### Pestaña de inicio
Esta ventana es la primera que se muestra al usuario, en ella se muestran los créditos.  

<img src="/img/vistaInicio.png" alt="Vista Inicio" width="996"/>

## Bibliotecas utilizadas
| Dependencias    | Versión | Casos de uso                                           |
|-----------------|---------|--------------------------------------------------------|
| postgresql      | 42.3.1  | Uso y conexión a la base de datos                      |
| JUnit           | 5.8.1   | Realización de los test unitarios                      |
| Mockito         | 5.12.0  | Simulación de la base de datos para los test unitarios |
| JBcrypt         | 0.4.1   | Encriptación de las contraseñas de los usuarios        |
| LGoodDatePicker | 11.2.1  | Calendario para seleccionar fechas                     |

## Diagramas
### Diagrama de clases
### Diagrama de secuencia
### Diagrama E/R

# COD
## Arquitectura MVC
Arquitectura MVC utilizada en el proyecto, utilizando los controladores para comunicar la vista con el modelo.

<img src="/img/clasesMVC.png" alt="Interfaz Principal" height="551"/>

## Patrones de diseño
De los patrones de diseño vistos en clase hemos utilizado `Singleton` en varias ocasiones:
- En la clase `Usuario`, para que solo haya un usuario conectado a la vez.
- En la clase `Servicios`, que proporciona diferentes recursos utilizados en el programa (por ejemplo, fuentes y colores).
- En la clase `ObjRecursos`, que se utilizó para crear algunos de los componentes Swing sin Layouts. 

## Tests unitarios
Se han realizado los test unitarios utilizando `JUnit`, y `Mockito` para simular la base de datos.  
A medida que el programa ha ido cambiando han tenido que ser rehechos varias veces debido a los cambios en el programa, primero al implementar la base de datos y posteriormente al implementar el patrón MVC.

<p style="display: flex; align-items: flex-start;">
  <img src="/img/testTareas.png" style="margin-right: 40px;" />
  <img src="/img/testUsuarios.png" />
</p>

## Uso de GitHub
| <!-- -->                 | <!-- -->                                                                                                                                                                                                                                                                                                                                                                                                                                                               | <!-- -->                                 |
|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------|
| ___Proyecto de GitHub___ | La utilización ha sido continua para facilitar el la implementación de nuevas características.<br/> Para su utilización, fueron creadas 3 pestañas: ToDo, ToDo donde solo se ven las tareas propias, y vista semanal<br/>Los issues fueron creados a través de las tareas y automatizados; cuando uno fue completado, pasa automáticamente a la columna de completados.<br/>Al final de cada semana, las tareas ya realizadas fueron archivadas.                       | ![Proyecto](/img/proyecto.png)           |
| ___Commits y Ramas___    | Fueron creadas 4 ramas; una por cada desarrollador (Enrique y Pablo), una pre-release y una main.<br/>El desarrollo se hacía en las ramas de cada desarrollador y, posteriormente, se juntaba en pre-release para mantener la rama de desarrollo conjunto actualizada.<br/>Las ramas personales se mantenían actualizadas con merge o con rebase, según el caso.<br/>Una vez terminada la semana, se hacía merge --squash a la rama main para realizar el lanzamiento. | ![Commits](/img/ramas.png)               |
| ___Issues___             | Hemos creado issues cuando se requería arreglar o implementar algo nuevo.<br/>Una vez finalizados, fueron cerrados desde los commits o manualmente, según el caso.                                                                                                                                                                                                                                                                                                     | ![Issues](/img/issues.png)               |
| ___Pull requests___      | Debido a como trabajamos con las ramas, no fue necesario hacer Pull Request, ya que no hemos creado ramas para implementaciones específicas.<br/>Aun así, se ha realizado un pull request final por demostración.                                                                                                                                                                                                                                                      | ![Pull requests](/img/pull_requests.png) |
| ___JavaDoc___            | Se ha ido creado la documentación a medida que se avanzaba.<br/>Se ha generado el JavaDoc y hecho el lanzamiento de éste en la página de GitHub, a la que se puede acceder desde el repositorio.                                                                                                                                                                                                                                                                       | ![JavaDoc](/img/javadoc.png)             |
| ___Releases___           | Se ha realizado un Release final, con los tags y el jar pertinentes.<br/>También se han hecho lanzamientos cuando fueron requeridos algunas de las semanas.                                                                                                                                                                                                                                                                                                            | ![Release](/img/release.png)             |
| ___Discord___            | Se ha utilizado la automatización de notificaciones desde Discord, implementando un WebHook y configurando las notificaciones que queríamos que llegasen.                                                                                                                                                                                                                                                                                                              | ![Discord](/img/discord.png)             |
| ___Readme___             | Se ha ido actualizando el Readme a medida que se avanzaba en el proyecto, añadiendo los avances semanales y los cambios realizados.<br/>Finalmente, ha sido modificado para el lanzamiento final, añadiendo nuevos elemntos y reestructurándolo.                                                                                                                                                                                                                       | ![Readme](/img/readme.png)               |

# Seguimiento del proyecto
A continuación se presenta el seguimiento del proyecto,
en el cual se detallan los avances realizados y los
resultados obtenidos semanalmente.

## Seguimiento resumido
### 00_23

### 01_23
Durante la primera semana del proyecto hemos estado trabajando
en la parte gráfica de la `interfaz de inicio de sesión` y en las
`principales clases` del programa (Usuario y Tarea).
### 02_23
Durante la segunda semana del proyecto hemos hecho todo lo relacionado con
la `base de datos` y hemos avanzado en la `interfaz principal`.
### 03_23
Esta semana hemos trabajado en las `pestañas de la interfaz principal`, `rehecho partes` del programa,
y creadas `nuevas funciones`. Aparte, se han arreglado diversos errores.
### 04_23
### 05_23

## Seguimiento detallado
### 00_23
Durante la semana 0 hemos pensado la idea general del proyecto,
y también realizado los siguientes puntos:
1. Diseño del diagrama de clases.
2. Realización del documento inicial del proyecto.
3. División y organización del trabajo.

### 01_23
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

### 02_23
Durante la segunda semana del proyecto hemos hecho todo lo relacionado con
la **base de datos** y hemos avanzado en la **interfaz principal**.
Los cambios realizados han sido:
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

### 03_23
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

### 04_23
Esta semana se han refinado muchas cosas, terminado otras.
Cambios detallados:
1. Arreglado diversos bugs:
   1. Bugs relacionados con las tareas recién creadas en VistaTareas; ahora se actualizan automáticamente en la vista.
   2. Bug del título de VistaMatrix; ahora todos se ven completos.
   3. Arreglados conflictos
2. Se han límites en los nombres de las tareas.
3. Se han añadido mensajes de error.
4. Se han añadido timer a todos los mensajes de error de la aplicación.
5. Creado botón y funcionalidad para eliminar todas las tareas completadas.
6. Creado botón y funcionalidad para modificar la tarea seleccionada.
7. Se ha cambiado el diseño y la funcionalidad de diversos card de la columna de información.
   1. Acabada cardCrearTarea y cambios en su diseño.
   2. Cambiada cardTareaSeleccionada, y añadida funcionalidad para eliminar o modificar la tarea, y cambios en su diseño.
8. Se ha añadido un calendario e implementado en los cards de la columna de información.
9. Refactorizado muchas partes del código.
10. Terminada la vista inicio
11. Eliminadas clases y paquetes no utilizados.
12. Otros cambios de diseño y funcionalidad.

### Seguimiento_05_23
