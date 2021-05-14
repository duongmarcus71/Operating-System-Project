package others;

import java.util.Random;
import java.util.Vector;

public class Query {
	
	private int posProcess;
	
	private Vector<Integer> request;
	
	public Query(int n,Vector<Integer> max) {
		
		Random rd = new Random();
		
		posProcess = rd.nextInt(n);
		request = new Vector<Integer>(max.size());
		for(int i = 0; i < max.size(); ++ i) {
			request.add(rd.nextInt(max.get(i)));
		}
	}
	
	int getPos() {
		return posProcess;
	}

}
