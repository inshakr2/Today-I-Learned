package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_10773 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Integer> stack = new Stack<>();

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            int k = Integer.parseInt(br.readLine());

            if (k == 0) {
                stack.pop();
            } else {
                stack.push(k);
            }
        }

        int sum = stack.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);

    }
}
