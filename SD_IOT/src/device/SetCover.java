package device;

import java.util.Set;
import java.util.HashSet;

public class SetCover {
	private SetCover() {
	}

	/**
	 * return all sensors selected by baseline setcover, return null if no
	 * solution existed
	 */
	public static Set<Sensor> setcover(Targets targets, Sensors sensors) {
		final Set<Target> X = new HashSet<>(targets.values());
		final Set<Sensor> F = new HashSet<>(sensors.values());
		Set<Target> U = new HashSet<>(X); // U = X
		Set<Target> S = new HashSet<>();
		Set<Set<Target>> e = new HashSet<>(); // e = empty set
		Set<Sensor> sensorsSelected = new HashSet<>();
		Set<Sensor> sensorsAvailable = new HashSet<>(F);

		/* while U != empty set */
		while (!U.isEmpty() && !sensorsAvailable.isEmpty()) {
			S = selectMax(sensorsAvailable, U, sensorsSelected);
			/* U = U - S */
			simpleRemove(U, S, e);
		}
		if (!U.isEmpty() && sensorsAvailable.isEmpty()) {
			// no solution
			return null;
		}

		if (!checkCorrectness(X, e)) {
			System.err.println("Sensor(s) Not Covered!");
			return null;
		}
		return sensorsSelected; // return all sensors selected
	} // end method setcover

	private static Set<Target> selectMax(Set<Sensor> sensorsAvailable, Set<Target> U, Set<Sensor> sensorsSelected) {
		Sensor maxSensor = new Sensor();

		/* select an S which is a member of F that maximizes |S ∩ U| */
		int max = -1;
		Set<Target> S = new HashSet<>();
		for (Sensor sensor : sensorsAvailable) {
			/* initialize S */
			Set<Target> intersection = new HashSet<>(sensor.getCoverage());
			intersection.retainAll(U); // S ∩ U
			if (intersection.size() >= max) {
				max = intersection.size();
				S = intersection;
				maxSensor = sensor;
			}
		}
		sensorsSelected.add(maxSensor);
		sensorsAvailable.remove(maxSensor);
		return S;
	} // end method selectMax

	private static Set<Target> selectMinCost(Set<Sensor> sensorsAvailable, Set<Target> U, Set<Sensor> sensorsSelected) {
		Sensor minSensor = new Sensor();

		/* select an S which is a member of F that minimize (cost / |S ∩ U|) */
		double min = Double.MAX_VALUE;
		Set<Target> S = new HashSet<>();
		for (Sensor sensor : sensorsAvailable) {
			/* initialize S */
			Set<Target> intersection = new HashSet<>(sensor.getCoverage());
			intersection.retainAll(U); // S ∩ U
			if (intersection.size() == 0) {
				// covering 0 target
				continue;
			}
			if ((sensor.getCost() / intersection.size()) <= min) {
				min = sensor.getCost() / intersection.size();
				S = intersection;
				minSensor = sensor;
			}
		}
		sensorsSelected.add(minSensor);
		sensorsAvailable.remove(minSensor);
		return S;
	} // end method selectMinCost

	private static void simpleRemove(Set<Target> U, Set<Target> S, Set<Set<Target>> e) {
		U.removeAll(S); // U = U - S
		e.add(S); // e = e ∪ {S}
	}

	private static boolean checkCorrectness(Set<Target> X, Set<Set<Target>> e) {
		Set<Target> covered = new HashSet<>();
		for (Set<Target> element : e) {
			covered.addAll(element); // covered = covered ∪ element
		}
		return X.equals(covered);
	} // end method checkCorrectness

	// total energy consumed by sensors selected
	public static double computeTotalSelectedCost(Set<Sensor> sensorsSelected) {
		double consumed = 0;
		for (Sensor sensor : sensorsSelected) {
			// Sensor
			if (sensor.getClass() == Sensor.class) {
				consumed += sensor.getCost();
			}
		}
		return consumed;
	} // end method computeTotalSelectedCost

	public static Set<Sensor> greedyMSC(Targets targets, Sensors sensors) {
		final Set<Target> X = new HashSet<>(targets.values());
		final Set<Sensor> F = new HashSet<>(sensors.values());
		Set<Target> U = new HashSet<>(X); // U = X
		Set<Target> S = new HashSet<>();
		Set<Set<Target>> e = new HashSet<>(); // e = empty set
		Set<Sensor> sensorsSelected = new HashSet<>();
		Set<Sensor> sensorsAvailable = new HashSet<>(F);

		/* while U != empty set */
		while (!U.isEmpty() && !sensorsAvailable.isEmpty()) {
			Target minCovered = selectMinCovered(U);
			S = selectMscMax(minCovered, sensorsAvailable, U, sensorsSelected);
			/* U = U - S */
			simpleRemove(U, S, e);
		}
		if (!U.isEmpty() && sensorsAvailable.isEmpty()) {
			// no solution
			return null;
		}

		if (!checkCorrectness(X, e)) {
			System.err.println("Sensor(s) Not Covered!");
			return null;
		}
		return sensorsSelected; // return all sensors selected
	} // end method greedyMSC

	private static Target selectMinCovered(Set<Target> U) {
		Target minTarget = new Target();
		int min = Integer.MAX_VALUE;
		for (Target target : U) {
			if (target.getCoverdBy().size() <= min) {
				min = target.getCoverdBy().size();
				minTarget = target;
			}
		}
		return minTarget;
	} // end method selectMinCovered

	private static Set<Target> selectMscMax(Target minCovered, Set<Sensor> sensorsAvailable, Set<Target> U,
			Set<Sensor> sensorsSelected) {
		Sensor maxSensor = new Sensor();

		int max = -1;
		Set<Target> S = new HashSet<>();
		for (Sensor sensor : minCovered.getCoverdBy()) {
			/* initialize S */
			Set<Target> intersection = new HashSet<>(sensor.getCoverage());
			intersection.retainAll(U); // S ∩ U
			if (intersection.size() >= max) {
				max = intersection.size();
				S = intersection;
				maxSensor = sensor;
			}
		}
		sensorsSelected.add(maxSensor);
		sensorsAvailable.remove(maxSensor);
		return S;
	} // end method selectMscMax

}
