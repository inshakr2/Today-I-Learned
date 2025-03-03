package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BeakJoon10773 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < K; i++) {

            int k = Integer.parseInt(br.readLine());
            if (k == 0) {
                if (i == 0) {
                    continue;
                }
                stack.pop();
            } else {
                stack.push(k);
            }
        }

        int count = 0;
        for (Integer i : stack) {
            count += i;
        }
        System.out.println(count);
    }
}
