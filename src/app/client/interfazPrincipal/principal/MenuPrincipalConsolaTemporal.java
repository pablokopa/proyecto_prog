package app.client.interfazPrincipal.principal;

import tareas.TareaToDo;
import usuarios.GestorUsuarios;
import usuarios.Usuario;

import java.util.Scanner;

public class MenuPrincipalConsolaTemporal {
    Scanner sc = new Scanner(System.in);
    GestorUsuarios gestorUsuarios;
    Usuario usuarioConectado;

    String menuPrincipal = """
            (1) Crear tarea
            (2) Completar tarea
            (3) Modificar tarea
            (4) Eliminar tarea
            (5) Ver tareas
            (6) Cambiar contraseña
            (7) Desconectar usuario""";

    public MenuPrincipalConsolaTemporal(GestorUsuarios gestorUsuario){
        this.gestorUsuarios = gestorUsuario;
        this.usuarioConectado = gestorUsuario.getUsuarioConectado();
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
                usuarioConectado.getGestorTareasUsuario().agregarTarea(nuevaTarea);
            }

            if (opcion == 2) {
                System.out.println("");
            }


        }
    }

}
