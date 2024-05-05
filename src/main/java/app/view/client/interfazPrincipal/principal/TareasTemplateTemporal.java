package app.view.client.interfazPrincipal.principal;

import app.view.services.ObjGraficos;
import app.view.services.Recursos;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TareasTemplateTemporal extends JFrame {
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;

    private int mouseX, x;
    private int mouseY, y;

    private JPanel panelOpciones, panelVistaPrincipal, panelSuperior;
    private JScrollPane panelVerTareas;
    private JPanel panelInformacionExtra;

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


        // [!!] Para cambiar el tamaño de los 2 siguientes paneles, simplemente cambiar el valor de sizeDerecha
        int sizeDerecha = 300;

        /* Panel donde estarán las tareas */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i=0; i<100; i++){
            JCheckBox checkBox = sObjGraficos.construirJCheckBox("Tarea "+i, 100, 100, sRecursos.getCursorMano(), sRecursos.getFontTArialDefault(13), sRecursos.getGRANATE());
            panel.add(checkBox);
        }

        /* ScrollPane donde se van a ver las tareas */
        panelVerTareas = sObjGraficos.construirPanelBarra(panel,10,10, panelVistaPrincipal.getWidth()-(20+sizeDerecha), panelVistaPrincipal.getHeight()-20, sRecursos.getGRANATE(), null);
        panelVistaPrincipal.add(panelVerTareas);

        /* Panel donde estará información de la tarea seleccionada */
        panelInformacionExtra = sObjGraficos.construirJPanel(panelVerTareas.getWidth()+15, 10, panelVistaPrincipal.getWidth()-panelVerTareas.getWidth()-25, panelVistaPrincipal.getHeight()-20, sRecursos.getGRANATE(), null);
        panelVistaPrincipal.add(panelInformacionExtra);

        add(panelVistaPrincipal);
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
     * Método para permitir el movimiento de la ventana desde panelSuperior.
     */
    public void moverVentana(){
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
        new TareasTemplateTemporal();
    }
}
