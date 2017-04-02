import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by guilh on 06/03/2017.
 */
public class Atividade2 {

    public int saturate(int value) {
        if (value > 255) {
            return 255;
        }
        if (value < 0) {
            return 0;
        }
        return value;
    }

    public BufferedImage pixelate(BufferedImage img, float kernel[][], int pixelSize) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int r = 0;
                int g = 0;
                int b = 0;

                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        int px = x + (kx-1);
                        int py = y + (ky-1);

                        if(px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight())
                            continue;

                        Color color = new Color(img.getRGB(px,py));
                        r += color.getRed() * kernel[kx][ky];
                        g += color.getGreen() * kernel[kx][ky];
                        b += color.getBlue() * kernel[kx][ky];

                        Color newColor =  new Color(saturate(r),saturate(g),saturate(b));
                        out.setRGB(x,y,newColor.getRGB());
                    }
                }
            }
        }
        for (int y = 0; y  + pixelSize <= out.getHeight(); y += pixelSize) {
            for (int x = 0; x + pixelSize <= out.getWidth(); x += pixelSize) {
                Color color = new Color(out.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                Color newColor = new Color(r, g, b);
                for (int py = 0; py < pixelSize; py++) {
                    for (int px = 0; px < pixelSize; px++) {
                        if (x + px > out.getWidth() || y + py > out.getHeight())
                            continue;
                        out.setRGB(x + px, y + py, newColor.getRGB());
                    }
                }
            }
        }
        return out;
    }

    float contrast[][] =
    {
            {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
    };

    float laplace[][] =
            {
                    {0,1,0},
                    {1,-4,1},
                    {0,1,0}
            };

    public void run() throws IOException {
        String PATH = "C:\\Users\\guilh\\OneDrive\\Pictures\\img\\cor";
        BufferedImage img = ImageIO.read(new File(PATH, "puppy.png"));
        BufferedImage newImg = pixelate(img, contrast, 10);

        ImageIO.write(newImg, "png",
                new File("puppyPixelate.png"));

        System.out.println("Pronto!");
    }

    public static void main(String [] args) throws IOException {
        new Atividade2().run();
    }
}

