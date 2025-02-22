package algstudent.s0;

import java.util.ArrayList;
import java.util.List;

public class JavaA1 {
	
	static List<Integer> listadoPrimos(int n) {
	    //calculates and returns all primes up to n
	    List<Integer> primes = new ArrayList<Integer>();
	    for(int i=2; i<2*n+1; i++) {
	        if (primoA1(i)) {
	            primes.add(i);
	        }
	    }
	    return primes;
	}
	    
	static boolean primoA1(int m) {
	    //returns whether m is prime or not """
		boolean p = true;
	    for (int i=2; i<m; i++) {
	        if (m%i == 0) {
	        	p = false;
	        }
	    }
	    return p;
	 }
	 
	public static void main(String[] args) {
		int n = 10000;
		for (int casos=0; casos<=7; casos++) {
			long t1 = System.currentTimeMillis();
			//List<Integer> primes = listadoPrimos(n);
			long t2 = System.currentTimeMillis();
			long t3 = ((t2-t1));
			System.out.println("n = " + n + "***" + "time = " + t3 + " milliseconds)");
			//System.out.println(primes);
			n = n*2;
		}
	}
}