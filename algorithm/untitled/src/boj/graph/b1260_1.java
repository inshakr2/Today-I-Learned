package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class b1260_1 {

    static int N, M, V;
    static int[][] graph;
    static boolean[] visited;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        graph = new int[N + 1][N + 1];
        visited = new boolean[N + 1];

        StringTokenizer st1 = null;
        for (int i = 1; i <= N; i++) {
            st1 = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st1.nextToken());
            int y = Integer.parseInt(st1.nextToken());

            graph[x][y] = graph[y][x] = 1;
        }

        // dfs
        // bfs
        // dfs Recursion
        dfsR(V);
        sb.append("\n");
        visited = new boolean[N + 1];
        bfs(V);
        System.out.println(sb.toString());

    }

    static void dfs(int start) {
        Stack<Integer> stack = new Stack();

        stack.push(start);
        visited[start] = true;

        while (!stack.isEmpty()) {
            Integer pop = stack.pop();

            for (int i = 1; i <= N; i++) {
                if (graph[pop][i] == 1 && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                }
            }

            sb.append(pop + " ");
        }
    }

    static void dfsR(int start) {
        visited[start] = true;
        sb.append(start + " ");
        for (int i = 1; i <= N; i++) {
            if (graph[start][i] == 1 && !visited[i]) {
                dfsR(i);
            }
        }
    }

    static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (int i = 1; i <= N; i++) {
                if (graph[poll][i] == 1 && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
            sb.append(poll + " ");
        }
    }
}
