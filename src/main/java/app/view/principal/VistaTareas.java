package app.view.principal;

import app.controller.ControladorTareas;
import app.model.CodigoError;
import app.model.tareas.Tarea;
import app.view.templates.TemplatePanelTareas;
import services.Recursos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 * Vista de las tareas.
 */
public class VistaTareas extends JPanel {
    private final Recursos sRecursos = Recursos.getService();
    private final InterfazPrincipal interfazPrincipal;
    private final ControladorTareas controladorTareas;

    private GridBagConstraints gbc;

    private JPanel columnaInformacion;
    private JPanel panelListaTareasToDo, panelListaTareasCompletadas;
    private JLabel labelCrearNuevaTarea, labelConfirmarTarea, labelModificarTarea, labelEliminarTarea, labelEliminarTodas;
    private JLabel labelMensajesError, labelMensajesErrorSeleccionada, labelMensajesDeErrorGeneral;
    private JLabel labelFechaCreacion, labelFechaFinalizacion;
    private JTextField textFieldNombreTarea, textFieldNombreTareaSelecionada;
    private JTextPane textPaneDescripcionTarea, textPaneDescripcionTareaSeleccionada;
    private JComboBox<String> comboEtiquetasNueva, comboEtiquetasSeleccionada;
    private JLabel labelContadorTareasToDo, labelContadorTareasCompletadas;

    private final String[] opcionesEtiquetas = {"Sin etiqueta", "Importante / Urgente", "Importante / No urgente", "No importante / Urgente", "No importante / No urgente"};

    private Tarea  tareaSeleccionada;
    private final CardLayout cardLayout;

    /**
     * Constructor de la vista de tareas
     */
    public VistaTareas(ControladorTareas controladorTareas, InterfazPrincipal interfazPrincipal) {
        this.controladorTareas = controladorTareas;
        this.interfazPrincipal = interfazPrincipal;
        this.tareaSeleccionada = null;

        this.setLayout(new GridBagLayout());
        cardLayout = new CardLayout();

        construirColumnaTareasToDo();
        construirColumnaTareasCompletadas();
        construirColumnaInformacionExtra();

        addListenersGenerales();

        setCardGeneral();
    }

