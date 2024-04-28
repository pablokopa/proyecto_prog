import app.client.principal.InterfazPrincipalTemplate;
import usuarios.GestorUsuarios;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        //new LoginTemplate(gestorUsuarios);
        new InterfazPrincipalTemplate();
    }
}