package social;

import java.util.Set;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import device.Sensor;

public class Social {
	public static final int ALPHA = 2;
	public static final double TRANSMISSION_COST = 1;

	private Social() {
	}

	public static double calculateDiscussionCost(int source, int sink, DijkstraShortestPath<Integer, DefaultEdge> d) {
		return d.getPathWeight(source, sink) * ALPHA * TRANSMISSION_COST;
	}

	// if source has a relationship with sensors selected in set cover
	public static boolean hasRelationship(int source, Set<Sensor> sensorsSelected,
			DijkstraShortestPath<Integer, DefaultEdge> d) {
		for (Sensor sensor : sensorsSelected) {
			if (d.getPathWeight(source, sensor.getId()) == 1) {
				return true;
			}
		}
		return false;
	}

}
