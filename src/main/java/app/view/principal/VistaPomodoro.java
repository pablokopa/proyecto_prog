package app.view.principal;

import services.Recursos;

import javax.swing.*;
import java.awt.*;

public class VistaPomodoro extends JPanel{
    private final Recursos sRecursos;

    private JPanel panelTiempos, panelBotones, conjuntoBotones;
    private JLabel labelTiempo;
    private JButton btnIniciar, btnPausar, btnReiniciar;

    public VistaPomodoro(){
        this.sRecursos = Recursos.getService();
        setLayout(new GridLayout(2, 1));

        crearPaneles();
    }

    public void personalizarBotones(JButton boton){
        boton.setPreferredSize(new Dimension(300,80));
        boton.setFont(sRecursos.getMontserratBold(20));
        boton.setBackground(sRecursos.getGRANATE());
        boton.setForeground(Color.WHITE);
    }

    private void crearPaneles() {
/* PANEL TIEMPO */
        panelTiempos = new JPanel();
        labelTiempo = new JLabel("25:00");

        // Layouts
        panelTiempos.setLayout(new BorderLayout());

        // Personalización panel
        panelTiempos.setBackground(Color.WHITE);

        // Personalización label
        labelTiempo.setFont(sRecursos.getMontserratBold(300));
        labelTiempo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTiempo.setForeground(sRecursos.getGRANATE());

        // Añadir elementos
        panelTiempos.add(labelTiempo, BorderLayout.SOUTH);
        add(panelTiempos, BorderLayout.CENTER);

/* PANEL BOTONES */
        panelBotones = new JPanel(new BorderLayout());
        conjuntoBotones = new JPanel();
        btnIniciar = new JButton("Iniciar");
        btnPausar = new JButton("Pausar");
        btnReiniciar = new JButton("Reiniciar");

        // Layouts
        conjuntoBotones.setLayout(new BoxLayout(conjuntoBotones, BoxLayout.X_AXIS));

        // Personalización paneles
        panelBotones.setBackground(Color.LIGHT_GRAY);
        conjuntoBotones.setBackground(Color.WHITE);

        // Personalización botones
        personalizarBotones(btnIniciar);
        personalizarBotones(btnPausar);
        personalizarBotones(btnReiniciar);

        // Añadir elementos
        conjuntoBotones.add(Box.createHorizontalGlue());
        conjuntoBotones.add(btnIniciar);
        conjuntoBotones.add(Box.createHorizontalStrut(40)); // Espacio entre botones
        conjuntoBotones.add(btnPausar);
        conjuntoBotones.add(Box.createHorizontalStrut(40));
        conjuntoBotones.add(btnReiniciar);
        conjuntoBotones.add(Box.createHorizontalGlue());

        panelBotones.add(conjuntoBotones, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
