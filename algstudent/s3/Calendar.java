package algstudent.s3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Calendar {
	
	final static String file = "C:\\Users\\vicen\\Dropbox\\Escritorio\\Nacho-UNI\\ALG\\algorithmics-template\\alg\\src\\algstudent\\s3\\participants.txt";
	static int numberOfParticipants;
	static int totalDays;
	static ArrayList<String> participants = new ArrayList<String>();
	static String [][] calendar;
	
    public static void main(String[] args) {
    	
    	int counter = 0;
    	try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    		String line;
    		
    		while ((line = br.readLine()) != null) {
    			if (counter == 0) {
    				numberOfParticipants = Integer.valueOf(line);
    				totalDays = numberOfParticipants-1;
    			}else {
    				participants.add(line);
    			}
    			System.out.println(line);
    			counter++;
    		}
    	} catch (IOException e) {
        System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
    	}
    	calendar = new String [numberOfParticipants][totalDays]; // 8 -> 4*2^(1*2);
    	double valueToIterate = 2^(1*2); //*2 in order to adjust complexity
    	long t1=System.currentTimeMillis();
    	for(int i=0; i<valueToIterate; i++) {
    		makeCalendar(0, totalDays, 0); //Executed for 4 people
    		resetCalendar();
    	}
    	long t2=System.currentTimeMillis();
    	
    	System.out.println();
    	System.out.println("CALENDAR:");
    	printCalendar();
    	System.out.println("ELAPSED TIME:" + (t2-t1));
    	
    }

	static void makeCalendar(int first, int last, int day) {
    	if (day > totalDays) { return;}
    	if (last == first) { return;}
    	printCalendar();
    	
    	if(last - first == 1) { // Update column
    		for(int i=0; i<=calendar.length/2; i+=1) { //Executed half of the nº of players times.
    			if (i+day+1 < calendar.length) {
    				if ((calendar [i][day] == null) && (calendar [i+day+1][day] == null)) {
    					calendar [i][day] = participants.get(i+day+1);
    					calendar [i+day+1][day] = participants.get(i);
    				}
    			}else {
    				if ((calendar [i][day] == null) && (calendar [i+1][day] == null)) {
        				calendar [i][day] = participants.get(calendar.length-i-1);
    					calendar [calendar.length-i-1][day] = participants.get(i);
    				}
    			}
    		}
    	}else {
    		int mid = first + (last - first) / 2;
    		makeCalendar(first, mid, day);
    		makeCalendar(mid, last, day+1);
    	}
    }

    private static void printCalendar() {
    	System.out.println("DAYS       1      2     3");
    	for(int i=0; i<calendar.length; i++) {
    		System.out.print(participants.get(i) + " | ");
        	for(int j=0; j<calendar[0].length; j++) {
        		System.out.print(calendar[i][j] + " ");
        	}
        	System.out.println();
    	}
    }
    
    private static void resetCalendar() {
    	for(int i=0; i<calendar.length; i++) {
        	for(int j=0; j<calendar[0].length; j++) {
        		calendar[i][j] = "";
        	}
    	}
	}

}