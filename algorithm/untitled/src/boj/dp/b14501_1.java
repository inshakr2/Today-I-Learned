package boj.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b14501_1 {

    static int[] timeTable;
    static int[] payTable;
    static int[] incomes;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        timeTable = new int[N + 1];
        payTable = new int[N + 1];
        incomes = new int[N + 2];

        StringTokenizer st;
        for (int d = 1; d <= N; d++) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            timeTable[d] = t;
            payTable[d] = p;
        }

        for (int day = 1; day <= N; day++) {
            if (day + timeTable[day] <= N+1) {
                incomes[day + timeTable[day]] = Math.max(incomes[day + timeTable[day]], incomes[day] + payTable[day]);
            }

            incomes[day + 1] = Math.max(incomes[day], incomes[day + 1]);
        }

        System.out.println(incomes[N+1]);
    }

    // 1
    // day + timetable[day] = paytable[day]
}
