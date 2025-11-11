package Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

/**
 * Utilidades para manejar imágenes
 */
public class ImagenUtils {

    /**
     * Genera una imagen circular con las iniciales del usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param size Tamaño de la imagen (ancho y alto)
     * @return byte[] con la imagen en formato PNG
     */
    public static byte[] generarImagenConIniciales(String nombre, String apellido, int size) {
        try {
            // Obtener iniciales
            String iniciales = "";
            if (nombre != null && !nombre.isEmpty()) {
                iniciales += nombre.charAt(0);
            }
            if (apellido != null && !apellido.isEmpty()) {
                iniciales += apellido.charAt(0);
            }
            iniciales = iniciales.toUpperCase();

            // Generar color basado en el nombre (para que siempre sea el mismo color)
            Color backgroundColor = generarColorDesdeTexto(nombre + apellido);

            // Crear imagen
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            // Activar antialiasing para mejor calidad
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Dibujar círculo de fondo
            g2d.setColor(backgroundColor);
            g2d.fillOval(0, 0, size, size);

            // Dibujar iniciales
            g2d.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, size / 2);
            g2d.setFont(font);

            // Centrar texto
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(iniciales);
            int textHeight = fm.getAscent();
            int x = (size - textWidth) / 2;
            int y = (size + textHeight) / 2 - fm.getDescent();

            g2d.drawString(iniciales, x, y);
            g2d.dispose();

            // Convertir a byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Genera un color consistente basado en un texto
     * @param text Texto para generar el color
     * @return Color generado
     */
    private static Color generarColorDesdeTexto(String text) {
        if (text == null || text.isEmpty()) {
            return new Color(100, 100, 100); // Gris por defecto
        }

        // Lista de colores agradables
        Color[] colores = {
            new Color(244, 67, 54),   // Rojo
            new Color(233, 30, 99),   // Rosa
            new Color(156, 39, 176),  // Púrpura
            new Color(103, 58, 183),  // Púrpura oscuro
            new Color(63, 81, 181),   // Índigo
            new Color(33, 150, 243),  // Azul
            new Color(3, 169, 244),   // Azul claro
            new Color(0, 188, 212),   // Cian
            new Color(0, 150, 136),   // Verde azulado
            new Color(76, 175, 80),   // Verde
            new Color(139, 195, 74),  // Verde lima
            new Color(205, 220, 57),  // Lima
            new Color(255, 235, 59),  // Amarillo
            new Color(255, 193, 7),   // Ámbar
            new Color(255, 152, 0),   // Naranja
            new Color(255, 87, 34)    // Naranja profundo
        };

        // Usar hashCode para seleccionar un color consistente
        int hash = Math.abs(text.hashCode());
        return colores[hash % colores.length];
    }

    /**
     * Redimensiona una imagen
     * @param originalBytes Bytes de la imagen original
     * @param targetSize Tamaño objetivo
     * @return byte[] con la imagen redimensionada
     */
    public static byte[] redimensionarImagen(byte[] originalBytes, int targetSize) {
        try {
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(originalBytes);
            BufferedImage originalImage = ImageIO.read(bais);

            Image scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);

            BufferedImage bufferedImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return originalBytes;
        }
    }
}
