package element;

import java.util.Vector;

import others.VectorOperator;

import java.util.Random;

public class Coordinator {
	private final int MAX_PROCESS = 10;
	private final int MAX_RESOURCE = 10;
	private Vector<Resource> resource;
	private Vector<Process> process;
	private int nProcess;
	private int nResource;
	
	public Coordinator() {
		
		Random rd = new Random();
		nProcess = rd.nextInt(MAX_PROCESS) + 1;
		nResource = rd.nextInt(MAX_RESOURCE) + 1;
		resource = new Vector<Resource>(nResource);
		process = new Vector<Process>(nProcess);
		
		for(int i = 0; i < nResource; ++ i) {
			resource.add(new Resource(rd.nextInt(1000)+1, "Resource " + (i+1)));
		}
		
		for(int i = 0; i < nProcess; ++ i) {
			process.add(new Process(nResource, resource));
		}
	}
	
	boolean isSafe() {
		
		Vector<Integer> work = new Vector<Integer>(nResource);
		Vector<Boolean> finish = new Vector<Boolean>();
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
				}
			}
		}
		
		for(int i = 0; i < nProcess; ++ i) {
			if( finish.get(i) == false ) return false;
		}
		return true;
	}
	
	void printInfor() {
		System.out.println(nResource);
		for(int i = 0; i < nResource; ++ i) {
			System.out.print(resource.get(i).getAvailable() + " ");
		}
		System.out.println( "\n" + nProcess );
		for(int i = 0; i < nProcess; ++ i) {
			process.get(i).printInfor();
		}
		
	}
	
	public Vector<Resource> getResource() {
		return resource;
	}

	public Vector<Process> getProcess() {
		return process;
	}

	public int getnProcess() {
		return nProcess;
	}

	public int getnResource() {
		return nResource;
	}

	public static void main(String[] args) {
		
		Coordinator coordinator = new Coordinator();
		
		coordinator.printInfor();
		
		System.out.println(coordinator.isSafe());
	}
}
