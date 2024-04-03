package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_17413 {
        static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        static void solve(String target) {
            StringBuilder builder = new StringBuilder();
            StringTokenizer leftTagTokenizer = new StringTokenizer(target, "<");
            while (leftTagTokenizer.hasMoreTokens()) {
                String leftToken = leftTagTokenizer.nextToken();
                boolean includeTag = leftToken.contains(">");
                StringTokenizer rightTagTokenizer = new StringTokenizer(leftToken, ">");
                while (rightTagTokenizer.hasMoreTokens()) {
                    if (includeTag) {
                        builder.append("<");
                        builder.append(rightTagTokenizer.nextToken());
                        builder.append(">");
                        includeTag = false;
                    } else {
                        String tagToken = rightTagTokenizer.nextToken();
                        StringTokenizer spaceTokenizer = new StringTokenizer(tagToken);
                        while (spaceTokenizer.hasMoreTokens()) {
                            StringBuilder reverseBuilder = new StringBuilder(spaceTokenizer.nextToken());
                            reverseBuilder.reverse();
                            builder.append(reverseBuilder);
                            if (spaceTokenizer.hasMoreTokens()) {
                                builder.append(" ");
                            }
                        }
                    }
                }
            }
            System.out.println(builder);
        }

        public static void main(String[] args) throws IOException {
            String line = reader.readLine();
            solve(line);
        }
    }
