package boj.graph;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class b1260_4 {

    static int N, M, V;
    static boolean[][] graph;
    static boolean[] visited;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        graph = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[x][y] = graph[y][x] = true;
        }

        dfsR(V);
        sb.append("\n");
        visited = new boolean[N + 1];
        bfs(V);

        bw.write(sb.toString());

        br.close();
        bw.close();

    }

    private static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            for (int i = 0; i <= N; i++) {
                if (!visited[i] && graph[node][i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
            sb.append(node + " ");
        }
    }

    private static void dfs(int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start] = true;

        while (!stack.isEmpty()) {
            Integer node = stack.pop();
            for (int i = 0; i <= N; i++) {
                if (!visited[i] && graph[node][i]) {
                    stack.push(i);
                    visited[i] = true;
                }
            }
            sb.append(node + " ");
        }
    }

    private static void dfsR(int node) {
        visited[node] = true;
        sb.append(node + " ");
        for (int i = 0; i <= N; i++) {
            if (!visited[i] && graph[node][i]) {
                dfsR(i);
            }
        }
    }
}
