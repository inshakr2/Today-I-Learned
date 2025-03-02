package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon14501 {

    static int N, pay;
    static int[] timeTable;
    static int[] payTable;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        timeTable = new int[N + 1];
        payTable = new int[N + 1];

        StringTokenizer st;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            timeTable[i] = t;
            payTable[i] = p;
        }

        int[] income = new int[N + 2];

        for (int day = 1; day <= N; day++) {
            if (day + timeTable[day] <= N) {

                income[day + timeTable[day]] = Math.max(income[day + timeTable[day]], income[day] + payTable[day]);

            }

            income[day + 1] = Math.max(income[day], income[day + 1]);

        }

        System.out.println(income[N]);
    }
}

