package boj;

public class avc {
    public static void main(String[] args) {

    }

    public boolean solution(int n) {
        return isPalindrome(n);
    }


    public static boolean isPalindrome(int number) {
        String str = String.valueOf(number);
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}