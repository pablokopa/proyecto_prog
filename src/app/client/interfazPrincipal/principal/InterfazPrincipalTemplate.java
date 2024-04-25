package app.client.interfazPrincipal.principal;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class InterfazPrincipalTemplate extends JFrame {
    /* Declaración de atributos */
    private JButton botonEnviar;
    private JTextField cuadroNombre;
    private JLabel textoNombre;

    public InterfazPrincipalTemplate(){



        /* Configuración de la ventana */
        setTitle("Interfaz Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(this);
        setLayout(null);
        setVisible(true);
    }
}
