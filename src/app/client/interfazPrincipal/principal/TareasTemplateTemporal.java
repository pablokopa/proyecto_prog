package app.client.interfazPrincipal.principal;

import app.services.ObjGraficos;
import app.services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TareasTemplateTemporal extends JFrame {
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;



    private JPanel panelOpciones, panelVistaPrincipal, panelSuperior;
    private JPanel panelVerTareas, panelInformacionExtra;

    private JLabel labelCerrarVentana;

    public TareasTemplateTemporal () {
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        setSize(1100, 650);

        crearPaneles();
        crearLabels();

        /* Configuración de la ventana */

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        setLayout(null);
        setUndecorated(true);
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

        panelVerTareas = sObjGraficos.construirJPanel(10,10, panelVistaPrincipal.getWidth()-(20+230), panelVistaPrincipal.getHeight()-20, sRecursos.getGRANATE(), sRecursos.getBordeGranate());
//        panelInformacionExtra = sObjGraficos.construirJPanel()
        panelVistaPrincipal.add(panelVerTareas);
        add(panelVistaPrincipal);

        /* Panel ver tareas */

    }

    private void crearLabels(){
        /* Label cerrar ventana */
        labelCerrarVentana = sObjGraficos.construirJLabel(null,panelVistaPrincipal.getWidth()-50, 5, 40, 40, sRecursos.getCursorMano(), sRecursos.getImagenCerrar(), null, null, null, null, "r");
        labelCerrarVentana.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        panelSuperior.add(labelCerrarVentana);
    }

    public static void main(String[] args) {
        new TareasTemplateTemporal();
    }
}
