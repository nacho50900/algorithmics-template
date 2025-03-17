package algstudent.s3;

public class Division4 {
	public static long rec4 (int n)
	{ 
	 long cont = 0;
	 if (n<=0) cont++;
	 else
	  { for (int i=1;i<n;i++)  
	  		for (int j=1;j<n;j++) cont++ ;
	    rec4(n/3);
	  }
	 return cont;   
	}
	
	public static void main (String arg []) 
	{
		long t1,t2,cont = 0;
		for (int n=1000;n<=10000000;n*=2)
		 {
			 t1 = System.currentTimeMillis ();
			
			 cont=rec4(n);
			 
			 t2 = System.currentTimeMillis ();
			
			 System.out.println ("n="+n+ "**TIME="+(t2-t1)+"**cont="+cont);
		 }  // for
	} // main
} //class