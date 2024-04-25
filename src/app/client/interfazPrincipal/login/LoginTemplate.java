package app.client.interfazPrincipal.login;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginTemplate extends JFrame{
    /* Declaracion elementos */
    private JLabel textoLogin;
    private JTextField cuadroUsuario;
    private JPasswordField cuadroPassword;
    private JButton botonEntrar, botonCerrar, botonRegistrar;
    private JPanel panelDerecha, panelIzquierda;

    /* Declaracion colores y fuentes */
    private Color GRANATE = new Color(82,0,0);
    private Font ArialDefault = new Font("Arial", Font.PLAIN, 14);
    private Font ArialTitle = new Font("Arial", Font.BOLD, 24);
    private Font ArialBold = new Font("Arial", Font.BOLD, 13);

    /* Declaracion tipo de cursor y bordes */
    private Cursor cursorMano = new Cursor(Cursor.HAND_CURSOR);
    private Border borde = BorderFactory.createMatteBorder(0, 0, 2, 0, GRANATE);

    /* Declaración imagenes (y labels) */
    private JLabel labelLogo = new JLabel();
    private JLabel labelCerrar = new JLabel();
    private JLabel labelUsuario = new JLabel();
    private JLabel labelPassword = new JLabel();
    private ImageIcon imagenLogo, imagenCerrar, imagenUsuario, imagenPassword;

    public LoginTemplate(){
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
        labelLogo.setBounds(50, 125, 550, 250);

        /* Parte de derecha de la interfaz */
        panelDerecha=new JPanel();
        panelDerecha.setSize(400,500);
        panelDerecha.setLocation(600,0);
        panelDerecha.setBackground(Color.WHITE);
        panelDerecha.setLayout(null);

        textoLogin = new JLabel("Iniciar Sesión");
        textoLogin.setSize(400, 80);
        textoLogin.setLocation((panelDerecha.getWidth() - textoLogin.getWidth()) / 2, 65);
        textoLogin.setForeground(GRANATE);
        textoLogin.setFont(ArialTitle);
        textoLogin.setHorizontalAlignment(SwingConstants.CENTER);

        cuadroUsuario=new JTextField();
        cuadroUsuario.setSize(260, 40);
        cuadroUsuario.setLocation((panelDerecha.getWidth() - cuadroUsuario.getWidth()) / 2, 150); // Centrado en el panel de la derecha
        cuadroUsuario.setForeground(Color.DARK_GRAY); // Color de la letra
        cuadroUsuario.setBackground(Color.WHITE); // Color de fondo
        cuadroUsuario.setCaretColor(GRANATE); // Color de la línea del cursor
        cuadroUsuario.setHorizontalAlignment(SwingConstants.CENTER); // Para que el texto se empiece a escribir en el centro
        cuadroUsuario.setFont(ArialBold);
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
        botonEntrar.setFont(ArialDefault);
        botonEntrar.setCursor(cursorMano); // Cambiar el cursor

        /*botonCerrar = new JButton("X");
        botonCerrar.setBounds(330, 10, 45, 30);
        botonCerrar.setFocusable(false);
        botonCerrar.setBackground(GRANATE);
        botonCerrar.setForeground(Color.WHITE);
        botonCerrar.setFont(ArialBold);
        botonCerrar.setCursor(cursorMano);*/

        botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setSize(150, 40);
        botonRegistrar.setLocation((panelDerecha.getWidth() - botonEntrar.getWidth())-25, 370);
        botonRegistrar.setBackground(GRANATE);
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusable(false);
        botonRegistrar.setFont(ArialDefault);
        botonRegistrar.setCursor(cursorMano);

        labelCerrar.setIcon(imagenCerrar);
        labelCerrar.setBounds(360, 5, 40, 40);
        labelCerrar.setFocusable(false);
        labelCerrar.setBorder(null);
        labelCerrar.setCursor(cursorMano);

        labelPassword.setIcon(imagenPassword);
        labelPassword.setBounds(25, 230, 32, 32);
        labelPassword.setFocusable(false);
        labelPassword.setBorder(null);

        labelUsuario.setIcon(imagenUsuario);
        labelUsuario.setBounds(25, 160, 32, 32);
        labelUsuario.setFocusable(false);
        labelUsuario.setBorder(null);

        /* AÑADIR BOTONES */
        this.add(panelDerecha);
        this.add(panelIzquierda);
        panelDerecha.add(textoLogin);
        panelDerecha.add(cuadroUsuario);
        panelDerecha.add(cuadroPassword);
        panelDerecha.add(botonEntrar);
        /*panelDerecha.add(botonCerrar);*/
        panelDerecha.add(botonRegistrar);
        panelDerecha.add(labelCerrar);
        panelDerecha.add(labelUsuario);
        panelDerecha.add(labelPassword);
        panelIzquierda.add(labelLogo);

        /* CONFIGURACIÓN DE LA VENTANA */
        setTitle("BLOOM - Login");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(this);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }
}
