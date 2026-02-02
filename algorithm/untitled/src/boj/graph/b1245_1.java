package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1245_1 {

    static boolean[][] graph, visited;
    static int N, M, count;

    static int[] move_x = {1, -1, 0, 0};
    static int[] move_y = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // y
        M = Integer.parseInt(st.nextToken()); // x

        graph = new boolean[N + 1][M + 1];
        visited = new boolean[N + 1][M + 1];

        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < M; x++) {
                int node = Integer.parseInt(st.nextToken());
                if (node != 0) {
                    graph[y + 1][x + 1] = true;
                }
            }
        }

        for (int y = 1; y <= N; y++) {
            for (int x = 1; x <= M; x++) {
                if (graph[y][x] && !visited[y][x]) {
                    dfs(x, y);
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static void dfs(int x, int y) {
        visited[y][x] = true;

        for (int i = 0; i < 4; i++) {
            int mx = move_x[i] + x;
            int my = move_y[i] + y;

            if (canMove(mx, my) && graph[my][mx] && !visited[my][mx]) {
                dfs(mx, my);
            }
        }
    }

    private static boolean canMove(int x, int y) {
        if (x > 0 && y > 0 && x <= M && y <= N) {
            return true;
        }
        return false;
    }
}
