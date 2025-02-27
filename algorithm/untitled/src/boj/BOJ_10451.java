package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_10451 {

    static int N;
    static int[][] graph;
    static boolean[] visited;

    static int count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = Integer.parseInt(br.readLine());

        for (int i = 0; i < tc; i++) {

            count = 0;
            N = Integer.parseInt(br.readLine());

            graph = new int[N + 1][N + 1];
            visited = new boolean[N + 1];

            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int n = 1; n <= N; n++) {
                int x = Integer.parseInt(st.nextToken());

                graph[n][x] = graph[x][n] = 1;
            }

            for (int t = 1; t <= N; t++) {

                if(!visited[t]) {
                    dfsR(t);
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
