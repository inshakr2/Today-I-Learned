import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

    }

    static boolean isYoon;

    public int solution(String[] birth) {

        int answer = 0;

        for (String s : birth) {

            if (s.length() > 10) {
                continue;
            }
            String[] test = s.split("-");

            if (test.length != 3) {
                continue;
            }

            if (test[0].length() != 4 || test[1].length() != 2 || test[2].length() != 2) {
                continue;
            }
            int yyyy = Integer.parseInt(test[0]);
            isYoon = checkYoon(yyyy);
            if (!checkYYYY(yyyy)) {
                continue;
            }

            int mm = parseMM(test[1]);
            if (mm == -1) {
                continue;
            }

            int dd = parseDD(test[2]);
            if (dd == -1) {
                continue;
            }

            if (mm == 2 && isYoon && dd > 29) {
                continue;
            }

            if (mm == 2 && !isYoon && dd > 28) {
                continue;
            }

            // 4 6 9 11
            if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && dd > 30) {
                continue;
            }

            // 1 3 5 7 8 10
            if ((mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10) && dd > 31) {
                continue;
            }


            answer++;
        }
        return answer;
    }

    public static int parseMM(String mm) {
        if (mm.startsWith("0")) {
            int i = Integer.parseInt(mm.substring(1));
            if (i < 1) {
                return -1;
            }
            return i;
        } else {
            int i = Integer.parseInt(mm);
            if (i > 12) {
                return -1;
            }
            return i;
        }
    }

    public static int parseDD(String dd) {
        if (dd.startsWith("0")) {
            int i = Integer.parseInt(dd.substring(1));
            if (i < 1) {
                return -1;
            }
            return i;
        } else {
            int i = Integer.parseInt(dd);
            if (i > 31) {
                return -1;
            }
            return i;
        }
    }

    public static boolean checkYYYY(int yyyy) {
        if (yyyy >= 1900 && yyyy <= 2021) {
            return true;
        }
        return false;
    }

    public static boolean checkYoon(int yyyy) {

        if (yyyy % 400 == 0) {
            return true;
        }

        if (yyyy % 4 == 0 && yyyy % 100 != 0) {
            return true;
        }

        return false;
    }
}