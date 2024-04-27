package usuarios;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que gestiona los usuarios
 */
public class GestorUsuarios {
    private Usuario usuarioConectado;
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

    public Usuario getUsuarioConectado() {
        return usuarioConectado;
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
    public boolean conectarUsuario (Usuario usuario, JLabel textoComprobacion){
        if (this.listaUsuarios.contains(usuario)){
            int index = this.listaUsuarios.indexOf(usuario);
            if (Arrays.equals(this.listaUsuarios.get(index).getContraUsuario(), usuario.getContraUsuario())){
                System.out.println("Usuario conectado");
                usuarioConectado = usuario;
                return true;
            }
            textoComprobacion.setText("Contraseña incorrecta");
            return false;
        }
        textoComprobacion.setText("Usuario no registrado");
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
     * Comprueba si el nombre de usuario ya existe o si tiene al menos 3 carácteres
     * @param nombreUsuario (String) obtenida desde la interfaz de usuario
     * @return true si fue creado correctamente, mensaje en la interfaz si no
     */
    public boolean comprobarNombreUsuario(String nombreUsuario, JLabel textoComprobacion){
        Usuario usuario = new Usuario(nombreUsuario, null);
        if (this.listaUsuarios.contains(usuario)){
            textoComprobacion.setText("El nombre de usuario ya existe");
            return false;
        }
        else if (nombreUsuario.length()<3){
            textoComprobacion.setText("El nombre de usuario debe tener al menos 3 carácteres");
            return false;
        }
        return true;
    }

    /**
     * Comprueba si la contraseña tiene al menos 4 carácteres y no tiene espacios en blanco ni ciertos carácteres extraños
     * @param passwordUsuario (char[]) obtenida desde la interfaz de usuario
     * @return true si fue creado correctamente, mensaje en la interfaz si no
     */
    public boolean comprobarPasswordUsuario(char[] passwordUsuario, JLabel textoComprobacion){
        String caracteresNoPermitidos = "^`:@´;·ªº|\"{}";
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