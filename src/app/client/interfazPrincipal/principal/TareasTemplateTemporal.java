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

    private int mouseX, x;
    private int mouseY, y;

    private JPanel panelOpciones, panelVistaPrincipal, panelSuperior;
    private JPanel panelVerTareas, panelInformacionExtra;

    private JLabel labelCerrarVentana;

    public TareasTemplateTemporal () {
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        /* Configuración de la ventana */
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        setLayout(null);
        setUndecorated(true);

        /* Generar los componentes */
        crearPaneles();
        crearLabels();
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

        // [!!] Para cambiar el tamaño de los 2 siguientes paneles, simplemente cambia el valor +250 del ancho de panelVerTareas. (3er argumento que recibe, último número)

        /* Panel donde estarán las tareas */
        panelVerTareas = sObjGraficos.construirJPanel(10,10, panelVistaPrincipal.getWidth()-(20+250), panelVistaPrincipal.getHeight()-20, sRecursos.getGRANATE(), null);
        panelVistaPrincipal.add(panelVerTareas);

        /* Panel donde estará información de la tarea seleccionada */
        panelInformacionExtra = sObjGraficos.construirJPanel(panelVerTareas.getWidth()+15, 10, panelVistaPrincipal.getWidth()-panelVerTareas.getWidth()-25, panelVistaPrincipal.getHeight()-20, sRecursos.getGRANATE(), null);
        panelVistaPrincipal.add(panelInformacionExtra);
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

    /**
     * Método para permitir el movimiento de la ventana.
     */
    public void moverVentana(){
        panelSuperior.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
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
        new TareasTemplateTemporal();
    }
}
