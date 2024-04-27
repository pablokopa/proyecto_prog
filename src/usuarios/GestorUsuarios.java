package usuarios;

import javax.swing.*;
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
    public boolean comprobarNombreUsuario(String nombreUsuario, JLabel textoComprobacion){
        Usuario usuario = new Usuario(nombreUsuario, null);
        if (this.listaUsuarios.contains(usuario)){
            textoComprobacion.setText("El nombre de usuario ya existe");
            return false;
        }
        else if (nombreUsuario.length()<4){
            textoComprobacion.setText("El nombre de usuario debe tener al menos 4 carácteres");
            return false;
        }
        return true;
    }

    public boolean comprobarPasswordUsuario(char[] passwordUsuario, JLabel textoComprobacion){
        String caracteresNoPermitidos = "!@#$%^&*()_+{}|:\"<>?`~\\[\\];',./";
        if (passwordUsuario.length<4){
            textoComprobacion.setText("La contraseña debe tener al menos 4 carácteres");
            return false;
        }
        for (int i=0; i<passwordUsuario.length; i++){
            if (passwordUsuario[i]==' '){
                textoComprobacion.setText("La contraseña no puede tener espacios en blanco");
                return false;
            } else if (caracteresNoPermitidos.indexOf(passwordUsuario[i]) != -1){
                textoComprobacion.setText("La contraseña no puede tener carácteres extraños");
                return false;
            }
        }
        return true;
    }
}