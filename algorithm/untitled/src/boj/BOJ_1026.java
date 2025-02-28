package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1026 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        List<Integer> A = new ArrayList<>();
        List<Integer> B = new ArrayList<>();

        for (int a = 0; a < N; a++) {
            A.add(Integer.parseInt(st.nextToken()));
        }

        st = new StringTokenizer(br.readLine());
        for (int b = 0; b < N; b++) {
            B.add(Integer.parseInt(st.nextToken()));
        }

        A.sort(Comparator.reverseOrder());
        Collections.sort(B);

        int minSum = 0;
        for (int i = 0; i < N; i++) {
            minSum += (A.get(i) * B.get(i));
        }

        System.out.println(minSum);
    }
}
