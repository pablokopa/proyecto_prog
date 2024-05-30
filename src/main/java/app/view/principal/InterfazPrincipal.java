package app.view.principal;

import app.controller.ControladorTareas;
import app.controller.ControladorUsuarios;
import app.model.tareas.Tarea;
import app.model.usuarios.GestorUsuarios;
import app.model.usuarios.Usuario;
import app.view.login.InterfazLogin;
import app.view.templates.TemplatePanelMatrix;
import app.view.templates.TemplatePanelTareas;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Interfaz fija de la aplicación. Contiene el menú principal y la barra supeior.
 * En ella aparecen el resto de las vistas del programa.
 */
public class InterfazPrincipal extends JFrame {
    private final Recursos sRecursos;

    private int xRelativoFrame, yRelativoFrame, xRelativoPantalla, yRelativoPantalla;

    private JPanel panelMenu;
    private JPanel panelSuperior;
    private JPanel panelPrincipal;
    private JButton botonInicio, botonAjustes, botonCerrarSesion, botonTareas, botonPomodoro, botonMatrix;
    private VistaTareas vistaTareas;
    private VistaMatrix vistaMatrix;

    private CardLayout cardLayout;
    private ArrayList<JButton> listaBotonesMenu;
    private final Dimension dimensionPantallaCompleta, dimensionPantallaNormal;

    private String textoBotonActual = "";

    private final ArrayList<TemplatePanelTareas> listaTareasToDo, listaTareasCompletadas;
    private final ArrayList<TemplatePanelMatrix> listaTareasArribaI, listaTareasArribaD, listaTareasAbajoI, listaTareasAbajoD;

    private final ControladorTareas controladorTareas;

    /**
     * Constructor de la clase InterfazPrincipal.
     * @param controladorTareas Controlador de tareas
     */
    public InterfazPrincipal(ControladorTareas controladorTareas) {
        this.sRecursos = Recursos.getService();

        this.controladorTareas = controladorTareas;

        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        dimensionPantallaCompleta = new Dimension(screenSize.width, screenSize.height-1);
        dimensionPantallaNormal = new Dimension(1400, 750);


        this.listaTareasToDo = new ArrayList<>();
        this.listaTareasCompletadas = new ArrayList<>();
        this.listaTareasArribaI = new ArrayList<>();
        this.listaTareasArribaD = new ArrayList<>();
        this.listaTareasAbajoI = new ArrayList<>();
        this.listaTareasAbajoD = new ArrayList<>();

        this.setLayout(new BorderLayout());
        this.setSize(dimensionPantallaNormal);              // Debe empezar en dimension pequeña para evitar errores y posteriormente redimensionar a completa
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setIconImage(sRecursos.getImagenLogo2().getImage());


        crearPaneles();
        crearBotonesVentana();
        crearBotonesMenu();
        botonesActionListener();
        botonesChangeListener();

        redimensionarPaneles();
        moverVentana();

        addTareasAListas();
        addTareasAPaneles();


        this.setVisible(true);

        setSize(dimensionPantallaCompleta);     // Línea necesaria para redimensionar sin errores
    }

    /**
     * Añade la tarea a la columna de tareas por hacer.
     * @param tarea
     */
    public void addAColumnaToDo(Tarea tarea){
        TemplatePanelTareas panelTarea = new TemplatePanelTareas(tarea, controladorTareas, this);
        vistaTareas.getPanelListaTareasToDo().add(panelTarea);
        listaTareasToDo.add(panelTarea);
    }

    /**
     * Añade la tarea a la columna de tareas completadas.
     * @param tarea
     */
    public void addAColumnaCompletada(Tarea tarea){
        TemplatePanelTareas panelTarea = new TemplatePanelTareas(tarea, controladorTareas, this);
        vistaTareas.getPanelListaTareasCompletadas().add(panelTarea);
        listaTareasCompletadas.add(panelTarea);
    }

    /**
     * Elimina la tarea de la columna de tareas por hacer.
     * @param tarea
     */
    public void removeDeColumnaToDo(Tarea tarea){
        TemplatePanelTareas panelTarea = listaTareasToDo.get(listaTareasToDo.indexOf(new TemplatePanelTareas(tarea, controladorTareas, this)));
        vistaTareas.getPanelListaTareasToDo().remove(panelTarea);
        listaTareasToDo.remove(panelTarea);
    }

