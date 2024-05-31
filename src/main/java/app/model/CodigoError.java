package app.model;

public interface CodigoError {
    /**
     * Cuando no hay errores y todo ha ido bien
     */
    int SIN_ERROR = 0;

    /**
     * Error al intentar realizar una operación sin conexión
     */
    int ERROR_SIN_CONEXION = 1;

    /**
     * Error al intentar crear un usuario con un nombre que ya existe
     */
    int ERROR_USUARIO_YA_EXISTE = 2;

    /**
     * Error al intentar conectar un usuario no registrado
     */
    int ERROR_USUARIO_NO_REGISTRADO = 3;

    /**
     * Error al intentar conectar un usuario con una contraseña incorrecta
     */
    int ERROR_USUARIO_PASSWORD_INCORRECTA = 4;

    /**
     * Error al intentar crear un usuario con un nombre menor a 3 caracteres
     */
    int ERROR_USUARIO_NOMBRE_CORTO = 5;

    /**
     * Error al intentar crear un usuario con un nombre mayor a 40 caracteres
     */
    int ERROR_USUARIO_NOMBRE_LARGO = 6;

    /**
     * Error al intentar crear una contraseña de usuario con menos de 4 caracteres
     */
    int ERROR_USUARIO_PASSWORD_CORTA = 7;

    /**
     * Error al intentar crear una contraseña de usuario con más de 50 caracteres
     */
    int ERROR_USUARIO_PASSWORD_LARGA = 8;

    /**
     * Error al intentar crear una constraseña de usuario con espacios en la contraseña
     */
    int ERROR_USUARIO_PASSWORD_CON_ESPACIOS = 9;

    /**
     * Error al intentar crear una contraseña de usuario con caracteres raros en la contraseña
     */
    int ERROR_USUARIO_PASSWORD_CARACTERES_RAROS = 10;

    /**
     * Error al intentar crear una tarea sin nombre cuando creas una nueva tarea. Se lanza cuando no se ha modificado el texto "Nombre de la tarea" del campo de texto.
     */
    int ERROR_TAREA_SIN_NOMBRE = 11;

    /**
     * Error al intentar crear o modificar una tarea con un nombre en blanco o solo con espacios
     */
    int ERROR_TAREA_NOMBRE_EN_BLANCO = 12;

    /**
     * Error al intentar crear o modificar una tarea con un nombre mayor a 35 caracteres
     */
    int ERROR_TAREA_NOMBRE_LARGO = 13;

    /**
     * Error al intentar modificar una tarea sin haber hecho cambios
     */
    int ERROR_TAREA_SIN_CAMBIOS = 14;

    /**
     * Error al intentar eliminar todas las tareas sin completar sin haber ninguna
     */
    int ERROR_TAREA_ELIMINAR_TODAS_SIN_COMPLETADAS = 15;
}
