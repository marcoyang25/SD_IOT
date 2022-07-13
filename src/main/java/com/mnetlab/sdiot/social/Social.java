package com.mnetlab.sdiot.social;

import com.mnetlab.sdiot.device.Sensor;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Social {
	public static final double GAMMA = 0.02;

	private Social() {
	}

	public static double calculateDiscussionCost(int source, int sink, DijkstraShortestPath<Integer, DefaultEdge> d) {
		return d.getPathWeight(source, sink) * GAMMA * Sensor.SENSOR_TRANSMISSION_COST;
	}

	// if source has a relationship with sensors selected in set cover
	public static boolean hasRelationship(int source, Set<Sensor> sensorsSelected,
			Graph<Integer, DefaultEdge> graph) {
		for (Sensor sensor : sensorsSelected) {
			if (graph.containsEdge(source, sensor.getId())) {
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

	public static Graph<Integer, DefaultEdge> createRandomGraph(List<Sensor> sensors) {
		Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		final Random random = new Random();
		// create vertices
		for (Sensor sensor : sensors) {
			g.addVertex(sensor.getId());
		}
		// for each vertex, randomly add some edges
		for (Integer v0 : g.vertexSet()) {
			List<Integer> vertices = new ArrayList<>(g.vertexSet());
			// remove itself
			vertices.remove(v0);
			// shuffle the list
			Collections.shuffle(vertices);
			// select some vertices from list
			for (int i = 0, degree = random.nextInt(20) + 20; i < degree; i++) {
				int v1 = vertices.get(i);
				g.addEdge(v0, v1);
			}
		}
		return g;
	} // end method createRandomGraph

	public static Graph<Integer, DefaultEdge> createDistanceGraph(List<Sensor> sensors) {
		Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		// create vertices
		for (Sensor sensor : sensors) {
			g.addVertex(sensor.getId());
		}
		// for each vertex, add some edges to vertices based on distance
		for (Sensor s0 : sensors) {
			for (Sensor s1 : sensors) {
				if (!s0.equals(s1)
						&& calculateDistance(s0.getX(), s0.getY(), s1.getX(), s1.getY()) < Sensor.MAX_DIS_BETWEEN) {
					g.addEdge(s0.getId(), s1.getId());
				}
			}
		}
		return g;
	} // end method createDistanceGraph

	public static double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
	} // end method calculateDistance

}