    /**
     * Elimina la tarea de la columna de tareas completadas.
     * @param tarea
     */
    public void removeDeColumnaCompletada(Tarea tarea){
        TemplatePanelTareas panelTarea = listaTareasCompletadas.get(listaTareasCompletadas.indexOf(new TemplatePanelTareas(tarea, controladorTareas, this)));
        vistaTareas.getPanelListaTareasCompletadas().remove(panelTarea);
        listaTareasCompletadas.remove(panelTarea);
    }

    /**
     * Cambia la tarea de columna completada a la de tareas por hacer.
     * @param tarea
     */
    public void cambiarAColumnaToDo(Tarea tarea){
        removeDeColumnaCompletada(tarea);
        addAColumnaToDo(tarea);
    }

    /**
     * Cambia la tarea de columna de tareas por hacer a la de tareas completadas.
     * @param tarea
     */
    public void cambiarAColumnaCompletada(Tarea tarea){
        removeDeColumnaToDo(tarea);
        addAColumnaCompletada(tarea);
    }

    /**
     * Añade la tarea a la matriz de Eisenhower.
     */
    public void addEnMatrix(Tarea tarea){
        switch (tarea.getNombreE()){
            case "No importante / No urgente" -> addAPanelArribaI(tarea);
            case "No importante / Urgente" -> addAPanelArribaD(tarea);
            case "Importante / No urgente" -> addAPanelAbajoI(tarea);
            case "Importante / Urgente" -> addAPanelAbajoD(tarea);
        }
    }

    /**
     * Elimina la tarea de la matriz de Eisenhower.
     */
    public void eliminarEnMatrix(Tarea tarea){
        switch (tarea.getNombreE()){
            case "No importante / No urgente" -> removeDePanelArribaI(tarea);
            case "No importante / Urgente" -> removeDePanelArribaD(tarea);
            case "Importante / No urgente" -> removeDePanelAbajoI(tarea);
            case "Importante / Urgente" -> removeDePanelAbajoD(tarea);
        }
    }

    /**
     * Completa o descompleta la tarea en la matriz de Eisenhower.
     */
    public void completarEnMatrix(Tarea tarea){
        if (tarea.getCompletadaT()){
            eliminarEnMatrix(tarea);
        } else {
            addEnMatrix(tarea);
        }
    }

    /**
     * Añade la tarea al panel "No importante / No urgente" de la matriz de Eisenhower.
     */
    public void addAPanelArribaI(Tarea tarea){
        TemplatePanelMatrix panelTarea = new TemplatePanelMatrix(tarea, controladorTareas, this);
        vistaMatrix.getPanelTareasArribaI().add(panelTarea);
        listaTareasArribaI.add(panelTarea);
    }

    /**
     * Añade la tarea al panel "No importante / Urgente" de la matriz de Eisenhower.
     */
    public void addAPanelArribaD(Tarea tarea){
        TemplatePanelMatrix panelTarea = new TemplatePanelMatrix(tarea, controladorTareas, this);
        vistaMatrix.getPanelTareasArribaD().add(panelTarea);
        listaTareasArribaD.add(panelTarea);
    }

    /**
     * Añade la tarea al panel "Importante / No urgente" de la matriz de Eisenhower.
     */
    public void addAPanelAbajoI(Tarea tarea){
        TemplatePanelMatrix panelTarea = new TemplatePanelMatrix(tarea, controladorTareas, this);
        vistaMatrix.getPanelTareasAbajoI().add(panelTarea);
        listaTareasAbajoI.add(panelTarea);
    }

    /**
     * Añade la tarea al panel "Importante / Urgente" de la matriz de Eisenhower.
     */
    public void addAPanelAbajoD(Tarea tarea){
        TemplatePanelMatrix panelTarea = new TemplatePanelMatrix(tarea, controladorTareas, this);
        vistaMatrix.getPanelTareasAbajoD().add(panelTarea);
        listaTareasAbajoD.add(panelTarea);
    }

    /**
     * Elimina la tarea del panel "No importante / No urgente" de la matriz de Eisenhower.
     */
    public void removeDePanelArribaI(Tarea tarea){
        TemplatePanelMatrix panelTarea = listaTareasArribaI.get(listaTareasArribaI.indexOf(new TemplatePanelMatrix(tarea, controladorTareas, this)));
        vistaMatrix.getPanelTareasArribaI().remove(panelTarea);
        listaTareasArribaI.remove(panelTarea);
    }

