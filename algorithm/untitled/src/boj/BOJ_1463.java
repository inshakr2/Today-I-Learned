package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1463 {

    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        dp = new int[N + 1];
        dp[0] = dp[1] = 1;

        System.out.println(calc(N));
    }

    static int calc(int n) {

        if (dp[n] == 0) {

            if (n % 6 == 0) {
                dp[n] = Math.min(calc(n - 1), Math.min(calc(n / 3), calc(n / 2))) + 1;
            }

            else if (n % 3 == 0) {
                dp[n] = Math.min(calc(n / 3), calc(n - 1)) + 1;
            }

            else if (n % 2 == 0) {
                dp[n] = Math.min(calc(n / 2), calc(n - 1)) + 1;
            }

            else {
                dp[n] = calc(n - 1) + 1;
            }
        }

        return dp[n];
    }
}
