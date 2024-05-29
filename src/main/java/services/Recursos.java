package services;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase Recursos que proporciona recursos gráficos para la aplicación.
 * Contiene colores, fuentes, cursores, bordes e imágenes que se utilizan en la interfaz de usuario.
 * Implementa el patrón Singleton para garantizar una única instancia de esta clase en toda la aplicación.
 */
public class Recursos {
    // Declaración de recursos gráficos
    private Color GRANATE, BLANCO, GRIS_CLARO, GRIS_DEFAULT, GRANATE_MID_LIGHT;
    private Color colorGrisPrincipal, colorVerde, colorAmarillo, colorRojo, colorAzul;
    private Font MontserratRegular, MontserratMedium, MontserratBold, MontserratItalic;
    private Cursor cursorMano, cursorNormal;
    private Border bordeGranate, borderBlanco;
    private ImageIcon imagenLogo, imagenLogo2;
    private ImageIcon imagenUsuario, imagenPassword;
    private ImageIcon imagenPlay, imagenPause, imagenStop;
    private ImageIcon imagenCheck, imagenCheckSinCheck;
    private ImageIcon imagenCalendario;

    /**
     * Constante de tamaño de letra estándar para los botones.
     */
    public static final int SIZE_LETRA_BOTON = 15;

    static private Recursos servicio; // Instancia única de la clase

    private Recursos() { // Constructor privado para evitar instanciación
        this.crearColores();
        this.crearCursores();
        this.crearBordes();
        this.crearImagenes();
    }