    /**
     * Elimina la tarea del panel "No importante / Urgente" de la matriz de Eisenhower.
     */
    public void removeDePanelArribaD(Tarea tarea){
        TemplatePanelMatrix panelTarea = listaTareasArribaD.get(listaTareasArribaD.indexOf(new TemplatePanelMatrix(tarea, controladorTareas, this)));
        vistaMatrix.getPanelTareasArribaD().remove(panelTarea);
        listaTareasArribaD.remove(panelTarea);
    }

    /**
     * Elimina la tarea del panel "Importante / No urgente" de la matriz de Eisenhower.
     */
    public void removeDePanelAbajoI(Tarea tarea){
        TemplatePanelMatrix panelTarea = listaTareasAbajoI.get(listaTareasAbajoI.indexOf(new TemplatePanelMatrix(tarea, controladorTareas, this)));
        vistaMatrix.getPanelTareasAbajoI().remove(panelTarea);
        listaTareasAbajoI.remove(panelTarea);
    }

    /**
     * Elimina la tarea del panel "Importante / Urgente" de la matriz de Eisenhower.
     */
    public void removeDePanelAbajoD(Tarea tarea){
        TemplatePanelMatrix panelTarea = listaTareasAbajoD.get(listaTareasAbajoD.indexOf(new TemplatePanelMatrix(tarea, controladorTareas, this)));
        vistaMatrix.getPanelTareasAbajoD().remove(panelTarea);
        listaTareasAbajoD.remove(panelTarea);
    }

    /**
     * Actualiza la vista de Matrix.
     */
    public void actualizarVistaMatrix(){
        vistaMatrix.actualizarVistaMatrix();
    }

    /**
     * Actualiza la vista de Tareas.
     */
    public void actualizarVistaTareas(){
        vistaTareas.actualizarVistaTareas();
    }

    /**
     * Selecciona el card de la tarea seleccionada en la vista Tareas.
     * @param panelTarea
     */
    public void setCardTareaSeleccionada(TemplatePanelTareas panelTarea){
        vistaTareas.setCardTareaSeleccionada(panelTarea.getTarea());
    }

    /**
     * Añade las tareas a las listas correspondientes.
     */
    public void addTareasAListas() {

        for (Tarea tarea : controladorTareas.getListaTareas()) {
            TemplatePanelTareas panelTareas = new TemplatePanelTareas(tarea, controladorTareas, this);
            TemplatePanelMatrix panelMatrix = new TemplatePanelMatrix(tarea, controladorTareas, this);

            if (tarea.getCompletadaT()) {
                listaTareasCompletadas.add(panelTareas);
            } else {
                listaTareasToDo.add(panelTareas);

                switch (panelTareas.getTarea().getNombreE()) {
                    case "No importante / No urgente" -> listaTareasArribaI.add(panelMatrix);
                    case "No importante / Urgente" -> listaTareasArribaD.add(panelMatrix);
                    case "Importante / No urgente" -> listaTareasAbajoI.add(panelMatrix);
                    case "Importante / Urgente" -> listaTareasAbajoD.add(panelMatrix);
                }
            }
        }
    }

    /**
     * Añade las tareas a los paneles correspondientes de las vistas Tareas y Matrix.
     */
    public void addTareasAPaneles(){
        for (TemplatePanelTareas panelTarea : listaTareasToDo) {
            vistaTareas.getPanelListaTareasToDo().add(panelTarea);
        }

        for (TemplatePanelTareas panelTarea : listaTareasCompletadas) {
            vistaTareas.getPanelListaTareasCompletadas().add(panelTarea);
        }

        for (TemplatePanelMatrix panelTarea : listaTareasArribaI) {
            vistaMatrix.getPanelTareasArribaI().add(panelTarea);
        }

        for (TemplatePanelMatrix panelTarea :listaTareasArribaD) {
            vistaMatrix.getPanelTareasArribaD().add(panelTarea);
        }

        for (TemplatePanelMatrix panelTarea : listaTareasAbajoI) {
            vistaMatrix.getPanelTareasAbajoI().add(panelTarea);
        }

        for (TemplatePanelMatrix panelTarea : listaTareasAbajoD) {
            vistaMatrix.getPanelTareasAbajoD().add(panelTarea);
        }
    }

