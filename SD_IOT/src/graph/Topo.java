package graph;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.*;

public class Topo {
	private static final Random random = new Random();

	private Topo() {
	}

	public static SimpleGraph<Vertex, DefaultEdge> createRandomGraph(Vertices vertices, int switch_size, int bs_size) {
		int nodescreated = 1; // BSs and MECs created
		boolean fail = true;

		// Create the graph object
		SimpleGraph<Vertex, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		// Create the ScaleFreeGraphGenerator object
		ScaleFreeGraphGenerator<Vertex, DefaultEdge> ScaleFreeGenerator = new ScaleFreeGraphGenerator<>(switch_size);

		// Create the VertexFactory so the generator can create vertices
		VertexFactory<Vertex> vFactory = new VertexFactory<Vertex>() {
			private int id = 51;

			@Override
			public Vertex createVertex() {
				Vertex v = new Vertex(id, Type.SWITCH);
				vertices.idToVertex.put(id, v);
				vertices.switches.add(v);
				id++;
				return v;
			}
		};

		// using ScaleFreeGraphGenerator
		ScaleFreeGenerator.generateGraph(g, vFactory, null);

		// add a cloud server and connect to switches
		Vertex cloudServer = new Vertex(Vertex.CS_ID, Type.CLOUDSERVER);
		g.addVertex(cloudServer);
		vertices.idToVertex.put(Vertex.CS_ID, cloudServer);
		fail = true;
		do {
			// randomly connecting with a switch which's degree is 1
			Vertex sw = vertices.switches.get(random.nextInt(vertices.switches.size()));
			if (g.degreeOf(sw) == 1) {
				g.addEdge(cloudServer, sw);
				fail = false;
			}
		} while (fail);

		// add bs_size base stations and connect to switches
		for (int i = 0; i < bs_size; i++) {
			nodescreated++; // number of nodes created ++
			Vertex baseStation = new Vertex(nodescreated, Type.BS);
			g.addVertex(baseStation);
			vertices.idToVertex.put(nodescreated, baseStation);
			vertices.bs.add(baseStation);
			fail = true;
			do {
				// randomly connecting with a switch which's degree is 1
				Vertex sw = vertices.switches.get(random.nextInt(vertices.switches.size()));
				if (g.degreeOf(sw) >= 1) {
					g.addEdge(baseStation, sw);
					fail = false;
				}
			} while (fail);
		} // end for

		return g;

	} // end method createRandomGraph

	public static double getEnergyConsumed(Vertex source, Vertex sink, double size,
			DijkstraShortestPath<Vertex, DefaultEdge> d) {
		double consumed = 0;
		if (d == null)
			return -1;
		for (Vertex v : d.getPath(source, sink).getVertexList()) {
			switch (v.getType()) {
			case BS:
				consumed += Vertex.BS_ENERGY * size;
				break;
			case CLOUDSERVER:
				consumed += Vertex.CLOUDSEVER_ENERGY;
				break;
			case MEC:
				consumed += Vertex.MEC_FORWARDING_ENERGY * size;
				break;
			case SWITCH:
				consumed += Vertex.SWITCH_ENERGY * size;
				break;
			}
		}
		return consumed;
	} // end method getEnergyConsumed

	public static double getMinAggregationEnergyConsumed(Vertex bs, List<Vertex> mecs, Vertex cloud, double size,
			DijkstraShortestPath<Vertex, DefaultEdge> d) {
		double min = Double.MAX_VALUE;
		double energyConsumed;
		// calculate for each MEC
		for (Vertex mec : mecs) {
			energyConsumed = getAggregationEnergyConsumed(bs, mec, cloud, size, d);
			if (energyConsumed <= min) {
				min = energyConsumed;
			}
		}
		return min;
	} // end method getMinAggregationEnergyConsumed

	/*
	 * calculates the energy consumption of aggregation path from the serving BS
	 * to the cloud server D by computing the energy consumption of paths from
	 * BS to the MEC server v and from v to D
	 */
	private static double getAggregationEnergyConsumed(Vertex bs, Vertex mec, Vertex cloud, double size,
			DijkstraShortestPath<Vertex, DefaultEdge> d) {
		double consumed = 0;
		if (d == null)
			return -1;
		consumed += (Topo.getEnergyConsumed(bs, mec, size, d) - Vertex.MEC_FORWARDING_ENERGY + Vertex.MEC_ENERGY);
		// flow size becomes size * (1 - Vertex.RATIO) after traversing the MEC
		consumed += Topo.getEnergyConsumed(mec, cloud, size * (1 - Vertex.RATIO), d);
		return consumed;
	} // end method getAggregationEnergyConsumed

	public static GraphImporter<Vertex, DefaultEdge> createImporter() {
		// create vertex provider
		VertexProvider<Vertex> vertexProvider = new VertexProvider<Vertex>() {
			@Override
			public Vertex buildVertex(String id, Map<String, String> attributes) {
				Vertex v = new Vertex(Integer.valueOf(id), Type.SWITCH);
				return v;
			}
		};

		// create edge provider
		EdgeProvider<Vertex, DefaultEdge> edgeProvider = new EdgeProvider<Vertex, DefaultEdge>() {
			@Override
			public DefaultEdge buildEdge(Vertex from, Vertex to, String label, Map<String, String> attributes) {
				return new DefaultEdge();
			}
		};

		// create GML importer
		GmlImporter<Vertex, DefaultEdge> importer = new GmlImporter<>(vertexProvider, edgeProvider);

		return importer;
	} // end method GraphImporter

}
