package boj.graph;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class b2606_2 {

    static int N;
    static int M;
    static boolean[][] graph;
    static boolean[] visited;
    static int answer;

    public static void dfs(int start) {
        Stack<Integer> stack = new Stack<>();

        stack.push(start);
        visited[start] = true;

        while (!stack.isEmpty()) {
            Integer node = stack.pop();

            for (int i = 0; i <= N; i++) {
                if (!visited[i] && graph[node][i]) {
                    stack.push(i);
                    visited[i] = true;
                    answer++;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        graph = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[x][y] = true;
            graph[y][x] = true;
        }

        dfs(1);

        bw.write(String.valueOf(answer));

        bw.close();
        br.close();
    }
}