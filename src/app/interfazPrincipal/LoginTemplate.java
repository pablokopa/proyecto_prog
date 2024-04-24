package app.interfazPrincipal;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoginTemplate extends JFrame{
    /* Declaracion elementos */
    private JLabel tituloLogin;
    private JTextField cuadroUsuario;
    private JPasswordField cuadroContraseña;
    private JButton botonEntrar, botonCerrar, botonRegistrar;
    private JPanel panelDerecha, panelIzquierda;

    /* Declaracion colores y fuentes */
    private Color GRANATE = new Color(82,0,0);
    private Font MONSERRAT_BIG = new Font("Montserrat", Font.PLAIN, 22);
    private Font MONSERRAT_SMALL = new Font("Montserrat", Font.PLAIN, 13);

    /* Declaracion tipo de cursor */


    public LoginTemplate(){
        this.generarFuente(); // Generar la fuente

        /* Parte de izquierda de la interfaz */
        panelIzquierda=new JPanel();
        panelIzquierda.setSize(600,500);
        panelIzquierda.setLocation(0,0);
        panelIzquierda.setBackground(GRANATE);
        panelIzquierda.setLayout(null);

        tituloLogin=new JLabel("Login Usuario");
        tituloLogin.setBounds(100,20,200,30);
        tituloLogin.setForeground(Color.WHITE);
        tituloLogin.setFont(MONSERRAT_BIG);

        /* Parte de derecha de la interfaz */
        panelDerecha=new JPanel();
        panelDerecha.setSize(400,500);
        panelDerecha.setLocation(600,0);
        panelDerecha.setBackground(Color.WHITE);
        panelDerecha.setLayout(null);

        cuadroUsuario=new JTextField();
        cuadroUsuario.setSize(260, 40);
        cuadroUsuario.setLocation((panelDerecha.getWidth() - cuadroUsuario.getWidth()) / 2, 150); // Centrado en el panel de la derecha
        cuadroUsuario.setForeground(Color.DARK_GRAY); // Color de la letra
        cuadroUsuario.setBackground(Color.WHITE); // Color de fondo
        cuadroUsuario.setCaretColor(GRANATE); // Color de la línea del cursor
        cuadroUsuario.setHorizontalAlignment(SwingConstants.CENTER); // Para que el texto se empiece a escribir en el centro
        cuadroUsuario.setFont(MONSERRAT_SMALL);

        cuadroContraseña=new JPasswordField();
        cuadroContraseña.setSize(260, 40);
        cuadroContraseña.setLocation((panelDerecha.getWidth() - cuadroContraseña.getWidth()) / 2, 220);
        cuadroContraseña.setForeground(Color.DARK_GRAY);
        cuadroUsuario.setBackground(Color.WHITE);
        cuadroContraseña.setCaretColor(GRANATE);
        cuadroContraseña.setHorizontalAlignment(SwingConstants.CENTER);

        botonEntrar = new JButton("Entrar");
        botonEntrar.setSize(250, 45);
        botonEntrar.setLocation((panelDerecha.getWidth() - botonEntrar.getWidth()) / 2, 300);
        botonEntrar.setBackground(GRANATE);
        botonEntrar.setForeground(Color.WHITE);
        botonEntrar.setFocusable(false); // Para que no se vea el cuadro de selección en el texto
        botonEntrar.setFont(MONSERRAT_SMALL);

        botonCerrar = new JButton("X");
        botonCerrar.setBounds(330, 10, 45, 30);
        botonCerrar.setFocusable(false);
        botonCerrar.setBackground(GRANATE);
        botonCerrar.setForeground(Color.WHITE);
        botonCerrar.setFont(MONSERRAT_SMALL);

        botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setSize(145, 35);
        botonRegistrar.setLocation((panelDerecha.getWidth() - botonEntrar.getWidth())-20, 380);
        botonRegistrar.setBackground(GRANATE);
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusable(false);
        botonRegistrar.setFont(MONSERRAT_SMALL);

        /* AÑADIR BOTONES */
        this.add(panelDerecha);
        this.add(panelIzquierda);
        panelIzquierda.add(tituloLogin);
        panelDerecha.add(cuadroUsuario);
        panelDerecha.add(cuadroContraseña);
        panelDerecha.add(botonEntrar);
        panelDerecha.add(botonCerrar);
        panelDerecha.add(botonRegistrar);

        /* CONFIGURACIÓN DE LA VENTANA */
        setTitle("Interfaz Principal");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(this);
        setVisible(true);
    }

    /* Método para generar la fuente */
    private void generarFuente() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); // Para que se pueda usar la fuente en toda la app
            ge.registerFont( Font.createFont( // Crear fuente
                    Font.TRUETYPE_FONT, // Formato de la fuente (TRUETYPE no altera la fuente)
                    new File("proyecto_prog/resources/fonts/Montserrat.ttf")
            ));
        } catch (IOException | FontFormatException e) { // Excepcione por si no se encuentra la fuente
            System.out.println(e);
        }
    }
}
