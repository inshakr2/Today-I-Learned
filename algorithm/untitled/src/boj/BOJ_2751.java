package boj;

import java.io.*;

public class BOJ_2751 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());

        // -1,000,000 ~ 1,000,0000
        boolean[] arr = new boolean[2000001];

        for (int i = 0; i < N; i++) {
            arr[Integer.parseInt(br.readLine()) + 1000000] = true;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i]) {
                bw.write(i - 1000000 + "\n");
            }
        }

        br.close();
        bw.flush();
        bw.close();
    }
}