    /**
     * Añade los listeners generales a los botones de la vista
     */
    private void addListenersGenerales(){

        /* Listener del boton crear tarea para crear una nueva tarea */
        labelCrearNuevaTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(columnaInformacion, "cardNuevaTarea");
            }
        });


        /* Listener del boton confirmar tarea para confirmar la creación de una nueva tarea */
        labelConfirmarTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nombreT = textFieldNombreTarea.getText();
                String descripcionT = textPaneDescripcionTarea.getText();
                if (descripcionT.equals("Descripción de la tarea")){
                    descripcionT = "";
                }
                String nombreE = comboEtiquetasNueva.getSelectedItem().toString();

                int codigoError = controladorTareas.crearTarea(nombreT, descripcionT, nombreE);
                if (codigoError != CodigoError.SIN_ERROR){
                    labelMensajesError.setText(getMensajeError(codigoError));
                    sRecursos.crearTimer(labelMensajesError);
                    return;
                }

                Tarea tarea = controladorTareas.getUltimaTarea();        // Obtiene la tarea creada de la base de datos para obtener su idT y los datos automáticos

                interfazPrincipal.addAColumnaToDo(tarea);
                interfazPrincipal.addEnMatrix(tarea);

                textFieldNombreTarea.setText("Nombre de la tarea");
                textPaneDescripcionTarea.setText("Descripción de la tarea");
                comboEtiquetasNueva.setSelectedIndex(0);

                actualizarVistaTareas();
                interfazPrincipal.actualizarVistaMatrix();

                tareaSeleccionada = tarea;
                setCardTareaSeleccionada(tarea);
            }
        });

        /* Listener del boton modificar tarea para modificar la tarea seleccionada */
        labelModificarTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nombreT = textFieldNombreTareaSelecionada.getText();
                String descripcionT = textPaneDescripcionTareaSeleccionada.getText();
                String nombreE = comboEtiquetasSeleccionada.getSelectedItem().toString();

                Tarea tareaModificada = new Tarea(
                        tareaSeleccionada.getIdT(),
                        nombreT,
                        descripcionT,
                        tareaSeleccionada.getFechaCreacionT(),
                        tareaSeleccionada.getFechaFinalizacionT(),
                        tareaSeleccionada.getCompletadaT(),
                        controladorTareas.getUsuario().getNombreU(),
                        nombreE
                );

                int codigoError = controladorTareas.modificarTarea(tareaSeleccionada, tareaModificada);
                if (codigoError == CodigoError.SIN_ERROR){
                    TemplatePanelTareas panelTemporalTareas = new TemplatePanelTareas(tareaModificada, controladorTareas, interfazPrincipal);
                    TemplatePanelTareas panelTareas;

                    if (tareaSeleccionada.getCompletadaT()){
                        panelTareas = interfazPrincipal.getListaTareasCompletadas().get(interfazPrincipal.getListaTareasCompletadas().indexOf(panelTemporalTareas));
                    } else {
                        panelTareas = interfazPrincipal.getListaTareasToDo().get(interfazPrincipal.getListaTareasToDo().indexOf(panelTemporalTareas));

                        if (!tareaSeleccionada.getNombreE().equals("Sin etiqueta")){
                            interfazPrincipal.eliminarEnMatrix(tareaSeleccionada);
                        }
                        interfazPrincipal.addEnMatrix(tareaModificada);
                    }

                    panelTareas.getTarea().setNombreT(nombreT);
                    panelTareas.getTarea().setDescripcionT(descripcionT);
                    panelTareas.getTarea().setNombreE(nombreE);
                    panelTareas.setLabelTituloText(nombreT);

                    labelMensajesErrorSeleccionada.setText("Tarea modificada correctamente");
                    sRecursos.crearTimer(labelMensajesErrorSeleccionada);

                    actualizarVistaTareas();
                    interfazPrincipal.actualizarVistaMatrix();
                } else {
                    labelMensajesErrorSeleccionada.setText(getMensajeError(codigoError));
                    sRecursos.crearTimer(labelMensajesErrorSeleccionada);
                }
            }
        });

        /* Listener del boton eliminar tarea para eliminar la tarea seleccionada */
        labelEliminarTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "Estás seguro de que quieres eliminar la tarea "+tareaSeleccionada.getNombreT()+"?", "Eliminar tarea", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION){

                    int codigoError = controladorTareas.eliminarTarea(tareaSeleccionada);
                    if (codigoError == CodigoError.SIN_ERROR) {
                        if (tareaSeleccionada.getCompletadaT()) {
                            interfazPrincipal.removeDeColumnaCompletada(tareaSeleccionada);
                        } else {
                            interfazPrincipal.removeDeColumnaToDo(tareaSeleccionada);
                        }
                        interfazPrincipal.eliminarEnMatrix(tareaSeleccionada);

                        actualizarVistaTareas();
                        interfazPrincipal.actualizarVistaMatrix();

                        setCardGeneral();
                    } else {
                        labelMensajesErrorSeleccionada.setText(getMensajeError(codigoError));
                        sRecursos.crearTimer(labelMensajesErrorSeleccionada);
                    }
                }
            }
        });

        /* Listener del boton eliminar todas las tareas para eliminar todas las tareas completadas */
        labelEliminarTodas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCardGeneral();

                if (panelListaTareasCompletadas.getComponentCount()==0){
                    labelMensajesDeErrorGeneral.setText(getMensajeError(CodigoError.ERROR_TAREA_ELIMINAR_TODAS_SIN_COMPLETADAS));
                    sRecursos.crearTimer(labelMensajesDeErrorGeneral);
                    return;
                }
                int opcion = JOptionPane.showConfirmDialog(null, "Deseas eliminar todas las tareas?", "Eliminar todas las tareas", JOptionPane.YES_NO_OPTION);
                if (opcion==JOptionPane.YES_OPTION){
                    for (int i=interfazPrincipal.getListaTareasCompletadas().size()-1; i>=0; i--){
                        Tarea tarea = interfazPrincipal.getListaTareasCompletadas().get(i).getTarea();
                        int codigoError = controladorTareas.eliminarTarea(tarea);
                        if (codigoError == CodigoError.SIN_ERROR){
                            interfazPrincipal.removeDeColumnaCompletada(tarea);
                            actualizarVistaTareas();
                        } else {
                            labelMensajesDeErrorGeneral.setText(getMensajeError(codigoError));
                            sRecursos.crearTimer(labelMensajesDeErrorGeneral);
                        }
                    }
                }
            }
        });

        /* Listener del textField de la nueva tarea para que al hacer click se borre el texto */
        textFieldNombreTarea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldNombreTarea.getText().equals("Nombre de la tarea")){
                    textFieldNombreTarea.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldNombreTarea.getText().isBlank()){
                    textFieldNombreTarea.setText("Nombre de la tarea");
                }
            }
        });

        /* Listener del textPane de la nueva tarea para que al hacer click se borre el texto */
        textPaneDescripcionTarea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textPaneDescripcionTarea.getText().equals("Descripción de la tarea")){
                    textPaneDescripcionTarea.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textPaneDescripcionTarea.getText().isBlank()){
                    textPaneDescripcionTarea.setText("Descripción de la tarea");
                }
            }
        });
    }

    /**
     * Método para obtener un mensaje de error según el código de error
     * @param codigoError
     * @return mensaje de error
     */
    private String getMensajeError(int codigoError) {
        String mensajeError = "";

        switch (codigoError) {
            case CodigoError.ERROR_TAREA_SIN_NOMBRE -> mensajeError = "Introduce un nombre";
            case CodigoError.ERROR_TAREA_NOMBRE_EN_BLANCO -> mensajeError = "El nombre no puede estar en blanco";
            case CodigoError.ERROR_TAREA_NOMBRE_LARGO -> mensajeError = "El nombre no puede tener más de 35 caracteres";
            case CodigoError.ERROR_TAREA_SIN_CAMBIOS -> mensajeError = "No se han realizado cambios";
            case CodigoError.ERROR_SIN_CONEXION -> mensajeError = "No hay conexión";
            case CodigoError.ERROR_TAREA_ELIMINAR_TODAS_SIN_COMPLETADAS -> mensajeError = "No hay tareas completadas";
        }
        return mensajeError;
    }


    /**
     * Construye la columna de tareas por hacer
     */
    private void construirColumnaTareasToDo() {
        /* Crea la columna Tareas To Do */
        JPanel columnaTareasToDo = construirColumnasTareas("Por hacer", 0);
        columnaTareasToDo.setBorder(new MatteBorder(0, 10, 10, 5, sRecursos.getBLANCO()));

        /* Panel donde están las tareas to do y convertido a JScrollPane */
        this.panelListaTareasToDo = new JPanel();
        panelListaTareasToDo.setBackground(sRecursos.getGRIS_DEFAULT());
        JScrollPane scrollPanelListaTareasToDo = crearPanelListaTareas(panelListaTareasToDo);
        columnaTareasToDo.add(scrollPanelListaTareasToDo, BorderLayout.CENTER);

        /* Label para crear nueva tarea */
        this.labelCrearNuevaTarea = crearLabelBoton("Crear nueva tarea");
        columnaTareasToDo.add(labelCrearNuevaTarea, BorderLayout.SOUTH);
    }

    /**
     * Construye la columna de tareas completadas
     */
    private void construirColumnaTareasCompletadas() {
        /* Columna Tareas Completadas */
        JPanel columnaTareasCompletadas = construirColumnasTareas("Completadas", 1);
        columnaTareasCompletadas.setBorder(new MatteBorder(0, 5, 10, 5, sRecursos.getBLANCO()));

        /* Panel donde están las tareas completadas y convertido a JScrollPane */
        this.panelListaTareasCompletadas = new JPanel();
        JScrollPane scrollPanelListaTareasCompletadas = crearPanelListaTareas(panelListaTareasCompletadas);
        columnaTareasCompletadas.add(scrollPanelListaTareasCompletadas, BorderLayout.CENTER);

        /* Label para eliminar todas las tareas */
        this.labelEliminarTodas = crearLabelBoton("Eliminar todas");
        columnaTareasCompletadas.add(labelEliminarTodas, BorderLayout.SOUTH);
    }

    /**
     * Construye la columna de información extra
     */
    private void construirColumnaInformacionExtra() {
        /* Columna Información Extra */
        this.columnaInformacion = new JPanel();
        columnaInformacion.setLayout(cardLayout);
        columnaInformacion.setBorder(new MatteBorder(0, 5, 10, 10, sRecursos.getBLANCO()));
        columnaInformacion.setPreferredSize(new Dimension(200, 0));
        gbc = setGbc(2, 0, 0.4, 1, GridBagConstraints.BOTH);
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
        JPanel cardGeneral = new JPanel();
        cardGeneral.setBackground(sRecursos.getGRIS_DEFAULT());
        cardGeneral.setLayout(new BorderLayout());
        columnaInformacion.add(cardGeneral, "cardGeneral");

        JLabel labelTituloInformacionGeneral = crearLabelTituloDeColumna("Información");
        cardGeneral.add(labelTituloInformacionGeneral, BorderLayout.NORTH);

        JPanel panelInformacionGeneral = new JPanel();
        panelInformacionGeneral.setBackground(sRecursos.getGRIS_DEFAULT());
        panelInformacionGeneral.setLayout(new BorderLayout());
        cardGeneral.add(panelInformacionGeneral, BorderLayout.CENTER);

        JPanel panelNumeroTareas = new JPanel();
        panelNumeroTareas.setLayout(new GridLayout(1, 2));
        panelNumeroTareas.setPreferredSize(new Dimension(0, 100));
        panelNumeroTareas.setBorder(new EmptyBorder(5,10,5,10));
        panelInformacionGeneral.add(panelNumeroTareas, BorderLayout.NORTH);

        this.labelContadorTareasToDo = new JLabel("Por hacer: "+interfazPrincipal.getListaTareasToDo().size());
        labelContadorTareasToDo.setFont(sRecursos.getMontserratMedium(18));
        labelContadorTareasToDo.setForeground(sRecursos.getGRANATE());
        labelContadorTareasToDo.setHorizontalAlignment(SwingConstants.CENTER);
        panelNumeroTareas.add(labelContadorTareasToDo);

        this.labelContadorTareasCompletadas = new JLabel("Completadas: "+interfazPrincipal.getListaTareasCompletadas().size());
        labelContadorTareasCompletadas.setFont(sRecursos.getMontserratMedium(18));
        labelContadorTareasCompletadas.setHorizontalAlignment(SwingConstants.CENTER);
        labelContadorTareasCompletadas.setForeground(sRecursos.getGRANATE());
        panelNumeroTareas.add(labelContadorTareasCompletadas);

        JPanel panelSinTareaSeleccionada = new JPanel();
        panelSinTareaSeleccionada.setLayout(new BorderLayout());
        panelSinTareaSeleccionada.setPreferredSize(new Dimension(0, 100));
        panelSinTareaSeleccionada.setBorder(new EmptyBorder(5,10,5,10));
        panelInformacionGeneral.add(panelSinTareaSeleccionada, BorderLayout.CENTER);

        JLabel labelSinTareaSeleccionada = new JLabel("Selecciona una tarea");
        labelSinTareaSeleccionada.setFont(sRecursos.getMontserratItalic(26));
        labelSinTareaSeleccionada.setForeground(sRecursos.getGRANATE());
        labelSinTareaSeleccionada.setHorizontalAlignment(SwingConstants.CENTER);
        panelSinTareaSeleccionada.add(labelSinTareaSeleccionada, BorderLayout.CENTER);

        JPanel panelMensajesDeError = new JPanel();
        panelInformacionGeneral.add(panelMensajesDeError, BorderLayout.SOUTH);

        this.labelMensajesDeErrorGeneral = construirLabelMensajeError();
        panelMensajesDeError.add(labelMensajesDeErrorGeneral);
    }

    /**
     * Card donde se muestra la información de la tarea seleccionada
     * Se incluye en la columna de información extra.
     */
    private void crearCardTareaSeleccionada() {
        /* Card donde se muestra la información de la tarea seleccionada */
        JPanel cardTareaSeleccionada = new JPanel();
        cardTareaSeleccionada.setLayout(new BorderLayout());
        columnaInformacion.add(cardTareaSeleccionada, "cardTareaSeleccionada");

        /* Título de la columna Información Extra en el card Tarea Seleccionada */
        JLabel tituloTareaSeleccionada = crearLabelTituloDeColumna("Tarea Seleccionada");
        cardTareaSeleccionada.add(tituloTareaSeleccionada, BorderLayout.NORTH);

        JPanel panelInformacionTarea = new JPanel();
        panelInformacionTarea.setLayout(new GridBagLayout());
        cardTareaSeleccionada.add(panelInformacionTarea, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BorderLayout(0, 1));

        this.labelModificarTarea = crearLabelBoton("Modificar");
        panelBotones.add(labelModificarTarea, BorderLayout.NORTH);
        this.labelEliminarTarea = crearLabelBoton("Eliminar");
        panelBotones.add(labelEliminarTarea, BorderLayout.SOUTH);
        cardTareaSeleccionada.add(panelBotones, BorderLayout.SOUTH);

        this.textFieldNombreTareaSelecionada = construirTextFieldNombreTarea();
        gbc = setGbc(0, 0, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(textFieldNombreTareaSelecionada, gbc);

        this.textPaneDescripcionTareaSeleccionada = construirTextPaneDescripcion();
        gbc = setGbc(0, 1, 1, 0.95, GridBagConstraints.BOTH);
        panelInformacionTarea.add(textPaneDescripcionTareaSeleccionada, gbc);

        JPanel panelInformacionDatas = new JPanel();
        panelInformacionDatas.setLayout(new GridLayout(1, 2));
        panelInformacionDatas.setBackground(sRecursos.getBLANCO());
        panelInformacionDatas.setPreferredSize(new Dimension(0, 50));
        panelInformacionDatas.setBorder(new EmptyBorder(5,10,5,10));
        gbc = setGbc(0, 2, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(panelInformacionDatas, gbc);

        this.labelFechaCreacion = new JLabel("Fecha de creación: ");
        labelFechaCreacion.setFont(sRecursos.getMontserratMedium(14));
        labelFechaCreacion.setForeground(sRecursos.getGRANATE());
        panelInformacionDatas.add(labelFechaCreacion);

        this.labelFechaFinalizacion = new JLabel("Fecha de finalización: ");
        labelFechaFinalizacion.setFont(sRecursos.getMontserratMedium(14));
        labelFechaFinalizacion.setForeground(sRecursos.getGRANATE());
        panelInformacionDatas.add(labelFechaFinalizacion);

        JPanel panelOpciones = contruirPanelOpciones();
        gbc = setGbc(0, 3, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(panelOpciones, gbc);

        this.comboEtiquetasSeleccionada = contruirJComboBox(opcionesEtiquetas);
        comboEtiquetasSeleccionada.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        gbc = setGbc(1, 0, 0.5, 1, GridBagConstraints.BOTH);
        panelOpciones.add(comboEtiquetasSeleccionada, gbc);

        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BorderLayout());
        gbc = setGbc(0, 0, 0.3, 1, GridBagConstraints.BOTH);

        DatePicker datePicker = construirDatePicker();
        panelContenedor.add(datePicker, BorderLayout.CENTER);

        panelOpciones.add(panelContenedor, gbc);

        JPanel panelMensajesError = construirPanelMensajesError();
        gbc = setGbc(0, 4, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(panelMensajesError, gbc);

        labelMensajesErrorSeleccionada = construirLabelMensajeError();
        panelMensajesError.add(labelMensajesErrorSeleccionada, BorderLayout.CENTER);
    }

    /**
     * Card donde se muestran los campos para crear una nueva tarea.
     * Se incluye en la columna de información extra.
     */
    private void crearCardNuevaTarea() {
        /* Card donde se muestra la información de la tarea seleccionada */
        JPanel cardNuevaTarea = new JPanel();
        cardNuevaTarea.setLayout(new BorderLayout());
        columnaInformacion.add(cardNuevaTarea, "cardNuevaTarea");

        /* Título de la columna Información Extra en el card Nueva Tarea */
        JLabel labelTituloInformacionNuevaTarea = crearLabelTituloDeColumna("Nueva Tarea");
        cardNuevaTarea.add(labelTituloInformacionNuevaTarea, BorderLayout.NORTH);

        this.labelConfirmarTarea = crearLabelBoton("Confirmar");
        cardNuevaTarea.add(labelConfirmarTarea, BorderLayout.SOUTH);

        /* Panel donde se introduce la información de la nueva tarea */
        JPanel panelInformacionCrearNuevaTarea = new JPanel();
        panelInformacionCrearNuevaTarea.setLayout(new GridBagLayout());
        cardNuevaTarea.add(panelInformacionCrearNuevaTarea, BorderLayout.CENTER);

        this.textFieldNombreTarea = construirTextFieldNombreTarea();
        gbc = setGbc(0, 0, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(textFieldNombreTarea, gbc);

        this.textPaneDescripcionTarea = construirTextPaneDescripcion();
        gbc = setGbc(0, 1, 1, 0.95, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(textPaneDescripcionTarea, gbc);

        JPanel panelOpciones = contruirPanelOpciones();
        gbc = setGbc(0, 2, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(panelOpciones, gbc);

        this.comboEtiquetasNueva = contruirJComboBox(opcionesEtiquetas);
        comboEtiquetasNueva.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        gbc = setGbc(1, 0, 0.5, 1, GridBagConstraints.BOTH);
        panelOpciones.add(comboEtiquetasNueva, gbc);

        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BorderLayout());
        gbc = setGbc(0, 0, 0.3, 1, GridBagConstraints.BOTH);

        DatePicker datePicker = construirDatePicker();
        panelContenedor.add(datePicker, BorderLayout.CENTER);

        panelOpciones.add(panelContenedor, gbc);

        JPanel panelMensajesError = construirPanelMensajesError();
        gbc = setGbc(0, 3, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(panelMensajesError, gbc);

        this.labelMensajesError = construirLabelMensajeError();
        panelMensajesError.add(labelMensajesError, BorderLayout.CENTER);
    }

    /**
     * Construye el panel de opciones de la tarea
     * @return JPanel con las opciones de la tarea
     */
    private JPanel contruirPanelOpciones(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(0, 50));
        panel.setBorder(new MatteBorder(5, 5, 0, 5, sRecursos.getBLANCO()));
        return panel;
    }

    /**
     * Construye el label donde se muestran los mensajes de error
     * @return JLabel con los mensajes de error
     */
    private JLabel construirLabelMensajeError(){
        JLabel label = new JLabel();
        label.setFont(sRecursos.getMontserratItalic(15));
        label.setForeground(sRecursos.getGRANATE());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Construye un label con el título de la tarea
     * @return JLabel con el título de la tarea
     */
    private JTextField construirTextFieldNombreTarea(){
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(0, 50));
        textField.setText("Nombre de la tarea");
        textField.setFont(sRecursos.getMontserratMedium(20));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setForeground(sRecursos.getGRANATE());
        textField.setBorder(BorderFactory.createMatteBorder(0,1,0,1, sRecursos.getGRANATE()));
        return textField;
    }

    /**
     * Construye un JTextPane para la descripción con los ajustes necesarios
     * @return JTextPane con los ajustes necesarios
     */
    private JTextPane construirTextPaneDescripcion(){
        JTextPane textPane = new JTextPane();
        textPane.setEditorKit(new StyledEditorKit());
        textPane.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        textPane.setPreferredSize(new Dimension(0, 50));
        textPane.setBorder(new MatteBorder(1, 1, 1, 1, sRecursos.getGRANATE()));
        textPane.setForeground(sRecursos.getGRANATE());
        textPane.setFont(sRecursos.getMontserratMedium(20));
        textPane.setText("Descripción de la tarea");

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(center, 10);
        try{
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat-Medium.ttf"));
            StyleConstants.setFontFamily(center, customFont.getFamily());
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        // Aplicar la alineación centrada
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        return textPane;
    }

    /**
     * Construye el panel de los mensajes de error
     * @return JPanel con los mensajes de error
     */
    private JPanel construirPanelMensajesError(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(sRecursos.getBLANCO());
        panel.setPreferredSize(new Dimension(0, 50));
        panel.setBorder(new EmptyBorder(5,5,5,5));
        return panel;
    }

    /**
     * Construye un DatePicker con los ajustes necesarios
     * @return DatePicker con los ajustes necesarios
     */
    private DatePicker construirDatePicker(){
        DatePickerSettings datePickerSettings = new DatePickerSettings();

        datePickerSettings.setAllowKeyboardEditing(false);
        datePickerSettings.setFormatForDatesCommonEra("dd/MM/yyyy");

        datePickerSettings.setFontValidDate(sRecursos.getMontserratPlain(15));
        datePickerSettings.setFontInvalidDate(sRecursos.getMontserratItalic(15));
        datePickerSettings.setFontCalendarDateLabels(sRecursos.getMontserratPlain(16));
        datePickerSettings.setFontCalendarWeekdayLabels(sRecursos.getMontserratItalic(15));
        datePickerSettings.setFontCalendarWeekNumberLabels(sRecursos.getMontserratItalic(15));

        datePickerSettings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, sRecursos.getBLANCO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, sRecursos.getBLANCO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.CalendarTextWeekdays, sRecursos.getBLANCO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.CalendarBorderSelectedDate, sRecursos.getGRANATE());
        datePickerSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, sRecursos.getBLANCO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, sRecursos.getBLANCO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, sRecursos.getBLANCO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundVetoedDates, sRecursos.getGRIS_CLARO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, sRecursos.getBLANCO());
        datePickerSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearNavigationButtons, sRecursos.getBLANCO());

        DatePicker datePicker = new DatePicker(datePickerSettings);
        datePickerSettings.setDateRangeLimits(LocalDate.now(), LocalDate.MAX);

        datePicker.setText("Fecha límite");

        datePicker.getComponentToggleCalendarButton().setText(null);
        datePicker.getComponentToggleCalendarButton().setIcon(sRecursos.getImagenCalendario());
        datePicker.getComponentToggleCalendarButton().setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, sRecursos.getGRANATE()));
        datePicker.getComponentToggleCalendarButton().setBackground(sRecursos.getBLANCO());
        datePicker.getComponentToggleCalendarButton().setForeground(sRecursos.getGRANATE());
        datePicker.getComponentToggleCalendarButton().setMargin(new Insets(0, 0, 0, 0)); // Ajustar los márgenes internos a cero

        datePicker.getComponentDateTextField().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, sRecursos.getGRANATE()));
        datePicker.getComponentDateTextField().setMargin(new Insets(0, 0, 0, 0)); // Ajustar los márgenes internos a cero

        return datePicker;
    }

    /**
     * Construye un JComboBox con las opciones que se le pasan
     * @param opciones lista de opciones para el JComboBox
     * @return JComboBox con las opciones pasadas
     */
    private JComboBox<String> contruirJComboBox(String[] opciones){
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        comboBox.setFont(sRecursos.getMontserratPlain(15));
        comboBox.setForeground(sRecursos.getGRANATE());
        comboBox.setBackground(sRecursos.getBLANCO());
        return comboBox;
    }

    /**
     * Construye las columnas de tareas por hacer y completadas
     * @param titulo Título de la columna
     * @param columna Número de columna
     * @return JPanel con la columna de tareas
     */
    private JPanel construirColumnasTareas(String titulo, int columna) {
        /* Columna Tareas Completadas */
        JPanel panelColumna = new JPanel();
        panelColumna.setLayout(new BorderLayout());
        panelColumna.setPreferredSize(new Dimension(200, 0));
        gbc = setGbc(columna, 0, 0.3, 1, GridBagConstraints.BOTH);
        add(panelColumna, gbc);

        /* Título de la columna completadas */
        JLabel labelTituloColuma = crearLabelTituloDeColumna(titulo);
        panelColumna.add(labelTituloColuma, BorderLayout.NORTH);

        return panelColumna;
    }

    /**
     * Crea un JLabel con el texto que se le pasa y lo devuelve con el diseño requerido para el título de la columna.
     * @param texto Texto que se le quiere poner al JLabel
     * @return JLabel con el texto que se le ha pasado
     */
    private JLabel crearLabelTituloDeColumna(String texto){
        JLabel labelTitulo = new JLabel(texto);
        labelTitulo.setFont(sRecursos.getMontserratPlain(23));
        labelTitulo.setPreferredSize(new Dimension(getWidth(), 40));
        labelTitulo.setMaximumSize(new Dimension(getWidth(), 40));
        labelTitulo.setForeground(sRecursos.getBLANCO());
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBackground(sRecursos.getGRANATE_MID_LIGHT());
        labelTitulo.setOpaque(true);
        return labelTitulo;
    }

    /**
     * Crea un JLabel con el texto que se le pasa y lo devuelve con el diseño requerido para los botones de la columna de información extra.
     * @param texto Texto que se le quiere poner al JLabel
     * @return JLabel con el texto que se le ha pasado
     */
    private JLabel crearLabelBoton(String texto){
        JLabel labelBoton = new JLabel(texto);
        labelBoton.setFont(sRecursos.getMontserratPlain(18));
        labelBoton.setHorizontalAlignment(SwingConstants.CENTER);
        labelBoton.setBackground(sRecursos.getBLANCO());
        labelBoton.setForeground(sRecursos.getGRANATE());
        labelBoton.setPreferredSize(new Dimension(getWidth(), 30));
        labelBoton.setMaximumSize(new Dimension(getWidth(), 30));
        labelBoton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, sRecursos.getGRANATE()));
        labelBoton.setCursor(sRecursos.getCursorMano());
        labelBoton.setOpaque(true);

        labelBoton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                labelBoton.setBackground(sRecursos.getGRIS_CLARO());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelBoton.setBackground(sRecursos.getBLANCO());
            }
        });

        return labelBoton;
    }

    /**
     * Crea un scrollPane con el panel de la lista de tareas y le añade el diseño requerido
     * @param panel Panel donde se añadirán las tareas
     * @return JScrollPane con el panel de tareas
     */
    private JScrollPane crearPanelListaTareas(JPanel panel){
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPanel = new JScrollPane(panel);
        sRecursos.crearScrollModificado(scrollPanel, sRecursos.getGRANATE_MID_LIGHT(), sRecursos.getBLANCO());
        return scrollPanel;
    }

    /**
     * Cambia el cardLayout de la columna de información extra al card General Info
     */
    public void setCardGeneral(){
        labelContadorTareasToDo.setText("Por hacer: "+interfazPrincipal.getListaTareasToDo().size());
        labelContadorTareasCompletadas.setText("Completadas: "+interfazPrincipal.getListaTareasCompletadas().size());
        cardLayout.show(columnaInformacion, "cardGeneral");
    }

    /**
     * Cambia el cardLayout de la columna de información extra al card Tarea Seleccionada
     * @param tarea Tarea que se ha seleccionado
     */
    public void setCardTareaSeleccionada(Tarea tarea){
        this.tareaSeleccionada = tarea;
        textFieldNombreTareaSelecionada.setText(tareaSeleccionada.getNombreT());
        textPaneDescripcionTareaSeleccionada.setText(tareaSeleccionada.getDescripcionT());

        labelFechaCreacion.setText("Creación: "+tareaSeleccionada.getFechaCreacionFormat());
        if (tareaSeleccionada.getFechaFinalizacionT()==null){
            labelFechaFinalizacion.setText("Finalización: No finalizada");
        } else {
            labelFechaFinalizacion.setText("Finalización: "+tareaSeleccionada.getFechaFinalizacionFormat());
        }

        comboEtiquetasSeleccionada.setSelectedItem(tareaSeleccionada.getNombreE());

        cardLayout.show(columnaInformacion, "cardTareaSeleccionada");
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
     * Devuelve el panel de las tareas por hacer
     * @return JPanel de las tareas por hacer
     */
    public JPanel getPanelListaTareasToDo() {
        return panelListaTareasToDo;
    }

    /**
     * Devuelve el panel de las tareas completadas
     * @return JPanel de las tareas completadas
     */
    public JPanel getPanelListaTareasCompletadas() {
        return panelListaTareasCompletadas;
    }

    /**
     * Devuelve el label de los mensajes de error generales
     * @return JLabel de los mensajes de error generales
     */
    public JLabel getLabelMensajesDeErrorGeneral() {
        return labelMensajesDeErrorGeneral;
    }

    /**
     * Actualiza la vista de las tareas
     */
    public void actualizarVistaTareas(){
        this.repaint();
        this.revalidate();
    }
}