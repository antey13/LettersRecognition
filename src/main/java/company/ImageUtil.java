package company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImageUtil {

    public int[][] readLetter(char letter) {
        if (letter == 'A') {
            return readLetter("A.txt");
        } else if (letter == 'L') {
            return readLetter("L.txt");
        } else if (letter == ' ') {
            return readLetter("_.txt");
        }
        return new int[0][0];
    }

    public int[][] generateImage(char[] letters){
        int[][] image = null;
        for (char letter :
                letters) {
            int[][] ints = readLetter(letter);
            if (image == null) {
                image = ints;
                continue;
            }
            int[][] newImage = new int[12][image[0].length+ints[0].length];

            for (int s = 0; s < 12; s++) {
                System.arraycopy(image[s], 0, newImage[s], 0, image[0].length);
                final int length = newImage[0].length - image[0].length;
                if (length >= 0)
                    System.arraycopy(ints[s], 0, newImage[s], image[0].length, length);
            }

            image = newImage;
        }

        assert image != null;
        //noise(image);
        return image;
    }

    private int[][] readLetter(String path) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classloader.getResourceAsStream(path)))) {
            String line = reader.readLine();
            char[] chars = line.toCharArray();
            int[][] image = new int[12][chars.length];
            int v = 0;

            while (line != null) {
                chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    image[v][i] = Integer.parseInt(chars[i]+"");
                }
                v++;
                line = reader.readLine();
            }

            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void noise(int[][] image){
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                if(Math.random() < 0.25){
                    image[i][j] = 1 - image[i][j];
                }
            }
        }
    }
}
