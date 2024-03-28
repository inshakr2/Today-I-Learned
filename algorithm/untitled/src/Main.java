import redbook.Simulation1;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Simulation1 simulation1 = new Simulation1();
        int[] ints = simulation1.thePouring_sec(
                new int[]{14, 35, 86, 58, 25, 62},
                new int[]{6, 34, 27, 38, 9, 60},
                new int[]{1, 2, 4, 5, 3, 3, 1, 0},
                new int[]{0, 1, 2, 4, 2, 5, 3, 1});

        System.out.println("ints = " + Arrays.toString(ints));
    }
}