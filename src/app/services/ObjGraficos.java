package app.services;

import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;

/**
 * Esta clase es un singleton que proporciona métodos para construir componentes de la interfaz de usuario.
 * Los componentes que se pueden construir incluyen JPanel, JButton, JLabel, JTextField, JPasswordField, JScrollPane, JRadioButton, JCheckBox, JTextArea y JComboBox.
 */
public class ObjGraficos {
    private ObjGraficos(){} // Constructor privado para evitar instanciación
    static private ObjGraficos servicio; // Instancia única de la clase

    /**
     * Método para obtener la instancia única de la clase.
     * Si la instancia no existe, se crea.
     * @return La instancia única de la clase.
     */
    public static ObjGraficos getService(){
        if(servicio == null) servicio = new ObjGraficos();
        return servicio;
    }

    // Declaración de componentes de la interfaz de usuario
    private JPanel panel;
    private JButton boton;
    private JLabel etiqueta;
    private JTextField textField;
    private JPasswordField passwordField;
    private JScrollPane panelScroll;
    private JRadioButton radioButton;
    private JCheckBox check;
    private JTextArea textArea;
    private JComboBox<String> comboBox;

    /**
     * Método para construir un JPanel.
     * @param x La posición x del panel.
     * @param y La posición y del panel.
     * @param ancho El ancho del panel.
     * @param alto El alto del panel.
     * @param colorFondo El color de fondo del panel.
     * @param borde El borde del panel.
     * @return El panel construido.
     */
    public JPanel construirJPanel(int x, int y, int ancho, int alto, Color colorFondo, Border borde){
        panel = new JPanel();
        panel.setSize(ancho, alto);
        panel.setLocation(x, y);
        panel.setLayout(null);
        panel.setBackground(colorFondo);
        panel.setBorder(borde);
        return panel;
    }

    /**
     * Método para construir un JButton.
     * @param texto El texto del botón.
     * @param x La posición x del botón.
     * @param y La posición y del botón.
     * @param ancho El ancho del botón.
     * @param alto El alto del botón.
     * @param cursor El cursor del botón.
     * @param imagen La imagen del botón.
     * @param fuente La fuente del texto del botón.
     * @param colorFondo El color de fondo del botón.
     * @param colorFuente El color del texto del botón.
     * @param borde El borde del botón.
     * @param direccion La dirección del texto del botón.
     * @param esSolido Indica si el botón es sólido.
     * @return El botón construido.
     */
    public JButton construirJButton(
            String texto, int x, int y, int ancho, int alto, Cursor cursor, ImageIcon imagen, Font fuente,
            Color colorFondo, Color colorFuente, Border borde, String direccion, boolean esSolido
    ) {
        boton = new JButton(texto);
        boton.setLocation(x, y);
        boton.setSize(ancho, alto);
        boton.setFocusable(false);
        boton.setCursor(cursor);
        boton.setFont(fuente);
        boton.setBackground(colorFondo);
        boton.setForeground(colorFuente);
        boton.setIcon(imagen);
        boton.setBorder(borde);
        boton.setContentAreaFilled(esSolido);
        switch (direccion) {
            case "left":
                boton.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case "right":
                boton.setHorizontalAlignment(SwingConstants.RIGHT);
                boton.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            case "top":
                boton.setVerticalTextPosition(SwingConstants.TOP);
                boton.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            case "bottom":
                boton.setVerticalTextPosition(SwingConstants.BOTTOM);
                boton.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            default:
                break;
        }
        return boton;
    }

