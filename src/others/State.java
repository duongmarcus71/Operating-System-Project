package others;

public class State {
	private String name;
	
	private boolean status;
	
	State(String _name, boolean _status) {
		name = _name;
		status = _status;
	}
	
	void setStatus(boolean _status) {
		status = _status;
	}
}
