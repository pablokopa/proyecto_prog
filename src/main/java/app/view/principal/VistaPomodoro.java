package app.view.principal;

import services.Recursos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VistaPomodoro extends JPanel {
    private final Recursos sRecursos = Recursos.getService();

    private JPanel panelBotones, panelBotonesReproductor, panelBotonesCambiarTiempos;
    private JLabel labelTiempoConcentracion, labelTiempoDescanso;
    private JButton botonPlay, botonPause, botonStop, botonCambiarTiempo, botonConfirmarCambios;
    private JTextField fieldCambiarConcentracion, fieldCambiarDescanso, fieldCambiarDescansoLargo;

    public VistaPomodoro() {
        this.setLayout(new GridBagLayout());

        construirPaneles();
        construirBotones();
    }

    private void construirPaneles(){
        JPanel panelTiempos = new JPanel();
        panelTiempos.setLayout(new BoxLayout(panelTiempos, BoxLayout.Y_AXIS));
//        panelTiempos.setBackground(sRecursos.getColorRojo());
//        panelTiempos.setBackground(sRecursos.getColorVerde());
        panelTiempos.setBackground(sRecursos.getColorAzul());
//        panelTiempos.setBackground(sRecursos.getColorAmarillo());
        panelTiempos.setPreferredSize(new Dimension(0, 0));
        this.add(panelTiempos, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

//        JPanel panelTiempoConcentracion = new JPanel();
//        panelTiempoConcentracion.setBackground(Color.cyan);
//        panelTiempoConcentracion.setPreferredSize(new Dimension(0, 0));
//        panelTiempos.add(panelTiempoConcentracion, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));
//
//        JPanel panelTiempoDescanso = new JPanel();
//        panelTiempoDescanso.setBackground(Color.yellow);
//        panelTiempoDescanso.setPreferredSize(new Dimension(0, 0));
//        panelTiempos.add(panelTiempoDescanso, setGbc(0, 1, 1, 0.7, GridBagConstraints.BOTH));

        this.labelTiempoConcentracion = new JLabel("25:00");
        labelTiempoConcentracion.setFont(sRecursos.getMontserratMedium(325));
        labelTiempoConcentracion.setForeground(new Color(0, 0, 0));
        labelTiempoConcentracion.setVerticalAlignment(SwingConstants.BOTTOM);
        labelTiempoConcentracion.setBorder(BorderFactory.createEmptyBorder(0, 0, -25, 0));
        labelTiempoConcentracion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTiempos.add(Box.createVerticalGlue());
        panelTiempos.add(labelTiempoConcentracion);

        this.labelTiempoDescanso = new JLabel("5:00");
        labelTiempoDescanso.setFont(sRecursos.getMontserratMedium(200));
        labelTiempoDescanso.setForeground(new Color(0, 0, 0));
        labelTiempoDescanso.setVerticalAlignment(SwingConstants.TOP);
        labelTiempoDescanso.setBorder(BorderFactory.createEmptyBorder(-50, 0, 0, 0));
        labelTiempoDescanso.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTiempos.add(labelTiempoDescanso);
        panelTiempos.add(Box.createVerticalGlue());

        this.panelBotones = new JPanel();
        panelBotones.setLayout(new GridBagLayout());
        panelBotones.setBackground(sRecursos.getBLANCO());
        panelBotones.setPreferredSize(new Dimension(0, 120));
        panelBotones.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, sRecursos.getGRANATE_MID_LIGHT()));
        this.add(panelBotones, setGbc(0, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        this.panelBotonesReproductor = new JPanel();
        panelBotonesReproductor.setBackground(sRecursos.getBLANCO());
        panelBotonesReproductor.setLayout(new GridBagLayout());
        panelBotonesReproductor.setPreferredSize(new Dimension(0, 0));
        panelBotonesReproductor.setBorder(BorderFactory.createEmptyBorder(6, 30, 3, 30));
        panelBotones.add(panelBotonesReproductor, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.panelBotonesCambiarTiempos = new JPanel();
        panelBotonesCambiarTiempos.setLayout(new GridBagLayout());
        panelBotonesCambiarTiempos.setBackground(sRecursos.getBLANCO());
        panelBotonesCambiarTiempos.setPreferredSize(new Dimension(0, 0));
        panelBotonesCambiarTiempos.setBorder(BorderFactory.createEmptyBorder(3, 30, 5, 30));
        panelBotones.add(panelBotonesCambiarTiempos, setGbc(0, 1, 1, 1, GridBagConstraints.BOTH));
    }

    private void construirBotones(){
        Border borderFuera;
        Border borderDentro;

        this.botonPlay = new JButton();
        botonPlay.setIcon(sRecursos.getImagenPlay());
//        botonPlay.setBackground(sRecursos.getGRANATE_MID_LIGHT());
        botonPlay.setBackground(new Color(0, 0, 0));
        botonPlay.setPreferredSize(new Dimension(0, 0));
        botonPlay.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, sRecursos.getGRIS_DEFAULT()));
        botonPlay.setFocusable(false);
        botonPlay.setCursor(sRecursos.getCursorMano());
        panelBotonesReproductor.add(botonPlay, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.fieldCambiarConcentracion = new JTextField(" Concentración ");
        fieldCambiarConcentracion.setFont(sRecursos.getMontserratBold(18));
        fieldCambiarConcentracion.setBackground(sRecursos.getGRIS_DEFAULT());
        borderFuera = BorderFactory.createEmptyBorder(0, 0, 0, 5);
        borderDentro = sRecursos.getBordeGranate();
        fieldCambiarConcentracion.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
        fieldCambiarConcentracion.setHorizontalAlignment(SwingConstants.CENTER);
        fieldCambiarConcentracion.setVisible(false);
        panelBotonesReproductor.add(fieldCambiarConcentracion, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonPause = new JButton();
        botonPause.setIcon(sRecursos.getImagenPause());
        botonPause.setBackground(sRecursos.getGRANATE_MID_LIGHT());
        botonPause.setPreferredSize(new Dimension(0, 0));
        botonPause.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, sRecursos.getGRIS_DEFAULT()));
        botonPause.setFocusable(false);
        botonPause.setCursor(sRecursos.getCursorMano());
        panelBotonesReproductor.add(botonPause, setGbc(1, 0, 1, 1, GridBagConstraints.BOTH));

        this.fieldCambiarDescanso = new JTextField("Descanso corto");
        fieldCambiarDescanso.setFont(sRecursos.getMontserratBold(18));
        fieldCambiarDescanso.setBackground(sRecursos.getGRIS_DEFAULT());
        borderFuera = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        borderDentro = sRecursos.getBordeGranate();
        fieldCambiarDescanso.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
        fieldCambiarDescanso.setHorizontalAlignment(SwingConstants.CENTER);
        fieldCambiarDescanso.setVisible(false);
        panelBotonesReproductor.add(fieldCambiarDescanso, setGbc(1, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonStop = new JButton();
        botonStop.setIcon(sRecursos.getImagenStop());
        botonStop.setBackground(sRecursos.getGRANATE_MID_LIGHT());
        botonStop.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, sRecursos.getGRIS_DEFAULT()));
        botonStop.setPreferredSize(new Dimension(0, 0));
        botonStop.setFocusable(false);
        botonStop.setCursor(sRecursos.getCursorMano());
        panelBotonesReproductor.add(botonStop, setGbc(2, 0, 1, 1, GridBagConstraints.BOTH));

        this.fieldCambiarDescansoLargo = new JTextField("Descanso largo");
        fieldCambiarDescansoLargo.setFont(sRecursos.getMontserratBold(18));
        fieldCambiarDescansoLargo.setBackground(sRecursos.getGRIS_DEFAULT());
        borderFuera = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        borderDentro = sRecursos.getBordeGranate();
        fieldCambiarDescansoLargo.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
        fieldCambiarDescansoLargo.setHorizontalAlignment(SwingConstants.CENTER);
        fieldCambiarDescansoLargo.setVisible(false);
        panelBotonesReproductor.add(fieldCambiarDescansoLargo, setGbc(2, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonCambiarTiempo = new JButton();
        botonCambiarTiempo.setText("Cambiar los parámetros");
        botonCambiarTiempo.setFont(sRecursos.getMontserratBold(18));
        botonCambiarTiempo.setBackground(sRecursos.getGRANATE_MID_LIGHT());
        botonCambiarTiempo.setForeground(sRecursos.getBLANCO());
        botonCambiarTiempo.setFocusable(false);
        botonCambiarTiempo.setCursor(sRecursos.getCursorMano());
        panelBotonesCambiarTiempos.add(botonCambiarTiempo, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        this.botonConfirmarCambios = new JButton();
        botonConfirmarCambios.setText("Confirmar cambios");
        botonConfirmarCambios.setFont(sRecursos.getMontserratBold(18));
        botonConfirmarCambios.setBackground(sRecursos.getGRANATE_MID_LIGHT());
        botonConfirmarCambios.setForeground(sRecursos.getBLANCO());
        botonConfirmarCambios.setFocusable(false);
        botonConfirmarCambios.setVisible(false);
        botonConfirmarCambios.setCursor(sRecursos.getCursorMano());
        panelBotonesCambiarTiempos.add(botonConfirmarCambios, setGbc(0, 0, 1, 1, GridBagConstraints.BOTH));

        fieldCambiarConcentracion.addFocusListener(listenerComprobarText(fieldCambiarConcentracion));
        fieldCambiarDescanso.addFocusListener(listenerComprobarText(fieldCambiarDescanso));
        fieldCambiarDescansoLargo.addFocusListener(listenerComprobarText(fieldCambiarDescansoLargo));
        botonCambiarTiempo.addActionListener(e -> cambiarVisibles());
        botonConfirmarCambios.addActionListener(e -> {
            String tiempoConcentracion = fieldCambiarConcentracion.getText();
            String tiempoDescanso = fieldCambiarDescanso.getText();
            String tiempoDescansoLargo = fieldCambiarDescansoLargo.getText();

            if (comprobarTextoMinutos(tiempoConcentracion)){
                tiempoConcentracion = "";
            }
            if (comprobarTextoMinutos(tiempoDescanso)){
                tiempoDescanso = "";
            }
            if (!tiempoConcentracion.isBlank()){
                labelTiempoConcentracion.setText(tiempoConcentracion+":00");
            }
            if (!tiempoDescanso.isBlank()){
                labelTiempoDescanso.setText(tiempoDescanso+":00");
            }

            fieldCambiarConcentracion.setText(" Concentración ");
            fieldCambiarDescanso.setText("Descanso corto");
            fieldCambiarDescansoLargo.setText("Descanso largo");
            cambiarVisibles();
        });
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


}