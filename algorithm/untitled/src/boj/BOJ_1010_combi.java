package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1010_combi {

    static int[][] visited = new int[30][30];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        StringTokenizer st;

        for (int t = 0; t < tc; t++) {
            st = new StringTokenizer(br.readLine());
            int nSite = Integer.parseInt(st.nextToken());
            int mSite = Integer.parseInt(st.nextToken());

            int bridge = combinationR(mSite, nSite);

            System.out.println(bridge);
        }
    }

    private static int combinationR(int n, int r) {

        if (visited[n][r] > 0) {
            return visited[n][r];
        }

        if (n == r || r == 0) {
            return visited[n][r] = 1;
        }

        visited[n][r] = combinationR(n - 1, r - 1) + combinationR(n - 1, r);
        return visited[n][r];
    }
}
