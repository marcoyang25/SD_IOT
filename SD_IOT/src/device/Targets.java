package device;

import java.util.*;

public class Targets extends HashMap<Integer, Target> {
	public Targets(int n) {
		Target t;
		for (int i = 0; i < n; i++) {
			t = new Target();
			this.put(t.getId(), t);
		}
	}

	/**
	 * return true if each target is covered by at least one sensor, return
	 * false otherwise
	 */
	public static boolean hasSensorCover(Targets targets) {
		for (Target target : targets.values()) {
			if (target.getCoverdBy().size() == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Targets: %s", super.toString());
	}
}
