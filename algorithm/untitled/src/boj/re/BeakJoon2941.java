package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BeakJoon2941 {

    static String[] croAlpha = {"c=", "c-", "dz=", "d-", "lj", "nj", "s=", "z="};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        char[] charArray = input.toCharArray();

        for (String s : croAlpha) {
            input = input.replaceAll(s, "*");
        }

        System.out.println(input.length());

    }
}
