package com.mnetlab.device;

import com.mnetlab.graph.Topo;
import com.mnetlab.graph.Type;
import com.mnetlab.graph.Vertex;
import com.mnetlab.graph.Vertices;
import com.mnetlab.social.Social;

import java.util.*;
import java.io.*;

import org.jgrapht.ext.*;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

// Real world data
public class Real implements Runnable {
	private final String name;
	private final File file;
	private final int CS_ID;
	private final int SENSORS_SIZE;
	private final int REQUESTS_SIZE;
	private final int BS_NUM;
	private final int MEC_NUM;

	public Real(String name, String file, int id, int sensors_size, int requests_size, int bs_num, int mec_num) {
		this.name = name;
		this.file = new File(file);
		this.CS_ID = id;
		this.SENSORS_SIZE = sensors_size;
		this.REQUESTS_SIZE = requests_size;
		this.BS_NUM = bs_num;
		this.MEC_NUM = mec_num;
	}

	public void run() {
		Random random = new Random();
		List<Double> mss = new ArrayList<>();
		List<Double> gmsc = new ArrayList<>();
		List<Double> esr = new ArrayList<>();
		List<Double> esrs = new ArrayList<>();
		List<Double> groups_size = new ArrayList<>();
		List<Double> sensors_selected_size = new ArrayList<>();
		List<Double> groups_selected_size = new ArrayList<>();

		Targets targets;
		Sensors sensors;
		Requests requests;
		// save sensor groups
		Set<Sensor> groups;
		Set<Sensor> sensorsSelected;

		Graph<Vertex, DefaultEdge> graph;
		Graph<Integer, DefaultEdge> socialGraph;
		Vertices vertices;
		Vertex cloudServer;
		DijkstraShortestPath<Vertex, DefaultEdge> d;
		DijkstraShortestPath<Integer, DefaultEdge> socialDijkstra;

		// running n times
		for (int n = 0; n < 1000; n++) {
			// generating topology
			graph = new SimpleGraph<>(DefaultEdge.class);
			try {
				// System.out.println("-- Importing graph from GML");
				GraphImporter<Vertex, DefaultEdge> importer = Topo.createImporter();
				importer.importGraph(graph, file);
			} catch (ImportException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			// System.out.println(graph);

			// add vertices to list
			vertices = new Vertices();
			for (Vertex vertex : graph.vertexSet()) {
				vertices.idToVertex.put(vertex.getId(), vertex);
				if (vertex.getId() == CS_ID) {
					// change it's type to cloud server
					vertex.setType(Type.CLOUDSERVER);
				} else {
					vertices.switches.add(vertex);
				}
			}

			// add base stations and connect to switches
			int nodes_num = vertices.idToVertex.keySet().size();
			for (int id = nodes_num; id < nodes_num + BS_NUM; id++) {
				Vertex baseStation = new Vertex(id, Type.BS);
				graph.addVertex(baseStation);
				vertices.idToVertex.put(id, baseStation);
				vertices.bs.add(baseStation);
				Vertex sw = vertices.switches.get(random.nextInt(vertices.switches.size()));
				graph.addEdge(baseStation, sw);
			}
			// System.out.println(graph);

			// compute Dijkstra shortest path of randomGraph
			d = new DijkstraShortestPath<>(graph);

			// ----------------------------------------Generating---------------------------------------------------

			targets = new Targets();
			sensors = new Sensors();
			requests = new Requests();

			// putting targets
			for (int id = 1; id <= 40; id++) {
				targets.put(id, new Target(id));
			}

			// putting sensors
			sensors.put(45, new Sensor(45));
			sensors.put(41, new Sensor(41));
			sensors.put(40, new Sensor(40));
			sensors.put(34, new Sensor(34));
			sensors.put(33, new Sensor(33));

			sensors.put(27, new Sensor(27));
			sensors.put(26, new Sensor(26));
			sensors.put(72, new Sensor(72));
			sensors.put(11, new Sensor(11));
			sensors.put(43, new Sensor(43));

			sensors.put(21, new Sensor(21));
			sensors.put(22, new Sensor(22));
			sensors.put(23, new Sensor(23));
			sensors.put(1, new Sensor(1));
			sensors.put(51, new Sensor(51));

			sensors.put(10, new Sensor(10));
			sensors.put(12, new Sensor(12));
			sensors.put(2, new Sensor(2));
			sensors.put(20, new Sensor(20));
			sensors.put(25, new Sensor(25));

			sensors.put(24, new Sensor(24));
			sensors.put(9, new Sensor(9));
			sensors.put(39, new Sensor(39));
			sensors.put(3, new Sensor(3));
			sensors.put(19, new Sensor(19));

			sensors.put(18, new Sensor(18));
			
			// putting requests
			for (int id = 1; id <= 20; id++) {
				requests.put(id, new Request(id));
			}

			// initialize sensors
			initialSensorTargets(sensors.get(45), Arrays.asList(21, 22), targets);
			initialSensorTargets(sensors.get(41), Arrays.asList(18, 22), targets);
			initialSensorTargets(sensors.get(40), Arrays.asList(3, 12), targets);
			initialSensorTargets(sensors.get(34), Arrays.asList(2, 3), targets);
			initialSensorTargets(sensors.get(33), Arrays.asList(13, 14, 28), targets);

			initialSensorTargets(sensors.get(27), Arrays.asList(28), targets);
			initialSensorTargets(sensors.get(26), Arrays.asList(26, 27), targets);
			initialSensorTargets(sensors.get(72), Arrays.asList(19, 20), targets);
			initialSensorTargets(sensors.get(11), Arrays.asList(19, 20), targets);
			initialSensorTargets(sensors.get(43), Arrays.asList(9, 18, 15), targets);

			initialSensorTargets(sensors.get(21), Arrays.asList(1, 4, 16, 36, 37, 39, 40), targets);
			initialSensorTargets(sensors.get(22), Arrays.asList(29, 30, 31, 36), targets);
			initialSensorTargets(sensors.get(23), Arrays.asList(29, 30, 31, 32, 35), targets);
			initialSensorTargets(sensors.get(1), Arrays.asList(1, 11, 15), targets);
			initialSensorTargets(sensors.get(51), Arrays.asList(29, 32), targets);

			initialSensorTargets(sensors.get(10), Arrays.asList(5, 17), targets);
			initialSensorTargets(sensors.get(12), Arrays.asList(5, 17, 24), targets);
			initialSensorTargets(sensors.get(2), Arrays.asList(8, 24, 25), targets);
			initialSensorTargets(sensors.get(20), Arrays.asList(6, 7, 10, 38, 39), targets);
			initialSensorTargets(sensors.get(25), Arrays.asList(10, 33), targets);

			initialSensorTargets(sensors.get(24), Arrays.asList(10, 33, 35), targets);
			initialSensorTargets(sensors.get(9), Arrays.asList(23), targets);
			initialSensorTargets(sensors.get(39), Arrays.asList(5, 23), targets);
			initialSensorTargets(sensors.get(3), Arrays.asList(8, 25), targets);
			initialSensorTargets(sensors.get(19), Arrays.asList(6, 7), targets);

			initialSensorTargets(sensors.get(18), Arrays.asList(33, 34), targets);
			
			// initialize requests
			initialRequestTargets(requests.get(1), Arrays.asList(19, 20, 21, 22), targets);
			initialRequestTargets(requests.get(2), Arrays.asList(5, 9, 17, 19, 20), targets);
			initialRequestTargets(requests.get(3), Arrays.asList(5, 17, 23, 24), targets);
			initialRequestTargets(requests.get(4), Arrays.asList(9, 18, 21, 22), targets);
			initialRequestTargets(requests.get(5), Arrays.asList(1, 4, 9, 11, 12, 15, 18, 40), targets);
			
			initialRequestTargets(requests.get(6), Arrays.asList(5, 8, 17, 23, 24, 25), targets);
			initialRequestTargets(requests.get(7), Arrays.asList(9, 15, 18, 19, 20, 21, 22), targets);
			initialRequestTargets(requests.get(8), Arrays.asList(6, 7, 10, 33, 34, 38), targets);
			initialRequestTargets(requests.get(9), Arrays.asList(3, 2, 12, 37, 1, 4, 40, 11, 16, 39, 36), targets);
			initialRequestTargets(requests.get(10), Arrays.asList(14, 13, 28, 26, 27, 30, 31, 29, 35, 32), targets);
			
			initialRequestTargets(requests.get(11), Arrays.asList(15, 1, 4, 37, 11, 40, 25, 38, 39, 16, 36), targets);
			initialRequestTargets(requests.get(12), Arrays.asList(3, 2, 14, 13, 37, 4), targets);
			initialRequestTargets(requests.get(13), Arrays.asList(22, 3, 18, 12, 9, 15, 1, 4, 40, 11), targets);
			initialRequestTargets(requests.get(14), Arrays.asList(17, 5, 24, 25, 23, 8), targets);
			initialRequestTargets(requests.get(15), Arrays.asList(39, 16, 36, 38, 6, 10, 33, 7, 34), targets);
			
			initialRequestTargets(requests.get(16), Arrays.asList(37, 16, 39, 36, 30, 31, 35, 32, 29), targets);
			initialRequestTargets(requests.get(17), Arrays.asList(4, 37, 16, 36, 39, 38, 6, 10), targets);
			initialRequestTargets(requests.get(18), Arrays.asList(21, 22, 18, 9, 12, 1, 4, 40, 11, 15), targets);
			initialRequestTargets(requests.get(19), Arrays.asList(24, 8, 25, 39, 38, 6), targets);
			initialRequestTargets(requests.get(20), Arrays.asList(3, 2, 14, 13, 28, 26, 27), targets);
			
			// for all sensors selected, select base station
			// and calculate energy cost for each sensor
			cloudServer = vertices.idToVertex.get(CS_ID);
			for (Sensor sensor : sensors.values()) {
				// randomly select a base station and
				// set it's index in the list in association
				sensor.setAssociation(random.nextInt(vertices.bs.size()));
				Vertex baseStation = vertices.bs.get(sensor.getAssociation());
				sensor.setCost(Topo.getEnergyConsumed(baseStation, cloudServer, sensor.getSize(), d)
						+ Sensor.SENSOR_TRANSMISSION_COST);
			}

			// ----------------------------------------MSS_SPS---------------------------------------------------
			if ((sensorsSelected = SetCover.setcover(targets, sensors)) != null) {

			} else {
				System.err.println("Set cover no solution");
				System.exit(-1);
			}
			mss.add(SetCover.computeTotalSelectedCost(sensorsSelected));

			// ----------------------------------------G-MSC---------------------------------------------------
			if ((sensorsSelected = SetCover.greedyMSC(targets, sensors)) != null) {

			} else {
				System.err.println("Set cover no solution");
				System.exit(-1);
			}
			gmsc.add(SetCover.computeTotalSelectedCost(sensorsSelected));

			// ----------------------------------------ESRS---------------------------------------------------

			// add MEC servers
			nodes_num = vertices.idToVertex.keySet().size();
			for (int id = nodes_num; id < nodes_num + MEC_NUM; id++) {
				Vertex mec = new Vertex(id, Type.MEC);
				graph.addVertex(mec);
				vertices.idToVertex.put(id, mec);
				vertices.mec.add(mec);

				// connect to base station
				for (int i = 0; i < 1; i++) {
					Vertex bs = vertices.bs.get(random.nextInt(vertices.bs.size()));
					graph.addEdge(mec, bs);
				}
				// connect to switches
				for (int i = 0; i < 2; i++) {
					Vertex sw = vertices.switches.get(random.nextInt(vertices.switches.size()));
					graph.addEdge(mec, sw);
				}
			}
			// System.out.println(graph);

			// compute Dijkstra shortest path of randomGraph
			d = new DijkstraShortestPath<>(graph);

			// for all sensors, calculate each energy cost
			for (Sensor sensor : sensors.values()) {
				Vertex baseStation = vertices.bs.get(sensor.getAssociation());
				cloudServer = vertices.idToVertex.get(CS_ID);
				List<Vertex> mecs = vertices.mec;

				// after getting the energy consumption of both aggregation and
				// non-aggregation paths, sets its energy cost
				double minEnergy = Double.min(Topo.getEnergyConsumed(baseStation, cloudServer, sensor.getSize(), d),
						Topo.getMinAggregationEnergyConsumed(baseStation, mecs, cloudServer, sensor.getSize(), d));
				sensor.setCost(minEnergy + Sensor.SENSOR_TRANSMISSION_COST);
			}
			
			// create a list of sensors
			// so that generator can use it to create a social graph
			socialGraph = Social.createRandomGraph(new ArrayList<>(sensors.values()));
			
			// deal with ID problem!!
			// so that group ID will be different
			for (int i = 0; i < 100; i++) {
				new Sensor();
			}

			// initialize groups
			groups = new HashSet<>();
			
			// for each request, do ESRS
			for (Request request : requests.values()) {
				Sensor ESRSselected;
				// this request do not have any location
				if (request.getLocations().size() == 0) {
					continue;
				}
				// ESRS
				if ((ESRSselected = SetCover.ESRS(request.getLocations(), sensors, socialGraph)) != null) {
					// replace sensor group's coverage with virtual targets
					Sensors.replaceWithVirtualTargets(ESRSselected, request);
					// add this sensor group to groups
					groups.add(ESRSselected);
					// System.out.println("Setcover:" + ESRSselected);
				} else {
					// ESRS no solution
					// System.out.println("ESRS no solution");
					continue;
				}
			} // end for
			
			// generate virtual targets
			Requests.generateVirtualTargets(requests);

			// replace each's coverage with virtual targets
			Sensors.replaceWithVirtualTargets(sensors, requests);

			// union all virtual locations of each request
			Set<Target> virtualTargets = Requests.getAllVirtualTargets(requests);
			
			// modify cost
			for(Sensor sensor : sensors.values()) {
				sensor.setCost(sensor.getCost() * 3);
			}
			if ((sensorsSelected = SetCover.greedy(virtualTargets, sensors, groups)) != null) {
				/*Set<Sensor> groupSelected = new HashSet<>(sensorsSelected);
				groupSelected.retainAll(groups);*/
				
				/*groups_size.add(Double.valueOf(groups.size()));
				sensors_selected_size.add(Double.valueOf(sensorsSelected.size()));*/
				//groups_selected_size.add(Double.valueOf(groupSelected.size()));
				
				/*System.out.println("groups.size() " + groups.size());
				System.out.println("sensorsSelected " + sensorsSelected.size());
				System.out.println("groupSelected.size(): " + groupSelected.size());*/
			} else {
				System.err.println("Greedy no solution");
				System.exit(-1);
			}
			// reset cost
			for(Sensor sensor : sensors.values()) {
				sensor.setCost(sensor.getCost() / 3);
			}
			esrs.add(SetCover.computeTotalSelectedCost(sensorsSelected));

		} // end for

		System.out.println(name + " MSS-SPS = " + sum(mss) / mss.size());
		System.out.println(name + " G-MSC = " + sum(gmsc) / gmsc.size());
		System.out.println(name + " ESRS = " + sum(esrs) / esrs.size());

	} // end run

	public double sum(List<Double> valuesToSum) {
		double sum = 0;
		for (Double value : valuesToSum) {
			sum += value;
		}
		return sum;
	}

	public void initialSensorTargets(Sensor sensor, List<Integer> ids, Targets targets) {
		// for each target's ID in the list
		for (int id : ids) {
			Target target = targets.get(id);
			sensor.addCoverage(target);
			target.addCoveredBy(sensor);
		}
	}
	
	public void initialRequestTargets(Request request, List<Integer> ids, Targets targets) {
		// for each target's ID in the list
		for (int id : ids) {
			Target target = targets.get(id);
			request.AddLocation(target);
			target.addRequestedBy(request);
		}
	}
}
