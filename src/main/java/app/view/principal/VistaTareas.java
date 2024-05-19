package app.view.principal;

import app.controller.ControladorTareas;
import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.model.usuarios.Usuario;
import app.view.templates.TemplatePanelTareaEspecifica;
import services.Recursos;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaTareas extends JPanel {

    private ControladorTareas controladorTareas;

    private final Recursos sRecursos = Recursos.getService();
    private final GridBagConstraints gbc;
    private final GestorTareas gestorTareas;
    private final Usuario usuario;

    private JPanel panelColumnaTareasToDo, panelColumnaTareasCompletadas, panelColumnaInformacionExtra;
    private JPanel panelListaTareasToDo, panelListaTareasCompletadas, panelInformacionNuevaTarea, panelInformacionTareaSeleccionada, panelInformacionGeneral, panelInformacionCrearNuevaTarea, panelPrincipalInformacionTareaSeleccionada;
    private JScrollPane scroolPanelListaTareasToDo, scroolPanelListaTareasCompletadas;
    private JLabel labelTituloTareasToDo, labelCrearNuevaTarea, labelTituloTareasCompletadas, labelTituloInformacionNuevaTarea, labelTituloInformacionGeneral, labelTituloInformacionTareaSeleccionada;

    private final CardLayout cardLayout;

    public VistaTareas(GestorTareas gestorTareas) {

        this.controladorTareas = new ControladorTareas(gestorTareas, this);

        this.gestorTareas = gestorTareas;
        this.usuario = gestorTareas.getUsuario();
        cardLayout = new CardLayout();

        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        construirColumnaTareasToDo();
        construirColumnaTareasCompletadas();
        construirColumnaInformacionExtra();

        addTareas();

        setGeneralCardLayout();
    }

    /**
     * Construye la columna de tareas por hacer
     */
    private void construirColumnaTareasToDo() {
        /* Crea la columna Tareas To Do */
        this.panelColumnaTareasToDo = new JPanel();
        panelColumnaTareasToDo.setLayout(new BorderLayout());
        panelColumnaTareasToDo.setBorder(new MatteBorder(0, 10, 10, 5, sRecursos.getBLANCO()));
        panelColumnaTareasToDo.setBackground(Color.GREEN);
        panelColumnaTareasToDo.setPreferredSize(new Dimension(200, 0));   // Tiene que ser igual en todas las columnas para que 'gbc.weightx' funcione correctamente
        gbc.gridx = 0;                          // Columna 1
        gbc.gridy = 0;                          // Fila 0
        gbc.weightx = 0.28;                     // Ocupa x% del espacio horizontal
        gbc.weighty = 1;                        // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelColumnaTareasToDo, gbc);

        /* Título de la columna to do */
        this.labelTituloTareasToDo = crearLabelTituloDeColumna("Tareas por hacer");
        panelColumnaTareasToDo.add(labelTituloTareasToDo, BorderLayout.NORTH);

        /* Panel donde están las tareas to do y convertido a JScrollPane */
        this.panelListaTareasToDo = new JPanel();
        this.scroolPanelListaTareasToDo = crearPanelListaTareas(panelListaTareasToDo);
        panelColumnaTareasToDo.add(scroolPanelListaTareasToDo, BorderLayout.CENTER);

        /* Label para crear nueva tarea */
//        this.labelCrearNuevaTarea = crearLabelTituloDeColumna("Crear nueva tarea");
        this.labelCrearNuevaTarea = new JLabel("Crear nueva tarea");
        labelCrearNuevaTarea.setFont(sRecursos.getMonserratBold(25));
        labelCrearNuevaTarea.setHorizontalAlignment(SwingConstants.CENTER);
        labelCrearNuevaTarea.setBackground(Color.lightGray);
        labelCrearNuevaTarea.setOpaque(true);
        panelColumnaTareasToDo.add(labelCrearNuevaTarea, BorderLayout.SOUTH);

        labelCrearNuevaTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelColumnaInformacionExtra, "InfoNueva");
            }
        });
    }

    /**
     * Construye la columna de tareas completadas
     */
    private void construirColumnaTareasCompletadas() {
        /* Columna Tareas Completadas */
        this.panelColumnaTareasCompletadas = new JPanel();
        panelColumnaTareasCompletadas.setLayout(new BorderLayout());
        panelColumnaTareasCompletadas.setBorder(new MatteBorder(0, 5, 10, 5, sRecursos.getBLANCO()));
        panelColumnaTareasCompletadas.setBackground(Color.RED);
        panelColumnaTareasCompletadas.setPreferredSize(new Dimension(200, 0));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.28;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelColumnaTareasCompletadas, gbc);

        /* Título de la columna completadas */
        this.labelTituloTareasCompletadas = crearLabelTituloDeColumna("Tareas completadas");
        panelColumnaTareasCompletadas.add(labelTituloTareasCompletadas, BorderLayout.NORTH);

        /* Panel donde están las tareas completadas y convertido a JScrollPane */
        this.panelListaTareasCompletadas = new JPanel();
        this.scroolPanelListaTareasCompletadas = crearPanelListaTareas(panelListaTareasCompletadas);
        panelColumnaTareasCompletadas.add(scroolPanelListaTareasCompletadas, BorderLayout.CENTER);
    }

    /**
     * Construye la columna de información extra
     */
    private void construirColumnaInformacionExtra() {
        /* Columna Información Extra */
        this.panelColumnaInformacionExtra = new JPanel();
        panelColumnaInformacionExtra.setLayout(cardLayout);
        panelColumnaInformacionExtra.setBackground(Color.BLUE);
        panelColumnaInformacionExtra.setBorder(new MatteBorder(0, 5, 10, 10, sRecursos.getBLANCO()));
        panelColumnaInformacionExtra.setPreferredSize(new Dimension(200, 0));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.44;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelColumnaInformacionExtra, gbc);

        cardGeneralColumnaInformacionExtra();
        cardTareaSeleccionadaColumnaInformacionExtra();
        cardNuevaTareaColumnaInformacionExtra();
    }

    /**
     * Card donde se muestra la información general de las tareas
     * Se incluye en la columna de información extra.
     */
    private void cardGeneralColumnaInformacionExtra() {
        /* Card donde se muestra la información de la tarea seleccionada */
        this.panelInformacionGeneral = new JPanel();
        panelInformacionGeneral.setLayout(new BorderLayout());
        panelInformacionGeneral.setBackground(Color.orange);
        panelColumnaInformacionExtra.add(panelInformacionGeneral, "InfoGeneral");

        /* Título de la columna Información Extra en el card Info General */
        this.labelTituloInformacionGeneral = crearLabelTituloDeColumna("Info General");
        panelInformacionGeneral.add(labelTituloInformacionGeneral, BorderLayout.NORTH);
    }

    /**
     * Card donde se muestra la información de la tarea seleccionada
     * Se incluye en la columna de información extra.
     */
    private void cardTareaSeleccionadaColumnaInformacionExtra() {
        /* Card donde se muestra la información de la tarea seleccionada */
        this.panelInformacionTareaSeleccionada = new JPanel();
        panelInformacionTareaSeleccionada.setLayout(new BorderLayout());
        panelInformacionTareaSeleccionada.setBackground(Color.orange);
        panelColumnaInformacionExtra.add(panelInformacionTareaSeleccionada, "InfoSeleccionada");

        /* Título de la columna Información Extra en el card Tarea Seleccionada */
        this.labelTituloInformacionTareaSeleccionada = crearLabelTituloDeColumna("Tarea Seleccionada");
        panelInformacionTareaSeleccionada.add(labelTituloInformacionTareaSeleccionada, BorderLayout.NORTH);
    }

    /**
     * Card donde se muestran los campos para crear una nueva tarea.
     * Se incluye en la columna de información extra.
     */
    private void cardNuevaTareaColumnaInformacionExtra() {
        /* Card donde se muestra la información de la tarea seleccionada */
        this.panelInformacionNuevaTarea = new JPanel();
        panelInformacionNuevaTarea.setLayout(new BorderLayout());
        panelInformacionNuevaTarea.setBackground(Color.orange);
        panelColumnaInformacionExtra.add(panelInformacionNuevaTarea, "InfoNueva");

        /* Título de la columna Información Extra en el card Nueva Tarea */
        this.labelTituloInformacionNuevaTarea = crearLabelTituloDeColumna("Nueva Tarea");
        panelInformacionNuevaTarea.add(labelTituloInformacionNuevaTarea, BorderLayout.NORTH);

        /* Panel donde se introduce la información de la nueva tarea */
        this.panelInformacionCrearNuevaTarea = new JPanel();
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
            String nombre = textFieldNombreTarea.getText();
            String descripcion = textAreaDescripcionTarea.getText();
            controladorTareas.crearTarea(nombre, descripcion);
        });

        panelInformacionNuevaTarea.add(panelInformacionCrearNuevaTarea, BorderLayout.CENTER);
    }


    /**
     * Recupera las tareas del usuario de la base de datos, les añade las funciones (Listener) necesarias y las añade a las columnas
     */
    private void addTareas() {
        gestorTareas.getTareasDeBase();     // Llama al método que obtiene las tareas del usuario de la base de datos y las guarda en una lista

        /* Utiliza el template PanelTareaEspecífica para mostrarlas y aplicarle los Listener convenientes */
        for (Tarea tarea : gestorTareas.getListaTareas()) {
            new TemplatePanelTareaEspecifica(tarea, gestorTareas, this);
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
        return scrollPanel;
    }

    /**
     * Cambia el cardLayout de la columna de información extra al  card General Info
     */
    public void setGeneralCardLayout(){
        cardLayout.show(panelColumnaInformacionExtra, "InfoGeneral");
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
        panelListaTareasCompletadas.remove(panelTarea);
    }

    /**
     * Cambia una tarea de columna de tareas completadas a tareas por hacer
     * @param panelTarea Panel de la tarea que se quiere cambiar de columna
     */
    public void cambiarAColumnaCompletada(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasCompletadas.add(panelTarea);
        panelListaTareasToDo.remove(panelTarea);
    }

    /**
     * Actualiza la vista de las tareas
     */
    public void actualizarVistaTareas(){
        repaint();
        revalidate();
    }

    public JPanel getPanelColumnaInformacionExtra() {
        return panelColumnaInformacionExtra;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}