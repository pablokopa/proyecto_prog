package app.interfazPrincipal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

public class LoginTemplate extends JFrame{
    private JLabel tituloLogin;
    private JLabel tituloApp, lEslogan, lTituloLogin, lNotificaciones;
    private JPanel panelDerecha, panelIzquierda;
    private Color GRANATE = new Color(171,42,62);

    public LoginTemplate(){
/* Parte de derecha de la interfaz */
        /* Creación de panel derecho */
        panelIzquierda=new JPanel();
        panelIzquierda.setSize(600,500);
        panelIzquierda.setLocation(0,0);
        panelIzquierda.setBackground(GRANATE);
        panelIzquierda.setLayout(null);

        /* Texto login */
        tituloLogin=new JLabel("Login Usuario");
        tituloLogin.setBounds(100,20,200,30);
        tituloLogin.setForeground(Color.WHITE);

/* Parte de izquierda de la interfaz */
        /* Creación de panel izquierdo */
        panelDerecha=new JPanel();
        panelDerecha.setSize(400,500);
        panelDerecha.setLocation(600,0);
        panelDerecha.setBackground(Color.WHITE);
        panelDerecha.setLayout(null);

/* Añadir botones */
        this.add(panelDerecha);
        this.add(panelIzquierda);
        panelIzquierda.add(tituloLogin);

/* Configuración de la ventana */
        setTitle("Interfaz Principal");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(this);
        setVisible(true);
    }
}
