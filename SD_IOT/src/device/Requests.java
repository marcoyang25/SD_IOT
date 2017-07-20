package device;

import java.util.*;

public class Requests extends HashMap<Integer, Request> {
	public Requests(int n) {
		for (int i = 0; i < n; i++) {
			Request r = new Request();
			this.put(r.getId(), r);
		}
	}

	public static void generateVirtualTargets(Requests requests) {
		for (Request request : requests.values()) {
			Set<VirtualTarget> virtualTargets = new HashSet<>();
			for (Target target : request.getLocations()) {
				VirtualTarget v = new VirtualTarget(target.getId(), request.getId());
				virtualTargets.add(v);
			}
			request.setLocations(virtualTargets);
		}
	}

	@Override
	public String toString() {
		return String.format("Requests: %s", super.toString());
	}

}
