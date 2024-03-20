package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14501 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        int[] T = new int[N];
        int[] P = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }

        int[] incomes = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            if (i + T[i] <= N) {
                incomes[i + T[i]] = Math.max(incomes[i + T[i]], incomes[i] + P[i]);
            }
            incomes[i + 1] = Math.max(incomes[i + 1], incomes[i]);
        }

        System.out.println(incomes[N]);
    }
}