    /**
     * Crea un timer para hacer desaparecer el mensaje de error pasados 3.5 segundos.
     * @param label label con el mensaje de error
     */
    public void crearTimer(JLabel label){
        Timer timer = new Timer(3500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Método para modificar el scrollbar. Utiliza la clase interna ScrollBarBlanco.
     * @param zonaScroll Scroll que se modifica.
     */
    public void crearScrollModificado (JScrollPane zonaScroll, Color colorBarraInterior, Color colorBarraExterior) {
        zonaScroll.setBorder(null);
        zonaScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI(){

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = colorBarraInterior;
                this.trackColor = colorBarraExterior;
                this.thumbDarkShadowColor = new Color(0, 0, 0, 0);
                this.thumbHighlightColor = new Color(0, 0, 0, 0);
                this.thumbLightShadowColor = new Color(0, 0, 0, 0);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }

                g.setColor(thumbColor);
                g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            }
        });
        zonaScroll.getVerticalScrollBar().setUnitIncrement(16);
        zonaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    /**
     * Método privado para inicializar los colores.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearColores() {
        GRANATE = new Color(82,0,0);
        BLANCO = new Color(255, 255, 255);
        GRIS_CLARO = new Color(245, 245, 245);
        GRIS_DEFAULT = new Color(238, 238, 238);
        GRANATE_MID_LIGHT = new Color(100, 0, 0);

        colorGrisPrincipal = new Color(59, 59, 59);
        colorVerde = new Color(175, 255, 168);
        colorAmarillo = new Color(255, 255, 168);
        colorRojo = new Color(255, 168, 168);
        colorAzul = new Color(168, 235, 255);
    }

    // Cambiar fuente por otra Montserrat-Light/Plain
    private void crearFuentesMontserrat(float sizeLetra) {

        /* Crea la fuente MontserratRegular. Si no existe crea la fuente ArialPlain */
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Montserrat-Medium.ttf");
            MontserratMedium = Font.createFont(Font.TRUETYPE_FONT, is);
            MontserratMedium = MontserratMedium.deriveFont(sizeLetra);
        } catch (FontFormatException | IOException e) {
            System.out.println("Error al cargar la fuente Montserrat-Medium.ttf. Se cargará la fuente Arial.");
            MontserratMedium = new Font("Arial", Font.PLAIN, (int) sizeLetra);
        }

        /* Crea la fuente MontserratRegular. Si no existe crea la fuente ArialPlain */
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Montserrat-Regular.ttf");
            MontserratRegular = Font.createFont(Font.TRUETYPE_FONT, is);
            MontserratRegular = MontserratRegular.deriveFont(sizeLetra);
        } catch (FontFormatException | IOException e) {
            System.out.println("Error al cargar la fuente Montserrat-Regular.ttf. Se cargará la fuente Arial.");
            MontserratRegular = new Font("Arial", Font.PLAIN, (int) sizeLetra);
        }

        /* Crea la fuente MontserratBold. Si no existe crea la fuente ArialBold */
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf");
            MontserratBold = Font.createFont(Font.TRUETYPE_FONT, is);
            MontserratBold = MontserratBold.deriveFont(sizeLetra);
        } catch (FontFormatException | IOException e) {
            System.out.println("Error al cargar la fuente Montserrat-Bold.ttf. Se cargará la fuente Arial.");
            MontserratBold = new Font("Arial", Font.BOLD, (int) sizeLetra);
        }

        /* Crea la fuente MontserratItalic. Si no existe crea la fuente ArialItalic */
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Montserrat-Italic.ttf");
            MontserratItalic = Font.createFont(Font.TRUETYPE_FONT, is);
            MontserratItalic = MontserratItalic.deriveFont(sizeLetra);
        } catch (FontFormatException | IOException e) {
            System.out.println("error al cargar la fuente Montserrat-Italic.ttf. Se cargará la fuente Arial.");
            MontserratItalic = new Font("Arial", Font.ITALIC, (int) sizeLetra);
        }
    }

    /**
     * Método privado para inicializar los cursores.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearCursores() {
        cursorMano = new Cursor(Cursor.HAND_CURSOR);
        cursorNormal = new Cursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * Método privado para inicializar los bordes.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearBordes() {
        bordeGranate = BorderFactory.createMatteBorder(0, 0, 2, 0, GRANATE);
        borderBlanco = BorderFactory.createMatteBorder(0,0,1,0, BLANCO);
    }

    /**
     * Método privado para inicializar las imágenes.
     * Este método se llama en el constructor de la clase Recursos.
     */
    private void crearImagenes() {
        imagenLogo = new ImageIcon("src/main/resources/images/logo1.png");
        imagenLogo2 = new ImageIcon("src/main/resources/images/logo2.png");
        imagenUsuario = new ImageIcon("src/main/resources/images/user.png");
        imagenPassword = new ImageIcon("src/main/resources/images/password.png");
        imagenPlay = new ImageIcon("src/main/resources/images/playbuttton.png");
        imagenPause = new ImageIcon("src/main/resources/images/pausebutton.png");
        imagenStop = new ImageIcon("src/main/resources/images/stopbutton.png");
        imagenCalendario = new ImageIcon("src/main/resources/images/calendar.png");

        Image imageC = new ImageIcon("src/main/resources/images/checkResized.png").getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH);
        imagenCheck = new ImageIcon(imageC);
