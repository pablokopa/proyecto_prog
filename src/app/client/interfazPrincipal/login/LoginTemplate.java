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
    private Color GRANATE;
    private Font ArialDefault;
    private Font ArialTitle;
    private Font ArialBold;

    /* Declaracion tipo de cursor y bordes */
    private Cursor cursorMano;
    private Border borde;

    /* Declaración imagenes (y labels) */
    private JLabel labelLogo, labelCerrar, labelUsuario, labelPassword;
    private ImageIcon imagenLogo, imagenCerrar, imagenUsuario, imagenPassword;

    /* Constructor */
    public LoginTemplate(){
        this.crearDecoradores();
        this.crearJPanels();
        this.crearJTextFields();
        this.crearJPasswordField();
        this.crearJButtons();
        this.crearJLabels();

        /* Configuración */
        setTitle("BLOOM - Login");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(this);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }

    public void crearJPanels(){
        /* Panel Izquierda */
        panelIzquierda=new JPanel();
        panelIzquierda.setSize(600,500);
        panelIzquierda.setLocation(0,0);
        panelIzquierda.setBackground(GRANATE);
        panelIzquierda.setLayout(null);
        this.add(panelIzquierda);

        /* Panel Derecha */
        panelDerecha=new JPanel();
        panelDerecha.setSize(400,500);
        panelDerecha.setLocation(600,0);
        panelDerecha.setBackground(Color.WHITE);
        panelDerecha.setLayout(null);
        this.add(panelDerecha);
    }

    public void crearJTextFields(){
        /* Campo de texto - Usuario */
        cuadroUsuario=new JTextField();
        cuadroUsuario.setSize(260, 40);
        cuadroUsuario.setLocation((panelDerecha.getWidth() - cuadroUsuario.getWidth()) / 2, 150); // Centrado en el panel de la derecha
        cuadroUsuario.setForeground(Color.DARK_GRAY); // Color de la letra
        cuadroUsuario.setBackground(Color.WHITE); // Color de fondo
        cuadroUsuario.setCaretColor(GRANATE); // Color de la línea del cursor
        cuadroUsuario.setHorizontalAlignment(SwingConstants.CENTER); // Para que el texto se empiece a escribir en el centro
        cuadroUsuario.setFont(ArialBold);
        cuadroUsuario.setBorder(borde); // Borde personalizado
        panelDerecha.add(cuadroUsuario);
    }

    public void crearJPasswordField(){
        /* Campo de texto - Password */
        cuadroPassword =new JPasswordField();
        cuadroPassword.setSize(260, 40);
        cuadroPassword.setLocation((panelDerecha.getWidth() - cuadroPassword.getWidth()) / 2, 220);
        cuadroPassword.setForeground(Color.DARK_GRAY);
        cuadroUsuario.setBackground(Color.WHITE);
        cuadroPassword.setCaretColor(GRANATE);
        cuadroPassword.setHorizontalAlignment(SwingConstants.CENTER);
        cuadroPassword.setBorder(borde);
        panelDerecha.add(cuadroPassword);
    }

    public void crearDecoradores(){
        /* Color */
        GRANATE = new Color(82,0,0);

        /* Fuentes */
        ArialDefault = new Font("Arial", Font.PLAIN, 14);
        ArialTitle = new Font("Arial", Font.BOLD, 24);
        ArialBold = new Font("Arial", Font.BOLD, 13);

        /* Cursor */
        cursorMano = new Cursor(Cursor.HAND_CURSOR);

        /* Borde */
        borde = BorderFactory.createMatteBorder(0, 0, 2, 0, GRANATE);

        /* Imágenes */
        imagenLogo = new ImageIcon("resources/images/logo1.png");
        imagenCerrar = new ImageIcon("resources/images/close.png");
        imagenUsuario = new ImageIcon("resources/images/user.png");
        imagenPassword = new ImageIcon("resources/images/password.png");
    }

    public void crearJButtons(){
        /* Botón de entrar */
        botonEntrar = new JButton("Entrar");
        botonEntrar.setSize(250, 45);
        botonEntrar.setLocation((panelDerecha.getWidth() - botonEntrar.getWidth()) / 2, 300);
        botonEntrar.setBackground(GRANATE);
        botonEntrar.setForeground(Color.WHITE);
        botonEntrar.setFocusable(false); // Para que no se vea el cuadro de selección en el texto
        botonEntrar.setFont(ArialDefault);
        botonEntrar.setCursor(cursorMano); // Cambiar el cursor
        panelDerecha.add(botonEntrar);

        /* Botón de registrarse */
        botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setSize(150, 40);
        botonRegistrar.setLocation((panelDerecha.getWidth() - botonEntrar.getWidth())-25, 370);
        botonRegistrar.setBackground(GRANATE);
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusable(false);
        botonRegistrar.setFont(ArialDefault);
        botonRegistrar.setCursor(cursorMano);
        panelDerecha.add(botonRegistrar);
    }

    public void crearJLabels(){
        /* Texto login*/
        textoLogin = new JLabel("Iniciar Sesión");
        textoLogin.setSize(400, 80);
        textoLogin.setLocation((panelDerecha.getWidth() - textoLogin.getWidth()) / 2, 65);
        textoLogin.setForeground(GRANATE);
        textoLogin.setFont(ArialTitle);
        textoLogin.setHorizontalAlignment(SwingConstants.CENTER);
        panelDerecha.add(textoLogin);

        /* Imagen logo */
        labelLogo = new JLabel();
        labelLogo.setIcon(imagenLogo);
        labelLogo.setBounds(50, 125, 550, 250);
        panelIzquierda.add(labelLogo);

        /* Imagen cruz cerrar */
        labelCerrar = new JLabel();
        labelCerrar.setIcon(imagenCerrar);
        labelCerrar.setBounds(360, 5, 40, 40);
        labelCerrar.setFocusable(false);
        labelCerrar.setBorder(null);
        labelCerrar.setCursor(cursorMano);
        panelDerecha.add(labelCerrar);

        /* Imagen candado password */
        labelPassword = new JLabel();
        labelPassword.setIcon(imagenPassword);
        labelPassword.setBounds(25, 230, 32, 32);
        labelPassword.setFocusable(false);
        labelPassword.setBorder(null);
        panelDerecha.add(labelPassword);

        /* Imagen logo usuario */
        labelUsuario = new JLabel();
        labelUsuario.setIcon(imagenUsuario);
        labelUsuario.setBounds(25, 160, 32, 32);
        labelUsuario.setFocusable(false);
        labelUsuario.setBorder(null);
        panelDerecha.add(labelUsuario);
    }
}