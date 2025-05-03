package algstudent.s7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Branch and Bound solver for the Hamiltonian path problem from node 0 to n-1,
 * selecting the path whose total cost (absolute) is under 100 if possible.
 */
public class NullPathBB {
	static double p1=0.5; 
	static double p2=0.5;
    static final int MIN_WEIGHT = 10;
    static final int MAX_WEIGHT = 99;

    static int n;
    static int[][] weights;

    private List<Integer> bestPath = new ArrayList<>();
    static boolean[] visited;
    static List<Integer> path;

    public static void main(String[] args) {
    	long t1 = System.currentTimeMillis();
        n = Integer.parseInt(args[0]);
        weights = new int[n][n];
        visited = new boolean[n];
    	for(int repetitions=0; repetitions<100; repetitions++) {
	        path = new ArrayList<>();
	        generateRandomWeights(weights);
	
	        //System.out.println("WEIGHT MATRIX IS:");
	        //printMatrix(weights);
	
	        NullPathBB solver = new NullPathBB();
	        solver.solve();
	
	        //System.out.println("Best Path: " + solver.bestPath);
	        int totalCost = solver.calculateTotalCost();
	        //System.out.println("Total Cost: " + totalCost);
    	}
        long t2 = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (t2-t1));
    }

    /**
     * Perform Branch and Bound search: pop best partial paths by lexicographic edge cost,
     * accept first full path ending at n-1 whose |cost|<100.
     */
    public void solve() {
        PriorityQueue<State> pq = new PriorityQueue<>();
        visited[0] = true;
        path.add(0);
        pq.add(new State(path, visited, new ArrayList<>()));

        while (!pq.isEmpty()) {
            State st = pq.poll();
            // if full path and ends at n-1
            if (st.path.size() == n && st.path.get(n - 1) == n - 1) {
                int currentCost = calculateCost(st.path);
                if (Math.abs(currentCost) < 100) {
                    bestPath = new ArrayList<>(st.path);
                    //System.out.println("Found path with cost<100: " + bestPath);
                    //System.out.println("Total Cost: " + cost);
                    return;
                }
                // otherwise continue searching
            }
            // expand partial path
            int curr = st.path.get(st.path.size() - 1);
            for (int next = 0; next < n; next++) {
                if (!st.visited[next]) {
                    int cost = Math.abs(weights[curr][next]);
                    List<Integer> newEdges = new ArrayList<>(st.absEdges);
                    newEdges.add(cost);
                    boolean[] newVisited = Arrays.copyOf(st.visited, n);
                    newVisited[next] = true;
                    List<Integer> newPriorityQueue = new ArrayList<>(st.path);
                    newPriorityQueue.add(next);
                    pq.add(new State(newPriorityQueue, newVisited, newEdges));
                }
            }
        }
        System.out.println("No path found with total cost <100.");
    }

    /**
     * Compute total cost of a path.
     */
    private int calculateTotalCost() {
        return calculateCost(bestPath);
    }

    private int calculateCost(List<Integer> path) {
        int sum = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int u = path.get(i);
            int v = path.get(i + 1);
            sum += weights[u][v];
        }
        return sum;
    }

    /**
     * State for B&B: current path, visited mask, and lex-edge sequence.
     */
    private class State implements Comparable<State> {
        List<Integer> path;
        boolean[] visited;
        List<Integer> absEdges;

        State(List<Integer> p, boolean[] v, List<Integer> edges) {
            this.path = p;
            this.visited = v;
            this.absEdges = edges;
        }

        @Override
        public int compareTo(State other) {
            return lexCompare(this.absEdges, other.absEdges);
        }
    }

    // Lexicographic comparison of two lists
    // In order to prioritizing "smoothest" paths with the lowest individual step costs early.
    private int lexCompare(List<Integer> a, List<Integer> b) {
        int m = Math.min(a.size(), b.size());
        for (int i = 0; i < m; i++) {
            int cmp = a.get(i) - b.get(i);
            if (cmp != 0) return cmp;
        }
        return a.size() - b.size();
    }

	static void generateRandomWeights(int[][] weights) {
		Random random = new Random();
		for (int source = 0; source < weights.length; source++)
			for (int target = 0; target < weights.length; target++)
				if(random.nextInt(100) > p1*100) {
					weights[source][target] = random.nextInt(MIN_WEIGHT, MAX_WEIGHT); 
				}else { //Modified 2)
					weights[source][target] = -random.nextInt(MIN_WEIGHT, MAX_WEIGHT); 
				}
	}
	

	static void printMatrix(int[][] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(String.format("%10s", a[i][j]));
			System.out.println();
		}
		System.out.println();
	}
}
