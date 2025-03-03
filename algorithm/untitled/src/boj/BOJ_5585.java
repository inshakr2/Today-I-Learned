package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_5585 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int change = 1000 - N;
        int[] coin = {500, 100, 50, 10, 5, 1};
        int count = 0;

        for (int i : coin) {
            count += change / i;
            change %= i;
        }

        System.out.println(count);
    }
}
