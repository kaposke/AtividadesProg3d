import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.*;

/**
 * Created by guilh on 06/03/2017.
 */
public class Atividade3 {

    public int saturate(int value) {
        if (value > 255) {
            return 255;
        }
        if (value < 0) {
            return 0;
        }
        return value;
    }

    public int[] histoAcumulado(int[] histo)
    {
        int[] acumulado = new int[256];
        acumulado[0] =  histo[0];
        for (int i = 1; i < histo.length; i++) {
            acumulado[i] = histo[i] + acumulado[i - 1];
        }
        return acumulado;
    }

    public int getMenorTom(int[] histo)
    {
        for (int i = 0; i < histo.length; i++) {
            if(histo[i] != 0)
                return i;
        }
        return 0;
    }

    public BufferedImage equalize(BufferedImage img) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        int[] histograma = new int[256];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x,y));
                histograma[color.getRed()] += 1;
            }
        }
        int[] acumulado = histoAcumulado(histograma);
        float menorTom = getMenorTom(histograma);
        int pixels = img.getWidth() * img.getHeight();
        int[] mapaDeCores = new int[256];
        for (int i = 0; i < histograma.length; i++) {
            mapaDeCores[i] = Math.round(((acumulado[i] - menorTom) / (pixels - menorTom)) * 255);
        }
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x,y));
                int tom = color.getRed();
                int newTom = mapaDeCores[tom];
                Color newColor = new Color(newTom, newTom, newTom);
                out.setRGB(x,y,newColor.getRGB());
            }
        }
        return out;
    }

    public void run() throws IOException {
        String PATH = "C:\\Users\\guilh\\OneDrive\\Pictures\\img\\gray";
        BufferedImage img = ImageIO.read(new File(PATH, "university.png"));
        BufferedImage newImg = equalize(img);

        ImageIO.write(newImg, "png",
                new File("Equalized.png"));

        System.out.println("Pronto!");
    }

    public static void main(String [] args) throws IOException {
        new Atividade3().run();
    }
}

