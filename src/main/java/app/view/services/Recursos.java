package app.view.services;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase Recursos que proporciona recursos gráficos para la aplicación.
 * Contiene colores, fuentes, cursores, bordes e imágenes que se utilizan en la interfaz de usuario.
 * Implementa el patrón Singleton para garantizar una única instancia de esta clase en toda la aplicación.
 */
public class Recursos {
    // Declaración de recursos gráficos
    private Color GRANATE, BLANCO, GRIS_CLARO;
    private Font ArialDefault, ArialBold, ArialItalic, Monserrat, MonserratItalic;
    private Cursor cursorMano;
    private Border bordeGranate;
    private ImageIcon imagenLogo, imagenCerrar, imagenUsuario, imagenPassword;

    static private Recursos servicio; // Instancia única de la clase

    private Recursos() { // Constructor privado para evitar instanciación
        this.crearColores();
        this.crearCursores();
        this.crearBordes();
        this.crearImagenes();
    }

    /**
     * Método privado para inicializar los colores.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearColores() {
        GRANATE = new Color(82,0,0);
        BLANCO = new Color(255, 255, 255);
        GRIS_CLARO = new Color(245, 245, 245);
    }

    /**
     * Método privado para inicializar las fuentes.
     * @param sizeLetra tamaño de la fuente
     */
    private void crearFuentes(int sizeLetra) {
        ArialDefault = new Font("Arial", Font.PLAIN, sizeLetra);
        ArialBold = new Font("Arial", Font.BOLD, sizeLetra);
        ArialItalic = new Font("Arial" , Font.ITALIC, sizeLetra);
    }

    /**
     * Método privado para inicializar la fuente Monserrat.
     * @param sizeLetra tamaño de la fuente
     */
    private void crearFuenteMonserrat(float sizeLetra) {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Montserrat.ttf");
            Monserrat = Font.createFont(Font.TRUETYPE_FONT, is);
            Monserrat = Monserrat.deriveFont(sizeLetra);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            Monserrat = new Font("Arial", Font.PLAIN, (int) sizeLetra);
            System.out.println(Monserrat.toString());
        }
    }

    /**
     * Método privado para inicializar la fuente Monserrat Italic.
     * @param sizeLetra tamaño de la fuente
     */
    private void crearFuenteMonserratItalic(float sizeLetra) {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Montserrat.ttf");
            MonserratItalic = Font.createFont(Font.TRUETYPE_FONT, is);
            MonserratItalic = MonserratItalic.deriveFont(Font.ITALIC, sizeLetra);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            MonserratItalic = new Font("Arial", Font.ITALIC, (int) sizeLetra);
            System.out.println(MonserratItalic.toString());
        }
    }

    /**
     * Método privado para inicializar los cursores.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearCursores() {
        cursorMano = new Cursor(Cursor.HAND_CURSOR);
    }

    /**
     * Método privado para inicializar los bordes.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearBordes() {
        bordeGranate = BorderFactory.createMatteBorder(0, 0, 2, 0, GRANATE);
    }

    /**
     * Método privado para inicializar las imágenes.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearImagenes() {
        imagenLogo = new ImageIcon("src/main/resources/images/logo1.png");
        imagenCerrar = new ImageIcon("src/main/resources/images/close.png");
        imagenUsuario = new ImageIcon("src/main/resources/images/user.png");
        imagenPassword = new ImageIcon("src/main/resources/images/password.png");
    }

    /**
     * Método para obtener el color GRANATE.
     * @return El color GRANATE.
     */
    public Color getGRANATE() { return GRANATE; }

    /**
     * Método para obtener el color BLANCO.
     * @return El color BLANCO.
     */
    public Color getBLANCO() { return BLANCO; }

    /**
     * Método para obtener el color GRIS_CLARO.
     * @return El color GRIS_CLARO.
     */
    public Color getGRIS_CLARO() { return GRIS_CLARO; }

    /**
     * Método para obtener la fuente ArialDefault.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente ArialDefault.
     */
    public Font getFontTArialDefault(int sizeLetra) {
        crearFuentes(sizeLetra);
        return ArialDefault;
    }

    /**
     * Método para obtener la fuente ArialBold.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente ArialBold.
     */
    public Font getFontArialBold(int sizeLetra) {
        crearFuentes(sizeLetra);
        return ArialBold;
    }

    /**
     * Método para obtener la fuente ArialItalic.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente ArialItalic.
     */
    public Font getFontArialItalic(int sizeLetra) {
        crearFuentes(sizeLetra);
        return ArialItalic;
    }

    /**
     * Método para obtener la fuente Monserrat.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente Monserrat.
     */
    public Font getMonserrat(float sizeLetra) {
        crearFuenteMonserrat(sizeLetra);
        return Monserrat;
    }

    /**
     * Método para obtener la fuente Monserrat Italic.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente Monserrat Italic.
     */
    public Font getMonserratItalic(float sizeLetra) {
        crearFuenteMonserratItalic(sizeLetra);
        return MonserratItalic;
    }

    /**
     * Método para obtener el cursor de tipo mano.
     * @return El cursor de tipo mano.
     */
    public Cursor getCursorMano() { return cursorMano; }

    /**
     * Método para obtener el borde de color GRANATE.
     * @return El borde de color GRANATE.
     */
    public Border getBordeGranate() { return bordeGranate; }

    /**
     * Método para obtener la imagen de cierre.
     * @return La imagen de cierre.
     */
    public ImageIcon getImagenCerrar() { return imagenCerrar; }

    /**
     * Método para obtener la imagen del logo.
     * @return La imagen del logo.
     */
    public ImageIcon getImagenLogo() { return imagenLogo; }

    /**
     * Método para obtener la imagen del usuario.
     * @return La imagen del usuario.
     */
    public ImageIcon getImagenUsuario() { return imagenUsuario; }

    /**
     * Método para obtener la imagen de la contraseña.
     * @return La imagen de la contraseña.
     */
    public ImageIcon getImagenPassword() { return imagenPassword; }

    /**
     * Método para obtener la instancia única de la clase.
     * Si la instancia no existe, se crea.
     * @return La instancia única de la clase.
     */
    public static Recursos getService() {
        if (servicio == null) servicio = new Recursos();
        return servicio;
    }
}
