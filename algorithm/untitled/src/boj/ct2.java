package boj;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ct2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        System.out.println(calculateHatchDragon(N));
    }

    public int solution(int n) {
        int answer = calculateHatchDragon(n);
        return answer;
    }

    static class Egg {
        int hatchDay;

        public Egg(int today) {
            this.hatchDay = today + 2;
        }
    }

    static class Dragon {
        int eggsCount;

        public Dragon() {
            this.eggsCount = 0;
        }

        boolean canHaveEgg() {
            return this.eggsCount < 5;
        }

        void haveEgg() {
            eggsCount++;
        }
    }

    public static int calculateHatchDragon(int n) {

        List<Dragon> dragons = new ArrayList<>();
        List<Egg> eggs = new ArrayList<>();

        eggs.add(new Egg(0));
        int totalEggs = 1;
        int result = 0;

        for (int day = 0; day <= n; day++) {

            // 알에서 부화 & 드래곤 추가
            int todayEggs = 0;
            for (Egg egg : eggs) {
                if (egg.hatchDay == day) {
                    dragons.add(new Dragon());
                    todayEggs++;
                }
            }

            // 부화한 알 제거
            totalEggs -= todayEggs;

            // 현재 드래곤 중 알 부화 계산 / 알 추가
            for (Dragon dragon : dragons) {
                if (dragon.canHaveEgg()) {
                    dragon.haveEgg();
                    eggs.add(new Egg(day));
                    totalEggs++;
                }
            }

            if (day == n) {
                result = dragons.size() + totalEggs;
            }
        }

        return result;
    }
}
