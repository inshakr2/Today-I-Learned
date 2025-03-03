package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BeakJoon1316 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (isGroupWord(br.readLine())) {
                count++;
            }
        }

        System.out.println(count);

    }

    static boolean isGroupWord(String word) {
        boolean[] visited = new boolean[26];
        char[] charArray = word.toCharArray();
        char prev = ' ';

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c != prev) {
                if (visited[c - 'a']) {
                    return false;
                }
                visited[c - 'a'] = true;
            }
            prev = c;
        }

        return true;
    }
}
