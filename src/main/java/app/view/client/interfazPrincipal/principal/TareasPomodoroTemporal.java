package app.view.client.interfazPrincipal.principal;

import app.view.services.ObjGraficos;
import app.view.services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TareasPomodoroTemporal extends JFrame {
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;

    private int x, y, mouseX, mouseY;

    private JPanel panelOpciones, panelVistaPrincipal, panelSuperior;
    private JLabel labelCerrarVentana, labelTiempoConcentracion, labelTiempoDescanso;

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
        labelCerrarVentana = sObjGraficos.construirJLabel(null,panelVistaPrincipal.getWidth()-50, 5, 40, 40, sRecursos.getCursorMano(), sRecursos.getImagenCerrar(), null, null, null, null, "r");
        labelCerrarVentana.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        panelSuperior.add(labelCerrarVentana);

        labelTiempoConcentracion = sObjGraficos.construirJLabel(
                "25:00",
                10,
                40,
                panelVistaPrincipal.getWidth()-20,
                (panelVistaPrincipal.getHeight()/4)-20,
                null,
                null,
                sRecursos.getMonserratBold(100),
                sRecursos.getGRIS_CLARO(),
                Color.BLACK,
                null,
                "t"
        );
        panelVistaPrincipal.add(labelTiempoConcentracion);

        labelTiempoDescanso = sObjGraficos.construirJLabel(
                "5:00",
                10,
                labelTiempoConcentracion.getHeight()+40,
                panelVistaPrincipal.getWidth()-20,
                (panelVistaPrincipal.getHeight()/4)-30,
                null,
                null,
                sRecursos.getMonserratBold(50),
                sRecursos.getGRIS_CLARO(),
                Color.BLACK,
                sRecursos.getBordeGranate(),
                "t"
        );
        panelVistaPrincipal.add(labelTiempoDescanso);
    }

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
        new TareasPomodoroTemporal();
    }
}
