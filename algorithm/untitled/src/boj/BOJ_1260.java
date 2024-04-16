package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;


public class BOJ_1260 {

    static StringBuilder sb = new StringBuilder();
    static boolean[] check;
    static int[][] arr;

    static int node, line, start;

    static Queue<Integer> queue = new LinkedList<>();
    static Stack<Integer> stack = new Stack<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        node = Integer.parseInt(st.nextToken()); // N
        line = Integer.parseInt(st.nextToken()); // M
        start= Integer.parseInt(st.nextToken()); // V

        arr = new int[node+1][node+1];

        for (int i = 0; i < line; i++) {
            StringTokenizer str = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(str.nextToken());
            int y = Integer.parseInt(str.nextToken());

            arr[x][y] = arr[y][x] = 1;
        }

        check = new boolean[node+1];
        dfsR(start);

        // 초기화
        sb.append("\n");
        check = new boolean[node+1];
        bfs(start);

        System.out.println(sb);
    }

    public static void dfs(int start) {

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
            sb.append(start + " ");
        }
    }

    public static void dfsR(int start) {
        if (!check[start]) {
            sb.append(start + " ");
            check[start] = true;
            for (int r = 1; r <= node; r++) {
                if (arr[start][r] == 1 && !check[r]) {
                    dfsR(r);
                }
            }
        }
    }

    public static void bfs(int start) {

        check[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            start = queue.poll();
            sb.append(start + " ");

            for (int r = 1; r <= node; r++) {
                if (arr[start][r] == 1 && !check[r]) {
                    queue.add(r);
                    check[r] = true;
                }
            }
        }
    }
}
