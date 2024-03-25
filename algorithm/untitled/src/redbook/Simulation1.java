package redbook;

public class Simulation1 {

    public int[] thePouring(int[] capacities, int[] bottles, int[] fromId, int[] toId) {

//        for (int i = 0; i < fromId.length; i++) {
//
//            // bottle[from] -> bottle[to]
//            //  -> capacity 확인
//
//            int pour = bottles[fromId[i]] - (capacities[toId[i]] - bottles[toId[i]]);
//
//            if (pour > 0) {
//                bottles[fromId[i]] = bottles[fromId[i]] - Math.abs(pour);
//                bottles[toId[i]] = capacities[toId[i]];
//            } else {
//                bottles[toId[i]] = bottles[fromId[i]] + bottles[toId[i]];
//                bottles[fromId[i]] = 0;
//            }
//
//        }

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
}
