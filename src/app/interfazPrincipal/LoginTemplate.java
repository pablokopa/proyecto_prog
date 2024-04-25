package app.interfazPrincipal;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class LoginTemplate extends JFrame{
    /* Declaracion elementos */
    private JLabel tituloLogin;
    private JTextField cuadroUsuario;
    private JPasswordField cuadroPassword;
    private JButton botonEntrar, botonCerrar, botonRegistrar;
    private JPanel panelDerecha, panelIzquierda;

    /* Declaracion colores y fuentes */
    private Color GRANATE = new Color(82,0,0);
    private Font MONSERRAT_BIG = new Font("Montserrat", Font.PLAIN, 22);
    private Font MONSERRAT_SMALL = new Font("Montserrat", Font.PLAIN, 13);

    /* Declaracion tipo de cursor y bordes */
    private Cursor cursorMano = new Cursor(Cursor.HAND_CURSOR);
    private Border borde = BorderFactory.createMatteBorder(0, 0, 2, 0, GRANATE);

    /* Declaración imagenes (y labels) */
    private JLabel labelLogo = new JLabel();
    private JLabel labelCerrar = new JLabel();
    private JLabel labelUsuario = new JLabel();
    private JLabel labelPassword = new JLabel();
    private ImageIcon imagenLogo, imagenCerrar, imagenUsuario, imagenPassword, DimAux;

    public LoginTemplate(){
        this.generarFuente(); // Generar la fuente

        /* Cargar imágenes */
        imagenLogo = new ImageIcon("resources/images/logo1.png");
        imagenCerrar = new ImageIcon("resources/images/close.png");
        imagenUsuario = new ImageIcon("resources/images/user.png");
        imagenPassword = new ImageIcon("resources/images/password.png");

        /* Parte de izquierda de la interfaz */
        panelIzquierda=new JPanel();
        panelIzquierda.setSize(600,500);
        panelIzquierda.setLocation(0,0);
        panelIzquierda.setBackground(GRANATE);
        panelIzquierda.setLayout(null);

        labelLogo.setIcon(imagenLogo);
        labelLogo.setBounds(50, 100, 550, 250);

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
        cuadroUsuario.setBorder(borde); // Borde personalizado

        cuadroPassword =new JPasswordField();
        cuadroPassword.setSize(260, 40);
        cuadroPassword.setLocation((panelDerecha.getWidth() - cuadroPassword.getWidth()) / 2, 220);
        cuadroPassword.setForeground(Color.DARK_GRAY);
        cuadroUsuario.setBackground(Color.WHITE);
        cuadroPassword.setCaretColor(GRANATE);
        cuadroPassword.setHorizontalAlignment(SwingConstants.CENTER);
        cuadroPassword.setBorder(borde);

        botonEntrar = new JButton("Entrar");
        botonEntrar.setSize(250, 45);
        botonEntrar.setLocation((panelDerecha.getWidth() - botonEntrar.getWidth()) / 2, 300);
        botonEntrar.setBackground(GRANATE);
        botonEntrar.setForeground(Color.WHITE);
        botonEntrar.setFocusable(false); // Para que no se vea el cuadro de selección en el texto
        botonEntrar.setFont(MONSERRAT_SMALL);
        botonEntrar.setCursor(cursorMano); // Cambiar el cursor

        botonCerrar = new JButton("X");
        botonCerrar.setBounds(330, 10, 45, 30);
        botonCerrar.setFocusable(false);
        botonCerrar.setBackground(GRANATE);
        botonCerrar.setForeground(Color.WHITE);
        botonCerrar.setFont(MONSERRAT_SMALL);
        botonCerrar.setCursor(cursorMano);

        botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setSize(145, 35);
        botonRegistrar.setLocation((panelDerecha.getWidth() - botonEntrar.getWidth())-20, 380);
        botonRegistrar.setBackground(GRANATE);
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusable(false);
        botonRegistrar.setFont(MONSERRAT_SMALL);
        botonRegistrar.setCursor(cursorMano);

        /* AÑADIR BOTONES */
        this.add(panelDerecha);
        this.add(panelIzquierda);
        panelDerecha.add(cuadroUsuario);
        panelDerecha.add(cuadroPassword);
        panelDerecha.add(botonEntrar);
        panelDerecha.add(botonCerrar);
        panelDerecha.add(botonRegistrar);
        panelIzquierda.add(labelLogo);

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
            InputStream is = getClass().getResourceAsStream("/resources/fonts/Montserrat.ttf"); // Cargar fuente independientemente del sistema operativo
            Font font = Font.createFont(Font.TRUETYPE_FONT, is); // Crea fuente y le da formato (TRUETYPE no altera la fuente)
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font); // Registra la fuente
        } catch (IOException | FontFormatException e) { // Excepcion por si no se encuentra la fuente
            System.out.println(e);
        }
    }
}
