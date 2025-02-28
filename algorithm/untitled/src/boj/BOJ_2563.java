package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2563 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(br.readLine());

        StringTokenizer st;
        int total = 0;
        int[][] graph = new int[101][101];

        for (int i = 0; i < count; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if (graph[x][y] != 1) {
                graph[x][y] = 1;
                total++;
            }

            for (int k = 0; k < 10; k++) {
                for (int j = 0; j < 10; j++) {
                    if (graph[x + k][y + j] != 1) {
                        graph[x + k][y + j] = 1;
                        total++;
                    }
                }
            }
        }

        System.out.println(total);

    }
}
