import java.util.List;
import java.util.ArrayList;

public class GestorUsuarios {
    private static boolean usuarioConectado;
    private List<Usuario> listaUsuarios;

    public GestorUsuarios(){
        this.listaUsuarios = new ArrayList<>();
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public boolean crearUsuario (Usuario usuario){
        if (this.listaUsuarios.contains(usuario)){
            return false;
        }
        return this.listaUsuarios.add(usuario);
    }

    private int indexUsuario(Usuario usuario){
        if (this.listaUsuarios.contains(usuario)){
            return this.listaUsuarios.indexOf(usuario);
        }
        return -1;
    }

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
