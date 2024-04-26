package app.services;

import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;

public class ObjGraficos {
    // Se hace una clase singleton para instanciar un solo objeto
    private ObjGraficos(){}
    static private ObjGraficos servicio;
    public static ObjGraficos getService(){
        if(servicio == null) servicio = new ObjGraficos();
        return servicio;
    }

    /* Declaración */
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

    /* Métodos de construcción de JPanel*/
    public JPanel construirJPanel(int x, int y, int ancho, int alto, Color colorFondo, Border borde){
        panel = new JPanel();
        panel.setSize(ancho, alto);
        panel.setLocation(x, y);
        panel.setLayout(null);
        panel.setBackground(colorFondo);
        panel.setBorder(borde);
        return panel;
    }

    /* Métodos de construcción de JButton */
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

    /* Métodos de construcción de JLabel */
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

    /* Métodos de construcción de JTextField */
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

    /* Métodos de construcción de JPasswordField */
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

    /* Métodos de construcción de JScrollPane */
    /* Métodos de construcción de JRadioButton */
    /* Métodos de construcción de JCheckBox */
    /* Métodos de construcción de JTextArea */
    /* Métodos de construcción de JComboBox */
}
