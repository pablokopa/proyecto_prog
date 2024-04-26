package usuarios;

import java.util.Objects;

public class Usuario {
    private final String nombreUsuario;
    private String contraUsuario;

    public Usuario(String nombreUsuario, String contraUsuario){
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
     * Se utiliza junto con el método equals para comparar objetos.
     * El usuario no debe estar incluido dentro de una colección HashSet o HashMap,
     * porque la igualdad y la generación del hashcode se basan en el nombre de usuario
     * y podría dar problemas.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return nombreUsuario.hashCode();
    }

    @Override
    public String toString() {
        return this.nombreUsuario;
    }
}