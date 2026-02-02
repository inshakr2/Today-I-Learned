package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b4936_1 {

    static boolean[][] graph, visited;
    static int W, H;

    static int[] move_x = {1, -1, 0, 0, 1, -1, 1, -1};
    static int[] move_y = {0, 0, 1, -1, 1, 1, -1, -1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            if (W == 0 && H == 0) {
                break;
            }

            graph = new boolean[H][W];
            visited = new boolean[H][W];

            for (int y = 0; y < H; y++) {
                st = new StringTokenizer(br.readLine());
                for (int x = 0; x < W; x++) {
                    int node = Integer.parseInt(st.nextToken());
                    if (node != 0) {
                        graph[y][x] = true;
                    }
                }
            }

            int count = 0;
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    if (graph[y][x] && !visited[y][x]) {
                        dfs(x, y);
                        count++;
                    }
                }
            }
            System.out.println(count);
        }
    }

    private static void dfs(int x, int y) {
        visited[y][x] = true;

        for (int i = 0; i < 8; i++) {
            int mx = x + move_x[i];
            int my = y + move_y[i];

            if (canMove(mx, my) && graph[my][mx] && !visited[my][mx]) {
                dfs(mx, my);
            }
        }
    }

    private static boolean canMove(int x, int y) {
        return x >= 0 && y >= 0 && x < W && y < H;
    }
}
