package app.view.principal;

import app.controller.ControladorTareas;
import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.view.templates.TemplatePanelTareaEspecifica;
import services.Recursos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class VistaTareas extends JPanel {

    private final ControladorTareas controladorTareas;

    private final Recursos sRecursos = Recursos.getService();
    private GridBagConstraints gbc;
    private final GestorTareas gestorTareas;

    private JPanel columnaInformacion;
    private JPanel panelListaTareasToDo, panelListaTareasCompletadas;
    private JLabel labelEtiquetasTarea;
    private JLabel labelCrearNuevaTarea, labelConfirmarTarea, labelModificarTarea, labelEliminarTarea, labelEliminarTodas;
    private JLabel labelMensajesError, labelMensajesErrorSeleccionada;
    private JLabel labelFechaCreacion, labelFechaFinalizacion;
    private JTextField textFieldNombreTarea, textFieldNombreTareaSelecionada;
    private JTextPane textPaneDescripcionTarea, textPaneDescripcionTareaSeleccionada;
    private JComboBox<String> comboEtiquetasNueva, comboEtiquetasSeleccionada;

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
        JPanel columnaTareasToDo = construirColumnasTareas("Por hacer", 0);

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
        JPanel columnaTareasCompletadas = construirColumnasTareas("Completadas", 1);

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

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BorderLayout(0, 1));

        this.labelModificarTarea = crearLabelBoton("Modificar");
        panelBotones.add(labelModificarTarea, BorderLayout.NORTH);
        this.labelEliminarTarea = crearLabelBoton("Eliminar");
        panelBotones.add(labelEliminarTarea, BorderLayout.SOUTH);
        cardTareaSeleccionada.add(panelBotones, BorderLayout.SOUTH);

        this.textFieldNombreTareaSelecionada = new JTextField();
        textFieldNombreTareaSelecionada.setPreferredSize(new Dimension(0, 50));
        textFieldNombreTareaSelecionada.setFont(sRecursos.getMontserratMedium(20));
        textFieldNombreTareaSelecionada.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNombreTareaSelecionada.setForeground(sRecursos.getGRANATE());
        textFieldNombreTareaSelecionada.setBorder(BorderFactory.createMatteBorder(0,1,0,1, sRecursos.getGRANATE()));
        gbc = setGbc(0, 0, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(textFieldNombreTareaSelecionada, gbc);

        this.textPaneDescripcionTareaSeleccionada = new JTextPane();
        textPaneDescripcionTareaSeleccionada.setEditorKit(new StyledEditorKit());
        textPaneDescripcionTareaSeleccionada.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        textPaneDescripcionTareaSeleccionada.setPreferredSize(new Dimension(0, 50));
        textPaneDescripcionTareaSeleccionada.setBorder(new MatteBorder(1, 1, 1, 1, sRecursos.getGRANATE()));
        textPaneDescripcionTareaSeleccionada.setForeground(sRecursos.getGRANATE());
        textPaneDescripcionTareaSeleccionada.setFont(sRecursos.getMontserratMedium(20));
        StyledDocument doc = textPaneDescripcionTareaSeleccionada.getStyledDocument();
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

        JPanel panelOpciones = new JPanel();
        panelOpciones.setPreferredSize(new Dimension(0, 50));
        panelOpciones.setBorder(new MatteBorder(5, 5, 0, 5, sRecursos.getBLANCO()));
        panelOpciones.setLayout(new GridLayout(1, 2));
        gbc = setGbc(0, 3, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(panelOpciones, gbc);

        String[] opciones = {"No repetir", "Lunes a viernes", "Todos los días"};
        JComboBox<String> comboRepetible = new JComboBox<>(opciones);
        comboRepetible.setFont(sRecursos.getMontserratPlain(16));
        comboRepetible.setForeground(sRecursos.getGRANATE());
        comboRepetible.setBackground(sRecursos.getBLANCO());
        panelOpciones.add(comboRepetible);

        DatePickerSettings datePickerSettings = new DatePickerSettings();

        datePickerSettings.setAllowKeyboardEditing(false);

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

        LocalDate localDateHoy = LocalDate.now();
        LocalDate localDateFin = LocalDate.MAX;
        datePickerSettings.setDateRangeLimits(localDateHoy, localDateFin);

        datePicker.setText("Fecha límite");
        panelOpciones.add(datePicker);

        JPanel panelEtiquetas = new JPanel();
        panelEtiquetas.setToolTipText("Panel donde se encontrará información de las etiquetas");
        panelEtiquetas.setPreferredSize(new Dimension(0, 50));
        panelEtiquetas.setBorder(new MatteBorder(5, 5, 0, 5, sRecursos.getBLANCO()));
        gbc = setGbc(0, 4, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(panelEtiquetas, gbc);

        String[] opciones2 = {"Sin etiqueta", "Importante / Urgente", "Importante / No urgente", "No importante / Urgente", "No importante / No urgente"};
        this.comboEtiquetasSeleccionada = new JComboBox<>(opciones2);
        comboEtiquetasSeleccionada.setFont(sRecursos.getMontserratPlain(16));
        comboEtiquetasSeleccionada.setForeground(sRecursos.getGRANATE());
        comboEtiquetasSeleccionada.setBackground(sRecursos.getBLANCO());
        panelEtiquetas.add(comboEtiquetasSeleccionada);


        JPanel panelMensajesError = new JPanel();
        panelMensajesError.setLayout(new BorderLayout());
        panelMensajesError.setBackground(sRecursos.getBLANCO());
        panelMensajesError.setPreferredSize(new Dimension(0, 50));
        panelMensajesError.setBorder(new EmptyBorder(5,5,5,5));
        gbc = setGbc(0, 5, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionTarea.add(panelMensajesError, gbc);

        this.labelMensajesErrorSeleccionada = new JLabel();
        labelMensajesErrorSeleccionada.setFont(sRecursos.getMontserratItalic(15));
        labelMensajesErrorSeleccionada.setForeground(sRecursos.getGRANATE());
        labelMensajesErrorSeleccionada.setHorizontalAlignment(SwingConstants.CENTER);
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

        this.textFieldNombreTarea = new JTextField();
        textFieldNombreTarea.setPreferredSize(new Dimension(0, 50));
        textFieldNombreTarea.setText("Nombre de la tarea");
        textFieldNombreTarea.setFont(sRecursos.getMontserratMedium(20));
        textFieldNombreTarea.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNombreTarea.setForeground(sRecursos.getGRANATE());
        textFieldNombreTarea.setBorder(BorderFactory.createMatteBorder(0,1,0,1, sRecursos.getGRANATE()));
        gbc = setGbc(0, 0, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(textFieldNombreTarea, gbc);

        this.textPaneDescripcionTarea = new JTextPane();
        textPaneDescripcionTarea.setEditorKit(new StyledEditorKit());
        textPaneDescripcionTarea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        textPaneDescripcionTarea.setPreferredSize(new Dimension(0, 50));
        textPaneDescripcionTarea.setBorder(new MatteBorder(1, 1, 1, 1, sRecursos.getGRANATE()));
        textPaneDescripcionTarea.setForeground(sRecursos.getGRANATE());
        textPaneDescripcionTarea.setFont(sRecursos.getMontserratMedium(20));
        textPaneDescripcionTarea.setText("Descripción de la tarea");
        StyledDocument doc = textPaneDescripcionTarea.getStyledDocument();
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

        LocalDate localDateHoy = LocalDate.now();
        LocalDate localDateFin = LocalDate.MAX;
        datePickerSettings.setDateRangeLimits(localDateHoy, localDateFin);

        datePicker.setText("Fecha límite");
        panelOpciones.add(datePicker);

        JPanel panelEtiquetas = new JPanel();
        panelEtiquetas.setPreferredSize(new Dimension(0, 50));
        panelEtiquetas.setBorder(new MatteBorder(5, 5, 0, 5, sRecursos.getBLANCO()));
        panelEtiquetas.setBackground(Color.blue);
        gbc = setGbc(0, 3, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(panelEtiquetas, gbc);

        String[] opciones2 = {"Sin etiqueta", "Importante / Urgente", "Importante / No urgente", "No importante / Urgente", "No importante / No urgente"};
        this.comboEtiquetasNueva = new JComboBox<>(opciones2);
        comboEtiquetasNueva.setFont(sRecursos.getMontserratPlain(16));
        comboEtiquetasNueva.setForeground(sRecursos.getGRANATE());
        comboEtiquetasNueva.setBackground(sRecursos.getBLANCO());
        panelEtiquetas.add(comboEtiquetasNueva);

        JPanel panelMensajesError = new JPanel();
        panelMensajesError.setLayout(new BorderLayout());
        panelMensajesError.setBackground(sRecursos.getBLANCO());
        panelMensajesError.setPreferredSize(new Dimension(0, 50));
        panelMensajesError.setBorder(new EmptyBorder(5,5,5,5));
        gbc = setGbc(0, 4, 1, 0.01, GridBagConstraints.BOTH);
        panelInformacionCrearNuevaTarea.add(panelMensajesError, gbc);

        this.labelMensajesError = new JLabel();
        labelMensajesError.setFont(sRecursos.getMontserratItalic(15));
        labelMensajesError.setForeground(sRecursos.getGRANATE());
        labelMensajesError.setHorizontalAlignment(SwingConstants.CENTER);
        panelMensajesError.add(labelMensajesError, BorderLayout.CENTER);
    }

    public ArrayList<TemplatePanelTareaEspecifica> getListaPanelesTareasToDo() {
        return listaPanelesTareasToDo;
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

                String nombreE = comboEtiquetasNueva.getSelectedItem().toString();

                Tarea tarea = new Tarea(nombreT, descripcionT, gestorTareas.getUsuario().getNombreU(), nombreE);
                gestorTareas.crearTarea(tarea);
                Tarea tareaReal = gestorTareas.getUltimaTarea();        // Obtiene la tarea creada de la base de datos para obtener su idT y los datos automáticos
                TemplatePanelTareaEspecifica panel = new TemplatePanelTareaEspecifica(tareaReal);

                addListenerATareas(tareaReal, panel);
                actualizarVistaTareas();

                textFieldNombreTarea.setText("");
                textPaneDescripcionTarea.setText("");
            }
        });

        labelModificarTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nombreTOriginal = tareaSeleccionada.getNombreT();
                String descripcionTOriginal = tareaSeleccionada.getDescripcionT();
                String nombreEOriginal = tareaSeleccionada.getNombreE();

                String nombreT = textFieldNombreTareaSelecionada.getText();
                if (nombreT.isBlank()){
                    labelMensajesErrorSeleccionada.setText("El nombre de la tarea no puede estar vacío");
                    return;
                }
                if (nombreT.length()>50) {
                    labelMensajesErrorSeleccionada.setText("El nombre de la tarea no puede tener más de 50 carácteres");
                    return;
                }
                String descripcionT = textPaneDescripcionTareaSeleccionada.getText();
                String nombreE = comboEtiquetasSeleccionada.getSelectedItem().toString();

                if (nombreT.equals(nombreTOriginal) && descripcionT.equals(descripcionTOriginal) && nombreE.equals(nombreEOriginal)){
                    labelMensajesErrorSeleccionada.setText("No se ha modificado ningún campo");
                    return;
                }

                Tarea tarea = new Tarea(
                        tareaSeleccionada.getIdT(),
                        nombreT,
                        descripcionT,
                        tareaSeleccionada.getFechaCreacionT(),
                        tareaSeleccionada.getFechaFinalizacionT(),
                        tareaSeleccionada.getCompletadaT(),
                        gestorTareas.getUsuario().getNombreU(),
                        nombreE
                );

                TemplatePanelTareaEspecifica panelTemporal = new TemplatePanelTareaEspecifica(tarea);
                if (tareaSeleccionada.getCompletadaT()){
                    TemplatePanelTareaEspecifica panelReal = listaPanelesTareasCompletadas.get(listaPanelesTareasCompletadas.indexOf(panelTemporal));
//                    listaPanelesTareasCompletadas.remove(panelReal);
//                    listaPanelesTareasCompletadas.add(panelTemporal);
                    panelReal.getTarea().setNombreT(nombreT);
                    panelReal.getTarea().setDescripcionT(descripcionT);
                    panelReal.getTarea().setNombreE(nombreE);
                    panelReal.setLabelTituloText(nombreT);
//                    panelReal = panelTemporal;
                } else {
                    TemplatePanelTareaEspecifica panelReal = listaPanelesTareasToDo.get(listaPanelesTareasToDo.indexOf(panelTemporal));
//                    listaPanelesTareasToDo.remove(panelReal);
//                    listaPanelesTareasToDo.add(panelTemporal);
                    panelReal.getTarea().setNombreT(nombreT);
                    panelReal.getTarea().setDescripcionT(descripcionT);
                    panelReal.getTarea().setNombreE(nombreE);
                    panelReal.setLabelTituloText(nombreT);
//                    panelReal = panelTemporal;
                }


                gestorTareas.modificarTarea(tarea);

                actualizarVistaTareas();
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
                        removeDeColumnaCompletada(panelReal);
                    } else {
                        TemplatePanelTareaEspecifica panelReal = listaPanelesTareasToDo.get(listaPanelesTareasToDo.indexOf(panelTemporal));
                        removeDeColumnaToDo(panelReal);
                    }
                    gestorTareas.eliminarTarea(tareaSeleccionada);
                    actualizarVistaTareas();

                    cardLayout.show(columnaInformacion, "cardGeneral");
                }
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
                    for (int i=listaPanelesTareasCompletadas.size()-1; i>=0; i--){
                        if (gestorTareas.eliminarTarea(listaPanelesTareasCompletadas.get(i).getTarea())){
                            removeDeColumnaCompletada(listaPanelesTareasCompletadas.get(i));
                            actualizarVistaTareas();
                        }
                    }
                }
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


    private JPanel construirColumnasTareas(String titulo, int columna) {
        /* Columna Tareas Completadas */
        JPanel panelColumna = new JPanel();
        panelColumna.setLayout(new BorderLayout());
        panelColumna.setBorder(new MatteBorder(0, 5, 10, 5, sRecursos.getBLANCO()));
        panelColumna.setPreferredSize(new Dimension(200, 0));
        gbc = setGbc(columna, 0, 0.28, 1, GridBagConstraints.BOTH);
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

    private void removeDeColumnaToDo(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasToDo.remove(panelTarea);
        listaPanelesTareasToDo.remove(panelTarea);
    }


    private void removeDeColumnaCompletada(TemplatePanelTareaEspecifica panelTarea){
        panelListaTareasCompletadas.remove(panelTarea);
        listaPanelesTareasCompletadas.remove(panelTarea);
    }

    /**
     * Cambia una tarea de columna de tareas por hacer a tareas completadas
     * @param panelTarea Panel de la tarea que se quiere cambiar de columna
     */
    private void cambiarAColumnaToDo(TemplatePanelTareaEspecifica panelTarea){
        addAColumnaToDo(panelTarea);
        removeDeColumnaCompletada(panelTarea);
    }

    /**
     * Cambia una tarea de columna de tareas completadas a tareas por hacer
     * @param panelTarea Panel de la tarea que se quiere cambiar de columna
     */
    private void cambiarAColumnaCompletada(TemplatePanelTareaEspecifica panelTarea){
        addAColumnaCompletada(panelTarea);
        removeDeColumnaToDo(panelTarea);
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