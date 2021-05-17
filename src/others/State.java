package others;

public class State {
	private String name;
	
	private String status;
	
	public State(String _name, String _status) {
		name = _name;
		status = _status;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setName(String _name) {
		name = _name;
	}
	
	public void setStatus(String _status) {
		status = _status;
	}
	
}