    public ArrayList<TemplatePanelTareas> getListaTareasToDo() {
        return listaTareasToDo;
    }

    public ArrayList<TemplatePanelTareas> getListaTareasCompletadas() {
        return listaTareasCompletadas;
    }

    /**
     * Crea los paneles principales de la interfaz.
     */
    private void crearPaneles(){
        panelMenu = templatePanelesPrincipales("menu");
        JPanel panelCentral = templatePanelesPrincipales("central");
        panelSuperior = templatePanelesPrincipales("superior");
        panelPrincipal = templatePanelesPrincipales("principal");
        VistaInicio vistaInicio = new VistaInicio();
        this.vistaTareas = new VistaTareas(controladorTareas, this);
        this.vistaMatrix = new VistaMatrix();
        VistaPomodoro vistaPomodoro = new VistaPomodoro();

        cardLayout = new CardLayout();
        panelPrincipal.setLayout(cardLayout);

        this.add(panelMenu, BorderLayout.WEST);
        this.add(panelCentral, BorderLayout.CENTER);
        panelCentral.add(panelSuperior, BorderLayout.NORTH);
        panelCentral.add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.add(vistaInicio, "Inicio");    // Se añade la vista de inicio al panel principal (por defecto
        panelPrincipal.add(vistaTareas, "Tareas");
        panelPrincipal.add(vistaPomodoro, "Pomodoro");
        panelPrincipal.add(vistaMatrix, "Matrix");
    }

    /**
     * Crea los botones de control de la ventana principal con el método estático construirBotonesVentana y los añade
     */
    private void crearBotonesVentana() {
        JButton botonMinimizar = construirBotonesVentana("minimizar");
        JButton botonMaximizar = construirBotonesVentana("maximizar");
        JButton botonCerrar = construirBotonesVentana("cerrar");

        panelSuperior.add(botonMinimizar);
        panelSuperior.add(botonMaximizar);
        panelSuperior.add(botonCerrar);
    }

    /**
     * Crea los botones del menú principal con el método templateBotonesMEnu y los añade
     */
    private void crearBotonesMenu() {
        botonInicio = templateBotonesMenu("Inicio");
        botonTareas = templateBotonesMenu("Tareas");
        botonMatrix = templateBotonesMenu("Matrix");
        botonPomodoro = templateBotonesMenu("Pomodoro");
        botonAjustes = templateBotonesMenu("Ajustes");
        botonCerrarSesion = templateBotonesMenu("Cerrar Sesión");

        panelMenu.add(botonInicio);
        panelMenu.add(botonTareas);
        panelMenu.add(botonMatrix);
        panelMenu.add(botonPomodoro);
        panelMenu.add(Box.createVerticalGlue());   //espacio entre botones
        panelMenu.add(botonAjustes);
        panelMenu.add(botonCerrarSesion);

        addBotonesALista();
    }

    /**
     * Añade ActionListener a los botones.
     * Permite cambiar la vista al pulsar un botón del menú y cambia su tamaño o desconecta al usuario.
     */
    private void botonesActionListener() {
        for (JButton boton : listaBotonesMenu){
            boton.addActionListener(e -> {
                String textoBoton = boton.getText();

                switch (textoBoton) {
                    case "Cerrar Sesión":   // Si el botón es Cerrar Sesión, se desconecta al usuario y vuelve a la ventana de login
                        dispose();
                        Usuario.desconectarUsuario();
                        new InterfazLogin(new ControladorUsuarios(new GestorUsuarios()));
                        return;
                    case "Inicio":      // Si el botón es Inicio, contrae el resto de botones
                        contraerBotones(listaBotonesMenu);
                        break;
                    case "Tareas":      // Si el botón es Tareas, se cambia el card de la columna Información Extra de la VistaTareas y continua a default
                        vistaTareas.setCardGeneral();
                    default:            // Si es cualquier otro botón, expande el botón seleccionado y contrae el resto de botones
                        contraerBotones(listaBotonesMenu);
                        boton.setPreferredSize(new Dimension(getWidth(), 75));
                }
                cardLayout.show(panelPrincipal, textoBoton);        // Muestra la ventana del botón seleccionado en el menú principal
                panelMenu.revalidate();

                textoBotonActual = textoBoton;    // Guarda el texto del botón seleccionado para mantenerlo con el color 'seleccionado'
            });
        }
    }

