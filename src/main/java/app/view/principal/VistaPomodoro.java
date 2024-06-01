package app.view.principal;

import services.Recursos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VistaPomodoro extends JPanel {
    private final Recursos sRecursos = Recursos.getService();

    private Timer timer;

    private int cantidadDescansosCortos = 0;
    private int limiteDescansosCortos;
    private int tiempoIntermedio;

    private int tiempoConcentracion, tiempoDescanso, tiempoDescansoLargo, tiempoRestante;

    private JPanel panelTiempos, panelBotones, panelBotonesReproductor, panelBotonesCambiarTiempos;
    private JLabel labelTiempoConcentracion, labelTiempoDescanso;
    private JButton botonPlay, botonPause, botonStop, botonCambiarTiempo, botonConfirmarCambios;
    private JTextField fieldCambiarConcentracion, fieldCambiarDescanso, fieldCambiarDescansoLargo;

    public VistaPomodoro() {
        this.setLayout(new GridBagLayout());

        this.limiteDescansosCortos = 3;
        this.tiempoIntermedio = 5;

        this.tiempoConcentracion = 25;
        this.tiempoDescanso = 5;
        this.tiempoDescansoLargo = 15;

        crearPanelTiempos();
        crearPanelBotones();
    }

    private void crearPanelTiempos(){
        this.panelTiempos = new JPanel();
        panelTiempos.setLayout(new BoxLayout(panelTiempos, BoxLayout.Y_AXIS));
        panelTiempos.setBackground(sRecursos.getGRIS_DEFAULT());
        panelTiempos.setPreferredSize(new Dimension(0, 0));
        this.add(panelTiempos, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.labelTiempoConcentracion = construirLabelTiempo(tiempoConcentracion+":00", 325, SwingConstants.BOTTOM);
        labelTiempoConcentracion.setBorder(BorderFactory.createEmptyBorder(0, 0, -25, 0));

        this.labelTiempoDescanso = construirLabelTiempo(tiempoDescanso+":00", 200, SwingConstants.TOP);
        labelTiempoDescanso.setBorder(BorderFactory.createEmptyBorder(-50, 0, 0, 0));

        panelTiempos.add(Box.createVerticalGlue());
        panelTiempos.add(labelTiempoConcentracion);
        panelTiempos.add(labelTiempoDescanso);
        panelTiempos.add(Box.createVerticalGlue());
    }

    private void crearPanelBotones(){
        this.panelBotones = new JPanel();
        panelBotones.setLayout(new GridBagLayout());
        panelBotones.setBackground(sRecursos.getBLANCO());
        panelBotones.setPreferredSize(new Dimension(0, 120));
        panelBotones.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, sRecursos.getGRANATE_MID_LIGHT()));
        this.add(panelBotones, setGbc(0, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        this.panelBotonesReproductor = new JPanel();
        panelBotonesReproductor.setLayout(new GridBagLayout());
        panelBotonesReproductor.setBackground(sRecursos.getBLANCO());
        panelBotonesReproductor.setPreferredSize(new Dimension(0, 0));
        panelBotonesReproductor.setBorder(BorderFactory.createEmptyBorder(6, 30, 3, 30));
        panelBotones.add(panelBotonesReproductor, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.panelBotonesCambiarTiempos = new JPanel();
        panelBotonesCambiarTiempos.setLayout(new GridBagLayout());
        panelBotonesCambiarTiempos.setBackground(sRecursos.getBLANCO());
        panelBotonesCambiarTiempos.setPreferredSize(new Dimension(0, 0));
        panelBotonesCambiarTiempos.setBorder(BorderFactory.createEmptyBorder(3, 30, 5, 30));
        panelBotones.add(panelBotonesCambiarTiempos, setGbc(0, 1, 1, 1, GridBagConstraints.BOTH));

        Border borderFuera;
        Border borderDentro;

        this.botonPlay = construirBotonReproductor(sRecursos.getImagenPlay());
        botonPlay.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, sRecursos.getBLANCO()));
        panelBotonesReproductor.add(botonPlay, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonPause = construirBotonReproductor(sRecursos.getImagenPause());
        botonPause.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, sRecursos.getBLANCO()));
        panelBotonesReproductor.add(botonPause, setGbc(1, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonStop = construirBotonReproductor(sRecursos.getImagenStop());
        botonStop.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, sRecursos.getBLANCO()));
        panelBotonesReproductor.add(botonStop, setGbc(2, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonCambiarTiempo = construirBotonParametros("Cambiar los parámetros");
        panelBotonesCambiarTiempos.add(botonCambiarTiempo, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonConfirmarCambios = construirBotonParametros("Confirmar cambios");
        botonConfirmarCambios.setVisible(false);
        panelBotonesCambiarTiempos.add(botonConfirmarCambios, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.fieldCambiarConcentracion = construirFieldParametros(" Concentración ");
        borderFuera = BorderFactory.createEmptyBorder(0, 0, 0, 5);
        borderDentro = sRecursos.getBordeGranate();
        fieldCambiarConcentracion.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
        panelBotonesReproductor.add(fieldCambiarConcentracion, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.fieldCambiarDescanso = construirFieldParametros("Descanso corto");
        borderFuera = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        borderDentro = sRecursos.getBordeGranate();
        fieldCambiarDescanso.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
        panelBotonesReproductor.add(fieldCambiarDescanso, setGbc(1, 0, 1, 1, GridBagConstraints.BOTH));

        this.fieldCambiarDescansoLargo = construirFieldParametros("Descanso largo");
        borderFuera = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        borderDentro = sRecursos.getBordeGranate();
        fieldCambiarDescansoLargo.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
        panelBotonesReproductor.add(fieldCambiarDescansoLargo, setGbc(2, 0, 1, 1, GridBagConstraints.BOTH));

        fieldCambiarConcentracion.addFocusListener(listenerComprobarText(fieldCambiarConcentracion));
        fieldCambiarDescanso.addFocusListener(listenerComprobarText(fieldCambiarDescanso));
        fieldCambiarDescansoLargo.addFocusListener(listenerComprobarText(fieldCambiarDescansoLargo));

        botonCambiarTiempo.addActionListener(e -> {
            if (timer != null){
                return;
            }
            cambiarVisibles();

            fieldCambiarConcentracion.setText(" Concentración ");
            fieldCambiarDescanso.setText("Descanso corto");
            fieldCambiarDescansoLargo.setText("Descanso largo");
        });

        botonConfirmarCambios.addActionListener(e -> {
            String textoConcentracion = fieldCambiarConcentracion.getText();
            String textoDescanso = fieldCambiarDescanso.getText();
            String textoDescansoLargo = fieldCambiarDescansoLargo.getText();

            if (comprobarTextoMinutos(textoConcentracion)){
                textoConcentracion = "";
            }
            if (!textoConcentracion.isBlank()){
                tiempoConcentracion = Integer.parseInt(textoConcentracion);
                labelTiempoConcentracion.setText(tiempoConcentracion+":00");
            }

            if (comprobarTextoMinutos(textoDescanso)){
                textoDescanso = "";
            }
            if (!textoDescanso.isBlank()){
                tiempoDescanso = Integer.parseInt(textoDescanso);
                labelTiempoDescanso.setText(tiempoDescanso+":00");
            }

            if (comprobarTextoMinutos(textoDescansoLargo)){
                textoDescansoLargo = "";
            }
            if (!textoDescansoLargo.isBlank()){
                tiempoDescansoLargo = Integer.parseInt(textoDescansoLargo);
            }

            cambiarVisibles();
        });

        botonPlay.addActionListener(e -> {
            if (timer == null){
                crearTimerConcentracion(tiempoConcentracion);
            } else {
                timer.start();
            }
        });

        botonPause.addActionListener(e -> {
            if (timer != null){
                timer.stop();
            }
        });

        botonStop.addActionListener(e -> {
            if (timer != null){
                timer.stop();
                timer = null;
                labelTiempoConcentracion.setText(String.format("%d:%02d", tiempoConcentracion, 0));
                labelTiempoDescanso.setText(String.format("%d:%02d", tiempoDescanso, 0));
                panelTiempos.setBackground(sRecursos.getGRIS_DEFAULT());
            }
        });
    }

    private void crearTimerIntermedio(String nuevoTimer){
        lanzarAlerta(nuevoTimer);
        timer = new Timer(1000, e -> {
            System.out.println(tiempoIntermedio);
            tiempoIntermedio--;
            if (tiempoIntermedio==0){
                tiempoIntermedio = 5;
                timer.stop();
                timer = null;
                switch (nuevoTimer){
                    case "concentración" -> crearTimerConcentracion(tiempoConcentracion);
                    case "descanso" -> crearTimerDescanso(tiempoDescanso);
                    case "descanso largo" -> crearTimerDescansoLargo(tiempoDescansoLargo);
                }
            }
        });
        timer.start();
    }

    private void crearTimerConcentracion(int tiempo){
        if (timer != null){
            return;
        }
        panelTiempos.setBackground(sRecursos.getColorRojo());
        tiempoRestante = tiempo*60;
        timer = new Timer(1000, e -> {
            tiempoRestante--;
            if (tiempoRestante>=0){
                int minutos = tiempoRestante/60;
                int segundos = tiempoRestante%60;
                labelTiempoConcentracion.setText(String.format("%d:%02d", minutos, segundos));
            } else {
                labelTiempoConcentracion.setText(String.format("%d:%02d", tiempoConcentracion, 0));
                timer.stop();
                timer = null;
                if (cantidadDescansosCortos<limiteDescansosCortos){
                    cantidadDescansosCortos++;
                    crearTimerIntermedio("descanso");
                } else {
                    cantidadDescansosCortos = 0;
                    crearTimerIntermedio("descanso largo");
                }

            }
        });
        timer.start();
    }

    private void crearTimerDescanso(int tiempo){
        if (timer != null){
            return;
        }
        panelTiempos.setBackground(sRecursos.getColorVerde());
        tiempoRestante = tiempo*60;
        timer = new Timer(1000, e -> {
            tiempoRestante--;
            if (tiempoRestante>=0){
                int minutos = tiempoRestante/60;
                int segundos = tiempoRestante%60;
                labelTiempoDescanso.setText(String.format("%d:%02d", minutos, segundos));
            } else {
                if (cantidadDescansosCortos == limiteDescansosCortos){
                    labelTiempoDescanso.setText(String.format("%d:%02d", tiempoDescansoLargo, 0));
                } else {
                    labelTiempoDescanso.setText(String.format("%d:%02d", tiempoDescanso, 0));
                }
                timer.stop();
                timer = null;
                crearTimerIntermedio("concentración");
            }
        });
        timer.start();
    }

    private void crearTimerDescansoLargo(int tiempo){
        if (timer != null){
            return;
        }
        panelTiempos.setBackground(sRecursos.getColorAzul());
        tiempoRestante = tiempo*60;
        timer = new Timer(1000, e -> {
            tiempoRestante--;
            if (tiempoRestante>=0){
                int minutos = tiempoRestante/60;
                int segundos = tiempoRestante%60;
                labelTiempoDescanso.setText(String.format("%d:%02d", minutos, segundos));
            } else {
                labelTiempoDescanso.setText(String.format("%d:%02d", tiempoDescanso, 0));
                timer.stop();
                timer = null;
                crearTimerIntermedio("concentración");
            }
        });
        timer.start();
    }

    private void lanzarAlerta(String nuevoTimer){
        Toolkit.getDefaultToolkit().beep();
        if (!SystemTray.isSupported()){
            System.out.println("No se pueden lanzar notificaciones");
            return;
        }
        try {
            TrayIcon trayIcon = new TrayIcon(sRecursos.getImagenLogo2().getImage(), "Alerta de pomodoro");
            SystemTray tray = SystemTray.getSystemTray();
            tray.add(trayIcon);
            trayIcon.displayMessage("Alerta de pomodoro", "Ha empezado el tiempo de "+ nuevoTimer, TrayIcon.MessageType.INFO);

            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        tray.remove(trayIcon);
                    }
                }, 5000
            );


        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private FocusAdapter listenerComprobarText(JTextField textField) {
        String titulo = textField.getText();
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (comprobarTextoMinutos(textField.getText())){
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (comprobarTextoMinutos(textField.getText())){
                    textField.setText(titulo);
                }
            }
        };
    }

    private boolean comprobarTextoMinutos(String text){
        if (text.isBlank()){
            return true;
        }
        for (int i=0; i<text.length(); i++){
            if (!Character.isDigit(text.charAt(i))){
                return true;
            }
        }
        return false;
    }

    private void cambiarVisibles() {
        botonPlay.setVisible(!botonPlay.isVisible());
        botonPause.setVisible(!botonPause.isVisible());
        botonStop.setVisible(!botonStop.isVisible());

        botonCambiarTiempo.setVisible(!botonCambiarTiempo.isVisible());
        botonConfirmarCambios.setVisible(!botonConfirmarCambios.isVisible());

        fieldCambiarConcentracion.setVisible(!fieldCambiarConcentracion.isVisible());
        fieldCambiarDescanso.setVisible(!fieldCambiarDescanso.isVisible());
        fieldCambiarDescansoLargo.setVisible(!fieldCambiarDescansoLargo.isVisible());
    }

    private GridBagConstraints setGbc(int gridx, int gridy, double weightx, double weighty, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;
        return gbc;
    }

    private JLabel construirLabelTiempo(String texto, int sizeLetra, int posicionVertical){
        JLabel label = new JLabel(texto);
        label.setFont(sRecursos.getMontserratBold(sizeLetra));
        label.setForeground(new Color(30, 30, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(posicionVertical);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton construirBotonReproductor(ImageIcon icono){
        JButton boton = new JButton();
        boton.setIcon(icono);
        boton.setBackground(new Color(30, 30, 30));
        boton.setPreferredSize(new Dimension(0, 0));
        boton.setFocusable(false);
        boton.setCursor(sRecursos.getCursorMano());
        return boton;
    }

    private JButton construirBotonParametros(String texto){
        JButton boton = new JButton();
        boton.setText(texto);
        boton.setFont(sRecursos.getMontserratBold(18));
        boton.setBackground(sRecursos.getGRANATE_MID_LIGHT());
        boton.setForeground(sRecursos.getBLANCO());
        boton.setFocusable(false);
        boton.setCursor(sRecursos.getCursorMano());
        return boton;
    }

    private JTextField construirFieldParametros(String texto){
        JTextField field = new JTextField(texto);
        field.setFont(sRecursos.getMontserratBold(18));
        field.setBackground(sRecursos.getGRIS_DEFAULT());
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setVisible(false);
        return field;
    }
}