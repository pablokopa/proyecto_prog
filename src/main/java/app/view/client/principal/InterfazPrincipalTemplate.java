package app.view.client.principal;

import app.view.services.ObjGraficos;
import app.view.services.Recursos;

import javax.swing.*;

public class InterfazPrincipalTemplate extends JFrame {
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;

    private JPanel panelNavegacion, panelBarra, panelPrincipal;


    public InterfazPrincipalTemplate(){
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        this.crearJPanels();

        /* Configuración de la ventana */
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(this);
        setLayout(null);
        setUndecorated(true);
        setIconImage(sRecursos.getImagenLogo2().getImage());
        setVisible(true);
    }

    public void crearJPanels(){
        panelNavegacion = sObjGraficos.construirJPanel(0, 0, 250, 700,sRecursos.getGRANATE(), null);
        this.add(panelNavegacion);

        panelBarra = sObjGraficos.construirJPanel(250, 0, 850, 50, sRecursos.getBLANCO(), null);
        this.add(panelBarra);

        panelPrincipal = sObjGraficos.construirJPanel(250, 50, 850, 600, sRecursos.getGRIS_CLARO(), null);
        this.add(panelPrincipal);
    }
}
