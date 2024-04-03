package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class BOJ_2941 {

    static List<String> croatiaAlphabets = List.of("c=", "c-", "dz=", "d-", "lj", "nj", "s=", "z=");
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        input = replaceCroatic(input);
        System.out.println(input.length());
    }

    private static String replaceCroatic(String input) {
        for (String croatiaAlphabet : croatiaAlphabets) {
            input = input.replace(croatiaAlphabet, "*");
        }
        return input;
    }
}
