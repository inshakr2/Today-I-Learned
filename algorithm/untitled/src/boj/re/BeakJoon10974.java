package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BeakJoon10974 {

    static int[] graph;
    static boolean[] visited;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        graph = new int[N + 1];
        visited = new boolean[N + 1];

        permutation(1);
    }

    static void permutation(int depth) {

        if (depth > N) {
//            visited = new boolean[N + 1];
            for (int i = 1; i <= N; i++) {
                System.out.print(graph[i] + " ");
            }
            System.out.print("\n");
            return;
        }

        for (int n = 1; n <= N; n++) {
            if (!visited[n]) {
                graph[depth] = n;
                visited[n] = true;
                permutation(depth + 1);
                visited[n] = false;
            }
        }
    }
}
