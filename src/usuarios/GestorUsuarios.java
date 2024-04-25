package usuarios;

import java.util.List;
import java.util.ArrayList;

public class GestorUsuarios {
    private static boolean usuarioConectado;
    private final List<Usuario> listaUsuarios;

    public GestorUsuarios(){
        this.listaUsuarios = new ArrayList<>();
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * Crea un usuario si el nombre de usuario no existe
     * @param usuario
     * @return true si fue creado correctamente
     */
    public boolean crearUsuario (Usuario usuario){
        if (this.listaUsuarios.contains(usuario)){
            return false;
        }
        return this.listaUsuarios.add(usuario);
    }

    /*
    private int indexUsuario(Usuario usuario){
        if (this.listaUsuarios.contains(usuario)){
            return this.listaUsuarios.indexOf(usuario);
        }
        return -1;
    }
    */

    /**
     * Elimina un usuario si se encuentra en la lista
     * @param usuario (Usuario)
     * @return true si fue eliminado correctamente
     */
    public boolean eliminarUsuario (Usuario usuario){
        return this.listaUsuarios.remove(usuario);
    }

    public boolean conectarUsuario (Usuario usuario){

        return false;
    }

    public boolean desconectarUsuario (Usuario usuario){
        return false;
    }

}
