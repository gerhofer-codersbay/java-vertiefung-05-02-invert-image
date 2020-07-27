package codersbay;

import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        BufferedImage image = readFile("src/main/java/resources/city.jpg");
        int[] invertedPixels = invertPixels(image);
        writeFile(invertedPixels, "src/main/java/resources/inverted-city.jpg");
    }

    private static BufferedImage readFile(String path) {
        // TODO read file and return buffered image
        return null;
    }

    private static int[] invertPixels(BufferedImage toBeInverted) {
        int width = toBeInverted.getWidth();
        int height = toBeInverted.getHeight();

        int[] pixels = toBeInverted.getRGB(0, 0, width, height, null, 0, width);
        int[] destinationPixels = new int[pixels.length];

        // create ForkInvert task
        ForkInvert forkBlur = new ForkInvert(pixels, 0, pixels.length, destinationPixels);
        ForkJoinPool pool = new ForkJoinPool();

        // start the task in our thread pool and time it
        long startTime = System.currentTimeMillis();
        pool.invoke(forkBlur);
        long endTime = System.currentTimeMillis();

        System.out.printf("Stats:\n Duration: %d ms | Arraysize : %d | Splitting threshhold: %d\n",
                endTime - startTime, pixels.length, ForkInvert.THRESHOLD);

        return destinationPixels;
    }

    private static void writeFile(int[] pixels, String path) {
        // TODO write result file
    }

}
