package app.services;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Clase Recursos que proporciona recursos gráficos para la aplicación.
 * Contiene colores, fuentes, cursores, bordes e imágenes que se utilizan en la interfaz de usuario.
 * Implementa el patrón Singleton para garantizar una única instancia de esta clase en toda la aplicación.
 */
public class Recursos {
    // Declaración de recursos gráficos
    private Color GRANATE;
    private Font ArialDefault, ArialTitle, ArialBold, ArialItalic;
    private Cursor cursorMano;
    private Border bordeGranate;
    private ImageIcon imagenLogo, imagenCerrar, imagenUsuario, imagenPassword;

    static private Recursos servicio; // Instancia única de la clase

    private Recursos() { // Constructor privado para evitar instanciación
        this.crearColores();
//        this.crearFuentes();
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
    }

    /**
     * Método privado para inicializar las fuentes.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearFuentes(int sizeLetra) {
        ArialDefault = new Font("Arial", Font.PLAIN, sizeLetra);
        ArialTitle = new Font("Arial", Font.BOLD, sizeLetra);
        ArialBold = new Font("Arial", Font.BOLD, sizeLetra);
        ArialItalic = new Font("Arial" , Font.ITALIC, sizeLetra);
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
        imagenLogo = new ImageIcon("resources/images/logo1.png");
        imagenCerrar = new ImageIcon("resources/images/close.png");
        imagenUsuario = new ImageIcon("resources/images/user.png");
        imagenPassword = new ImageIcon("resources/images/password.png");
    }

    /**
     * Método para obtener el color GRANATE.
     * @return El color GRANATE.
     */
    public Color getGRANATE() { return GRANATE; }

    /**
     * Método para obtener la fuente ArialDefault.
     * @return La fuente ArialDefault.
     */
    public Font getFontTArialDefault(int sizeLetra) {
        crearFuentes(sizeLetra);
        return ArialDefault;
    }

    /**
     * Método para obtener la fuente ArialTitle.
     * @return La fuente ArialTitle.
     */
    public Font getFontArialTitle(int sizeLetra) {
        crearFuentes(sizeLetra);
        return ArialTitle;
    }

    /**
     * Método para obtener la fuente ArialBold.
     * @return La fuente ArialBold.
     */
    public Font getFontArialBold(int sizeLetra) {
        crearFuentes(sizeLetra);
        return ArialBold;
    }

    /**
     * Método para obtener la fuente ArialItalic.
     * @return La fuente ArialItalic.
     */
    public Font getFontArialItalic(int sizeLetra) {
        crearFuentes(sizeLetra);
        return ArialItalic;
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
