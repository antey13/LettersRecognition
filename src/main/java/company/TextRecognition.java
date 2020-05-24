package company;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextRecognition {
    private int[][] text;
    private int[][] cache;

    public TextRecognition(int[][] text) {
        this.text = text;
    }

    public List<Character> recognize() {
        int length = text[0].length;
        cache = new int[3][length];
        int[][] A = new ImageUtil().readLetter('A');
        int[][] L = new ImageUtil().readLetter('L');
        int[][] Space = new ImageUtil().readLetter(' ');

        int[] lengths = new int[]{A[0].length, L[0].length, Space[0].length};

        for (int i = 0; i < length; i++) {
            cache[0][i] = calculateADiff(i, A);
            cache[1][i] = calculateADiff(i, L);
            cache[2][i] = calculateADiff(i, Space);
        }

        Pair<Integer, Integer>[][] indexCache = new Pair[3][length];
        indexCache[0][0] = Pair.with(cache[0][0], 1);
        indexCache[1][0] = Pair.with(cache[1][0], 1);
        indexCache[2][0] = Pair.with(cache[2][0], 1);

        for (int i = 1; i < length; i++) {
            for (int j = 0; j < 3; j++) {
                int min = cache[0][i] + cache[2][i - 1];
                int index = 2;
                for (int k = 0; k < 3; k++) {
                    if ((i - lengths[k]) < 0)
                        continue;
                    int diff = cache[j][i] + cache[k][i - lengths[k]];
                    if (diff < min) {
                        min = diff;
                        index = k;
                    }
                }
                indexCache[j][i] = Pair.with(min, index);
            }
        }

        ArrayList<Character> result = new ArrayList<>();
        int index = indexCache[0].length;
        int letter = 0;
        int min = indexCache[0][index - lengths[1]].getValue0();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Integer value0 = indexCache[i][index - lengths[j]].getValue0();
                if(value0 < min) {
                    min = value0;
                    letter = i;
                }
            }
        }

        index -= lengths[letter];

        result.add(intToChar(letter));

        while (index > 0){
            Pair<Integer, Integer> subRes = indexCache[letter][index];
            letter = subRes.getValue1();
            result.add(intToChar(letter));
            index = index - lengths[letter];
        }
        return result;
    }

    private int calculateADiff(int startIndex, int[][] letter) {
        int diff = 0;
        for (int i = 0; i < text.length; i++) {
            for (int j = startIndex; j < startIndex + letter[0].length; j++) {
                if(j >= text[0].length)
                    continue;
                if (text[i][j] != letter[i][j - startIndex])
                    diff++;
            }
        }
        return diff;
    }

    private char intToChar(int i) {
        if (i == 0)
            return 'A';
        if (i == 1)
            return 'L';
        else
            return ' ';
    }
}
