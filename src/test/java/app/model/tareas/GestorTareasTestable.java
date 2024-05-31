package app.model.tareas;

import app.model.usuarios.Usuario;
import app.view.principal.InterfazPrincipal;
import app.view.principal.VistaMatrix;
import app.view.principal.VistaTareas;

import java.lang.reflect.Field;

class GestorTareasTestable extends GestorTareas {
    private Usuario usuario;
    private InterfazPrincipal interfazPrincipal;
    private VistaTareas vistaTareas;
    private VistaMatrix vistaMatrix;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        try {
            Field usuarioField = GestorTareas.class.getDeclaredField("usuario");
            usuarioField.setAccessible(true);
            usuarioField.set(this, usuario);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setInterfazPrincipal(InterfazPrincipal interfazPrincipal) {
        this.interfazPrincipal = interfazPrincipal;
    }

    public void setVistaTareas(VistaTareas vistaTareas) {
        this.vistaTareas = vistaTareas;
    }

    public void setVistaMatrix(VistaMatrix vistaMatrix) {
        this.vistaMatrix = vistaMatrix;
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }
}