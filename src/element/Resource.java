package element;

public class Resource {
	private String name;
	private int available;
	
	public Resource(int n, String name) {
		this.name = name;
		available = n;
	}
	
	public int getAvailable() {
		
		return available;
	}
	
	public String getName() {
		return this.name;
	}
}
