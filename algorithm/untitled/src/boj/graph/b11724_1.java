package boj.graph;

import java.io.*;
import java.util.StringTokenizer;

public class b11724_1 {

    static int N, M, answer;
    static boolean[][] graph;
    static boolean[] visited;

    public static void dfs(int node) {
        for (int i = 1; i <= N; i++) {
            if (!visited[i] && graph[node][i]) {
                visited[i] = true;
                dfs(i);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[x][y] = graph[y][x] = true;
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(i);
                answer++;
            }
        }

        bw.write(String.valueOf(answer));

        bw.close();
        br.close();
    }
}