    /**
     * Utilizado en botonesActionListener para contraer los botones que ya no están seleccionados.
     */
    private void contraerBotones(ArrayList<JButton> listaBotonesMenuConVista) {
        for (JButton boton : listaBotonesMenuConVista) {
            boton.setPreferredSize(new Dimension(getWidth(), 50));
            if (boton.getText().equals("Inicio")){
                continue;
            }
            boton.setBackground(sRecursos.getGRANATE());
        }
    }

    /**
     * Añade ChangeListener a los botones.
     * Cambia el color de fondo del botón al pasar el ratón por encima y permite que el botón seleccionado mantenga ese color.
     */
    private void botonesChangeListener() {
        for (JButton boton : listaBotonesMenu) {
            boton.addChangeListener(e -> {
                /* Si el botón es Inicio, no cambia de color */
                if (boton.getText().equals("Inicio")) {
                    return;
                }
                /* Cambia el color del botón al pasar el ratón por encima */
                if (boton.getModel().isRollover()) {
                    boton.setBackground(sRecursos.getGRANATE().brighter());
                }
                /* Mantiene el color del botón seleccionado */
                else if (textoBotonActual.equals(boton.getText())) {
                    boton.setBackground(sRecursos.getGRANATE().brighter());
                }
                /* Restaura el color del botón al sacar el ratón de encima */
                else {
                    boton.setBackground(sRecursos.getGRANATE());
                }
            });
        }
    }

