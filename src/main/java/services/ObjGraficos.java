package services;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Esta clase es un singleton que proporciona métodos para construir componentes de la interfaz de usuario si tiene layout null.
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
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);     // posiciona el texto en el centro del label horizontalmente
                etiqueta.setVerticalAlignment(SwingConstants.TOP);          // posiciona el texto en la parte de arriba del label verticalmente
                etiqueta.setHorizontalTextPosition(SwingConstants.CENTER);  // posiciona el texto en el centro de la imagen horizontalmente (si existe en el label)
                etiqueta.setVerticalTextPosition(SwingConstants.TOP);       // posiciona el texto por encima de la imagen verticalmente (si existe en el label)
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
     * @param x La posición x del campo de contraseña.
     * @param y La posición y del campo de contraseña.
     * @param ancho El ancho del campo de contraseña.
     * @param alto El alto del campo de contraseña.
     * @param colorFondo El color de fondo del campo de contraseña.
     * @param colorFuente El color del texto del campo de contraseña.
     * @param colorCaret El color del caret del campo de contraseña.
     * @param borde El borde del campo de contraseña.
     * @param direccion La dirección del texto del campo de contraseña.
     * @return El campo de contraseña construido.
     */
    public JPasswordField construirJPasswordField(
            int x, int y, int ancho, int alto, Color colorFondo,
            Color colorFuente, Color colorCaret, Border borde, String direccion
    ) {
        passwordField = new JPasswordField();
        passwordField.setLocation(x, y);
        passwordField.setSize(ancho, alto);
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
}