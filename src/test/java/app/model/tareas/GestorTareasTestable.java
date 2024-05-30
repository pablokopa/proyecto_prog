package app.model.tareas;

import app.model.usuarios.Usuario;
import app.view.principal.InterfazPrincipal;
import app.view.principal.VistaMatrix;
import app.view.principal.VistaTareas;

import java.lang.reflect.Field;

class GestorTareasTestable extends GestorTareas {
    private final Usuario usuario;

    public GestorTareasTestable(Usuario usuario, InterfazPrincipal interfazPrincipal, VistaTareas vistaTareas, VistaMatrix vistaMatrix) {
        super(interfazPrincipal, vistaTareas, vistaMatrix);
        this.usuario = usuario;
        setUsuario(usuario);
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }

    private void setUsuario(Usuario usuario) {
        try {
            Field usuarioField = GestorTareas.class.getDeclaredField("usuario");
            usuarioField.setAccessible(true);
            usuarioField.set(this, usuario);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getTareasDeBase() {
        // No hacer nada durante las pruebas
    }
}