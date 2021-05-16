package others;

import java.util.Random;
import java.util.Vector;
import element.Process;

public class Query {
	
	private int posProcess;
	
	private Vector<Integer> request;
	
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
	
}
