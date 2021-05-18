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
		Vector<Integer> need = p.get(posProcess).getNeed();
		Vector<Integer> max = p.get(posProcess).getMax();
		request = new Vector<Integer>(need.size());

		for(int i = 0; i < need.size(); ++ i) {
			double percentage = 1.0 / (rd.nextInt(4)+1);
			if(percentage < 1) {
				request.add(rd.nextInt(need.get(i) + 1));
			}
			else {
				request.add(rd.nextInt(max.get(i)));
			}
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
