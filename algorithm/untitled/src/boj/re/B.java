package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B {

    static int[][] graph;
    static boolean[] visited;
    static int N;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = Integer.parseInt(br.readLine());

        for (int i = 0; i < tc; i++) {

            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());

            graph = new int[N + 1][N + 1];
            visited = new boolean[N + 1];
            int count = 0;

            for (int j = 1; j <= N; j++) {
                int t = Integer.parseInt(st.nextToken());

                graph[j][t] = graph[t][j] = 1;
            }

            for (int j = 1; j <= N; j++) {
                if (!visited[j]) {
                    dfsR(j);
                    count++;
                }
            }
            System.out.println(count);
        }
    }


    static void dfsR(int start) {
        if (!visited[start]) {
            visited[start] = true;
            for (int i = 1; i <= N; i++) {
                if (!visited[i] && graph[start][i] == 1) {
                    dfsR(i);
                }
            }
        }
    }
}
