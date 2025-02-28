package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1316 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int count = 0;
        for (int i = 0; i < N; i++) {
            String str = br.readLine();

            if (isGroupWord(str)) {
                count++;
            }
        }

        System.out.println(count);
    }

    private static boolean isGroupWord(String str) {

        char prev = ' ';
        boolean[] alpha = new boolean[26];

        for (char s : str.toCharArray()) {
            if (prev != s) {
                if (alpha[s - 'a']) {
                    return false;
                }
                alpha[s - 'a'] = true;
            }
            prev = s;
        }
        return true;
    }
}
