package app.view.principal;

import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.model.usuarios.Usuario;
import app.view.pruebas.PanelTareaEspecifica;
import services.Recursos;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VistaTareas extends JPanel {
    private final Recursos sRecursos = Recursos.getService();
    private final GridBagConstraints gbc;
    private final GestorTareas gestorTareas;
    private final Usuario usuario;

    private JPanel panelColumnaTareasToDo, panelColumnaTareasCompletadas, panelColumnaInformacionExtra;
    private JPanel panelListaTareasToDo, panelListaTareasCompletadas, panelInformacionNuevaTarea;
    private JScrollPane scroolPanelListaTareasToDo, scroolPanelListaTareasCompletadas;
    private JLabel labelTituloTareasToDo, labelCrearNuevaTarea, labelTituloTareasCompletadas, labelTituloInformacionNuevaTarea;

    private final CardLayout cardLayout;

    public VistaTareas(GestorTareas gestorTareas) {
        this.gestorTareas = gestorTareas;
        this.usuario = gestorTareas.getUsuario();
        this.cardLayout = new CardLayout();

        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        construirColumnaTareasToDo();
        construirColumnaTareasCompletadas();
        construirColumnaInformacionExtra();
        addTareas();
    }

    public void crearPaneles() {
    }

    private void construirColumnaTareasToDo() {
        /* Columna Tareas To Do */
        this.panelColumnaTareasToDo = new JPanel();
        panelColumnaTareasToDo.setLayout(new BorderLayout());
        panelColumnaTareasToDo.setBorder(new MatteBorder(0, 10, 10, 5, sRecursos.getBLANCO()));
        panelColumnaTareasToDo.setBackground(Color.GREEN);
        panelColumnaTareasToDo.setPreferredSize(new Dimension(200, 0));
        gbc.gridx = 0;      // Columna 1
        gbc.gridy = 0;      // Fila 0
        gbc.weightx = 0.3;  // Ocupa x% del espacio horizontal
        gbc.weighty = 1;    // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelColumnaTareasToDo, gbc);

        /* Título de la columna to do */
        this.labelTituloTareasToDo = new JLabel("Tareas por hacer");
        labelTituloTareasToDo.setFont(sRecursos.getMonserratBold(25));
        labelTituloTareasToDo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTituloTareasToDo.setBackground(Color.lightGray);
        labelTituloTareasToDo.setOpaque(true);
        panelColumnaTareasToDo.add(labelTituloTareasToDo, BorderLayout.NORTH);

        /* Panel donde están las tareas to do y convertido a JScrollPane */
        this.panelListaTareasToDo = new JPanel();
        panelListaTareasToDo.setLayout(new BoxLayout(panelListaTareasToDo, BoxLayout.Y_AXIS));
        panelListaTareasToDo.setBackground(Color.yellow);

        this.scroolPanelListaTareasToDo = new JScrollPane(panelListaTareasToDo);
        scroolPanelListaTareasToDo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroolPanelListaTareasToDo.setBorder(null);
        panelColumnaTareasToDo.add(scroolPanelListaTareasToDo, BorderLayout.CENTER);

        /* Label para crear nueva tarea */
        this.labelCrearNuevaTarea = new JLabel("Crear nueva tarea");
        labelCrearNuevaTarea.setFont(sRecursos.getMonserratBold(25));
        labelCrearNuevaTarea.setHorizontalAlignment(SwingConstants.CENTER);
        labelCrearNuevaTarea.setBackground(Color.lightGray);
        labelCrearNuevaTarea.setOpaque(true);
        panelColumnaTareasToDo.add(labelCrearNuevaTarea, BorderLayout.SOUTH);

        labelCrearNuevaTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                gestorTareas.crearTarea();
            }
        });
    }

    private void construirColumnaTareasCompletadas() {
        /* Columna Tareas Completadas */
        this.panelColumnaTareasCompletadas = new JPanel();
        panelColumnaTareasCompletadas.setLayout(new BorderLayout());
        panelColumnaTareasCompletadas.setBorder(new MatteBorder(0, 5, 10, 5, sRecursos.getBLANCO()));
        panelColumnaTareasCompletadas.setBackground(Color.RED);
        panelColumnaTareasCompletadas.setPreferredSize(new Dimension(200, 0));
        gbc.gridx = 1;      // Columna 2
        gbc.gridy = 0;      // Fila 0
        gbc.weightx = 0.3;  // Ocupa x% del espacio horizontal
        gbc.weighty = 1;    // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelColumnaTareasCompletadas, gbc);

        /* Título de la columna completadas */
        this.labelTituloTareasCompletadas = new JLabel("Tareas completadas");
        labelTituloTareasCompletadas.setFont(sRecursos.getMonserratBold(25));
        labelTituloTareasCompletadas.setHorizontalAlignment(SwingConstants.CENTER);
        labelTituloTareasCompletadas.setBackground(Color.lightGray);
        labelTituloTareasCompletadas.setOpaque(true);
        panelColumnaTareasCompletadas.add(labelTituloTareasCompletadas, BorderLayout.NORTH);

        /* Panel donde están las tareas completadas y convertido a JScrollPane */
        this.panelListaTareasCompletadas = new JPanel();
        panelListaTareasCompletadas.setLayout(new BoxLayout(panelListaTareasCompletadas, BoxLayout.Y_AXIS));
        panelListaTareasCompletadas.setBackground(Color.yellow);

        this.scroolPanelListaTareasCompletadas = new JScrollPane(panelListaTareasCompletadas);
        scroolPanelListaTareasCompletadas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroolPanelListaTareasCompletadas.setBorder(null);
        panelColumnaTareasCompletadas.add(scroolPanelListaTareasCompletadas, BorderLayout.CENTER);
    }

    private void construirColumnaInformacionExtra() {
        /* Columna Información Extra */
        this.panelColumnaInformacionExtra = new JPanel();
        panelColumnaInformacionExtra.setLayout(cardLayout);
        panelColumnaInformacionExtra.setBackground(Color.BLUE);
        panelColumnaInformacionExtra.setBorder(new MatteBorder(0, 5, 10, 10, sRecursos.getBLANCO()));
        panelColumnaInformacionExtra.setPreferredSize(new Dimension(200, 0));
        gbc.gridx = 2;          // Columna 3
        gbc.gridy = 0;          // Fila 0
        gbc.weightx = 0.4;      // Ocupa x% del espacio horizontal
        gbc.weighty = 1;        // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelColumnaInformacionExtra, gbc);

        /* Título de la columna información extra */
        this.labelTituloInformacionNuevaTarea = new JLabel("Nueva tarea");
        labelTituloInformacionNuevaTarea.setFont(sRecursos.getMonserratBold(25));
        labelTituloInformacionNuevaTarea.setHorizontalAlignment(SwingConstants.CENTER);
        labelTituloInformacionNuevaTarea.setBackground(Color.lightGray);
        labelTituloInformacionNuevaTarea.setOpaque(true);
        panelInformacionNuevaTarea.add(labelTituloInformacionNuevaTarea, BorderLayout.NORTH);

        /* Panel donde se muestra la información de la tarea seleccionada */
        this.panelInformacionNuevaTarea = new JPanel();
        panelInformacionNuevaTarea.setLayout(new BorderLayout());
        panelInformacionNuevaTarea.setBackground(Color.orange);
        panelColumnaInformacionExtra.add(panelInformacionNuevaTarea, "Crear nueva tarea");
    }

    /**
     * Recupera las tareas del usuario de la base de datos, y les añade las funciones (Listener) necesarias
     */
    private void addTareas() {
        /* Obtiene las tareas del usuario de la base de datos y las guarda en un ArrayList */
        gestorTareas.getTareasDeBase();
        ArrayList<Tarea> listaTareas = gestorTareas.getListaTareas();

        /* Recorre la lista de tareas y utiliza el template PanelTareaEspecífica para mostrarlas */
        for (Tarea tarea : listaTareas) {
            PanelTareaEspecifica panelTarea = new PanelTareaEspecifica(tarea, gestorTareas);

            /* Añade un MouseListener al icono del check que completa la tarea al hacer click sobre él y cambia la tarea de columna */
            panelTarea.getLabelImagen().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (gestorTareas.completarTarea(tarea)){
                        if (tarea.getCompletadaT()){
                            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheck());
                            panelListaTareasCompletadas.add(panelTarea);
                            panelListaTareasToDo.remove(panelTarea);
                        } else {
                            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheckSinCheck());
                            panelListaTareasToDo.add(panelTarea);
                            panelListaTareasCompletadas.remove(panelTarea);
                        }
                    }
                    repaint();
                    revalidate();
                }
            });

            panelTarea.getPanelTarea().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Click en " + tarea.getNombreT());
                    System.out.println(tarea);
                }
            });

            /* Al iniciar la aplicación; si la tarea está completada la añade al panel de tareas completadas, si no, la añade al panel de tareas por hacer */
            if (tarea.getCompletadaT()){
                panelListaTareasCompletadas.add(panelTarea);
            } else {
                panelListaTareasToDo.add(panelTarea);
            }
        }
    }
}