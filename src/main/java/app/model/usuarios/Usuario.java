package app.model.usuarios;

import java.util.Objects;

/**
 * Clase Usuario
 */
public class Usuario {
    private final String nombreUsuario;     // Nombre de usuario; es único
    private String contraUsuario;

    /**
     * Constructor
     * @param nombreUsuario nombre de usuario
     * @param contraUsuario contraseña de usuario
     */
    public Usuario(String nombreUsuario, String contraUsuario){
        this.nombreUsuario = nombreUsuario;
        this.contraUsuario = contraUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraUsuario() {
        return contraUsuario;
    }

    /**
     * Comprueba si dos objetos (Usuario) tienen el mismo nombre de usuario
     * @param obj usuario a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())    return false;
        if (obj == this)    return true;
        Usuario userObj = (Usuario) obj;
        return Objects.equals(this.nombreUsuario, userObj.nombreUsuario);
    }

    /**
     * Método toString
     * @return nombre de usuario
     */
    @Override
    public String toString() {
        return this.nombreUsuario;
    }
}