package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_10819 {

    static int[] depth, arr;
    static boolean[] visited = new boolean[201];
    static int N, max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        depth = new int[N + 1];
        arr = new int[N + 1];


        for (int n = 1; n <= N; n++) {
            arr[n] = Integer.parseInt(st.nextToken());
        }

        max = 0;
        permutation(1);
        System.out.println(max);
    }


    static void permutation(int level) {

        if (level > N) {
            for (int i : depth) {
                System.out.print(i + " ");
            }
            System.out.print("\n");
            max = Math.max(max, calc(depth));
            return;
        }

        for (int i = 1; i <= N; i++) {
            int n = arr[i];
//            if (!visited[n + 100]) {
//            visited[n + 100] = true;
            depth[level] = n;
            permutation(level + 1);
//            visited[n + 100] = false;
//            }
        }
    }

    static int calc(int[] arr) {
        int result = 0;
        for (int i = 1; i < N ; i++) {
            result += Math.abs(arr[i] - arr[i + 1]);
        }
        return result;
    }
}
