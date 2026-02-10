package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class b2644_1 {

    static boolean[][] graph;
    static int[] visited;
    static int n, m, x, y;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(br.readLine());

        graph = new boolean[n + 1][n + 1];
        visited = new int[n + 1];
        Arrays.fill(visited, -1);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int xx = Integer.parseInt(st.nextToken());
            int yy = Integer.parseInt(st.nextToken());

            graph[xx][yy] = graph[yy][xx] = true;
        }

        System.out.println(bfs(x));
    }

    private static int bfs(int start) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(start);
        visited[start] = 0; // 거리 0부터 시작

        while (!q.isEmpty()) {
            int v = q.poll();

            for (int w = 1; w <= n; w++) {
                if (!graph[v][w]){
                    continue;
                }

                if (visited[w] != -1){
                    continue;
                }

                visited[w] = visited[v] + 1;
                if (w == y){
                    return visited[w];
                }

                q.add(w);
            }
        }

        return -1;
    }
}
