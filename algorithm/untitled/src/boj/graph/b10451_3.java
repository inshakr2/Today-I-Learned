package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class b10451_3 {

    static int N;
    static int[][] graph;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        for (int t = 0; t < tc; t++) {

            N = Integer.parseInt(br.readLine());
            graph = new int[N + 1][N + 1];
            visited = new boolean[N + 1];
            int count = 0;
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int n = 1; n <= N; n++) {
                int x = Integer.parseInt(st.nextToken());

                graph[n][x] = graph[x][n] = 1;
            }

            for (int n = 1; n <= N; n++) {
                if (!visited[n]) {
                    dfsR(n);
                    count++;
                }
            }
            System.out.println(count);
        }
    }

    static void dfs(int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start] = true;

        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            for (int i = 1; i <= N; i++) {
                if (!visited[i] && graph[pop][i] == 1) {
                    stack.push(i);
                    visited[i] = true;
                }
            }
        }
    }

    static void dfsR(int start) {
        if (!visited[start]) {
            visited[start] = true;
            for (int i = 1; i <= N; i++) {
                if (!visited[i] && graph[start][i] == 1) {
                    dfs(i);
                }
            }
        }
    }
}
