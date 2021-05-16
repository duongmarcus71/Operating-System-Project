package others;

import java.util.Random;
import java.util.Vector;
import element.Process;

public class Query {
	
	private int posProcess;
	
	private Vector<Integer> request;
	
	public Query(int n) {
		posProcess = 0;
		request = new Vector<Integer>(n+1);
		
		for(int i = 0; i < n; ++ i) {
			request.add(0);
		}
	}
	
	public Query(int n, Vector<Process> p ) {
		
		Random rd = new Random();
		posProcess = rd.nextInt(n);
		Vector<Integer> max = p.get(posProcess).getMax();
		request = new Vector<Integer>(max.size());
		
		for(int i = 0; i < max.size(); ++ i) {
			request.add(rd.nextInt(max.get(i)));
		}
	}
	
	public int getPos() {
		return posProcess;
	}
	
	public Vector<Integer> getRequest() {
		return request;
	}
	
	public void print() {
		for(int i = 0;i < request.size(); ++ i) {
			System.out.println(request.get(i));
		}
	}
	
}