    /**
     * Método para construir un JLabel.
     * @param texto El texto de la etiqueta.
     * @param x La posición x de la etiqueta.
     * @param y La posición y de la etiqueta.
     * @param ancho El ancho de la etiqueta.
     * @param alto El alto de la etiqueta.
     * @param cursor El cursor de la etiqueta.
     * @param imagen La imagen de la etiqueta.
     * @param fuente La fuente del texto de la etiqueta.
     * @param colorFondo El color de fondo de la etiqueta.
     * @param colorFuente El color del texto de la etiqueta.
     * @param borde El borde de la etiqueta.
     * @param direccion La dirección del texto de la etiqueta.
     * @return La etiqueta construida.
     */
    public JLabel construirJLabel(
            String texto, int x, int y, int ancho, int alto, Cursor cursor, ImageIcon imagen,
            Font fuente, Color colorFondo, Color colorFuente, Border borde, String direccion
    ) {
        etiqueta = new JLabel(texto);
        etiqueta.setLocation(x, y);
        etiqueta.setSize(ancho, alto);
        etiqueta.setForeground(colorFuente);
        etiqueta.setFont(fuente);
        etiqueta.setCursor(cursor);
        etiqueta.setIcon(imagen);
        etiqueta.setBorder(borde);
        if (colorFondo != null) {
            etiqueta.setOpaque(true);
            etiqueta.setBackground(colorFondo);
        }
        switch (direccion) {
            case "c":
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "r":
                etiqueta.setHorizontalAlignment(SwingConstants.RIGHT);
                etiqueta.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            case "t":
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
                etiqueta.setVerticalTextPosition(SwingConstants.TOP);
                etiqueta.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            case "b":
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
                etiqueta.setVerticalTextPosition(SwingConstants.BOTTOM);
                etiqueta.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            default:
                break;
        }
        return etiqueta;
    }

    /**
     * Método para construir un JTextField.
     * @param texto El texto inicial del campo de texto.
     * @param x La posición x del campo de texto.
     * @param y La posición y del campo de texto.
     * @param ancho El ancho del campo de texto.
     * @param alto El alto del campo de texto.
     * @param fuente La fuente del texto del campo de texto.
     * @param colorFondo El color de fondo del campo de texto.
     * @param colorFuente El color del texto del campo de texto.
     * @param colorCaret El color del caret del campo de texto.
     * @param borde El borde del campo de texto.
     * @param direccion La dirección del texto del campo de texto.
     * @return El campo de texto construido.
     */
    public JTextField construirJTextField(
            String texto, int x, int y, int ancho, int alto, Font fuente, Color colorFondo,
            Color colorFuente, Color colorCaret, Border borde, String direccion
    ) {
        textField = new JTextField();
        textField.setLocation(x, y);
        textField.setSize(ancho, alto);
        textField.setText(texto);
        textField.setForeground(colorFuente);
        textField.setBackground(colorFondo);
        textField.setCaretColor(colorCaret);
        textField.setFont(fuente);
        textField.setBorder(borde);
        switch (direccion) {
            case "c":
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "r":
                textField.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            default:
                break;
        }
        return textField;
    }

    /**
     * Método para construir un JPasswordField.
     * @param texto El texto inicial del campo de contraseña.
     * @param x La posición x del campo de contraseña.
     * @param y La posición y del campo de contraseña.
     * @param ancho El ancho del campo de contraseña.
     * @param alto El alto del campo de contraseña.
     * @param fuente La fuente del texto del campo de contraseña.
     * @param colorFondo El color de fondo del campo de contraseña.
     * @param colorFuente El color del texto del campo de contraseña.
     * @param colorCaret El color del caret del campo de contraseña.
     * @param borde El borde del campo de contraseña.
     * @param direccion La dirección del texto del campo de contraseña.
     * @return El campo de contraseña construido.
     */
    public JPasswordField construirJPasswordField(
            String texto, int x, int y, int ancho, int alto, Font fuente, Color colorFondo,
            Color colorFuente, Color colorCaret, Border borde, String direccion
    ) {
        passwordField = new JPasswordField();
        passwordField.setLocation(x, y);
        passwordField.setSize(ancho, alto);
        passwordField.setText(texto);
        passwordField.setForeground(colorFuente);
        passwordField.setBackground(colorFondo);
        passwordField.setCaretColor(colorCaret);
        passwordField.setBorder(borde);
        switch (direccion) {
            case "c":
                passwordField.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "r":
                passwordField.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            default:
                break;
        }
        return passwordField;
    }

    /**
     * Método para construir un JScrollPane.
     * @param componente El componente que se va a agregar al JScrollPane.
     * @param x La posición x del JScrollPane.
     * @param y La posición y del JScrollPane.
     * @param ancho El ancho del JScrollPane.
     * @param alto El alto del JScrollPane.
     * @param colorFondo El color de fondo del JScrollPane.
     * @param borde El borde del JScrollPane.
     * @return El JScrollPane construido.
     */
    public JScrollPane construirPanelBarra(
            Component componente, int x, int y, int ancho, int alto, Color colorFondo, Border borde
    ) {
        panelScroll = new JScrollPane(componente);
        panelScroll.setLocation(x, y);
        panelScroll.setSize(ancho, alto);
        panelScroll.getViewport().setBackground(colorFondo);
        panelScroll.setBorder(borde);
        return panelScroll;
    }

