package element;

import java.util.Vector;
import java.util.Random;

public class Process {
	
	private Vector<Integer> max;   // số lượng lớn nhất mỗi kiểu TN của từng tiến trình
	private Vector<Integer> allocation;   // số lượng mỗi kiểu TN đã cấp cho tiến trình
	private Vector<Integer> need;   // số lượng mỗi kiểu TN còn cần đến của từng tiến trình
	
	public Process(int n, Vector<Resource> resource) {
		
		max = new Vector<Integer>(n);
		allocation = new Vector<Integer>(n);
		need = new Vector<Integer>(n);
		
		Random rd = new Random();
		for(int i = 0; i < n; ++ i) {
			int Max = resource.get(i).getAvailable() + 1;
			int min = (int) (Max * 0.8) ;
			max.add(rd.nextInt(Max - min + 1 ) + min);
		}
		
		for(int i = 0; i < n; ++ i) {
			allocation.add(rd.nextInt(max.get(i)));
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
	
	public void setAllocation(Vector<Integer> c) {
		allocation.clear();
		allocation.addAll(c);
	}
	
	public void setNeed(Vector<Integer> c) {
		need.clear();
		need.addAll(c);
	}
	
	public void setMax(Vector<Integer> c) {
		max.clear();
		max.addAll(c);
	}

}