//        imagenCheck = new ImageIcon("src/main/resources/images/checkResized.png");      // 64 x 64

        Image imageCsC = new ImageIcon("src/main/resources/images/checkSinCheckResized.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        imagenCheckSinCheck = new ImageIcon(imageCsC);
//        imagenCheckSinCheck = new ImageIcon("src/main/resources/images/checkSinCheckResized.png");      // 64 x 64
    }

    /**
     * Método para obtener el color GRANATE.
     * @return El color GRANATE.
     */
    public Color getGRANATE() { return GRANATE; }

    /**
     * Método para obtener el color GRANATE_MID_LIGHT.
     * @return El color GRANATE_MID_LIGHT.
     */
    public Color getGRANATE_MID_LIGHT() { return GRANATE_MID_LIGHT; }

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
     * Método para obtener el color GRIS_DEFAULT.
     * @return El color GRIS_DEFAULT.
     */
    public Color getGRIS_DEFAULT() { return GRIS_DEFAULT; }

    /**
     * Método para obtener el color GRIS_PRINCIPAL.
     * @return El color GRIS_PRINCIPAL.
     */
    public Color getColorGrisPrincipal() { return colorGrisPrincipal; }

    /**
     * Método para obtener el color VERDE.
     * @return El color VERDE.
     */
    public Color getColorVerde() { return colorVerde; }

    /**
     * Método para obtener el color AMARILLO.
     * @return El color AMARILLO.
     */
    public Color getColorAmarillo() { return colorAmarillo; }

    /**
     * Método para obtener el color ROJO.
     * @return El color ROJO.
     */
    public Color getColorRojo() { return colorRojo; }

    /**
     * Método para obtener el color AZUL.
     * @return El color AZUL.
     */
    public Color getColorAzul() { return colorAzul; }

    /**
     * Método para obtener la fuente MontserratBold.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente MontserratBold.
     */
    public Font getMontserratBold(float sizeLetra) {
        crearFuentesMontserrat(sizeLetra);
        return MontserratBold;
    }

    /**
     * Método para obtener la fuente MontserratBold Italic.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente MontserratBold Italic.
     */
    public Font getMontserratItalic(float sizeLetra) {
        crearFuentesMontserrat(sizeLetra);
        return MontserratItalic;
    }

    /**
     * Método para obtener la fuente MontserratRegular.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente MontserratRegular.
     */
    public Font getMontserratPlain(float sizeLetra) {
        crearFuentesMontserrat(sizeLetra);
        return MontserratRegular;
    }

    /**
     * Método para obtener la fuente MontserratMedium.
     * @param sizeLetra tamaño de la fuente
     * @return La fuente MontserratMedium.
     */
    public Font getMontserratMedium(float sizeLetra) {
        crearFuentesMontserrat(sizeLetra);
        return MontserratMedium;
    }

    /**
     * Método para obtener el cursor de tipo mano.
     * @return El cursor de tipo mano.
     */
    public Cursor getCursorMano() { return cursorMano; }

    /**
     * Método para obtener el cursor normal.
     * @return El cursor normal.
     */
    public Cursor getCursorNormal() { return cursorNormal; }


    /**
     * Método para obtener el borde de color GRANATE.
     * @return El borde de color GRANATE.
     */
    public Border getBordeGranate() { return bordeGranate; }

    /**
     * Método para obtener el borde de color BLANCO.
     * @return El borde de color BLANCO.
     */
    public Border getBorderBlanco() {
        return borderBlanco;
    }

    /**
     * Método para obtener la imagen del logo.
     * @return La imagen del logo.
     */
    public ImageIcon getImagenLogo() { return imagenLogo; }

    /**
     * Método para obtener la imagen del logo secundaria
     * @return la imagen del logo secundaria
     */
    public ImageIcon getImagenLogo2() { return imagenLogo2; }

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
     * Método para obtener la imagen del botón de play.
     * @return La imagen del botón de play.
     */
    public ImageIcon getImagenPlay() { return imagenPlay; }

    /**
     * Método para obtener la imagen del botón de pausa.
     * @return La imagen del botón de pausa.
     */
    public ImageIcon getImagenPause() { return imagenPause; }

    /**
     * Método para obtener la imagen del botón de detener.
     * @return La imagen del botón de detener.
     */
    public ImageIcon getImagenStop() { return imagenStop; }

    /**
     * Método para obtener la imagen del check.
     * @return La imagen del check.
     */
    public ImageIcon getImagenCheck() { return imagenCheck; }

    /**
     * Método para obtener la imagen del check sin check.
     * @return La imagen del check sin check.
     */
    public ImageIcon getImagenCheckSinCheck() { return imagenCheckSinCheck; }

    /**
     * Método para obtener la imagen del calendario.
     * @return La imagen del calendario.
     */
    public ImageIcon getImagenCalendario() { return imagenCalendario; }

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
