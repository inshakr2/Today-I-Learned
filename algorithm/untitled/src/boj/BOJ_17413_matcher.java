package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BOJ_17413_matcher {

    private static final Pattern tagPattern = Pattern.compile("<.[a-zA-Z가-힣\\s]*>");

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        Matcher tagMatcher = tagPattern.matcher(input);

        StringBuilder sb = new StringBuilder();

        int exEndIndex = -1;

        if (tagMatcher.find()) {
            exEndIndex = tagMatcher.end();
            sb.append(tagMatcher.group());

            while (tagMatcher.find()) {
                if (exEndIndex != -1) {

                    String str = input.substring(exEndIndex, tagMatcher.start());
                    List<String> split = Arrays.asList(str.split(" "));
                    for (String s : split) {
                        sb.append(reverse(s));
                        if (split.size() > 1) {
                            sb.append(" ");
                        }
                    }
                    exEndIndex = tagMatcher.end();
                }

                exEndIndex = tagMatcher.end();
                sb.append(tagMatcher.group());
            }

            String str = input.substring(exEndIndex);
            List<String> split = Arrays.asList(str.split(" "));
            for (String s : split) {
                sb.append(reverse(s));
                if (split.size() > 1) {
                    sb.append(" ");
                }
            }
        } else {
            List<String> split = Arrays.asList(input.split(" "));
            for (String s : split) {
                sb.append(reverse(s));
                if (split.size() > 1) {
                    sb.append(" ");
                }
            }
        }

        System.out.println(sb.toString());
    }

    private static String reverse(String str) {
        if (str.isEmpty()) {
            return str;
        }

        return reverse(str.substring(1)) + str.charAt(0);
    }
}
