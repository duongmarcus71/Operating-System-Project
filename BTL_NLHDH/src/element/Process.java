package element;

import java.util.Vector;
import java.util.Random;

public class Process {
	
	private Vector<Integer> max;
	private Vector<Integer> allocation;
	private Vector<Integer> need;
	
	public Process(int n, Vector<Resource> resource) {
		
		max = new Vector<Integer>(n);
		allocation = new Vector<Integer>(n);
		need = new Vector<Integer>(n);
		
		Random rd = new Random();
		for(int i = 0; i < n; ++ i) {
			max.add(rd.nextInt(resource.get(i).getAvailable())+1);
		}
		
		for(int i = 0; i < n; ++ i) {
			allocation.add(rd.nextInt(max.get(i))+1);
		}
		
		for(int i = 0; i < n; ++ i) {
			need.add(max.get(i) - allocation.get(i));
		}
	}
	
	public Vector<Integer> getMax() {
		
		return max;
	}
	
	public Vector<Integer> getAllocation() {
		
		return allocation;
	}
	
	public Vector<Integer> getNeed() {
		
		return need;
	}
	
/*	void printInfor() {
		for(int i = 0; i < max.size(); ++ i) {
			System.out.println(max.get(i) + " " + allocation.get(i) + " " + need.get(i));
		}
		System.out.print("\n");
		
	}*/

}
