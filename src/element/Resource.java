package element;

public class Resource {
	private String name;   
	private int available;   // số đơn vị tài nguyên sẵn có trong hệ thống
	
	public Resource(int n, String name) {
		this.name = name;
		available = n;
	}
	
	public int getAvailable() {
		
		return available;
	}
	
	public void setAvailable(int v) {
		
		available = v;
	}
	
	public String getName() {
		return name;
	}
	
}
