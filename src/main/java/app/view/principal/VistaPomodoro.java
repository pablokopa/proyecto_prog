package app.view.principal;

import services.Recursos;

import javax.swing.*;
import java.awt.*;

public class VistaPomodoro extends JPanel{
    private final Recursos sRecursos;

    private JPanel panelTiempos, panelBotones, panelTiempoConcentracion, panelTiempoDescanso;
    private JLabel labelTiempoConcentracion, labelTiempoDescanso;
    private JButton botonPlay, botonPause, botonStop, botonCambiarTiempo, botonConfirmarCambios;
    private JTextField textMinutosConcentracion, textMinutosDescanso;

    public VistaPomodoro(){
        this.sRecursos = Recursos.getService();
        setLayout(new BorderLayout());

        crearPaneles();
    }

    private void crearPaneles() {
        panelTiempos = new JPanel();
        panelTiempos.setBackground(Color.BLUE);
        panelTiempos.setLayout(new GridLayout(2, 1));

        panelTiempoConcentracion = new JPanel();
        panelTiempoConcentracion.setBackground(Color.GREEN);
        panelTiempos.add(panelTiempoConcentracion);

        panelTiempoDescanso = new JPanel();
        panelTiempoDescanso.setBackground(Color.YELLOW);
        panelTiempos.add(panelTiempoDescanso);

        panelTiempos.add(panelTiempoConcentracion);
        panelTiempos.add(panelTiempoDescanso);
        add(panelTiempos, BorderLayout.CENTER);


        panelBotones = new JPanel();
        panelBotones.setBackground(Color.RED);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
