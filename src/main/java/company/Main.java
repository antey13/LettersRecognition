package company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String next = reader.readLine();
        int[][] ints = new ImageUtil().generateImage(next.toCharArray());
        final List<Character> recognize = new TextRecognition(ints).recognize();
        System.out.println(recognize);
    }
}
