package others;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import element.*;
import element.Process;
public class Query {
	
	private int posProcess;
	private Vector<Integer> request;
	private Map<String, Integer> queryMap;
	
	public Query(int n, Coordinator c) {
		
		Random rd = new Random();
		
		posProcess = rd.nextInt(n);
		Process p = c.getProcess().elementAt(posProcess);
		Vector<Integer> max = p.getMax();
		
		request = new Vector<Integer>(max.size());
		queryMap = new HashMap<>(max.size());
		
		for(int i = 0; i < max.size(); ++ i) {
			int random = rd.nextInt(max.get(i));
			request.add(random);
			queryMap.put(c.getResource().elementAt(i).getName(), random);
		}
		
		
	}
	
	int getPos() {
		return posProcess;
	}

	public Map<String, Integer> getQueryMap(){
		return queryMap;
	}
	
	public void printQuery() {
		for(String name : queryMap.keySet()) {
			System.out.println(name + " - " + queryMap.get(name));
		}
	}
}
