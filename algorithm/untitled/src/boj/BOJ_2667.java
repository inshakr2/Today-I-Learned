package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BOJ_2667 {

    static int node;
    static int[][] arr;
    static boolean[][] check;

    static int[] move_x = {1, -1, 0, 0};
    static int[] move_y = {0, 0, 1, -1};
    static int cnt;
    static List<Integer> result = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        node = Integer.parseInt(br.readLine());

        arr = new int[node+1][node+1];
        check = new boolean[node+1][node+1];

        for (int i = 1; i <= node; i++) {
            char[] charArray = br.readLine().toCharArray();
            for (int j = 1; j <= node; j++) {
                arr[i][j] = Character.getNumericValue(charArray[j - 1]);
            }
        }

        cnt = 1;
        for (int x = 0; x <= node; x++) {
            for (int y = 0; y <= node; y++) {
                if (!check[x][y] && arr[x][y] == 1) {
                    dfs(x, y);
                    result.add(cnt);
                    cnt = 1;
                }
            }
        }
        Collections.sort(result);
        System.out.println(result.size());
        for (Integer i : result) {
            System.out.println(i);
        }
    }

    public static void dfs(int x, int y) {

        check[x][y] = true;

        for (int m = 0; m < 4; m++) {
            int mx = x + move_x[m];
            int my = y + move_y[m];

            if (mx >= 0 && my >= 0 && mx <= node && my <= node
                && !check[mx][my] && arr[mx][my] == 1) {
                cnt++;
                dfs(mx, my);
            }
        }

    }
}
