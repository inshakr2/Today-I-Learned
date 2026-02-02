package boj.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class b2667_1 {

    static int N, zoneCount;
    static boolean[][] graph;
    static boolean[][] visited;

    static int[] move_x = {1, -1, 0, 0};
    static int[] move_y = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        graph = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1][N + 1];

        for (int x = 0; x < N; x++) {
            char[] charArray = br.readLine().toCharArray();
            for (int y = 0; y < charArray.length; y++) {
                if (charArray[y] == '1') {
                    graph[x + 1][y + 1] = true;
                }
            }
        }
        List<Integer> answer = new ArrayList<>();

        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                if (!visited[x][y] && graph[x][y]) {
                    dfs(x, y);
                    answer.add(zoneCount);
                    zoneCount = 0;
                }
            }
        }

        answer.sort(Comparator.naturalOrder());
        System.out.println(answer.size());
        for (Integer a : answer) {
            System.out.println(a);
        }

        br.close();
    }

    private static void dfs(int x, int y) {
        zoneCount++;
        visited[x][y] = true;

        for (int m = 0; m < 4; m++) {
            int mx = move_x[m];
            int my = move_y[m];

            if (canMove(x + mx, y + my) && graph[x + mx][y + my] && !visited[x + mx][y + my]) {
                dfs(x + mx, y + my);
            }
        }

    }

    private static boolean canMove(int x, int y) {
        if (x >= 0 && y >= 0 && x <= N && y <= N) {
            return true;
        }
        return false;
    }
}

