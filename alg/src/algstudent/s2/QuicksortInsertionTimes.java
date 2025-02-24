package algstudent.s2;

public class QuicksortInsertionTimes {
	static int[] v;

	public static void main(String arg[]) {
		long t1, t2;

		v = new int[16000000];

		Vector.randomSorted(v);

		t1 = System.currentTimeMillis();

		QuicksortInsertion.quicksort(v);

		t2 = System.currentTimeMillis();

		System.out.println(16000000 + "\t" + (t2 - t1));
	}
}
