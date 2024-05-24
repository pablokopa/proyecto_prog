package app.view.principal;

import app.controller.ControladorTareas;
import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.view.templates.TemplatePanelTareaEspecifica;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import services.Recursos;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class VistaTareas extends JPanel {

    private final ControladorTareas controladorTareas;

    private final Recursos sRecursos = Recursos.getService();
    private GridBagConstraints gbc;
    private final GestorTareas gestorTareas;

    private JPanel columnaInformacion;
    private JPanel panelListaTareasToDo, panelListaTareasCompletadas;
    private JLabel labelNombreTarea, labelDescripcionTarea, labelEtiquetasTarea;
    private JLabel labelCrearNuevaTarea, labelConfirmarTarea, labelEliminarTarea, labelEliminarTodas;
    private JLabel labelMensajesError;
    private JTextField textFieldNombreTarea;
    private JTextPane textPaneDescripcionTarea;

    private Tarea  tareaSeleccionada;

    private final CardLayout cardLayout;

    private final ArrayList<TemplatePanelTareaEspecifica> listaPanelesTareasToDo;
    private ArrayList<TemplatePanelTareaEspecifica> listaPanelesTareasCompletadas;

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
        addListenersGenerales();

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
        columnaTareasToDo.setPreferredSize(new Dimension(200, 0));                // Tiene que ser igual en todas las columnas para que 'gbc.weightx' funcione correctamente
        gbc = setGbc(0, 0, 0.28, 1, GridBagConstraints.BOTH);       // Posición y tamaño de la columna
        add(columnaTareasToDo, gbc);

        /* Título de la columna to do */
        JLabel labelTituloTareasToDo = crearLabelTituloDeColumna("Por hacer");
        columnaTareasToDo.add(labelTituloTareasToDo, BorderLayout.NORTH);

        /* Panel donde están las tareas to do y convertido a JScrollPane */
        this.panelListaTareasToDo = new JPanel();
        JScrollPane scroolPanelListaTareasToDo = crearPanelListaTareas(panelListaTareasToDo);
        columnaTareasToDo.add(scroolPanelListaTareasToDo, BorderLayout.CENTER);

        /* Label para crear nueva tarea */
        this.labelCrearNuevaTarea = crearLabelBoton("Crear nueva tarea");
        columnaTareasToDo.add(labelCrearNuevaTarea, BorderLayout.SOUTH);
    }

    /**
     * Construye la columna de tareas completadas
     */
    private void construirColumnaTareasCompletadas() {
        /* Columna Tareas Completadas */
        JPanel columnaTareasCompletadas = new JPanel();
        columnaTareasCompletadas.setLayout(new BorderLayout());
        columnaTareasCompletadas.setBorder(new MatteBorder(0, 5, 10, 5, sRecursos.getBLANCO()));
        columnaTareasCompletadas.setPreferredSize(new Dimension(200, 0));
        gbc = setGbc(1, 0, 0.28, 1, GridBagConstraints.BOTH);
        add(columnaTareasCompletadas, gbc);

        /* Título de la columna completadas */
        JLabel labelTituloTareasCompletadas = crearLabelTituloDeColumna("Completadas");
        columnaTareasCompletadas.add(labelTituloTareasCompletadas, BorderLayout.NORTH);

        /* Panel donde están las tareas completadas y convertido a JScrollPane */
        this.panelListaTareasCompletadas = new JPanel();
        JScrollPane scroolPanelListaTareasCompletadas = crearPanelListaTareas(panelListaTareasCompletadas);
        columnaTareasCompletadas.add(scroolPanelListaTareasCompletadas, BorderLayout.CENTER);

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
        columnaInformacion.add(cardGeneral, "cardGeneral");

        /* Título de la columna Información Extra en el card Info General */
        JLabel labelTituloInformacionGeneral = crearLabelTituloDeColumna("Información");
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
        columnaInformacion.add(cardTareaSeleccionada, "cardTareaSeleccionada");

        /* Título de la columna Información Extra en el card Tarea Seleccionada */
        JLabel tituloTareaSeleccionada = crearLabelTituloDeColumna("Tarea Seleccionada");
        cardTareaSeleccionada.add(tituloTareaSeleccionada, BorderLayout.NORTH);

        JPanel panelInformacionTarea = new JPanel();
        panelInformacionTarea.setLayout(new GridBagLayout());
        cardTareaSeleccionada.add(panelInformacionTarea, BorderLayout.CENTER);

        this.labelEliminarTarea = crearLabelBoton("Eliminar");
        cardTareaSeleccionada.add(labelEliminarTarea, BorderLayout.SOUTH);

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

        this.textFieldNombreTarea = new JTextField();
        textFieldNombreTarea.setPreferredSize(new Dimension(0, 50));
        textFieldNombreTarea.setText("Nombre de la tarea");
        textFieldNombreTarea.setFont(sRecursos.getMonserratItalic(20));
        textFieldNombreTarea.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNombreTarea.setForeground(sRecursos.getGRANATE());
        textFieldNombreTarea.setBorder(BorderFactory.createMatteBorder(0,1,0,1, sRecursos.getGRANATE()));
        gbc = setGbc(0, 0, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(textFieldNombreTarea, gbc);

        this.textPaneDescripcionTarea = new JTextPane();
        textPaneDescripcionTarea.setEditorKit(new StyledEditorKit());
        textPaneDescripcionTarea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        StyledDocument doc = textPaneDescripcionTarea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(center, 10);
        try{
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat-Italic.ttf"));
            StyleConstants.setFontFamily(center, customFont.getFamily());
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        // Aplicar la alineación centrada
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        textPaneDescripcionTarea.setPreferredSize(new Dimension(0, 50));
        textPaneDescripcionTarea.setBorder(new MatteBorder(1, 1, 1, 1, sRecursos.getGRANATE()));
        textPaneDescripcionTarea.setForeground(sRecursos.getGRANATE());
        textPaneDescripcionTarea.setFont(sRecursos.getMonserratItalic(20));
        textPaneDescripcionTarea.setText("Descripción de la tarea");
        gbc = setGbc(0, 1, 1, 0.95, GridBagConstraints.BOTH);

        panelInformacionCrearNuevaTarea.add(textPaneDescripcionTarea, gbc);

        JPanel panelOpciones = new JPanel();
        panelOpciones.setPreferredSize(new Dimension(0, 50));
        panelOpciones.setBorder(new MatteBorder(5, 5, 0, 5, sRecursos.getBLANCO()));
        panelOpciones.setLayout(new GridLayout(1, 2));
        gbc = setGbc(0, 2, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(panelOpciones, gbc);

        String[] opciones = {"No repetir", "Lunes a viernes", "Todos los días"};
        JComboBox<String> comboRepetible = new JComboBox<>(opciones);
        comboRepetible.setFont(sRecursos.getMontserratPlain(16));
        comboRepetible.setForeground(sRecursos.getGRANATE());
        comboRepetible.setBackground(sRecursos.getBLANCO());
        panelOpciones.add(comboRepetible);

        DatePickerSettings datePickerSettings = new DatePickerSettings();

        datePickerSettings.setAllowKeyboardEditing(false);

        datePickerSettings.setFontValidDate(sRecursos.getMontserratPlain(15));
        datePickerSettings.setFontInvalidDate(sRecursos.getMonserratItalic(15));
        datePickerSettings.setFontCalendarDateLabels(sRecursos.getMontserratPlain(16));
        datePickerSettings.setFontCalendarWeekdayLabels(sRecursos.getMonserratItalic(15));
        datePickerSettings.setFontCalendarWeekNumberLabels(sRecursos.getMonserratItalic(15));

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

        LocalDate localDateHoy = LocalDate.now();
        LocalDate localDateFin = LocalDate.MAX;
        datePickerSettings.setDateRangeLimits(localDateHoy, localDateFin);

        datePicker.setText("Fecha límite");
        panelOpciones.add(datePicker);

        JPanel panelEtiquetas = new JPanel();
        panelEtiquetas.setPreferredSize(new Dimension(0, 50));
        panelEtiquetas.setBorder(new MatteBorder(5, 5, 0, 5, sRecursos.getBLANCO()));
        gbc = setGbc(0, 3, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(panelEtiquetas, gbc);

        this.labelMensajesError = new JLabel();
        labelMensajesError.setPreferredSize(new Dimension(0, 50));
        labelMensajesError.setLayout(new FlowLayout());
        labelMensajesError.setBorder(new MatteBorder(5, 5, 5, 5, sRecursos.getBLANCO()));
        labelMensajesError.setText("Mensaje de error!");
        labelMensajesError.setFont(sRecursos.getMonserratBold(18));
        labelMensajesError.setForeground(sRecursos.getGRANATE());
        labelMensajesError.setHorizontalAlignment(SwingConstants.CENTER);
        gbc = setGbc(0, 4, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(labelMensajesError, gbc);

        cardNuevaTarea.add(panelInformacionCrearNuevaTarea, BorderLayout.CENTER);
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

    private void addListenersGenerales(){

        labelCrearNuevaTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(columnaInformacion, "cardNuevaTarea");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelCrearNuevaTarea.setBackground(sRecursos.getGRIS_CLARO());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelCrearNuevaTarea.setBackground(sRecursos.getBLANCO());
            }
        });

        labelConfirmarTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nombreT = textFieldNombreTarea.getText();
                if (nombreT.equals("Nombre de la tarea")){
                    nombreT = "";
                }
                if (nombreT.isBlank()){
                    labelMensajesError.setText("El nombre de la tarea no puede estar vacío");
                    return;
                }
                if (nombreT.length()>50) {
                    labelMensajesError.setText("El nombre de la tarea no puede tener más de 50 carácteres");
                    return;
                }
                String descripcionT = textPaneDescripcionTarea.getText();
                if (descripcionT.equals("Descripción de la tarea")){
                    descripcionT = "";
                }

                Tarea tarea = new Tarea(nombreT, descripcionT, gestorTareas.getUsuario().getNombreU());
                gestorTareas.crearTarea(tarea);
                Tarea tareaReal = gestorTareas.getUltimaTarea();        // Obtiene la tarea creada de la base de datos para obtener su idT y los datos automáticos
                TemplatePanelTareaEspecifica panel = new TemplatePanelTareaEspecifica(tareaReal);

                addListenerATareas(tareaReal, panel);
                actualizarVistaTareas();

                textFieldNombreTarea.setText("");
                textPaneDescripcionTarea.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelConfirmarTarea.setBackground(sRecursos.getGRIS_CLARO());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelConfirmarTarea.setBackground(sRecursos.getBLANCO());
            }
        });

        labelEliminarTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelEliminarTarea.setBackground(sRecursos.getGRIS_CLARO());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelEliminarTarea.setBackground(sRecursos.getBLANCO());
            }
        });

        labelEliminarTodas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panelListaTareasCompletadas.getComponentCount()==0){
                    System.out.println("No hay tareas completadas");
                    return;
                }
                int opcion = JOptionPane.showConfirmDialog(null, "Deseas eliminar todas las tareas?", "Eliminar todas las tareas", JOptionPane.YES_NO_OPTION);
                if (opcion==JOptionPane.YES_OPTION){
                    for (TemplatePanelTareaEspecifica panel : listaPanelesTareasCompletadas){
                        if(gestorTareas.eliminarTarea(panel.getTarea())){
                            panelListaTareasCompletadas.remove(panel);
                            actualizarVistaTareas();
                        }
                    }
                    listaPanelesTareasCompletadas = new ArrayList<>();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelEliminarTodas.setBackground(sRecursos.getGRIS_CLARO());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelEliminarTodas.setBackground(sRecursos.getBLANCO());
            }
        });

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

    private JLabel crearLabelBoton(String texto){
        JLabel labelBoton = new JLabel(texto);
        labelBoton.setFont(sRecursos.getMonserratItalic(20));
        labelBoton.setHorizontalAlignment(SwingConstants.CENTER);
        labelBoton.setBackground(sRecursos.getBLANCO());
        labelBoton.setForeground(sRecursos.getGRANATE());
        labelBoton.setPreferredSize(new Dimension(getWidth(), 30));
        labelBoton.setMaximumSize(new Dimension(getWidth(), 30));
        labelBoton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, sRecursos.getGRANATE()));
        labelBoton.setCursor(sRecursos.getCursorMano());
        labelBoton.setOpaque(true);
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