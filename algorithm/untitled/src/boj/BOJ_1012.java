package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_1012 {

    static int test, M, N, K;

    static int[][] graph;
    static boolean[][] visited;

    static int[] move_x = {-1, 1, 0, 0};
    static int[] move_y = {0, 0, 1, -1};

    static int count;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        test = Integer.parseInt(st.nextToken());

        for (int t = 0; t < test; t++) {
            count = 0;
            StringTokenizer str = new StringTokenizer(br.readLine());
            M = Integer.parseInt(str.nextToken());
            N = Integer.parseInt(str.nextToken());
            K = Integer.parseInt(str.nextToken());

            graph = new int[M][N];
            visited = new boolean[M][N];

            for (int i = 0; i < K; i++) {

                str = new StringTokenizer(br.readLine());
                int m = Integer.parseInt(str.nextToken());
                int n = Integer.parseInt(str.nextToken());

                graph[m][n] = 1;
            }

            for (int x = 0; x < M; x++) {
                for (int y = 0; y < N; y++) {

                    if (!visited[x][y] && graph[x][y] == 1) {
                        count++;
                        bfs(x, y);
                    }
                }
            }

            System.out.println(count);
        }
    }

    static void dfsR(int x, int y) {

        visited[x][y] = true;

        for (int s = 0; s < 4; s++) {
            int mx = x + move_x[s];
            int my = y + move_y[s];
            if (mx >= 0 && my >= 0 && mx < M && my < N) {
                if (!visited[mx][my] && graph[mx][my] == 1) {
                    dfsR(mx, my);
                }
            }
        }
    }

    static class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static void dfs(int x, int y) {

        Stack<Node> stack = new Stack<>();

        stack.add(new Node(x, y));
        visited[x][y] = true;

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            for (int i = 0; i < 4; i++) {
                int mx = node.x + move_x[i];
                int my = node.y + move_y[i];

                if (mx >= 0 && my >= 0 && mx < M && my < N) {
                    if (!visited[mx][my] && graph[mx][my] == 1) {

                        stack.add(new Node(mx, my));
                        visited[mx][my] = true;
                    }
                }
            }
        }
    }

    static void bfs(int x, int y) {

        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(x, y));
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            for (int i = 0; i < 4; i++) {
                int mx = node.x + move_x[i];
                int my = node.y + move_y[i];

                if (mx >= 0 && my >= 0 && mx < M && my < N) {
                    if (!visited[mx][my] && graph[mx][my] == 1) {
                        queue.add(new Node(mx, my));
                        visited[mx][my] = true;
                    }
                }
            }
        }
    }
}