    /**
     * Método para construir un JRadioButton.
     * @param texto El texto del JRadioButton.
     * @param x La posición x del JRadioButton.
     * @param y La posición y del JRadioButton.
     * @param ancho El ancho del JRadioButton.
     * @param alto El alto del JRadioButton.
     * @param cursor El cursor del JRadioButton.
     * @param fuente La fuente del texto del JRadioButton.
     * @param colorFuente El color del texto del JRadioButton.
     * @return El JRadioButton construido.
     */
    public JRadioButton construirJRadioButton(
            String texto, int x, int y, int ancho, int alto, Cursor cursor, Font fuente, Color colorFuente
    ) {
        radioButton = new JRadioButton(texto);
        radioButton.setLocation(x, y);
        radioButton.setSize(ancho, alto);
        radioButton.setFocusable(false);
        radioButton.setBackground(null);
        radioButton.setCursor(cursor);
        radioButton.setFont(fuente);
        radioButton.setForeground(colorFuente);
        return radioButton;
    }

    /**
     * Método para construir un JCheckBox.
     * @param texto El texto del JCheckBox.
     * @param x La posición x del JCheckBox.
     * @param y La posición y del JCheckBox.
     * @param ancho El ancho del JCheckBox.
     * @param alto El alto del JCheckBox.
     * @param cursor El cursor del JCheckBox.
     * @param fuente La fuente del texto del JCheckBox.
     * @param colorFuente El color del texto del JCheckBox.
     * @return El JCheckBox construido.
     */
    public JCheckBox construirJCheckBox(
            String texto, int x, int y, int ancho, int alto, Cursor cursor, Font fuente, Color colorFuente
    ) {
        check = new JCheckBox(texto);
        check.setLocation(x, y);
        check.setSize(ancho, alto);
        check.setFocusable(false);
        check.setBackground(null);
        check.setCursor(cursor);
        check.setFont(fuente);
        check.setForeground(colorFuente);
        return check;
    }

    /**
     * Método para construir un JTextArea.
     * @param texto El texto inicial del área de texto.
     * @param x La posición x del área de texto.
     * @param y La posición y del área de texto.
     * @param ancho El ancho del área de texto.
     * @param alto El alto del área de texto.
     * @param fuente La fuente del texto del área de texto.
     * @param colorFondo El color de fondo del área de texto.
     * @param colorFuente El color del texto del área de texto.
     * @param colorCaret El color del caret del área de texto.
     * @param borde El borde del área de texto.
     * @return El área de texto construida.
     */
    public JTextArea construirJTextArea(
            String texto, int x, int y, int ancho, int alto, Font fuente,
            Color colorFondo, Color colorFuente, Color colorCaret, Border borde
    ) {
        textArea = new JTextArea();
        textArea.setLocation(x, y);
        textArea.setSize(ancho, alto);
        textArea.setText(texto);
        textArea.setFont(fuente);
        textArea.setForeground(colorFuente);
        textArea.setBackground(colorFondo);
        textArea.setCaretColor(colorCaret);
        textArea.setBorder(borde);
        return textArea;
    }

    /**
     * Método para construir un JComboBox.
     * @param cadena La cadena de texto que contiene los elementos del JComboBox, separados por "_".
     * @param x La posición x del JComboBox.
     * @param y La posición y del JComboBox.
     * @param ancho El ancho del JComboBox.
     * @param alto El alto del JComboBox.
     * @param fuente La fuente del texto del JComboBox.
     * @param colorFondo El color de fondo del JComboBox.
     * @param colorFuente El color del texto del JComboBox.
     * @param direccion La dirección del texto del JComboBox. Puede ser "c" para centrado o "r" para alineado a la derecha.
     * @return El JComboBox construido.
     */
    public JComboBox<String> construirJComboBox(
            String cadena, int x, int y, int ancho, int alto, Font fuente,
            Color colorFondo, Color colorFuente, String direccion
    ) {
        comboBox = new JComboBox<String>();
        comboBox.setLocation(x, y);
        comboBox.setSize(ancho, alto);
        for (String item : cadena.split("_")) {
            comboBox.addItem(item);
        }
        comboBox.setFont(fuente);
        comboBox.setBackground(colorFondo);
        comboBox.setForeground(colorFuente);
        switch (direccion) {
            case "c":
                ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "r":
                ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            default:
                break;
        }
        return comboBox;
    }
}