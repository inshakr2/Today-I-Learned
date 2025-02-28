package redbook;

public class Simulation1 {

    public int[] thePouring (int[] capacities, int[] bottles, int[] fromId, int[] toId) {

        for (int i = 0; i < fromId.length; i++) {

            int space = (capacities[toId[i]] - bottles[toId[i]]);

            if (bottles[fromId[i]] > space) {

                bottles[toId[i]] = capacities[toId[i]]; // 가득참
                bottles[fromId[i]] -= space;

            } else {

                bottles[toId[i]] += bottles[fromId[i]];
                bottles[fromId[i]] = 0; // 다 따름
            }

        }

        return bottles;
    }

    public int[] thePouring_sec (int[] capacities, int[] bottles, int[] fromId, int[] toId) {

        for (int i = 0; i < fromId.length; i++) {

            // 옮길 양과 옮길 주스 병의 남아있는 공간 중 더 작은 값이 결국은 옮길 양임
            int pour = Math.min(bottles[fromId[i]], (capacities[toId[i]] - bottles[toId[i]]));

            bottles[fromId[i]] -= pour;
            bottles[toId[i]] += pour;
        }

        return bottles;
    }
}
