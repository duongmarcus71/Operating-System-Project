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
		
		for(int i = 0; i < n; ++ i) {
			x.set(i, x.get(i) + y.get(i));
		}
		return x;
	}

}
