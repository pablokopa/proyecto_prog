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
    private GridBagConstraints gbc;
    private final GestorTareas gestorTareas;

    private JPanel columnaInformacion;
    private JPanel panelListaTareasToDo, panelListaTareasCompletadas;
    private JLabel labelNombreTarea, labelDescripcionTarea, labelEtiquetasTarea;
    private JButton botonCrearTarea, botonEliminarTarea;
    private JTextField textFieldNombreTarea;
    private JTextArea textAreaDescripcionTarea;

    private Tarea  tareaSeleccionada;

    private final CardLayout cardLayout;

    private final ArrayList<TemplatePanelTareaEspecifica> listaPanelesTareasToDo;
    private final ArrayList<TemplatePanelTareaEspecifica> listaPanelesTareasCompletadas;

    public VistaTareas(GestorTareas gestorTareas) {
        this.controladorTareas = new ControladorTareas(gestorTareas, this);
        this.gestorTareas = gestorTareas;

        this.listaPanelesTareasToDo = new ArrayList<>();
        this.listaPanelesTareasCompletadas = new ArrayList<>();

        this.tareaSeleccionada = null;

        this.setLayout(new GridBagLayout());
        cardLayout = new CardLayout();

        construirColumnaTareasToDo();
        construirColumnaTareasCompletadas();
        construirColumnaInformacionExtra();

        addTareas();
        addListenersBotones();

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
        columnaTareasToDo.setPreferredSize(new Dimension(200, 0));                // Tiene que ser igual en todas las columnas para que 'gbc.weightx' funcione correctamente
        gbc = setGbc(0, 0, 0.28, 1, GridBagConstraints.BOTH);       // Posición y tamaño de la columna
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
        gbc = setGbc(1, 0, 0.28, 1, GridBagConstraints.BOTH);
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
        gbc = setGbc(2, 0, 0.44, 1, GridBagConstraints.BOTH);
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
        gbc = setGbc(0, 0, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(labelNombreTarea, gbc);

        this.labelDescripcionTarea = new JLabel("Descripción de la tarea");
        labelDescripcionTarea.setFont(sRecursos.getMonserratBold(20));
        gbc = setGbc(0, 1, 1, 0.97, GridBagConstraints.BOTH);
        panelInformacionTarea.add(labelDescripcionTarea, gbc);

        this.labelEtiquetasTarea = new JLabel("Etiquetas de la tarea");
        labelEtiquetasTarea.setFont(sRecursos.getMonserratBold(20));
        gbc = setGbc(0, 2, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(labelEtiquetasTarea, gbc);

        this.botonEliminarTarea = new JButton("Eliminar tarea");
        botonEliminarTarea.setFont(sRecursos.getMonserratBold(20));
        gbc = setGbc(0, 3, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(botonEliminarTarea, gbc);
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

        this.textFieldNombreTarea = new JTextField();
        textFieldNombreTarea.setPreferredSize(new Dimension(0, 50));
        textFieldNombreTarea.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getGRIS_CLARO()));
        gbc = setGbc(0, 0, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(textFieldNombreTarea, gbc);

        this.textAreaDescripcionTarea = new JTextArea();
        textAreaDescripcionTarea.setPreferredSize(new Dimension(0, 50));
        textAreaDescripcionTarea.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getGRANATE()));
        gbc = setGbc(0, 1, 1, 0.97, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(textAreaDescripcionTarea, gbc);

        JPanel panelSelectorEtiquetas = new JPanel();
        panelSelectorEtiquetas.setPreferredSize(new Dimension(0, 50));
        panelSelectorEtiquetas.setLayout(new FlowLayout());
        panelSelectorEtiquetas.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getBLANCO()));
        gbc = setGbc(0, 2, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(panelSelectorEtiquetas, gbc);

        this.botonCrearTarea = new JButton("Crear tarea");
        botonCrearTarea.setPreferredSize(new Dimension(0, 50));
        botonCrearTarea.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getBLANCO()));
        gbc = setGbc(0, 3, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(botonCrearTarea, gbc);

        cardNuevaTarea.add(panelInformacionCrearNuevaTarea, BorderLayout.CENTER);
    }

    private void addListenersBotones(){

        botonCrearTarea.addActionListener(e -> {
            String nombreT = textFieldNombreTarea.getText();
            if (nombreT.isBlank() || nombreT.length()>50){
                return;
            }
            String descripcionT = textAreaDescripcionTarea.getText();

            Tarea tarea = new Tarea(nombreT, descripcionT, gestorTareas.getUsuario().getNombreU());
            gestorTareas.crearTarea(tarea);
            Tarea tareaReal = gestorTareas.getUltimaTarea();        // Obtiene la tarea creada de la base de datos para obtener su idT y los datos automáticos
            TemplatePanelTareaEspecifica panel = new TemplatePanelTareaEspecifica(tareaReal);

            addListenerATareas(tareaReal, panel);
            actualizarVistaTareas();

            textFieldNombreTarea.setText("");
            textAreaDescripcionTarea.setText("");
        });

        botonEliminarTarea.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(null, "Estás seguro de que quieres eliminar la tarea "+tareaSeleccionada.getNombreT()+"?", "Eliminar tarea", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION){
                TemplatePanelTareaEspecifica panelTemporal = new TemplatePanelTareaEspecifica(tareaSeleccionada);

                if (tareaSeleccionada.getCompletadaT()){
                    TemplatePanelTareaEspecifica panelReal = listaPanelesTareasCompletadas.get(listaPanelesTareasCompletadas.indexOf(panelTemporal));
                    panelListaTareasCompletadas.remove(panelReal);
                    listaPanelesTareasCompletadas.remove(panelReal);
                } else {
                    TemplatePanelTareaEspecifica panelReal = listaPanelesTareasToDo.get(listaPanelesTareasToDo.indexOf(panelTemporal));
                    panelListaTareasToDo.remove(panelReal);
                    listaPanelesTareasToDo.remove(panelReal);
                }
                gestorTareas.eliminarTarea(tareaSeleccionada);
                actualizarVistaTareas();

                cardLayout.show(columnaInformacion, "cardGeneral");
            }
        });
    }

    private void addListenerATareas(Tarea tarea, TemplatePanelTareaEspecifica panelTarea){

        /* Pone el check correspondiente cuando se abre la aplicación */
        /* Al iniciar la aplicación; si la tarea está completada la añade al panel de tareas completadas, si no, la añade al panel de tareas por hacer */
        if (tarea.getCompletadaT()){
            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheck());
            addAColumnaCompletada(panelTarea);
        } else {
            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheckSinCheck());
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
            TemplatePanelTareaEspecifica panelTarea = new TemplatePanelTareaEspecifica(tarea);
            addListenerATareas(tarea, panelTarea);
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
     * Cambia el cardLayout de la columna de información extra al card General Info
     */
    public void setCardGeneral(){
        cardLayout.show(columnaInformacion, "cardGeneral");
    }

    private void setCardTareaSeleccionada(Tarea tarea){
        this.tareaSeleccionada = tarea;
        labelNombreTarea.setText(tareaSeleccionada.getNombreT());
        labelDescripcionTarea.setText(tareaSeleccionada.getDescripcionT());
        cardLayout.show(columnaInformacion, "cardTareaSeleccionada");
    }

    /**
     * Añade una tarea al panel de tareas por hacer
     * @param panelTarea Panel de la tarea que se quiere añadir
     */
    private void addAColumnaToDo(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasToDo.add(panelTarea);
        listaPanelesTareasToDo.add(panelTarea);
    }

    /**
     * Añade una tarea al panel de tareas completadas
     * @param panelTarea Panel de la tarea que se quiere añadir
     */
    private void addAColumnaCompletada(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasCompletadas.add(panelTarea);
        listaPanelesTareasCompletadas.add(panelTarea);
    }

    /**
     * Cambia una tarea de columna de tareas por hacer a tareas completadas
     * @param panelTarea Panel de la tarea que se quiere cambiar de columna
     */
    private void cambiarAColumnaToDo(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasToDo.add(panelTarea);
        listaPanelesTareasToDo.add(panelTarea);
        panelListaTareasCompletadas.remove(panelTarea);
        listaPanelesTareasCompletadas.remove(panelTarea);
    }

    /**
     * Cambia una tarea de columna de tareas completadas a tareas por hacer
     * @param panelTarea Panel de la tarea que se quiere cambiar de columna
     */
    private void cambiarAColumnaCompletada(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasCompletadas.add(panelTarea);
        listaPanelesTareasCompletadas.add(panelTarea);
        panelListaTareasToDo.remove(panelTarea);
        listaPanelesTareasToDo.remove(panelTarea);
    }

    /**
     * Devuelve un objeto GridBagConstraints con los valores que se le pasan
     * @param gridx Posición en el eje x
     * @param gridy Posición en el eje y
     * @param weightx Peso en el eje x
     * @param weighty Peso en el eje y
     * @param fill Dirección en la que se expande
     * @return Objeto GridBagConstraints con los valores que se le han pasado
     */
    private GridBagConstraints setGbc(int gridx, int gridy, double weightx, double weighty, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;
        return gbc;
    }

    /**
     * Actualiza la vista de las tareas
     */
    private void actualizarVistaTareas(){
        repaint();
        revalidate();
    }
}