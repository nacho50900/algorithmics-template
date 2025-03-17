package algstudent.s3;

public class Division5 {
	public static long rec5 (int n)
	{
     long cont = 0;
	 if (n<=0) cont++;
	 else
	  { cont++ ; // O(1)    
	    rec5(n/2);
	    rec5(n/2);
	    rec5(n/2);
	    rec5(n/2);
	  }
	 return cont;   
	}
	
	public static void main (String arg []) 
	{
		 long t1,t2,cont = 0;	 
		 for (int n=1000;n<=10000000;n*=2)
		 {
			  t1 = System.currentTimeMillis ();
			   
			  cont = rec5(n);
			      
			  t2 = System.currentTimeMillis ();
			
			  System.out.println ("n="+n+ "**TIME="+(t2-t1)+"**cont="+cont);	
		 }  // for
	} // main
} //class