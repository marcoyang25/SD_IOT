package social;

import java.util.*;
import org.jgraph.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
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

	// create a complete graph
	public static Graph<Integer, DefaultEdge> createIntGraph(List<Sensor> sensors) {
		Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		// Create the CompleteGraphGenerator object
		CompleteGraphGenerator<Integer, DefaultEdge> completeGenerator = new CompleteGraphGenerator<>(sensors.size());
		// Create the VertexFactory so the generator can create vertices
		VertexFactory<Integer> vFactory = new VertexFactory<Integer>() {
			private int index = 0;

			@Override
			public Integer createVertex() {
				return sensors.get(index++).getId();
			}
		};

		// Use the CompleteGraphGenerator object to make completeGraph a
		// complete graph with [sensors.size()] number of vertices
		completeGenerator.generateGraph(g, vFactory, null);
		return g;
	} // end method createIntGraph

}
