package app.view.interfazPrincipal.principal;

import services.ObjGraficos;
import services.Recursos;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class TareasPomodoroTemporal extends JFrame {
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;

    private int x, y, mouseX, mouseY;

    private JPanel panelOpciones, panelVistaPrincipal, panelSuperior;
    private JLabel labelCerrarVentana, labelTiempoConcentracion, labelTiempoDescanso;
    private JButton botonPlay, botonPause, botonDetener, botonCambiarTiempo, botonConfirmarCambios;
    private JTextField textMinutosConcentracion, textMinutosDescanso;

    TareasPomodoroTemporal() {
        this.sObjGraficos = ObjGraficos.getService();
        this.sRecursos = Recursos.getService();

        /* Configuración de la ventana */
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        setLayout(null);
        setUndecorated(true);
        setIconImage(sRecursos.getImagenLogo2().getImage());

        /* Generar los componentes */
        crearPaneles();
        crearLabels();
        crearBotones();
        crearTextFields();
        moverVentana();

        /* Hacer visible la ventana */
        setVisible(true);
    }

    private void crearPaneles(){
        /* Panel opciones izquierda */
        panelOpciones = sObjGraficos.construirJPanel(0,0,250,650, sRecursos.getGRANATE(), null);
        add(panelOpciones);

        /* Panel superior */
        panelSuperior = sObjGraficos.construirJPanel(250,0, 850, 50, sRecursos.getBLANCO(), null);
        add(panelSuperior);

        /* Panel principal */
        panelVistaPrincipal = sObjGraficos.construirJPanel(250,50, 850, 600, sRecursos.getGRIS_CLARO(), null);
        add(panelVistaPrincipal);
    }

    private void crearLabels(){
        /* Label cerrar ventana */
        labelCerrarVentana = sObjGraficos.construirJLabel(
                null,
                panelVistaPrincipal.getWidth()-50,
                5,
                40,
                40,
                sRecursos.getCursorMano(),
                sRecursos.getImagenCerrar(),
                null,
                null,
                null,
                null,
                "r"
        );
        labelCerrarVentana.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        panelSuperior.add(labelCerrarVentana);

        labelTiempoConcentracion = sObjGraficos.construirJLabel(
                "25:00",
                0,
                75,
                panelVistaPrincipal.getWidth(),
                175,
                null,
                null,
                sRecursos.getMonserratBold(175),
                sRecursos.getGRIS_CLARO(),
                sRecursos.getGRANATE(),
                null,
                "c"
        );
        panelVistaPrincipal.add(labelTiempoConcentracion);

        labelTiempoDescanso = sObjGraficos.construirJLabel(
                "5:00",
                20,
                labelTiempoConcentracion.getHeight()+100,
                panelVistaPrincipal.getWidth()-40,
                195,
                null,
                null,
                sRecursos.getMonserratBold(75),
                sRecursos.getGRIS_CLARO(),
                new Color(82,0,0,250),
                sRecursos.getBordeGranate(),
                "t"
        );
        panelVistaPrincipal.add(labelTiempoDescanso);
    }

    /**
     * Método para crear los botones de la vista principal.
     */
    private void crearBotones(){

        /* Boton play */
        botonPlay = sObjGraficos.construirJButton(
                null,
                (panelVistaPrincipal.getWidth()-620)/2,
                panelVistaPrincipal.getHeight()-100-20,
                200,
                50,
                sRecursos.getCursorMano(),
                sRecursos.getImagenPlay(),
                null,
                sRecursos.getGRANATE(),
                null,
                null,
                "",
                true
        );
        botonPlay.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonPlay.getModel().isPressed()) {
                    botonPlay.setBackground(sRecursos.getBLANCO());
                }
                if (botonPlay.getModel().isRollover()) {
                    botonPlay.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonPlay.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        botonPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int minutos = Integer.valueOf(labelTiempoConcentracion.getText());
            }
        });
        panelVistaPrincipal.add(botonPlay);

        /* Boton pausa */
        botonPause = sObjGraficos.construirJButton(
                null,
                botonPlay.getX()+botonPlay.getWidth()+10,
                panelVistaPrincipal.getHeight()-100-20,
                200,
                50,
                sRecursos.getCursorMano(),
                sRecursos.getImagenPause(),
                null,
                sRecursos.getGRANATE(),
                null,
                null,
                "",
                true
        );
        botonPause.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonPause.getModel().isRollover()) {
                    botonPause.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonPause.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        panelVistaPrincipal.add(botonPause);

        /* Boton detener */
        botonDetener = sObjGraficos.construirJButton(
                null,
                botonPause.getX()+botonPause.getWidth()+10,
                panelVistaPrincipal.getHeight()-100-20,
                200,
                50,
                sRecursos.getCursorMano(),
                new ImageIcon(sRecursos.getImagenStop().getImage().getScaledInstance(36,36, Image.SCALE_DEFAULT)),
                null,
                sRecursos.getGRANATE(),
                null,
                null,
                "",
                true
        );
        botonDetener.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonDetener.getModel().isRollover()) {
                    botonDetener.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonDetener.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        panelVistaPrincipal.add(botonDetener);

        /* Boton cambiar tiempos de concentración y descanso */
        botonCambiarTiempo = sObjGraficos.construirJButton(
                "Cambiar los parámetros",
                botonPlay.getX(),
                panelVistaPrincipal.getHeight()-50-10,
                botonPlay.getWidth()*3+20,
                50,
                sRecursos.getCursorMano(),
                null,
                sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON),
                sRecursos.getGRANATE(),
                sRecursos.getBLANCO(),
                null,
                "",
                true
        );
        botonCambiarTiempo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonCambiarTiempo.getModel().isRollover()) {
                    botonCambiarTiempo.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonCambiarTiempo.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        botonCambiarTiempo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonCambiarTiempo.setVisible(false);
                botonPlay.setVisible(false);
                botonPause.setVisible(false);
                botonDetener.setVisible(false);
                botonConfirmarCambios.setVisible(true);
                textMinutosConcentracion.setVisible(true);
                textMinutosDescanso.setVisible(true);
            }
        });
        panelVistaPrincipal.add(botonCambiarTiempo);

        botonConfirmarCambios = sObjGraficos.construirJButton(
            "Confirmar",
            botonPlay.getX(),
            panelVistaPrincipal.getHeight()-50-10,
            botonPlay.getWidth()*3+20,
            50,
            sRecursos.getCursorMano(),
            null,
            sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON),
            sRecursos.getGRANATE(),
            sRecursos.getBLANCO(),
            null,
            "",
            true
        );
        botonConfirmarCambios.setVisible(false);
        botonConfirmarCambios.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (botonConfirmarCambios.getModel().isRollover()){
                    botonConfirmarCambios.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonConfirmarCambios.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        botonConfirmarCambios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonConfirmarCambios.setVisible(false);
                textMinutosConcentracion.setVisible(false);
                textMinutosDescanso.setVisible(false);
                botonPlay.setVisible(true);
                botonPause.setVisible(true);
                botonDetener.setVisible(true);
                botonCambiarTiempo.setVisible(true);

            }
        });
        panelVistaPrincipal.add(botonConfirmarCambios);
    }

    private void crearTextFields(){
        textMinutosConcentracion = sObjGraficos.construirJTextField(
                null,
                20,
                botonPlay.getY(),
                (panelVistaPrincipal.getWidth()-50)/2,
                50,
                sRecursos.getMonserratBold(15),
                Color.WHITE,
                Color.DARK_GRAY,
                sRecursos.getGRANATE(),
                sRecursos.getBordeGranate(),
                "c"
        );
        textMinutosConcentracion.setText("Minutos de concentración");

        textMinutosConcentracion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textMinutosConcentracion.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!comprobarTextoMinutos(textMinutosConcentracion.getText(), textMinutosConcentracion)){
                    textMinutosConcentracion.setText("Minutos de concentración");
                }
            }
        });
        textMinutosConcentracion.setVisible(false);
        panelVistaPrincipal.add(textMinutosConcentracion);

        textMinutosDescanso = sObjGraficos.construirJTextField(
                null,
                textMinutosConcentracion.getX()+textMinutosConcentracion.getWidth()+10,
                botonPlay.getY(),
                (panelVistaPrincipal.getWidth()-50)/2,
                50,
                sRecursos.getMonserratBold(15),
                Color.WHITE,
                Color.DARK_GRAY,
                sRecursos.getGRANATE(),
                sRecursos.getBordeGranate(),
                "c"
        );
        textMinutosDescanso.setText("Minutos de descanso");
        textMinutosDescanso.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textMinutosDescanso.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!comprobarTextoMinutos(textMinutosDescanso.getText(), textMinutosDescanso)){
                    textMinutosDescanso.setText("Minutos de descanso");
                }
            }
        });
        textMinutosDescanso.setVisible(false);
        panelVistaPrincipal.add(textMinutosDescanso);
    }

    /**
     * Método para comprobar si el texto introducido en el JTextField es un número.
     * @param text texto a comprobar
     * @param textField JTextField a comprobar
     * @return true si solo son números
     */
    private boolean comprobarTextoMinutos(String text, JTextField textField){
        if (text.isBlank()){
            return false;
        }
        for (int i=0; i<text.length(); i++){
            if (!Character.isDigit(text.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private void moverVentana(){
        panelSuperior.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX()+250;      // +250 porque es la posición x de panelSuperior
                mouseY = e.getY();
            }
        });

        panelSuperior.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getXOnScreen();
                y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });
    }

    public static void main(String[] args) {
        new TareasPomodoroTemporal();
    }
}
