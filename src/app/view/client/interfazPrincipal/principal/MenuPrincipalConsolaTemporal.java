package app.view.client.interfazPrincipal.principal;

import app.model.tareas.TareaToDo;
import app.model.usuarios.Usuario;
import app.model.usuarios.UsuarioConectado;

import java.util.Scanner;

public class MenuPrincipalConsolaTemporal {
    Scanner sc = new Scanner(System.in);
    UsuarioConectado usuarioConectado;

    String menuPrincipal = """
            (1) Crear tarea
            (2) Completar tarea
            (3) Modificar tarea
            (4) Eliminar tarea
            (5) Ver tareas
            (6) Cambiar contraseña
            (7) Desconectar usuario
            (0) Cerrar aplicación""";

    public MenuPrincipalConsolaTemporal(Usuario usuario){
        usuarioConectado = UsuarioConectado.getUsuarioConectado(usuario);
    }

    public void elegirEnMenu(){

        while (true) {
            System.out.println(menuPrincipal);
            int opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.println("Nombre de la tarea: ");
                String nombreTarea = sc.nextLine();
                System.out.println("Descripción de la tarea: ");
                String descripcionTarea = sc.nextLine();

                TareaToDo nuevaTarea = new TareaToDo(nombreTarea, descripcionTarea);
                usuarioConectado.agregarTarea(nuevaTarea);
            }

            if (opcion == 2) {

            }

            if (opcion == 4) {
                System.out.println("Qué tipo de tarea deseas eliminar? \t\n(1) Por hacer\t\n(2) Completada");
                int tipo = Integer.parseInt(sc.nextLine());
                System.out.println("Qué tarea deseas eliminar? (index)");
                int index = Integer.parseInt(sc.nextLine())-1;

                if (tipo == 1){
                    usuarioConectado.eliminarTarea(usuarioConectado.getListaTareasToDo().get(index));
                }
                if (tipo == 2){
                    usuarioConectado.eliminarTarea(usuarioConectado.getListaTareasCompletadas().get(index));
                }
            }

            if (opcion == 5) {
                usuarioConectado.verTareas();
            }

            if (opcion == 0) {
                System.out.println("Cerrando aplicación...");
                break;
            }
        }
    }

}
