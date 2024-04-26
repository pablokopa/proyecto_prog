package usuarios;

import java.time.LocalDate;
import java.util.Objects;

public class Usuario {
    private String nombreUsuario;
    private String contraUsuario;
    private final LocalDate dataCreacion;

    public Usuario(String nombreUsuario, String contraUsuario){
        this.nombreUsuario = nombreUsuario;
        this.contraUsuario = contraUsuario;
        this.dataCreacion = LocalDate.now();    // añade la fecha de creación del usuario
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

    @Override
    public int hashCode() {
        return nombreUsuario.hashCode();
    }

    @Override
    public String toString() {
        return this.nombreUsuario + "(" + dataCreacion + ")";
    }
}