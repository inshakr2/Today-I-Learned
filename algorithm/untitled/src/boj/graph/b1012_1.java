package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1012_1 {

    static int T, M, N, K, count;
    static boolean[][] graph;
    static boolean[][] visited;

    static int[] move_x = {1, -1, 0, 0};
    static int[] move_y = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            graph = new boolean[N][M];
            visited = new boolean[N][M];

            for (int k = 0; k < K; k++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                graph[y][x] = true;
            }

            for (int y = 0; y < N; y++) {
                for (int x = 0; x < M; x++) {
                    if (graph[y][x] && !visited[y][x]) {
                        dfs(x, y);
                        count++;
                    }
                }
            }

            System.out.println(count);
            count = 0;
        }
    }

    private static void dfs(int x, int y) {
        visited[y][x] = true;

        for (int m = 0; m < 4; m++) {
            int mx = move_x[m];
            int my = move_y[m];

            if (canMove(x + mx, y + my) && graph[y + my][x + mx] && !visited[y + my][x + mx]) {
                dfs(x + mx, y + my);
            }
        }
    }

    private static boolean canMove(int x, int y) {
        if (x >= 0 && x < M && y >= 0 && y < N) {
            return true;
        }
        return false;
    }
}
