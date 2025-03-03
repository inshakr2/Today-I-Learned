package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BeakJoon2536 {

    static int[][] graph = new int[101][101];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int result = 0;
        for (int n = 1; n <= N; n++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());


            for (int i = x; i < x + 10; i++) {
                for (int j = y; j < y + 10; j++) {
                    if (graph[i][j] != 1) {
                        graph[i][j] = 1;
                        result++;
                    }
                }
            }
        }

        System.out.println(result);
    }
}
