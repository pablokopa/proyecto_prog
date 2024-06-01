package app.view.principal;

import services.Recursos;

import javax.swing.*;
import java.awt.*;

/**
 * Vista de inicio, utilizada para mostrar los créditos del proyecto
 */
public class VistaInicio extends JPanel {
    private final Recursos sRecursos = Recursos.getService();

    /**
     * Constructor de la clase
     */
    public VistaInicio() {
        this.setLayout(new GridBagLayout()); // Cambiar a GridBagLayout

        crearLabelCreditos();
    }

    /**
     * Método para crear los labels de los créditos
     */
    private void crearLabelCreditos(){
        JPanel panelCreditos = new JPanel();
        panelCreditos.setLayout(new BoxLayout(panelCreditos, BoxLayout.Y_AXIS));

        JLabel labelTitulo = construirLabel("proyecto de programación", 64, "italic");
        panelCreditos.add(labelTitulo);

        JLabel labelProyecto = construirLabel("To-Do", 64, "bold");
        panelCreditos.add(labelProyecto);

        JLabel labelEspacio = construirLabel("", 64, "");
        panelCreditos.add(labelEspacio);

        JLabel labelTitulo2 = construirLabel("Made by", 64, "italic");
        panelCreditos.add(labelTitulo2);

        JLabel labelMakers = construirLabel("Enrique & Pablo", 64, "bold");
        panelCreditos.add(labelMakers);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(panelCreditos, gbc);
    }

    /**
     * Método para construir un label
     * @param texto Texto del label
     * @param sizeLetra Tamaño de la letra
     * @param tipoLetra Tipo de letra
     * @return JLabel
     */
    private JLabel construirLabel(String texto, int sizeLetra, String tipoLetra) {
        JLabel label = new JLabel(texto);
        switch (tipoLetra) {
            case "bold" -> label.setFont(sRecursos.getMontserratBold(sizeLetra));
            case "italic" -> label.setFont(sRecursos.getMontserratItalic(sizeLetra));
            default -> label.setFont(sRecursos.getMontserratPlain(sizeLetra));
        }
        label.setPreferredSize(new Dimension(1000, 100));
        label.setMaximumSize(new Dimension(1000, 100));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(sRecursos.getGRANATE());
        return label;
    }
}
