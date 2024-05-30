package app.model;

public interface CodigoError {
    int SIN_ERROR = 0;
    int ERROR_SIN_CONEXION = 1;
    int ERROR_USUARIO_YA_EXISTE = 2;
    int ERROR_PASSWORD_INCORRECTA = 3;
    int ERROR_NOMBRE_CORTO = 4;
    int ERROR_NOMBRE_LARGO = 5;
    int ERROR_PASSWORD_CORTA = 6;
    int ERROR_PASSWORD_LARGA = 7;
    int ERROR_PASSWORD_CON_ESPACIOS = 8;
    int ERROR_PASSWORD_CARACTERES_RAROS = 9;
}
