package app.view.principal;

import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.model.usuarios.Usuario;
import services.ObjGraficos;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VistaTareas extends JPanel {
    private final Recursos sRecursos = Recursos.getService();
    private GridBagConstraints gbc;
    private GestorTareas gestorTareas;
    private Usuario usuario;

    private JPanel panelTareas, panelInformacion, panelTareasTitulo, panelTareasTareas;

    public VistaTareas(GestorTareas gestorTareas) {
        this.gestorTareas = gestorTareas;
        this.usuario = gestorTareas.getUsuario();

        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        crearPaneles();
        addTareas();
    }

    public void crearPaneles() {
        this.panelTareas = new JPanel();
        panelTareas.setLayout(new BorderLayout());
        panelTareas.setBackground(Color.GREEN);
        gbc.gridx = 0;      // Columna 0
        gbc.gridy = 0;      // Fila 0
        gbc.weightx = 0.4;  // Ocupa x% del espacio horizontal
        gbc.weighty = 1;    // Ocupa el y% del espacio vertical
        gbc.fill = GridBagConstraints.BOTH;     // Se expande en ambas direcciones
        add(panelTareas, gbc);

        // Los parámetros de gbc no sobrescritos también se aplican aquí (todos excepto gridx y weightx)
        this.panelInformacion = new JPanel();
        panelInformacion.setBackground(Color.BLUE);
        gbc.gridx = 1;          // Columna 1
        gbc.weightx = 0.6;      // Ocupa 60% del espacio horizontal
        add(panelInformacion, gbc);

        JLabel labelTareasTitulo = new JLabel("Tareas");
        labelTareasTitulo.setFont(sRecursos.getMonserratBold(25));
        labelTareasTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTareasTitulo.setBackground(Color.lightGray);
        labelTareasTitulo.setOpaque(true);
        panelTareas.add(labelTareasTitulo, BorderLayout.NORTH);

        this.panelTareasTareas = new JPanel();
        panelTareasTareas.setLayout(new BoxLayout(panelTareasTareas, BoxLayout.Y_AXIS));
        panelTareasTareas.setBackground(Color.yellow);
        panelTareas.add(panelTareasTareas, BorderLayout.CENTER);
    }

    private void addTareas() {
        gestorTareas.getTareasDeBase();
        ArrayList<Tarea> listaTareas = gestorTareas.getListaTareas();

        for (Tarea tarea : listaTareas) {
            JPanel panelTarea = ObjGraficos.construirPanelTarea(tarea);
            panelTareasTareas.add(panelTarea);
        }
    }
}
