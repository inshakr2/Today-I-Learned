package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b10451_1 {

    static int N;

    static int[][] graph;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        StringTokenizer st = null;
        for (int i = 0; i < tc; i++) {

            N = Integer.parseInt(br.readLine());
            graph = new int[N + 1][N + 1];
            visited = new boolean[N + 1];

            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int x = Integer.parseInt(st.nextToken());
                graph[j][x] = graph[x][j] = 1;
            }

            int count = 0;
            for (int n = 1; n <= N; n++) {
                if (!visited[n]) {
                    dfsR(n);
                    count++;
                }
            }
            System.out.println(count);

        }
    }

    static void dfsR(int start) {
        for (int i = 1; i <= N; i++) {
            if (!visited[i] && graph[start][i] == 1) {
                visited[i] = true;
                dfsR(i);
            }
        }
    }
}
