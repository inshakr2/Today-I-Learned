package boj.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b10974_1 {

    static int N;
    static int[] depth;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        depth = new int[N + 1];
        visited = new boolean[N + 1];

        permutation(1);

    }

    static void permutation(int level) {

        if (level > N) {
            for (int i = 1; i <= N; i++) {
                System.out.print(depth[i] + " ");
            }
            System.out.print("\n");
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                depth[level] = i;
                visited[i] = true;
                permutation(level + 1);
                visited[i] = false;
            }
        }
    }
}
