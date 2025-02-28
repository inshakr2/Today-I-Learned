package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BOJ_2108 {

    static int N;
    static int[] count = new int[8001];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        List<Integer> inputs = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int n = Integer.parseInt(br.readLine());
            inputs.add(n);
            count[n + 4000]++;
        }

        Collections.sort(inputs);

        System.out.println(mean(inputs));
        System.out.println(median(inputs));
        System.out.println(mode(inputs));
        System.out.println(scope(inputs));
    }

    static int mean(List<Integer> inputs) {

        int sum = 0;
        for (Integer input : inputs) {
            sum += input;
        }

        return (int) Math.round((double) sum / N);
    }

    static int median(List<Integer> inputs) {

        return inputs.get(N / 2);
    }

    // 3996 3997 3998 3999 4000
    //  0    1    2    2     0
    static int mode(List<Integer> inputs) {

        boolean isSec = false;
        int max = 0;
        int mode = inputs.get(0);
        for (int i = 0; i < count.length; i++) {

            if (count[i] == 0) continue;

            if (count[i] > max) {
                mode = i - 4000;
                max = count[i];
                isSec = true;
            } else if (isSec && max == count[i]) {
                mode = i - 4000;
                isSec = false;
            }
        }

        return mode;
    }

    static int scope(List<Integer> inputs) {

        return inputs.get(N - 1) - inputs.get(0);
    }
}
