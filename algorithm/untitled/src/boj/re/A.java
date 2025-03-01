package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class A {

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

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            graph[t][f] = graph[f][t] = 1;
        }

        dfsR(V);
        sb.append("\n");
        visited = new boolean[N + 1];
        bfs(V);
        System.out.println(sb.toString());
    }

    // stack
    static void dfs(int start) {
        Stack<Integer> stack = new Stack<>();

        visited[start] = true;
        stack.push(start);

        while (!stack.isEmpty()) {
            start = stack.pop();

            for (int i = 0; i <= N; i++) {
                if (graph[start][i] == 1 && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                }
            }

            sb.append(start + " ");
        }
    }

    // queue
    static void bfs(int start) {

        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {

            start = queue.poll();

            for (int i = 0; i <= N; i++) {
                if (graph[start][i] == 1 && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }

            sb.append(start + " ");
        }
    }

    static void dfsR(int start) {
        if (!visited[start]) {
            visited[start] = true;
            sb.append(start + " ");
            for (int i = 0; i <= N; i++) {
                if (graph[start][i] == 1 && !visited[i]) {
                    dfsR(i);
                }
            }
        }
    }
}
