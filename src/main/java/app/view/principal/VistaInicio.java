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
        this.setBackground(sRecursos.getBLANCO());
        this.setBorder(BorderFactory.createMatteBorder(1, 0,0,0, sRecursos.getGRANATE_MID_LIGHT().brighter()));

        crearLabelCreditos();
    }

    /**
     * Método para crear los labels de los créditos
     */
    private void crearLabelCreditos(){
        JPanel panelCreditos = new JPanel();
        panelCreditos.setBackground(sRecursos.getBLANCO());
        panelCreditos.setLayout(new BoxLayout(panelCreditos, BoxLayout.Y_AXIS));

        JLabel label3 = construirLabel("proyecto de programación", 64, "italic");
        panelCreditos.add(label3);

        JLabel label4 = construirLabel("To-Do", 64, "bold");
        panelCreditos.add(label4);

        JLabel labelEspacio = construirLabel("", 64, "");
        panelCreditos.add(labelEspacio);

        JLabel label1 = construirLabel("Made by", 64, "italic");
        panelCreditos.add(label1);

        JLabel label2 = construirLabel("Enrique & Pablo", 64, "bold");
        panelCreditos.add(label2);

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
