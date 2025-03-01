package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class C {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine();
        char[] charArray = S.toCharArray();

        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack<>();
        boolean isTag = false;

        for (int i = 0; i < charArray.length; i++) {
            String c = String.valueOf(charArray[i]);

            if (c.equals("<")) {
                isTag = true;
                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
                sb.append(c);
                continue;
            }

            if (c.equals(">")) {
                isTag = false;
                sb.append(c);
                continue;
            }

            if (!isTag && c.equals(" ")) {
                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
                sb.append(" ");
                continue;
            }

            if (isTag) {
                sb.append(c);
            } else {
                stack.push(c);
            }

        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        System.out.println(sb.toString());
    }
}
