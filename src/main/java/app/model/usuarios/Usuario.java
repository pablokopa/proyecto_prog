package app.model.usuarios;

import java.util.Objects;

/**
 * Clase Singleton que representa a un Usuario conectado.
 */
public class Usuario {
    private static Usuario usuario;

    private String nombreU;
    private String passwordU;

    private Usuario(String nombreU, String passwordU){
        this.nombreU = nombreU;
        this.passwordU = passwordU;
    }

    public static void setUsuarioConectado(String nombreUsuario, String passwordU){
        if (usuario == null){
            usuario = new Usuario(nombreUsuario, passwordU);
        } else {
            System.out.println("Ya hay un usuario conectado");
        }
    }

    public static Usuario getUsuarioConectado(){
        return usuario;
    }

    public String getNombreU() {
        return nombreU;
    }

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
