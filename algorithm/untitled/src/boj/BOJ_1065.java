package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1065 {

    static int a, b, c;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int count = 0;
        for (int n = 1; n <= N; n++) {
            if (isHanSoo(n)) {
                count++;
            }
        }
        System.out.println(count);
    }

    static boolean isHanSoo(int n) {

        if (n < 100) {
            return true;
        }

        if (n == 1000) {
            return false;
        }

        a = n % 10;
        b = (n % 100) / 10;
        c = (n % 1000) / 100;

        if ((b - a) == (c - b)) {
            return true;
        }

        return false;
    }
}
