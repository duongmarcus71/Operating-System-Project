package element;

import java.util.Vector;

import others.VectorOperator;

import java.util.Random;

public class Coordinator {
	
	private final int MAX_PROCESS = 10;
	
	private final int MAX_RESOURCE = 10;
	
	private Vector<Resource> resource;   // danh sách các tài nguyên của hệ thống
	
	private Vector<Process> process;   // danh sách các tiến trình của hệ thống
	
	private Vector<Boolean> trace;   // mảng truy vết các bước của thuật toán banker
	
	private int nProcess;   // số lượng tiến trình trong hệ thống
	
	private int nResource;   // số lượng tài nguyên trong hệ thống
	
	public Coordinator() {
		
		Random rd = new Random();
		trace = new Vector<Boolean>();
		nProcess = rd.nextInt(MAX_PROCESS) + 1;
		nResource = rd.nextInt(MAX_RESOURCE) + 1;
		resource = new Vector<Resource>(nResource);
		process = new Vector<Process>(nProcess);
		
		for(int i = 0; i < nResource; ++ i) {
			resource.add(new Resource(rd.nextInt(1000)+1, "Resource " + (i)));
		}
		
		for(int i = 0; i < nProcess; ++ i) {
			process.add(new Process(nResource, resource));
		}
	}
	
	public boolean isSafe() {
		
		Vector<Integer> work = new Vector<Integer>(nResource);   // vector cho biết mỗi TN còn bao nhiêu
		Vector<Boolean> finish = new Vector<Boolean>();   // vector cho biết TT có chắc chắn kết thúc không 
		trace.clear();
		for(int i = 0; i < nProcess; ++ i) {
			finish.add(false);
		}
		
		for(int i = 0; i < nResource; ++ i) {
			work.add(resource.get(i).getAvailable());
		}
		
		boolean flag = true;
		VectorOperator vO = new VectorOperator();
		while(flag != false) {	
			flag = false;
			for(int i = 0; i < nProcess; ++ i) {
				if(finish.get(i) != true && vO.cmp(nResource, process.get(i).getNeed(), work)) {
					finish.set(i, true);
					work = vO.add(nResource, work, process.get(i).getAllocation());
					flag = true;
					trace.add(true);
				} else trace.add(false);
			}
		}
		
		for(int i = 0; i < nProcess; ++ i) {
			if( finish.get(i) == false ) return false;
		}
		return true;
	}
	
	public void changeState(int n, Vector<Integer> r) {
		
		VectorOperator vO = new VectorOperator();
		process.get(n).setAllocation(vO.add(nResource, process.get(n).getAllocation(), r));
		process.get(n).setNeed(vO.sub(nResource, process.get(n).getMax(), process.get(n).getAllocation()));
		
		for(int i = 0; i < nResource; ++ i) {
			resource.get(i).setAvailable(resource.get(i).getAvailable() - r.get(i));
		}
	}
	
	public int getNProcess() {
		return nProcess;
	}
	
	public int getNResource() {
		return nResource;
	}
	
	public Vector<Resource> getResource() {
		return resource;
	}

	public Vector<Process> getProcess() {
		return process;
	}
	
	public Vector<Boolean> getTrace() {
		return trace;
	}
}
