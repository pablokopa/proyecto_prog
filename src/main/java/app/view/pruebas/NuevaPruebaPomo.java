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
        panelPrincipal.setBackground(Color.YELLOW);
        interfazFija.getPanelPrincipal().setBackground(Color.YELLOW);

        crearPaneles();
//        redimension();

//        interfazFija.revalidate();
    }

    private void crearPaneles() {
        panelTiempos = new JPanel();
//        panelTiempos.setBackground(Color.BLUE);

        labelTiempoConcentracion = new JLabel("25:00");
        labelTiempoConcentracion.setBackground(Color.GREEN);
        labelTiempoConcentracion.setOpaque(true);
        labelTiempoConcentracion.setPreferredSize(new Dimension(panelPrincipal.getWidth()+100, panelPrincipal.getHeight()));
        System.out.println("Panel principal: "+panelPrincipal.getWidth()+" "+panelPrincipal.getHeight());
        System.out.println("panel interfaz: "+interfazFija.getPanelPrincipal().getWidth()+" "+interfazFija.getPanelPrincipal().getHeight());
        labelTiempoConcentracion.setFont(sRecursos.getMonserratBold(175));

        /*labelTiempoConcentracion.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        interfazFija.redimensionarPaneles();

                        int anchoLetra = labelTiempoConcentracion.getFontMetrics(labelTiempoConcentracion.getFont()).stringWidth(labelTiempoConcentracion.getText());
                        int anchoPanel = panelTiempos.getWidth();

                        double ratio = (double)anchoPanel / (double)anchoLetra;

                        int newFontSize = (int)(labelTiempoConcentracion.getFont().getSize() * ratio / 2);
                        labelTiempoConcentracion.setFont(sRecursos.getMonserratBold(newFontSize));

                    }
                });


            }
        });*/



        panelTiempos.add(labelTiempoConcentracion);

        panelPrincipal.add(panelTiempos, BorderLayout.CENTER);

        panelBotones = new JPanel();
        panelBotones.setBackground(Color.RED);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
    }

 /*   public void redimension() {
        labelTiempoConcentracion.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
//                        labelTiempoConcentracion.setSize(new Dimension((int)(panelPrincipal.getWidth()*0.80), panelPrincipal.getHeight()));

//                        interfazFija.revalidate();
                    }
                });
            }
        });
    }*/

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
