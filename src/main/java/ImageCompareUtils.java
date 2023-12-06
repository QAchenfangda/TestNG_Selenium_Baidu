import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public  class ImageCompareUtils {

    public static double imageCompare(String imageA, String imageB) throws IOException {

        File file1 = new File(imageA);
        File file2 = new File(imageB);

        BufferedImage img1 = ImageIO.read(file1);
        BufferedImage img2 = ImageIO.read(file2);

        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();

        if (width1 != width2 || height1 != height2) System.out.println("Two pictures has differences in height and width, upload pic is "+width1+"*"+height1+", search result pic is "+width2+"*"+height2);

        long diff = 0;

        for (int y = 0; y < (Math.min(height1, height2)) ; y++)
            for (int x = 0; x < (Math.min(width1, width2)); x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        double n = width1 * height1 * 3;
        double p;
        p = diff / n / 255.0;
        // p is the parameter to measure how similar two pictures are from each other
        // smaller p gets, more similar two pics are from each other
        return p;
    }
}
