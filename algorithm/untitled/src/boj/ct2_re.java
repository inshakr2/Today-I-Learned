package boj;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ct2_re {

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

        int agedDragon = 0;
        Queue<Dragon> dragons = new LinkedList<>();
        Queue<Egg> eggs = new LinkedList<>();

        eggs.add(new Egg(0));
        int totalEggs = 1;
        int result = 0;

        for (int day = 0; day <= n; day++) {

            // 알에서 부화 & 드래곤 추가
            int todayEggs = 0;
            while (!eggs.isEmpty() && eggs.peek().hatchDay == day) {
                eggs.poll();
                dragons.add(new Dragon());
                todayEggs++;
            }

            // 부화한 알 제거
            totalEggs -= todayEggs;

            // 현재 드래곤 중 알 부화 계산 / 알 추가
            while (!dragons.isEmpty() && !dragons.peek().canHaveEgg()) {
                dragons.poll();
                agedDragon++;
            }
            for (Dragon dragon : dragons) {
                dragon.haveEgg();
                eggs.add(new Egg(day));
                totalEggs++;
            }

            if (day == n) {
                result = dragons.size() + totalEggs + agedDragon;
            }
            System.out.println("day = " + day);
            System.out.println("dragons.size() = " + dragons.size());
            System.out.println("eggs.size() = " + eggs.size());
            System.out.println("###########");
        }

        return result;
    }
}
