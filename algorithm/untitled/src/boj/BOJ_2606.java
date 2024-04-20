package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_2606 {

    static int node, line;
    static boolean[] check;
    static int[][] arr;
    static Stack<Integer> stack = new Stack<>();
    static int count = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        node = Integer.parseInt(br.readLine());
        line = Integer.parseInt(br.readLine());

        check = new boolean[node + 1];
        arr = new int[node+1][node+1];

        for (int i = 0; i < line; i++) {
            StringTokenizer str = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(str.nextToken());
            int y = Integer.parseInt(str.nextToken());

            arr[x][y] = arr[y][x] = 1;
        }

        dfs();
        System.out.println(count);

    }

    public static void dfs() {

        int start = 1;
        check[start] = true;
        stack.push(start);

        while (!stack.isEmpty()) {

            start = stack.pop();
            for (int r = 1; r <= node; r++) {
                if (arr[start][r] == 1 && !check[r]) {
                    stack.push(r);
                    check[r] = true;
                }
            }
            count++;
        }
    }
}
