package device;

import java.util.*;

public class Targets extends HashMap<Integer, Target> {
	public Targets(int n) {
		for (int i = 0; i < n; i++) {
			Target t = new Target();
			this.put(t.getId(), t);
		}
	}

	/**
	 * Return true if each target is covered by at least one sensor, return
	 * false otherwise.
	 */
	public static boolean hasSensorCover(Targets targets) {
		for (Target target : targets.values()) {
			if (target.getCoverdBy().size() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Randomly select requests that request this target, and at least one
	 * request will be selected.
	 */
	public static void generateRandomRequests(Targets targets, Requests requests) {
		final Random random = new Random();
		for (Target target : targets.values()) {
			int randomRequestNumber = random.nextInt(1) + 1;
			List<Request> values = new ArrayList<>(requests.values());
			for (int i = 0; i < randomRequestNumber; i++) {
				Request randomRequest = values.get(random.nextInt(values.size()));
				randomRequest.AddLocation(target);
				target.addRequestedBy(randomRequest);
				// remove this request so that the target will not
				// select it again
				values.remove(randomRequest);
			}
		}
	} // end method generateRandomRequests

	@Override
	public String toString() {
		return String.format("Targets: %s", super.toString());
	}
}
