package device;

import java.util.HashMap;

public class Requests extends HashMap<Integer, Request> {
	public Requests(int n) {
		for (int i = 0; i < n; i++) {
			Request r = new Request();
			this.put(r.getId(), r);
		}
	}

	@Override
	public String toString() {
		return String.format("Requests: %s", super.toString());
	}

}
