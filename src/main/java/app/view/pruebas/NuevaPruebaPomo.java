package app.view.pruebas;

import app.view.principal.InterfazFija;
import services.ObjGraficos;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class NuevaPruebaPomo {
    private Recursos sRecursos;

    private InterfazFija interfazFija;

    private JPanel panelPrincipal;
    private JPanel panelTiempos, panelBotones;
    private JLabel labelTiempoConcentracion, labelTiempoDescanso;
    private JButton botonPlay, botonPause, botonStop, botonCambiarTiempo, botonConfirmarCambios;
    private JTextField textMinutosConcentracion, textMinutosDescanso;

    NuevaPruebaPomo(InterfazFija interfazFija){
        this.sRecursos = Recursos.getService();
        this.interfazFija = interfazFija;
        panelPrincipal = interfazFija.getPanelPrincipal();
        panelPrincipal.setLayout(new BorderLayout());

        crearPaneles();
    }

    private void crearPaneles() {
        panelTiempos = new JPanel();
        panelTiempos.setBackground(Color.BLUE);
//        panelTiempos.setLayout(null);

        labelTiempoConcentracion = new JLabel("25:00");
        labelTiempoConcentracion.setFont(sRecursos.getMonserratBold(175));
        labelTiempoConcentracion.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        int anchoLetra = labelTiempoConcentracion.getFontMetrics(labelTiempoConcentracion.getFont()).stringWidth(labelTiempoConcentracion.getText());
                        int anchoPanel = panelTiempos.getWidth();

                        double ratio = (double)anchoPanel / (double)anchoLetra;

                        int newFontSize = (int)(labelTiempoConcentracion.getFont().getSize() * ratio);
                        labelTiempoConcentracion.setFont(sRecursos.getMonserratBold(newFontSize));

                    }
                });


            }
        });

        panelTiempos.add(labelTiempoConcentracion);

        panelPrincipal.add(panelTiempos, BorderLayout.CENTER);

        panelBotones = new JPanel();
        panelBotones.setBackground(Color.RED);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
    }

    private void crearLabels() {

    }

    private void crearBotones() {

    }

    private void crearTextFields() {

    }

    public static void main(String[] args) {
        new NuevaPruebaPomo(new InterfazFija());
    }
}
