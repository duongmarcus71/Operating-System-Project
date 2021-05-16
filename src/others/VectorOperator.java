package others;

import java.util.Vector;

public class VectorOperator {
	
	public boolean cmp ( int n, Vector<Integer> x, Vector<Integer> y) {
		
		for(int i = 0; i < n; ++ i) {
			if( x.get(i) > y.get(i) ) return false;
		}
		return true;
	}
	
	public Vector<Integer> add(int n, Vector<Integer> x, Vector<Integer> y) {
		
		Vector<Integer> res = new Vector<Integer>();
		for(int i = 0; i < n; ++ i) {
			res.add(x.get(i) + y.get(i));
		}
		return res;
	}
	
	public Vector<Integer> sub(int n, Vector<Integer> x, Vector<Integer> y) {
		
		Vector<Integer> res = new Vector<Integer>();
		for(int i = 0; i < n; ++ i) {
			res.add(x.get(i) - y.get(i));
		}
		return res;
	}
	
	public Vector<Integer> reverse(Vector<Integer> x) {
		
		Vector<Integer> res = new Vector<Integer>();
		for(int i = 0; i < x.size(); ++ i) {
			res.add(-x.get(i));
		}
		return res;
	}

}
