package boj.re;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BeakJoon17413 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine();

        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack<>();
        char[] charArray = S.toCharArray();

        boolean isTag = false;

        for (int i = 0; i < charArray.length; i++) {

            String s = String.valueOf(charArray[i]);

            if (s.equals("<")) {
                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
                isTag = true;
                sb.append(s);
                continue;
            }

            if (s.equals(">")) {
                isTag = false;
                sb.append(s);
                continue;
            }

            if (s.equals(" ") && !isTag) {
                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
                sb.append(" ");
                continue;
            }

            if (!isTag) {
                stack.add(s);
                if (s.equals(" ") || i == charArray.length -1) {
                    while (!stack.isEmpty()) {
                        sb.append(stack.pop());
                    }
                }
            } else {
                sb.append(s);
            }
        }

        System.out.println(sb.toString());
    }
}
