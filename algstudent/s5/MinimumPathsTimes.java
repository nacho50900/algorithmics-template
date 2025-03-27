package algstudent.s5;

import java.util.Random;

public class MinimumPathsTimes {
	static double p1=0.5; 
	static double p2=0.5; //50/50
	static int minWeight=10;
	static int maxWeight=99;

	static String[] v; //node vector
	static int[][] weights; //weight matrix
	static int[][] costs; //Floyd's paths cost matrix
	static int[][] p; //predecessor matrix (steps) in Floyd paths


	static void generateRandomWeights(int[][] weights) {
		Random random = new Random();
		for (int source = 0; source < weights.length; source++)
			for (int target = 0; target < weights.length; target++)
				if(random.nextInt(100) > p1*100) {
					weights[source][target] = random.nextInt(minWeight, maxWeight); 
				}
	}
	
	
	public static void main(String arg[]) {
		int n = 200;
		while(true) {
			
			long t1 = System.currentTimeMillis();
			
			v = new String[n];
			for (int i = 0; i < n; i++)
				v[i] = "NODE" + i;
	
			weights = new int[n][n];
			costs = new int[n][n];
			p = new int[n][n];
	
			generateRandomWeights(weights); //Random Generated Weights
			//System.out.println("WEIGHT MATRIX IS:");
			printMatrix(weights);
	
			floyd(weights, costs, p);
	
			//System.out.println("MINIMUM COST MATRIX IS:");
			printMatrix(costs);
	
			//System.out.println("P MATRIX IS:");
			printMatrix(p);
	
			//System.out.println();
			//System.out.println("MINIMUM PATHS IN THE EXAMPLE GRAPH (for every pair of different nodes):");
			//System.out.println();
			for (int source = 0; source <= n-1; source++)
				for (int target = 0; target <= n-1; target++)
					if (source != target) {
						//System.out.print("FROM " + v[source] + " TO " + v[target] + " = ");
						minimumPath(v, weights, costs, p, source, target);
						if (costs[source][target] < 10000000) {
							//System.out.println("MINIMUM COST=" + costs[source][target]);
						}//System.out.println("**************");
					}
			
			long t2 = System.currentTimeMillis();
			long tElpased = t2-t1;;
			System.out.println("N:"+ n + " -> Time:" + tElpased);
			n *= 2;
		}

	}

	/* ITERATIVE WITH CUBIC COMPLEXITY O(n^3) */
	static void floyd(int[][] weights, int[][] costs, int[][] p) {
		int n = weights.length;
		//System.out.println("COMPLETE THIS METHOD");
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				costs[i][j] = weights[i][j];
				if (weights[i][j] != 10000000 && i != j) { //Initialize p
	                p[i][j] = i;
	            } else {
	                p[i][j] = -1; 
	            }
			}
		}
		
		for (int pivot=0; pivot<n; pivot++) {
			for (int target=0; target<n; target++) {
				for (int origin=0; origin<n; origin++) {
					if (costs[origin][pivot] + costs[pivot][target] < costs[origin][target]) {
						costs[origin][target] = costs[origin][pivot] + costs[pivot][target];
						p[origin][target] = p[pivot][target];
					}
				} //Error when various pivots needed.
			}
		}
	}

	static void minimumPath(String[] v, int[][] weights, int[][] costs, int[][] steps, int source, int target) {
		if(costs[source][target] < 10000000) {
			//System.out.print(v[source]);
			path(v,steps,source,target);
			//System.out.print("-->" + v[target]);
			//System.out.println();
		}else {
			//System.out.println("THERE IS NO PATH");
		}
	}

	/* IT IS RECURSIVE and WORST CASE is O(n), IT IS O(n) if you write all nodes */
	static void path(String[] v, int[][] steps, int i, int j) {
		if (steps[i][j] == -1) {
			return;
		}
		int intermediate = steps[i][j];

	    if (intermediate == i || intermediate == j) {  // Avoid infinite loop
	        return;
	    }
	    path(v, steps, i, intermediate);
	    //System.out.print("-->" + v[intermediate]); 
	    path(v, steps, intermediate, j);
	}

	/* print the cost matrix */
	static void printMatrix(int[][] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				//System.out.print(String.format("%10s", a[i][j]));
			}//System.out.println();
		}
		//System.out.println();
	}
}