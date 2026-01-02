package boj.alg_250715;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj1966_250715 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        for (int i = 0; i < tc; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            Queue<int[]> queue = new LinkedList<>();

            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int priority = Integer.parseInt(st1.nextToken());
                queue.offer(new int[]{j, priority});
            }

            int count = 0;
            while (!queue.isEmpty()) {

                int[] turn = queue.poll();
                boolean isMax = true;

                for (int[] q : queue) {
                    if (turn[1] < q[1]){
                        queue.offer(turn);
                        isMax = false;
                        break;
                    }
                }

                if (isMax) {
                    count++;
                }


                if (isMax && turn[0] == M) {
                    break;
                }
            }

            System.out.println(count);
        }
    }
}
