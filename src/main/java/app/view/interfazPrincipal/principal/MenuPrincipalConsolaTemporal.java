package app.view.interfazPrincipal.principal;

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
            (6*) Cambiar contraseña
            (7*) Desconectar usuario
            (0) Cerrar aplicación""";

    public MenuPrincipalConsolaTemporal(Usuario usuario){
        usuarioConectado = UsuarioConectado.getUsuarioConectado(usuario);
    }

    public void elegirEnMenu(){

        while (true) {
            System.out.println(menuPrincipal);
            int opcion = Integer.parseInt(sc.nextLine());

            // crear tarea
            if (opcion == 1) {
                System.out.println("Nombre de la tarea: ");
                String nombreTarea = sc.nextLine();
                System.out.println("Descripción de la tarea: ");
                String descripcionTarea = sc.nextLine();

                TareaToDo nuevaTarea = new TareaToDo(nombreTarea, descripcionTarea);
                usuarioConectado.agregarTarea(nuevaTarea);
            }

            // completar tarea (ToDo)
            if (opcion == 2) {
                usuarioConectado.verTareasToDo();

                System.out.println("Qué tarea deseas completar? (index)");
                int index = Integer.parseInt(sc.nextLine())-1;
                usuarioConectado.completarTarea(usuarioConectado.getListaTareasToDo().get(index));
            }

            // modificar tarea
            if (opcion == 3) {
                usuarioConectado.verTareas();

                System.out.println("Qué tipo de tarea deseas modificar? \t\n(1) Por hacer\t\n(2) Completada");
                int tipo = Integer.parseInt(sc.nextLine());
                System.out.println("Qué tarea deseas modificar? (index)");
                int index = Integer.parseInt(sc.nextLine())-1;

                System.out.println("Nuevo nombre de tarea: ");
                String nombreTarea = sc.nextLine();
                System.out.println("Nueva descripción de tarea: ");
                String descripcionTarea = sc.nextLine();

                if (tipo == 1){
                    usuarioConectado.modificarTarea(usuarioConectado.getListaTareasToDo().get(index), nombreTarea, descripcionTarea);
                }
                if (tipo == 2){
                    usuarioConectado.modificarTarea(usuarioConectado.getListaTareasCompletadas().get(index), nombreTarea, descripcionTarea);
                }

                System.out.println("Opción aún no disponible");
            }

            // eliminar tarea
            if (opcion == 4) {
                usuarioConectado.verTareas();

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

            // ver tareas
            if (opcion == 5) {
                usuarioConectado.verTareas();
            }

            // cambiar contraseña
            if (opcion == 6) {

            }

            // desconectar usuario
            if (opcion == 7) {

            }

            if (opcion == 0) {
                System.out.println("Cerrando aplicación...");
                break;
            }
        }
    }

}
