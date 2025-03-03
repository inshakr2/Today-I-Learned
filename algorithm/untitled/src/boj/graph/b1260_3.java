package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class b1260_3 {

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

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            graph[x][y] = graph[y][x] = 1;
        }

        dfsR(V);
        sb.append("\n");
        visited = new boolean[N + 1];
        bfs(V);
        System.out.println(sb.toString());

    }

    static void dfs(int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start] = true;
        sb.append(start + " ");

        while (!stack.isEmpty()) {
            Integer pop = stack.pop();

            for (int i = 1; i <= N; i++) {
                if (!visited[i] && graph[pop][i] == 1) {
                    stack.push(i);
                    visited[i] = true;
                    sb.append(i + " ");
                }
            }
        }
    }

    static void dfsR(int start) {
        if (!visited[start]) {
            visited[start] = true;
            sb.append(start + " ");
            for (int i = 1; i <= N; i++) {
                if (!visited[i] && graph[start][i] == 1) {
                    dfsR(i);
                }
            }
        }
    }

    static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        sb.append(start + " ");

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();

            for (int i = 1; i <= N; i++) {
                if (!visited[i] && graph[poll][i] == 1) {
                    queue.offer(i);
                    visited[i] = true;
                    sb.append(i + " ");
                }
            }
        }
    }
}
