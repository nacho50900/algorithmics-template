package algstudent.s6;

import java.util.ArrayList;
import java.util.Random;

public class NullPath {
	static double p1=0.5; 
	static double p2=0.5; //50/50
	static int minWeight=10;
	static int maxWeight=99;
	
	static int minTolerance = -99;
	static int maxTolerance = 99;
	
	static int[][] weights; //weight matrix
	static int[][] objectiveCosts; // 0 paths matrix
	static ArrayList<Integer> path;
    static boolean[] visited;
	
	static int n;

	
	public static void main(String arg[]) {
		n = 4; //arg[0]

		weights = new int[n][n];
        visited = new boolean[n];
		path = new ArrayList<Integer>(n);
		
		generateRandomWeights(weights); //Random Generated Weights
		System.out.println("WEIGHT MATRIX IS:");
		printMatrix(weights);
		
		//System.out.println("OBJECTIVE COST MATRIX IS:");
		objectiveCosts = new int [n][n];
		visited[0] = true;
		path.add(0);
		findNullPath(0,n-1,0);
		//printMatrix(objectiveCosts);
	}
	
	/*
	private static void generateObjectiveCosts() {
		int n = weights.length;
		for (int origin = 0; origin < n; origin++) {
			for (int target = 0; target < n; target++) {
				findNullPath(origin,target,0);
			}
		}
	}*/
	
	//It works perfect :D
	//Looks factorial
	private static void findNullPath(int origin, int target, int currentCost) {
        if (path.size() == target) {
        	visited[target] = true;
            path.add(target);
            //currentCost = calculateCurrentCost(path);
            currentCost = currentCost + weights[path.get(target-1)][path.get(target)];
        	if (currentCost >= minTolerance && currentCost <= maxTolerance) {
                System.out.println("Null Path found: " + currentCost);
            }else {
				System.out.println("not found"+ currentCost);
			}
            visited[target] = false;
            path.removeLast();
            return;
        }

        for (int nextNode = 0; nextNode < target; nextNode++) {
            if (!visited[nextNode]) {
                visited[nextNode] = true;
                path.add(nextNode);
                
                //currentCost = calculateCurrentCost(path);
                int edge = weights[path.get(path.size()-2)][path.get(path.size()-1)];
                int newCost = currentCost + edge;
                findNullPath(nextNode, target, newCost);

                //dewalk the path
                visited[nextNode] = false;
                path.removeLast();
            }
        }
    }
	
	//Array de visited nodes y luego sumar costs de uno a otro al final
	static int calculateCurrentCost(ArrayList<Integer> path) {
		int cost = weights[path.get(0)][path.get(1)];
		for(int i=1; i<path.size()-1; i++) {
			if(path.get(i) == 0 || path.get(1) == 0) { //dewalked
				return cost;
			}
			cost += weights[path.get(i)][path.get(i+1)];
		}
		return cost;
	}

	static boolean Contains(int[] list, int node) {
		for(int i=0; i<list.length; i++) {
			if(list[i] == node) {
				return true;
			}
		}
		return false;
	}
	
	static void copyList(int[] copyFrom, int[] copyTo) {
		for(int i=0; i<copyFrom.length; i++) {
			copyTo[i] = copyFrom[i];
		}
	}
	
	static void generateRandomWeights(int[][] weights) {
		Random random = new Random();
		for (int source = 0; source < weights.length; source++)
			for (int target = 0; target < weights.length; target++)
				if(random.nextInt(100) > p1*100) {
					weights[source][target] = random.nextInt(minWeight, maxWeight); 
				}else { //Modified 2)
					weights[source][target] = -random.nextInt(minWeight, maxWeight); 
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
