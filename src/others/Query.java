package others;

import java.util.Random;
import java.util.Vector;
import element.Process;

public class Query {
	
	private int posProcess;   // tên tiến trình yêu cầu tài nguyên
	
	private Vector<Integer> request;   // vector yêu cầu tài nguyên của tiến trình
	
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
			int x =(int) (1 / (1 - Math.pow(0.7, 1/ (double)(need.size()))));
			int percentage = rd.nextInt(x);
			if(percentage != 0) {
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