    /**
     * Método para redimensionar el panelMenu y que se ajusten el resto en consecuencia.
     * Utiliza invokeLater para que se ejecute tras el resto de operaciones.
     */
    private void redimensionarPaneles() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        panelMenu.setPreferredSize(new Dimension((int)(getWidth()*0.15), getHeight()));
                        panelMenu.revalidate();
                    }
                });
            }
        });
    }

    /**
     * Método para mover y maximizar la ventana.
     */
    private void moverVentana(){
        panelSuperior.addMouseListener(new MouseAdapter() {

            /* Permite maximizar o minimizar la ventana dando doble click sobre el panel superior */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    if (getSize().getWidth() == dimensionPantallaCompleta.width && getSize().getHeight() == dimensionPantallaCompleta.height) {
                        setSize(dimensionPantallaNormal);
                        setLocationRelativeTo(null);
                    } else {
                        setSize(dimensionPantallaCompleta);
                        setLocation(0,0);
                    }
                }
            }

            /* Obtiene la posición del ratón relativa al frame */
            @Override
            public void mousePressed(MouseEvent e) {
                xRelativoFrame = e.getX() + (int)(getWidth()*0.15);     // Suma el tamaño del menú para obtener la posición x relativa al frame real
                yRelativoFrame = e.getY();
            }
        });

        panelSuperior.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (getSize().getWidth() == dimensionPantallaCompleta.width && getSize().getHeight() == dimensionPantallaCompleta.height) {
                    int anchoAntes = getWidth();
                    setSize(dimensionPantallaNormal);
                    double proporcionDiferencia = 1.0*anchoAntes/getWidth();            // La diferencia entre la ventana maximizada y no, aprox: 1920/1100 = 1.7
                    xRelativoFrame =(int) Math.round((e.getX() + getWidth()*0.15) / proporcionDiferencia);  // Divide la xRelativoFrame entre la proporción para obtener la nueva posición de x al cambiar de tamaño la ventana
                }
                xRelativoPantalla = e.getXOnScreen();
                yRelativoPantalla = e.getYOnScreen();
                setLocation(xRelativoPantalla - xRelativoFrame, yRelativoPantalla - yRelativoFrame);
            }
        });
    }

    /**
     * Construye los paneles principales (menú, central, superior y principal).
     * @param tipo El tipo de panel a construir. Puede ser "menu", "central", "principal" o "superior".
     * @return El panel construido.
     */
    private JPanel templatePanelesPrincipales(String tipo){
        JPanel panel = new JPanel();
        panel.setCursor(sRecursos.getCursorNormal());
        switch (tipo){
            case "menu":
                panel.setBackground(sRecursos.getGRANATE());
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setPreferredSize(new Dimension((int)(getWidth()*0.15), getHeight()));
                break;
            case "central":
                panel.setLayout(new BorderLayout());
                break;
            case "principal":
                break;
            case "superior":
                panel.setBackground(sRecursos.getBLANCO());
                panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                break;
        }
        return panel;
    }

    /**
     * Construye los JButton del Menú con el diseño requerido.
     * @param texto El texto del botón.
     * @return Un JButton con el diseño personalizado.
     */
    private JButton templateBotonesMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(getWidth(), 50));
        boton.setMaximumSize(new Dimension(getWidth(), 50));
        boton.setFocusable(false);
        boton.setCursor(sRecursos.getCursorMano());
        boton.setFont(sRecursos.getMontserratBold(Recursos.SIZE_LETRA_BOTON));
        if (texto.equals("Inicio")){
            boton.setBackground(sRecursos.getBLANCO());
            boton.setForeground(sRecursos.getGRANATE());
        } else {
            boton.setBackground(sRecursos.getGRANATE());
            boton.setForeground(sRecursos.getBLANCO());
        }
        boton.setBorder(null);

        return boton;
    }

    /**
     * Construye los JButton de las acciones de la ventana (maximizar, minimizar, cerrar).
     * El botón puede ser de tipo "minimizar", "maximizar" o "cerrar", y cambia su diseño en consecuencia.
     *
     * @param tipo El tipo de botón a construir. Puede ser "minimizar", "maximizar" o "cerrar".
     * @return Un JButton con un diseño personalizado según el tipo especificado.
     */
    private JButton construirBotonesVentana(String tipo) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.clearRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.setColor(sRecursos.getGRANATE());
                g2.setStroke(new BasicStroke(3));
                switch (tipo) {
                    case "minimizar":
                        g2.drawLine(10, getHeight() / 2 + 3, getWidth() - 10, getHeight() / 2 + 3);
                        break;
                    case "maximizar":
                        g2.drawLine(11, 9, 11, 6);
                        g2.drawLine(11, 6, getWidth() - 9, 6);
                        g2.drawLine(getWidth() - 8, 6, getWidth() - 8, getHeight() - 11);
                        g2.drawLine(getWidth() - 9, getHeight() - 11, getWidth() - 14, getHeight() - 11);
                        g2.drawRect(6, 12, getWidth() - 20, getHeight() - 18);
                        break;
                    case "cerrar":
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.drawLine(8, 8, getWidth() - 8, getHeight() - 8);
                        g2.drawLine(getWidth() - 8, 8, 8, getHeight() - 8);
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        break;
                }
                g2.dispose();
            }
        };
        boton.setPreferredSize(new Dimension(40, 40));
        boton.setBackground(sRecursos.getBLANCO());
        boton.setCursor(sRecursos.getCursorMano());
        boton.setBorder(null);

        addActionListenerBotonesVentana(boton, tipo);

        return boton;
    }

    /**
     * Añade ActionListener a los botones de la ventana.
     * Permite minimizar, maximizar o cerrar la ventana.
     *
     * @param boton El botón al que añadir el ActionListener.
     * @param tipo El tipo de botón. Puede ser "minimizar", "maximizar" o "cerrar".
     */
    private void addActionListenerBotonesVentana(JButton boton, String tipo) {
        boton.addActionListener(e -> {
            switch (tipo){
                case "minimizar":
                    setState(JFrame.ICONIFIED);
                    break;
                case "maximizar":
                    if (getSize().getWidth() == dimensionPantallaCompleta.width && getSize().getHeight() == dimensionPantallaCompleta.height) {
                        setSize(dimensionPantallaNormal);
                        setLocationRelativeTo(null);
                    } else {
                        setSize(dimensionPantallaCompleta);
                        setLocation(0,0);
                    }
                    break;
                case "cerrar":
                    dispose();
                    break;
            }
        });
    }

    /**
     * Añade los botones del menú a una lista para poder aplicarle las funciones.
     */
    private void addBotonesALista(){
        this.listaBotonesMenu = new ArrayList<>();
        listaBotonesMenu.add(botonInicio);
        listaBotonesMenu.add(botonTareas);
        listaBotonesMenu.add(botonMatrix);
        listaBotonesMenu.add(botonPomodoro);
        listaBotonesMenu.add(botonAjustes);
        listaBotonesMenu.add(botonCerrarSesion);
    }
}