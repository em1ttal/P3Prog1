package edu.ub.recercaarcaalianca;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Metodes d'utilitat general.
 *
 * @author ub.edu
 */
public class Util {
    // BufferedImage.TYPE_BYTE_INDEXED sense transparecies pera carrega menys que BufferedImage.TYPE_INT_ARGB que utillitza transparencies
    private static final int IMG_TYPE = BufferedImage.TYPE_INT_ARGB;

    /**
     * Carrega una Cambra des de fitxer.
     *
     * @param nomFitxer el nom del fitxer
     */
    public static Cambra carregarCambra(String nomFitxer) {
        Cambra cambra = new Cambra();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(getFitxerAsStream(nomFitxer)));
            String linea;
            int fila = 0;
            while ((linea = in.readLine()) != null) {
                String[] arr = linea.split(",");
                for (int i = 0; i < arr.length; i++) {
                    cambra.setValor(fila, i, arr[i].charAt(0));
                }
                fila++;
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format(
                    "El fitxer %s no existeix", nomFitxer));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        return cambra;
    }


    public static java.net.URL getUrl(String nomFitxer) {
        return Thread.currentThread().getContextClassLoader().
                getResource("recursos/" + nomFitxer);
    }

    public static InputStream getFitxerAsStream(String nomFitxer) {
        return Thread.currentThread().getContextClassLoader().
                getResourceAsStream("recursos/" + nomFitxer);
    }

    /**
     * Carrega una subimage d'una imatge d'un fitxer.
     *
     * @param nomFitxer el nom del fitxer
     * @param x         la x de la subimatge
     * @param y         la y de la subimatge
     * @param amplada   l'amplada de la subimatge
     * @param alcada    l'alacada de la subimatge
     * @return la subimatge
     */
    public static BufferedImage carregarImatge(String nomFitxer, int x, int y,
                                               int amplada, int alcada) {

        ImageIcon icon = new ImageIcon(getUrl(nomFitxer));

        BufferedImage bi = new BufferedImage(icon.getIconWidth(),
                icon.getIconHeight(), IMG_TYPE);

        Graphics g = bi.getGraphics();
        g.drawImage(icon.getImage(), 0, 0, new Canvas());
        g.dispose();
        return bi.getSubimage(x, y, amplada, alcada);
    }

    /**
     * Aplica una transformacio a la imatge de mirall horitzonal
     *
     * @param bi Objecte BufferedImage que volem transformar
     * @return La imatge transformada
     */
    public static BufferedImage flipImatgeHor(BufferedImage bi) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-bi.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bi, null);
    }

    /**
     * Aplica una transformacio a la imatge de mirall vertical
     *
     * @param bi Objecte BufferedImage que volem transformar
     * @return La imatge transformada
     */
    public static BufferedImage flipImatgeVer(BufferedImage bi) {
        AffineTransform tx = AffineTransform.getScaleInstance(1, 11);
        tx.translate(-bi.getHeight(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bi, null);
    }

    /**
     * Carrega una imatge d'un fitxer.
     *
     * @param nomFitxer el nom del fitxer
     * @return la imatge
     */
    public static BufferedImage carregarImatge(String nomFitxer) {
        return carregarImatge(nomFitxer, null);
    }

    /**
     * Carrega una imatge d'un fitxer.
     *
     * @param nomFitxer el nom del fitxer
     * @param tintat el color a aplicar a l'imatge com a tintat (El canal de transparència ha de ser 0)
     * @return la imatge
     */
    public static BufferedImage carregarImatge(String nomFitxer, Color tintat) {
        ImageIcon icon = new ImageIcon(getUrl(nomFitxer));
        BufferedImage bi = new BufferedImage(icon.getIconWidth(),
                icon.getIconHeight(), IMG_TYPE);
        Graphics g = bi.getGraphics();
        g.drawImage(icon.getImage(), 0, 0, new Canvas());
        g.dispose();

        if(tintat != null)
            tint(bi, tintat);
        return bi;
    }

    public static void tint(BufferedImage image, Color color) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y), true);
                int r = (pixelColor.getRed() + color.getRed()) / 2;
                int g = (pixelColor.getGreen() + color.getGreen()) / 2;
                int b = (pixelColor.getBlue() + color.getBlue()) / 2;
                int a = pixelColor.getAlpha();
                int rgba = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, rgba);
            }
        }
    }
}
