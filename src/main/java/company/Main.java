package company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter text line from A,L and spaces");
        String next = reader.readLine();
        int[][] ints = new ImageUtil().generateImage(next.toCharArray());
        final List<Character> recognize = new TextRecognition(ints).recognize();
        System.out.println(recognize);
    }
}
