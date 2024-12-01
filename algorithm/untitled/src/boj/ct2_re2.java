package boj;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class ct2_re2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        System.out.println(calculateHatchDragon(N));
    }

    public int solution(int n) {
        int answer = calculateHatchDragon(n);
        return answer;
    }

    public static int calculateHatchDragon(int n) {

        int[] dragons = new int[n + 1];
        int[] eggs = new int[n + 1];

        // day 1/2 모두 알 하나
        if (n >= 0) {
            eggs[0] = 1;
        }
        if (n >= 1) {
            eggs[1] = 1;
        }
        if (n >= 2) {
            dragons[2] = 1;
        }

        for (int day = 3; day <= n; day++) {

            // 2일전 부화
            dragons[day] = (day >= 2) ? eggs[day - 2] : 0;

            // 오늘 부화
            eggs[day] = (day >= 1) ? (eggs[day -1] + dragons[day - 1] - ((day >= 3) ? dragons[day - 3] : 0)) : 0;
        }


        return eggs[n] +dragons[n];
    }
}
