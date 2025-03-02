package algstudent.s0;

import java.util.ArrayList;

public class JavaA3 {
    
    public static boolean isPrime(int m) {
        for (int i = 2; i <= m / 2; i++) {
            if (m % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static ArrayList<Integer> listPrimes(int n) {
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }
    
    public static void main(String[] args) {
        int n = 10;
        for (int casos = 0; casos < 7; casos++) {
            long t1 = System.currentTimeMillis();
            //ArrayList<Integer> primes = listPrimes(n);
            long t2 = System.currentTimeMillis();
            System.out.println("n = " + n + " *** time = " + (t2 - t1) + " milliseconds");
            n *= 2;
        }
    }
}
