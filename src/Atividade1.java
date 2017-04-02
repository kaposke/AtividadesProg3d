import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by guilh on 06/03/2017.
 */
public class Atividade1 {

    public int saturate(int value) {
        if (value > 255) {
            return 255;
        }
        if (value < 0) {
            return 0;
        }
        return value;
    }

    public static int applyPallete(int value)
    {
        if(value <= 64)
            return 63;
        else if(value > 63 && value <= 127)
            return 127;
        else if(value > 127 && value <= 191)
            return 191;

        return 255;
    }

    public BufferedImage pallete(BufferedImage img) {
        BufferedImage out = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x,y));
                int r = applyPallete(color.getRed());
                int g = applyPallete(color.getGreen());
                int b = applyPallete(color.getBlue());

                Color newColor = new Color(r,g,b);

                out.setRGB(x,y,newColor.getRGB());
            }
        }
        return out;
    }

    public void run() throws IOException {
        String PATH = "C:\\Users\\guilh\\OneDrive\\Pictures\\img\\cor";
        BufferedImage img = ImageIO.read(new File(PATH, "puppy.png"));
        BufferedImage newImg = pallete(img);

        ImageIO.write(newImg, "png",
                new File("puppy64.png"));

        System.out.println("Pronto!");
    }

    public static void main(String [] args) throws IOException {
        new Atividade1().run();
    }
}

