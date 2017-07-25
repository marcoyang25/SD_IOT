package device;

import java.util.*;

public class Targets extends HashMap<Integer, Target> {
	public Targets() {
	}

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
	 * Randomly select requests that request this target.
	 */
	public static void generateRandomRequests(Targets targets, Requests requests, int start, int bound) {
		final Random random = new Random();
		for (Target target : targets.values()) {
			int randomRequestsNumber = random.nextInt(bound) + start;
			List<Request> values = new ArrayList<>(requests.values());
			Collections.shuffle(values);
			for (int i = 0; i < randomRequestsNumber; i++) {
				Request randomRequest = values.get(random.nextInt(values.size()));
				randomRequest.AddLocation(target);
				target.addRequestedBy(randomRequest);
			}
		}
	} // end method generateRandomRequests

	@Override
	public String toString() {
		return String.format("Targets: %s", super.toString());
	}
}
