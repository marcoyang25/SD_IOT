package device;

import java.util.*;

public class Requests extends HashMap<Integer, Request> {
	public Requests(int n) {
		for (int i = 0; i < n; i++) {
			Request r = new Request();
			this.put(r.getId(), r);
		}
	}

	// generate virtual targets for each request
	public static void generateVirtualTargets(Requests requests) {
		for (Request request : requests.values()) {
			Set<VirtualTarget> locations = new HashSet<>();
			for (Target target : request.getLocations()) {
				VirtualTarget v = new VirtualTarget(target.getId(), request.getId());
				locations.add(v);
			}
			request.setVirtualLocations(locations);
		}
	}

	/** Union all virtual locations of each request. */
	public static Set<Target> getAllVirtualTargets(Requests requests) {
		Set<Target> VirtualTargetsToCover = new HashSet<>();
		for (Request request : requests.values()) {
			// VirtualTargetsToCover ∪ request.getLocations
			VirtualTargetsToCover.addAll(request.getLocations());
		}
		return VirtualTargetsToCover;
	}

	@Override
	public String toString() {
		return String.format("Requests: %s", super.toString());
	}

}
