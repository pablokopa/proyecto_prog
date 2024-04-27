package usuarios;

import java.util.ArrayList;

/**
 * Clase que gestiona los usuarios
 */
public class GestorUsuarios {
    private final ArrayList<Usuario> listaUsuarios; // Lista de usuarios

    /**
     * Constructor
     */
    public GestorUsuarios(){
        this.listaUsuarios = new ArrayList<>();
    }

    // Getters
    /**
     * @return lista de usuarios
     */
    public ArrayList<Usuario> getListaUsuarios() {
        return this.listaUsuarios;
    }

    /**
     * Crea un usuario si el nombre de usuario no existe
     * @param usuario (Usuario)
     * @return true si fue creado correctamente
     */
    public boolean registrarUsuario(Usuario usuario){
        if (this.listaUsuarios.contains(usuario)){
            return false;
        }
        return this.listaUsuarios.add(usuario);
    }

    /**
     * Elimina un usuario si se encuentra en la lista de usuarios
     * @param usuario (Usuario)
     * @return true si fue eliminado correctamente
     */
    public boolean eliminarUsuario (Usuario usuario){
        return this.listaUsuarios.remove(usuario);
    }

    /**
     * Conecta un usuario si se encuentra en la lista y no está conectado
     * @param usuario (Usuario)
     * @return true si fue conectado correctamente
     */
    public boolean conectarUsuario (Usuario usuario){
        return false;
    }

    /**
     * Desconecta un usuario si se encuentra en la lista y está conectado
     * @param usuario (Usuario)
     * @return true si fue desconectado correctamente
     */
    public boolean desconectarUsuario (Usuario usuario){
        return false;
    }

    /**
     * Comprueba si el nombre de usuario ya existe o si tiene al menos 4 carácteres
     * @param nombreUsuario (String)
     * @return mensaje de error
     */
    public String comprobarNombreUsuario(String nombreUsuario){
        Usuario usuario = new Usuario(nombreUsuario, "");
        if (this.listaUsuarios.contains(usuario)){
            return "El nombre de usuario ya existe";
        }
        else if (nombreUsuario.length()<4){
            return "El nombre de debe tener al menos 4 carácteres";
        }
        return "Nombre disponible";
    }

    public String comprobarPasswordUsuario(char[] passwordUsuario){

        return "";
    }

}
