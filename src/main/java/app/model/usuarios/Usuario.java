package app.model.usuarios;

import java.util.Objects;

/**
 * Clase Singleton que representa a un Usuario conectado.
 */
public class Usuario {
    private static Usuario usuario;

    private String nombreU;
    private String passwordU;

    /**
     * Constructor de la clase Usuario
     * @param nombreU nombre del usuario
     * @param passwordU contraseña del usuario
     */
    private Usuario(String nombreU, String passwordU){
        this.nombreU = nombreU;
        this.passwordU = passwordU;
    }

    /**
     * Método para establecer un usuario conectado.
     * @param nombreUsuario nombre del usuario
     * @param passwordU contraseña del usuario
     */
    public static void setUsuarioConectado(String nombreUsuario, String passwordU){
        if (usuario == null){
            usuario = new Usuario(nombreUsuario, passwordU);
        } else {
            System.out.println("Ya hay un usuario conectado");
        }
    }

    /**
     * Método para desconectar un usuario.
     */
    public static void desconectarUsuario(){
        usuario = null;
    }

    /**
     * Método para obtener el usuario conectado.
     * @return usuario conectado
     */
    public static Usuario getUsuarioConectado(){
        return usuario;
    }

    /**
     * Método para obtener el nombre del usuario.
     * @return nombre del usuario
     */
    public String getNombreU() {
        return nombreU;
    }

    /**
     * Sobrescribe el método equals para comparar dos objetos de la clase Usuario por su nombre.
     * @param obj objeto a comparar
     * @return true si los nombres de usuario son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (obj == this) return true;
        Usuario userObj = (Usuario) obj;
        return Objects.equals(this.nombreU, userObj.nombreU);
    }

    /**
     * Método toString
     * @return nombre de usuario
     */
    @Override
    public String toString() {
        return this.nombreU;
    }
}
