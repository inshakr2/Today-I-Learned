package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class BaekJoon1260 {

    static StringBuilder sb = new StringBuilder();

    static int[][] graph;
    static boolean[] visit;

    static int node, line, startNode;

    static Stack<Integer> stack = new Stack<>();
    static Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        node = Integer.parseInt(st.nextToken());
        line = Integer.parseInt(st.nextToken());
        startNode = Integer.parseInt(st.nextToken());

        graph = new int[node + 1][node + 1];
        visit = new boolean[node + 1];

        for (int i = 0; i < line; i++) {
            StringTokenizer str = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(str.nextToken());
            int y = Integer.parseInt(str.nextToken());

            graph[x][y] = graph[y][x] = 1;
        }

        dfsR(startNode);
        visit = new boolean[node + 1];
        bfs(startNode);

        System.out.println(sb.toString());
//        for (int[] ints : graph) {
//            System.out.print("[");
//            for (int anInt : ints) {
//                System.out.print(anInt);
//                System.out.print(",");
//            }
//            System.out.print("] \n");
//        }
/**
 * [0,0,0,0,0]
 * [0,0,1,1,1]
 * [0,1,0,0,1]
 * [0,1,0,0,1]
 * [0,1,1,1,0]
 */

    }

    static void dfs(int startNode) {

        visit[startNode] = true;
        stack.push(startNode);

        while (!stack.isEmpty()) {

            startNode = stack.pop();

            for (int r = 0; r <= node; r++) {
                if (graph[startNode][r] == 1 && !visit[r]) {
                    stack.push(r);
                    visit[r] = true;
                }
            }

            sb.append(startNode + " ");
        }
    }

    static void dfsR(int startNode) {
        if (!visit[startNode]) {
            sb.append(startNode + " ");
            visit[startNode] = true;
            for (int r = 0; r <= node; r++) {
                if (graph[startNode][r] == 1 && !visit[r]) {
                    dfsR(r);
                }
            }
        }
    }

    static void bfs(int startNode) {
        sb.append("\n");
        visit[startNode] = true;
        queue.add(startNode);

        while (!queue.isEmpty()) {

            startNode = queue.poll();

            for (int r = 0; r <= node; r++) {
                if (graph[startNode][r] == 1 && !visit[r]) {
                    queue.add(r);
                    visit[r] = true;
                }
            }

            sb.append(startNode + " ");
        }
    }
}
