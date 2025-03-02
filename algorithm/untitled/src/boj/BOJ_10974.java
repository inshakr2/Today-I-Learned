package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_10974 {

    static int N;
    static boolean[] visited;
    static int[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        visited = new boolean[N + 1];
        graph = new int[N + 1];

        permutation(1);

    }

    static void permutation(int depth) {

        if (depth > N) {
            for (int i = 1; i <= N; i++) {
                System.out.print(graph[i] + " ");
            }
            System.out.print("\n");
            return;
        }

        for (int n = 1; n <= N; n++) {

            if (!visited[n]) {
                visited[n] = true;
                graph[depth] = n;
                permutation(depth + 1);
                visited[n] = false;
            }
        }
    }
}
