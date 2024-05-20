package app.view.principal;

import app.controller.ControladorTareas;
import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.view.templates.TemplatePanelTareaEspecifica;
import services.Recursos;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VistaTareas extends JPanel {

    private final ControladorTareas controladorTareas;

    private final Recursos sRecursos = Recursos.getService();
    private final GridBagConstraints gbc;
    private final GestorTareas gestorTareas;

    private JPanel columnaInformacion;
    private JPanel panelListaTareasToDo;
    private JPanel panelListaTareasCompletadas;
    private JLabel labelNombreTarea, labelDescripcionTarea, labelEtiquetasTarea;

    private Tarea  tareaSeleccionada;

    private final CardLayout cardLayout;

    private ArrayList<TemplatePanelTareaEspecifica> listaPanelesTareasToDo;
    private ArrayList<TemplatePanelTareaEspecifica> listaPanelesTareasCompletadas;

    public VistaTareas(GestorTareas gestorTareas) {

        this.controladorTareas = new ControladorTareas(gestorTareas, this);

        this.gestorTareas = gestorTareas;
        cardLayout = new CardLayout();

        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        this.tareaSeleccionada = null;

        this.listaPanelesTareasToDo = new ArrayList<>();
        this.listaPanelesTareasCompletadas = new ArrayList<>();

        construirColumnaTareasToDo();
        construirColumnaTareasCompletadas();
        construirColumnaInformacionExtra();

        addTareas();

        setCardGeneral();
    }

    /**
     * Construye la columna de tareas por hacer
     */
    private void construirColumnaTareasToDo() {
        /* Crea la columna Tareas To Do */
        JPanel columnaTareasToDo = new JPanel();
        columnaTareasToDo.setLayout(new BorderLayout());
        columnaTareasToDo.setBorder(new MatteBorder(0, 10, 10, 5, sRecursos.getBLANCO()));
        columnaTareasToDo.setBackground(Color.GREEN);
        columnaTareasToDo.setPreferredSize(new Dimension(200, 0));   // Tiene que ser igual en todas las columnas para que 'gbc.weightx' funcione correctamente
        gbc.gridx = 0;                          // Columna 1
        gbc.gridy = 0;                          // Fila 0
        gbc.weightx = 0.28;                     // Ocupa x% del espacio horizontal
        gbc.weighty = 1;                        // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(columnaTareasToDo, gbc);

        /* Título de la columna to do */
        JLabel labelTituloTareasToDo = crearLabelTituloDeColumna("Tareas por hacer");
        columnaTareasToDo.add(labelTituloTareasToDo, BorderLayout.NORTH);

        /* Panel donde están las tareas to do y convertido a JScrollPane */
        this.panelListaTareasToDo = new JPanel();
        JScrollPane scroolPanelListaTareasToDo = crearPanelListaTareas(panelListaTareasToDo);
        columnaTareasToDo.add(scroolPanelListaTareasToDo, BorderLayout.CENTER);

        /* Label para crear nueva tarea */
        JLabel labelCrearNuevaTarea = new JLabel("Crear nueva tarea");
        labelCrearNuevaTarea.setFont(sRecursos.getMonserratBold(25));
        labelCrearNuevaTarea.setHorizontalAlignment(SwingConstants.CENTER);
        labelCrearNuevaTarea.setBackground(Color.lightGray);
        labelCrearNuevaTarea.setOpaque(true);
        columnaTareasToDo.add(labelCrearNuevaTarea, BorderLayout.SOUTH);

        labelCrearNuevaTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(columnaInformacion, "cardNuevaTarea");
            }
        });
    }

    /**
     * Construye la columna de tareas completadas
     */
    private void construirColumnaTareasCompletadas() {
        /* Columna Tareas Completadas */
        JPanel columnaTareasCompletadas = new JPanel();
        columnaTareasCompletadas.setLayout(new BorderLayout());
        columnaTareasCompletadas.setBorder(new MatteBorder(0, 5, 10, 5, sRecursos.getBLANCO()));
        columnaTareasCompletadas.setBackground(Color.RED);
        columnaTareasCompletadas.setPreferredSize(new Dimension(200, 0));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.28;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(columnaTareasCompletadas, gbc);

        /* Título de la columna completadas */
        JLabel labelTituloTareasCompletadas = crearLabelTituloDeColumna("Tareas completadas");
        columnaTareasCompletadas.add(labelTituloTareasCompletadas, BorderLayout.NORTH);

        /* Panel donde están las tareas completadas y convertido a JScrollPane */
        this.panelListaTareasCompletadas = new JPanel();
        JScrollPane scroolPanelListaTareasCompletadas = crearPanelListaTareas(panelListaTareasCompletadas);
        columnaTareasCompletadas.add(scroolPanelListaTareasCompletadas, BorderLayout.CENTER);
    }

    /**
     * Construye la columna de información extra
     */
    private void construirColumnaInformacionExtra() {
        /* Columna Información Extra */
        this.columnaInformacion = new JPanel();
        columnaInformacion.setLayout(cardLayout);
        columnaInformacion.setBackground(Color.BLUE);
        columnaInformacion.setBorder(new MatteBorder(0, 5, 10, 10, sRecursos.getBLANCO()));
        columnaInformacion.setPreferredSize(new Dimension(200, 0));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.44;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(columnaInformacion, gbc);

        crearCardGeneral();
        crearCardTareaSeleccionada();
        crearCardNuevaTarea();
    }

    /**
     * Card donde se muestra la información general de las tareas
     * Se incluye en la columna de información extra.
     */
    private void crearCardGeneral() {
        /* Card donde se muestra la información de la tarea seleccionada */
        JPanel cardGeneral = new JPanel();
        cardGeneral.setLayout(new BorderLayout());
        cardGeneral.setBackground(Color.orange);
        columnaInformacion.add(cardGeneral, "cardGeneral");

        /* Título de la columna Información Extra en el card Info General */
        JLabel labelTituloInformacionGeneral = crearLabelTituloDeColumna("Información General");
        cardGeneral.add(labelTituloInformacionGeneral, BorderLayout.NORTH);
    }

    /**
     * Card donde se muestra la información de la tarea seleccionada
     * Se incluye en la columna de información extra.
     */
    private void crearCardTareaSeleccionada() {
        /* Card donde se muestra la información de la tarea seleccionada */
        JPanel cardTareaSeleccionada = new JPanel();
        cardTareaSeleccionada.setLayout(new BorderLayout());
        cardTareaSeleccionada.setBackground(Color.orange);
        columnaInformacion.add(cardTareaSeleccionada, "cardTareaSeleccionada");

        /* Título de la columna Información Extra en el card Tarea Seleccionada */
        JLabel tituloTareaSeleccionada = crearLabelTituloDeColumna("Tarea Seleccionada");
        cardTareaSeleccionada.add(tituloTareaSeleccionada, BorderLayout.NORTH);

        JPanel panelInformacionTarea = new JPanel();
        panelInformacionTarea.setLayout(new GridBagLayout());
        panelInformacionTarea.setBackground(Color.orange);
        cardTareaSeleccionada.add(panelInformacionTarea, BorderLayout.CENTER);

        this.labelNombreTarea = new JLabel("Nombre de la tarea");
        labelNombreTarea.setFont(sRecursos.getMonserratBold(20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionTarea.add(labelNombreTarea, gbc);

        this.labelDescripcionTarea = new JLabel("Descripción de la tarea");
        labelDescripcionTarea.setFont(sRecursos.getMonserratBold(20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.97;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionTarea.add(labelDescripcionTarea, gbc);

        this.labelEtiquetasTarea = new JLabel("Etiquetas de la tarea");
        labelEtiquetasTarea.setFont(sRecursos.getMonserratBold(20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionTarea.add(labelEtiquetasTarea, gbc);

        JButton eliminarTarea = new JButton("Eliminar tarea");
        eliminarTarea.setFont(sRecursos.getMonserratBold(20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionTarea.add(eliminarTarea, gbc);

        eliminarTarea.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(null, "Estás seguro de que quieres eliminar la tarea "+tareaSeleccionada.getNombreT()+"?", "Eliminar tarea", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION){
                if (tareaSeleccionada.getCompletadaT()){
                    TemplatePanelTareaEspecifica panelTemporal = new TemplatePanelTareaEspecifica(tareaSeleccionada, gestorTareas, this);
                    TemplatePanelTareaEspecifica panelSeleccionado = listaPanelesTareasCompletadas.get(listaPanelesTareasCompletadas.indexOf(panelTemporal));
                    panelListaTareasCompletadas.remove(panelSeleccionado);
                } else {
                    TemplatePanelTareaEspecifica panelTemporal = new TemplatePanelTareaEspecifica(tareaSeleccionada, gestorTareas, this);
                    TemplatePanelTareaEspecifica panelSeleccionado = listaPanelesTareasToDo.get(listaPanelesTareasToDo.indexOf(panelTemporal));
                    panelListaTareasToDo.remove(panelSeleccionado);
                }
                gestorTareas.eliminarTarea(tareaSeleccionada);
                actualizarVistaTareas();
            }
        });
    }

    /**
     * Card donde se muestran los campos para crear una nueva tarea.
     * Se incluye en la columna de información extra.
     */
    private void crearCardNuevaTarea() {
        /* Card donde se muestra la información de la tarea seleccionada */
        JPanel cardNuevaTarea = new JPanel();
        cardNuevaTarea.setLayout(new BorderLayout());
        cardNuevaTarea.setBackground(Color.orange);
        columnaInformacion.add(cardNuevaTarea, "cardNuevaTarea");

        /* Título de la columna Información Extra en el card Nueva Tarea */
        JLabel labelTituloInformacionNuevaTarea = crearLabelTituloDeColumna("Nueva Tarea");
        cardNuevaTarea.add(labelTituloInformacionNuevaTarea, BorderLayout.NORTH);

        /* Panel donde se introduce la información de la nueva tarea */
        JPanel panelInformacionCrearNuevaTarea = new JPanel();
        panelInformacionCrearNuevaTarea.setLayout(new GridBagLayout());

        JTextField textFieldNombreTarea = new JTextField();
        textFieldNombreTarea.setPreferredSize(new Dimension(0, 50));
        textFieldNombreTarea.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getGRIS_CLARO()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionCrearNuevaTarea.add(textFieldNombreTarea, gbc);

        JTextArea textAreaDescripcionTarea = new JTextArea();
        textAreaDescripcionTarea.setPreferredSize(new Dimension(0, 50));
        textAreaDescripcionTarea.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getGRANATE()));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.97;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionCrearNuevaTarea.add(textAreaDescripcionTarea, gbc);

        JPanel panelSelectorEtiquetas = new JPanel();
        panelSelectorEtiquetas.setPreferredSize(new Dimension(0, 50));
        panelSelectorEtiquetas.setLayout(new FlowLayout());
        panelSelectorEtiquetas.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getBLANCO()));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionCrearNuevaTarea.add(panelSelectorEtiquetas, gbc);

        JButton botonCrearTarea = new JButton("Crear tarea");
        botonCrearTarea.setPreferredSize(new Dimension(0, 50));
        botonCrearTarea.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getBLANCO()));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.BOTH;
        panelInformacionCrearNuevaTarea.add(botonCrearTarea, gbc);

        botonCrearTarea.addActionListener(e -> {
            String nombreT = textFieldNombreTarea.getText();
            if (nombreT.isBlank()){
                return;
            }
            String descripcionT = textAreaDescripcionTarea.getText();
            Tarea tarea = new Tarea(nombreT, descripcionT, gestorTareas.getUsuario().getNombreU());
            gestorTareas.crearTarea(tarea);
            TemplatePanelTareaEspecifica panel = new TemplatePanelTareaEspecifica(tarea, gestorTareas, this);
            addListenerATareas(tarea, panel);
            listaPanelesTareasToDo.add(panel);
            actualizarVistaTareas();
        });

        cardNuevaTarea.add(panelInformacionCrearNuevaTarea, BorderLayout.CENTER);
    }

    private void addListenerATareas(Tarea tarea, TemplatePanelTareaEspecifica panelTarea){

        /* Pone el check correspondiente cuando se abre la aplicación */
        if (tarea.getCompletadaT()){
            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheck());
        } else {
            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheckSinCheck());
        }

        /* Al iniciar la aplicación; si la tarea está completada la añade al panel de tareas completadas, si no, la añade al panel de tareas por hacer */
        if (tarea.getCompletadaT()){
            addAColumnaCompletada(panelTarea);
        } else {
            addAColumnaToDo(panelTarea);
        }

        /* Al hacer click en la tarea, se marca como completada o no y se cambia de columna */
        panelTarea.getLabelImagen().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gestorTareas.completarTarea(tarea)){
                    if (tarea.getCompletadaT()){
                        panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheck());
                        cambiarAColumnaCompletada(panelTarea);
                    } else {
                        panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheckSinCheck());
                        cambiarAColumnaToDo(panelTarea);
                    }
                }
                actualizarVistaTareas();
            }
        });

        panelTarea.getPanelTarea().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCardTareaSeleccionada(tarea);
                actualizarVistaTareas();
            }
        });
    }


    /**
     * Recupera las tareas del usuario de la base de datos, les añade las funciones (Listener) necesarias y las añade a las columnas
     */
    private void addTareas() {
        gestorTareas.getTareasDeBase();     // Llama al método que obtiene las tareas del usuario de la base de datos y las guarda en una lista

        /* Utiliza el template PanelTareaEspecífica para mostrarlas y aplicarle los Listener convenientes */
        for (Tarea tarea : gestorTareas.getListaTareas()) {
            TemplatePanelTareaEspecifica panelTarea = new TemplatePanelTareaEspecifica(tarea, gestorTareas, this);
            addListenerATareas(tarea, panelTarea);
            if (tarea.getCompletadaT()){
                listaPanelesTareasCompletadas.add(panelTarea);
            } else {
                listaPanelesTareasToDo.add(panelTarea);
            }
        }
    }

    /**
     * Crea un JLabel con el texto que se le pasa y lo devuelve con el diseño requerido para el título de la columna.
     * @param texto Texto que se le quiere poner al JLabel
     * @return JLabel con el texto que se le ha pasado
     */
    private JLabel crearLabelTituloDeColumna(String texto){
        JLabel labelTitulo = new JLabel(texto);
        labelTitulo.setFont(sRecursos.getMonserratBold(25));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBackground(Color.lightGray);
        labelTitulo.setOpaque(true);
        return labelTitulo;
    }

    /**
     * Crea un scrollPane con el panel de la lista de tareas y le añade el diseño requerido
     * @param panel Panel donde se añadirán las tareas
     * @return JScrollPane con el panel de tareas
     */
    private JScrollPane crearPanelListaTareas(JPanel panel){
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.yellow);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPanel;
    }

    /**
     * Cambia el cardLayout de la columna de información extra al  card General Info
     */
    public void setCardGeneral(){
        cardLayout.show(columnaInformacion, "cardGeneral");
    }

    public void setCardTareaSeleccionada(Tarea tarea){
        this.tareaSeleccionada = tarea;
        labelNombreTarea.setText(tareaSeleccionada.getNombreT());
        labelDescripcionTarea.setText(tareaSeleccionada.getDescripcionT());
        cardLayout.show(columnaInformacion, "cardTareaSeleccionada");
        actualizarVistaTareas();
    }

    /**
     * Añade una tarea al panel de tareas por hacer
     * @param panelTarea Panel de la tarea que se quiere añadir
     */
    public void addAColumnaToDo(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasToDo.add(panelTarea);
    }

    /**
     * Añade una tarea al panel de tareas completadas
     * @param panelTarea Panel de la tarea que se quiere añadir
     */
    public void addAColumnaCompletada(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasCompletadas.add(panelTarea);
    }

    /**
     * Cambia una tarea de columna de tareas por hacer a tareas completadas
     * @param panelTarea Panel de la tarea que se quiere cambiar de columna
     */
    public void cambiarAColumnaToDo(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasToDo.add(panelTarea);
        listaPanelesTareasToDo.add(panelTarea);
        panelListaTareasCompletadas.remove(panelTarea);
        listaPanelesTareasCompletadas.remove(panelTarea);
    }

    /**
     * Cambia una tarea de columna de tareas completadas a tareas por hacer
     * @param panelTarea Panel de la tarea que se quiere cambiar de columna
     */
    public void cambiarAColumnaCompletada(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasCompletadas.add(panelTarea);
        listaPanelesTareasCompletadas.add(panelTarea);
        panelListaTareasToDo.remove(panelTarea);
        listaPanelesTareasToDo.remove(panelTarea);
    }

    /**
     * Actualiza la vista de las tareas
     */
    public void actualizarVistaTareas(){
        repaint();
        revalidate();
    }
}