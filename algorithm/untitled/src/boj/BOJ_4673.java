package boj;

public class BOJ_4673 {
    static boolean[] notSelfNumbers = new boolean[10001];

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        for(int i=1;i<10000;i++)
            if(!notSelfNumbers[i]) {
                dn(i);
                sb.append(i+"\n");
            }

        System.out.println(sb);
    }

    static void dn(int n) {
        int dn = n;
        for(int i=n ; i>0 ; i/=10) dn += (i%10);

        if(dn<=10000 && !notSelfNumbers[dn]) {
            notSelfNumbers[dn]=true;
            dn(dn);
        }
    }
}
