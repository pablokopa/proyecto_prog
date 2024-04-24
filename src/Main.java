public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        Usuario usuario = new Usuario("Pepe", "pepe");
        System.out.println( gestorUsuarios.getListaUsuarios().add(usuario) );
        System.out.println( gestorUsuarios.eliminarUsuario(usuario) );
        System.out.println(usuario);
    }
}