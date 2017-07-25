package device;

import java.util.Set;
import java.util.HashSet;
import social.Social;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

public class SetCover {
	private SetCover() {
	}

	/**
	 * Return all sensors selected by baseline set cover, return null if no
	 * solution existed.
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

	public static Sensor ESRS(Set<Target> targets, Sensors sensors, Graph<Integer, DefaultEdge> graph) {
		final Set<Target> X = new HashSet<>(targets);
		final Set<Sensor> F = new HashSet<>(sensors.values());
		Set<Target> U = new HashSet<>(X); // U = X
		Set<Target> S = new HashSet<>();
		Set<Set<Target>> e = new HashSet<>(); // e = empty set
		Set<Sensor> sensorsSelected = new HashSet<>();
		Set<Sensor> sensorsAvailable = new HashSet<>(F);

		// selecting sensor with minimum transmission cost
		S = selectMinTransmissionCost(sensorsAvailable, U, sensorsSelected);
		/* U = U - S */
		simpleRemove(U, S, e);

		// the host of the sensor group
		Sensor host = null;
		// there's only host in sensorsSeleced now
		for (Sensor sensor : sensorsSelected) {
			host = sensor;
		}

		/* while U != empty set */
		while (!U.isEmpty() && !sensorsAvailable.isEmpty()) {
			if ((S = selectMaxWithRelation(sensorsAvailable, U, sensorsSelected, graph)) != null) {
				/* U = U - S */
				simpleRemove(U, S, e);
			} else {
				// no solution
				//System.out.println("ESRS no solution");
				return null;
			}
		}

		// setting sensor group's cost and coverage
		Sensor sensorGroup = new Sensor();
		sensorGroup.setCost(calculateGroupCost(host, sensorsSelected));
		sensorGroup.setCoverage(targets);

		if (!checkCorrectness(X, e)) {
			System.err.println("Sensor(s) Not Covered!");
			System.exit(-1);
		}
		return sensorGroup;
	} // end method ESRS

	private static Set<Target> selectMinTransmissionCost(Set<Sensor> sensorsAvailable, Set<Target> U,
			Set<Sensor> sensorsSelected) {
		Sensor minSensor = null;

		// select an S which is a member of F such that |S ∩ U| > 0
		// and minimize S' transmission cost to cloud server
		double minEnergy = Double.MAX_VALUE;
		Set<Target> S = new HashSet<>();
		for (Sensor sensor : sensorsAvailable) {
			/* initialize S */
			Set<Target> intersection = new HashSet<>(sensor.getCoverage());
			intersection.retainAll(U); // S ∩ U
			if (sensor.getCost() <= minEnergy && intersection.size() > 0) {
				minEnergy = sensor.getCost();
				S = intersection;
				minSensor = sensor;
			}
		}
		sensorsSelected.add(minSensor);
		sensorsAvailable.remove(minSensor);
		return S;
	} // end method selectMinTransmissionCost

	private static Set<Target> selectMaxWithRelation(Set<Sensor> sensorsAvailable, Set<Target> U,
			Set<Sensor> sensorsSelected, Graph<Integer, DefaultEdge> graph) {
		Sensor maxSensor = null;

		// select an S which is a member of F that maximize |S ∩ U|
		// and has relationship with host
		double max = 1; // at least cover one target
		Set<Target> S = new HashSet<>();
		for (Sensor sensor : sensorsAvailable) {
			/* initialize S */
			Set<Target> intersection = new HashSet<>(sensor.getCoverage());
			intersection.retainAll(U); // S ∩ U
			if (intersection.size() >= max && Social.hasRelationship(sensor.getId(), sensorsSelected, graph)) {
				max = intersection.size();
				S = intersection;
				maxSensor = sensor;
			}
		}
		// no sensor selected in this round
		if (maxSensor == null) {
			return null;
		} else {
			sensorsSelected.add(maxSensor);
			sensorsAvailable.remove(maxSensor);
			return S;
		}
	} // end method selectMaxWithRelation

	// calculate discussion cost for each sensor
	private static void calculateSensorsDiscussionCost(Sensor host, Set<Sensor> sensorsAvailable,
			DijkstraShortestPath<Integer, DefaultEdge> d) {
		for (Sensor sensor : sensorsAvailable) {
			sensor.setDiscussionCost(Social.calculateDiscussionCost(sensor.getId(), host.getId(), d));
		}
	} // end method calculateSensorsDiscussionCost

	private static double calculateGroupCost(Sensor host, Set<Sensor> sensorsSelected) {
		// host.getCost() = host's cost to cloud server
		return sensorsSelected.size() * Social.GAMMA + host.getCost();
	} // end method calculateGroupCost

	public static Set<Sensor> greedy(Set<Target> targets, Sensors sensors, Set<Sensor> groups) {
		final Set<Target> X = new HashSet<>(targets);
		final Set<Sensor> F = new HashSet<>(sensors.values());
		Set<Sensor> G = new HashSet<>(groups);
		Set<Target> U = new HashSet<>(X); // U = X
		Set<Target> S = new HashSet<>();
		Set<Set<Target>> e = new HashSet<>(); // e = empty set
		Set<Sensor> sensorsSelected = new HashSet<>();
		G.addAll(F); // G = F ∪ G
		// sensors and groups
		Set<Sensor> sensorsAvailable = new HashSet<>(G);

		/* while U != empty set */
		while (!U.isEmpty() && !sensorsAvailable.isEmpty()) {
			S = selectMinCost(sensorsAvailable, U, sensorsSelected);
			/* U = U - S */
			simpleRemove(U, S, e);
		}
		if (!U.isEmpty() && sensorsAvailable.isEmpty()) {
			// no solution
			return null;
		}
		if (!checkCorrectness(X, e)) {
			System.err.println("Sensor(s) Not Covered!");
			System.exit(-1);
		}
		return sensorsSelected; // return all sensors selected
	} // end method greedy
	
	public static Set<Sensor> ESR(Targets targets, Sensors sensors) {
		final Set<Target> X = new HashSet<>(targets.values());
		final Set<Sensor> F = new HashSet<>(sensors.values());
		Set<Target> U = new HashSet<>(X); // U = X
		Set<Target> S = new HashSet<>();
		Set<Set<Target>> e = new HashSet<>(); // e = empty set
		Set<Sensor> sensorsSelected = new HashSet<>();
		Set<Sensor> sensorsAvailable = new HashSet<>(F);

		/* while U != empty set */
		while (!U.isEmpty() && !sensorsAvailable.isEmpty()) {
			S = selectMinCost(sensorsAvailable, U, sensorsSelected);
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
	} // end method ESR

	private static Set<Target> selectMinCost(Set<Sensor> sensorsAvailable, Set<Target> U, Set<Sensor> sensorsSelected) {
		Sensor minSensor = null;

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
	} // end method simpleRemove

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
