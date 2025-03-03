package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BeakJoon1010 {

    static int[][] pascal;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        StringTokenizer st;
        for (int i = 0; i < tc; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());

            pascal = new int[30][30];

            System.out.println(combination(n, r));
        }
    }

    static int combination(int n, int r) {

        if (pascal[n][r] > 0) {
            return pascal[n][r];
        }

        if (n == r | r == 0) {
            return 1;
        }

        pascal[n][r] = combination(n - 1, r - 1) + combination(n - 1, r);
        return pascal[n][r];
    }

}
