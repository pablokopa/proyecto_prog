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
    private GridBagConstraints gbc;
    private GestorTareas gestorTareas;
    private Usuario usuario;

    private JPanel panelTareasToDo, panelTareasCompletadas, panelInformacionTarea, panelListaTareasToDo, panelListaTareasCompletadas;
    private JLabel labelTituloListaTareasToDo, labelTituloInformacionTarea, labelCrearNuevaTarea, labelTituloListaTareasCompletadas;

    public VistaTareas(GestorTareas gestorTareas) {
        this.gestorTareas = gestorTareas;
        this.usuario = gestorTareas.getUsuario();

        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        crearPaneles();
        crearLabels();
        addTareas();
    }

    public void crearPaneles() {
        this.panelTareasToDo = new JPanel();
        panelTareasToDo.setLayout(new BorderLayout());
        panelTareasToDo.setBorder(new MatteBorder(0, 10, 10, 5, sRecursos.getBLANCO()));
        panelTareasToDo.setBackground(Color.GREEN);
        gbc.gridx = 0;      // Columna 1
        gbc.gridy = 0;      // Fila 0
        gbc.weightx = 0.2;  // Ocupa x% del espacio horizontal
        gbc.weighty = 1;    // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelTareasToDo, gbc);

        this.panelTareasCompletadas = new JPanel();
        panelTareasCompletadas.setLayout(new BorderLayout());
        panelTareasCompletadas.setBorder(new MatteBorder(0, 5, 10, 5, sRecursos.getBLANCO()));
        panelTareasCompletadas.setBackground(Color.RED);
        gbc.gridx = 1;      // Columna 2
        gbc.gridy = 0;      // Fila 0
        gbc.weightx = 0.2;  // Ocupa x% del espacio horizontal
        gbc.weighty = 1;    // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelTareasCompletadas, gbc);

        // Los parámetros de gbc no sobrescritos también se aplican aquí (todos excepto gridx y weightx)
        this.panelInformacionTarea = new JPanel();
        panelInformacionTarea.setLayout(new BorderLayout());
        panelInformacionTarea.setBackground(Color.BLUE);
        panelInformacionTarea.setBorder(new MatteBorder(0, 5, 10, 10, sRecursos.getBLANCO()));
        gbc.gridx = 2;          // Columna 3
        gbc.gridy = 0;          // Fila 0
        gbc.weightx = 0.6;      // Ocupa x% del espacio horizontal
        gbc.weighty = 1;        // Ocupa y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelInformacionTarea, gbc);

        this.panelListaTareasToDo = new JPanel();
        panelListaTareasToDo.setLayout(new BoxLayout(panelListaTareasToDo, BoxLayout.Y_AXIS));
        panelListaTareasToDo.setBackground(Color.yellow);
        panelTareasToDo.add(panelListaTareasToDo, BorderLayout.CENTER);

        this.panelListaTareasCompletadas = new JPanel();
        panelListaTareasCompletadas.setLayout(new BoxLayout(panelListaTareasCompletadas, BoxLayout.Y_AXIS));
        panelListaTareasCompletadas.setBackground(Color.yellow);
        panelTareasCompletadas.add(panelListaTareasCompletadas, BorderLayout.CENTER);
    }

    private void crearLabels() {
        this.labelTituloListaTareasToDo = new JLabel("Tareas por hacer");
        labelTituloListaTareasToDo.setFont(sRecursos.getMonserratBold(25));
        labelTituloListaTareasToDo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTituloListaTareasToDo.setBackground(Color.lightGray);
        labelTituloListaTareasToDo.setOpaque(true);
        panelTareasToDo.add(labelTituloListaTareasToDo, BorderLayout.NORTH);

        this.labelTituloListaTareasCompletadas = new JLabel("Tareas completadas");
        labelTituloListaTareasCompletadas.setFont(sRecursos.getMonserratBold(25));
        labelTituloListaTareasCompletadas.setHorizontalAlignment(SwingConstants.CENTER);
        labelTituloListaTareasCompletadas.setBackground(Color.lightGray);
        labelTituloListaTareasCompletadas.setOpaque(true);
        panelTareasCompletadas.add(labelTituloListaTareasCompletadas, BorderLayout.NORTH);

        this.labelCrearNuevaTarea = new JLabel("Crear nueva tarea");
        labelCrearNuevaTarea.setFont(sRecursos.getMonserratBold(25));
        labelCrearNuevaTarea.setHorizontalAlignment(SwingConstants.CENTER);
        labelCrearNuevaTarea.setBackground(Color.lightGray);
        labelCrearNuevaTarea.setOpaque(true);
        panelTareasToDo.add(labelCrearNuevaTarea, BorderLayout.SOUTH);

        this.labelTituloInformacionTarea = new JLabel("Información");
        labelTituloInformacionTarea.setFont(sRecursos.getMonserratBold(25));
        labelTituloInformacionTarea.setHorizontalAlignment(SwingConstants.CENTER);
        labelTituloInformacionTarea.setBackground(Color.lightGray);
        labelTituloInformacionTarea.setOpaque(true);
        panelInformacionTarea.add(labelTituloInformacionTarea, BorderLayout.NORTH);
    }

    private void addTareas() {
        gestorTareas.getTareasDeBase();
        ArrayList<Tarea> listaTareas = gestorTareas.getListaTareas();

        for (Tarea tarea : listaTareas) {
            PanelTareaEspecifica panelTarea = new PanelTareaEspecifica(tarea, gestorTareas);

            /* Completa la tarea al hacer click en el check y lo cambia en consecuencia */
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

            if (tarea.getCompletadaT()){
                panelListaTareasCompletadas.add(panelTarea);
            } else {
                panelListaTareasToDo.add(panelTarea);
            }
        }
    }
}