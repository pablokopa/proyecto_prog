package app.client.interfazPrincipal.login;

import app.services.ObjGraficos;
import usuarios.GestorUsuarios;
import usuarios.Usuario;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class LoginTemplate extends JFrame{
    /* Servicios */
    private ObjGraficos sObjGraficos;

    /* Declaración movimiento ventana */
    private int mouseX;
    private int mouseY;
    private int x;
    private int y;

    /* Declaracion elementos */
    private JLabel textoLogin;
    private JTextField cuadroUsuario;
    private JPasswordField cuadroPassword;
    private JButton botonEntrar, botonRegistrar;
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
    public LoginTemplate(GestorUsuarios gestorUsuarios){
        sObjGraficos = ObjGraficos.getService();

        this.moverVentana();
        this.crearDecoradores();
        this.crearJPanels();
        this.crearJTextFields();
        this.crearJPasswordField();
        this.crearJButtons(gestorUsuarios);
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

    public void moverVentana(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getXOnScreen();
                y = e.getYOnScreen();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setLocation(x - mouseX, y - mouseY);
            }
        });
    }

    public void crearJPanels(){
        /* Panel Izquierda */
        panelIzquierda = sObjGraficos.construirJPanel(0, 0, 600, 500, GRANATE, null);
        this.add(panelIzquierda);

        /* Panel Derecha */
        panelDerecha = sObjGraficos.construirJPanel(600, 0, 400, 500, Color.WHITE, null);
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

    public void crearJButtons(GestorUsuarios gestorUsuarios){
        /* Botón de entrar */
        botonEntrar = sObjGraficos.construirJButton("Entrar", (panelDerecha.getWidth() - 250) / 2, 300, 250, 45, cursorMano, null, ArialDefault, GRANATE, Color.WHITE, null, "center", true);
        panelDerecha.add(botonEntrar);

        /* Botón de registrarse */
        botonRegistrar = sObjGraficos.construirJButton("Registrarse", (panelDerecha.getWidth() - botonEntrar.getWidth())-25, 370, 150, 40, cursorMano, null, ArialDefault, GRANATE, Color.WHITE, null, "center", true);
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioTemporal = new Usuario(labelUsuario.getText(),labelPassword.getText());
                if (gestorUsuarios.registrarUsuario(usuarioTemporal)){
                    System.out.println("Usuario registrado correctamente");
                }else{
                    System.out.println("El usuario no ha sido registrado");
                }
            }
        });
        panelDerecha.add(botonRegistrar);
    }

    public void crearJLabels(){
        /* Texto login*/
        textoLogin = sObjGraficos.construirJLabel("Iniciar Sesión", (panelDerecha.getWidth()-165) / 2, 65, 400, 80, null, null, ArialTitle, null, GRANATE, null, "center");
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
        // Acción del botón de cerrar
        labelCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Cuando se haga click
                dispose(); // Cerrar la ventana
            }
        });
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