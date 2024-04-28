package app.client.interfazPrincipal.principal;

import tareas.TareaCompletada;
import tareas.TareaToDo;
import usuarios.Usuario;
import usuarios.UsuarioConectado;

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
            (7) Desconectar usuario""";

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

            if (opcion == 5) {
                if (usuarioConectado.getListaTareasToDo().isEmpty()){
                    System.out.println("No hay tareas por hacer todavía");
                } else {
                    System.out.println("Tareas por hacer: ");
                    for (TareaToDo tarea : usuarioConectado.getListaTareasToDo()) {
                        System.out.println(tarea);
                    }
                }
                if (usuarioConectado.getListaTareasCompletadas().isEmpty()){
                    System.out.println("No hay tareas completadas todavía");
                } else {
                    System.out.println("Tareas completadas: ");
                    for (TareaCompletada tarea : usuarioConectado.getListaTareasCompletadas()) {
                        System.out.println(tarea);
                    }
                }
            }


        }
    }

}
