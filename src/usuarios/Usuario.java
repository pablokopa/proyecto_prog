package usuarios;

import java.util.Objects;

/**
 * Clase Usuario
 */
public class Usuario {
    private final String nombreUsuario;     // Nombre de usuario; es único
    private char[] contraUsuario;

    /**
     * Constructor
     * @param nombreUsuario nombre de usuario
     * @param contraUsuario contraseña de usuario
     */
    public Usuario(String nombreUsuario, char[] contraUsuario){
        this.nombreUsuario = nombreUsuario;
        this.contraUsuario = contraUsuario;
    }

    /**
     * Comprueba si dos objetos (Usuario) tienen el mismo nombre de usuario
     * @param obj usuario a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)                return false;
        if (!(obj instanceof Usuario))  return false;
        if (obj == this)                return true;
        Usuario userObj = (Usuario) obj;
        return Objects.equals(this.nombreUsuario, userObj.nombreUsuario);
    }

    /**
     * Se utiliza automáticamente en el caso de utilizar HashSet, HashMap o HashTable,
     * no se puede cambiar el nombre de usuario o podría causar problemas
     * (No utilizado actualmente en el proyecto)
     * @return hashCode de nombreUsuario
     */
    @Override
    public int hashCode() {
        return nombreUsuario.hashCode();
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