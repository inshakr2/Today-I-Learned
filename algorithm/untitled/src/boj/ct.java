package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;


public class ct {

    public static void main(String[] args) {

    }

    public static String convertTo24HAfterN(String p, int n) {

        String[] split = p.split(" ");
        String part = split[0];

        int hours = Integer.parseInt(split[1].split(":")[0]);
        int minutes = Integer.parseInt(split[1].split(":")[1]);
        int seconds = Integer.parseInt(split[1].split(":")[2]);

        if (part.equals("AM") && hours != 12) {
            hours += 12;
        } else if (part.equals("PM") && hours == 12) {
            hours = 0;
        }

        int calculateAfterNSeconds = (hours * 3600) + (minutes * 60) + seconds + n;
        calculateAfterNSeconds %= 86400;

        int newHours = calculateAfterNSeconds / 3600;
        int newMinutes = (calculateAfterNSeconds % 3600) / 60;
        int newSeconds = calculateAfterNSeconds % 60;

        return String.format("%02d:%02d:%02d", newHours, newMinutes, newSeconds);

    }
}
