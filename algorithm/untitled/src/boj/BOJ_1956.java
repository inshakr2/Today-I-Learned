package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1956 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        for (int i = 0; i < tc; i++) {

            Queue<int[]> queue = new LinkedList<>();

            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int token = Integer.parseInt(st1.nextToken());

                queue.add(new int[]{j, token});
            }

            int count = 0;
            // idx 0 = 최초 입력 순서 & idx 1 = 우선순위
            while (!queue.isEmpty()) {
                boolean isMax = true;
                int[] element = queue.poll();

                for (int[] q : queue) {
                    if (element[1] < q[1]) {
                        queue.add(element);
                        isMax = false;
                        break;
                    }
                }

                if (isMax) {
                    count++;
                }

                if (isMax && element[0] == M) {
                    break;
                }
            }

            System.out.println(count);
        }
    }
}
