package com.mnetlab.sdiot.network;

import com.mnetlab.sdiot.device.Request;
import com.mnetlab.sdiot.device.Requests;
import com.mnetlab.sdiot.device.Sensor;
import com.mnetlab.sdiot.device.Sensors;
import com.mnetlab.sdiot.device.SetCover;
import com.mnetlab.sdiot.device.Target;
import com.mnetlab.sdiot.device.Targets;
import com.mnetlab.sdiot.graph.Topo;
import com.mnetlab.sdiot.graph.Type;
import com.mnetlab.sdiot.graph.Vertex;
import com.mnetlab.sdiot.graph.Vertices;
import com.mnetlab.sdiot.social.Social;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.ext.GraphImporter;
import org.jgrapht.ext.ImportException;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

// The distance between MEC server and the nearest BS != 1
public class Network3 implements Runnable{

	private final String name;
	private final File file;
	private final int CS_ID;
	private final int SENSORS_SIZE;
	private final int REQUESTS_SIZE;
	private final int BS_NUM;
	private final int MEC_NUM;
	private final int NORMALIZE;

	public Network3(String name, String file, int id, int sensors_size, int requests_size, int bs_num, int mec_num) {
		this.name = name;
		this.file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getFile());
		this.CS_ID = id;
		this.SENSORS_SIZE = sensors_size;
		this.REQUESTS_SIZE = requests_size;
		this.BS_NUM = bs_num;
		this.MEC_NUM = mec_num;
		this.NORMALIZE = 5; // default
	}
	
	public Network3(String name, String file, int id, int sensors_size, int requests_size, int bs_num, int mec_num, int normalize) {
		this.name = name;
		this.file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getFile());
		this.CS_ID = id;
		this.SENSORS_SIZE = sensors_size;
		this.REQUESTS_SIZE = requests_size;
		this.BS_NUM = bs_num;
		this.MEC_NUM = mec_num;
		this.NORMALIZE = normalize;
	}

	public void run() {
		Random random = new Random();
		List<Double> mss = new ArrayList<>();
		List<Double> gmsc = new ArrayList<>();
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
		for (int n = 0; n < 1; n++) {
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
			//System.out.println(graph);

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
			//System.out.println(graph);

			// compute Dijkstra shortest path of randomGraph
			d = new DijkstraShortestPath<>(graph);

			// ----------------------------------------Generating---------------------------------------------------
			// generating sensors and targets until hasSensorCover is satisfied
			do {
				// System.out.println("Generating...");
				targets = new Targets(1000);
				sensors = new Sensors(SENSORS_SIZE, targets);
			} while (!Targets.hasSensorCover(targets));

			// ----------------------------------------MSS_SPS---------------------------------------------------
			if ((sensorsSelected = SetCover.setcover(targets, sensors)) != null) {

			} else {
				System.err.println("Set cover no solution");
				System.exit(-1);
			}

			// for all sensors selected, calculate each energy cost
			cloudServer = vertices.idToVertex.get(CS_ID);
			for (Sensor sensor : sensorsSelected) {
				// randomly select a base station
				Vertex baseStation = vertices.bs.get(random.nextInt(vertices.bs.size()));
				sensor.setCost(Topo.getEnergyConsumed(baseStation, cloudServer, sensor.getSize(), d)
						+ Sensor.SENSOR_TRANSMISSION_COST);
			}
			
			for (Sensor sensor : sensorsSelected) {
				sensor.setCost(sensor.getCost() * NORMALIZE);
			}
			
			mss.add(SetCover.computeTotalSelectedCost(sensorsSelected));

			// ----------------------------------------G-MSC---------------------------------------------------
			if ((sensorsSelected = SetCover.greedyMSC(targets, sensors)) != null) {

			} else {
				System.err.println("Set cover no solution");
				System.exit(-1);
			}
			// for all sensors selected, calculate each energy cost
			cloudServer = vertices.idToVertex.get(CS_ID);
			for (Sensor sensor : sensorsSelected) {
				// randomly select a base station
				Vertex baseStation = vertices.bs.get(random.nextInt(vertices.bs.size()));
				sensor.setCost(Topo.getEnergyConsumed(baseStation, cloudServer, sensor.getSize(), d));
			}
			
			for (Sensor sensor : sensorsSelected) {
				sensor.setCost(sensor.getCost() * NORMALIZE);
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

				// connect to switches
				for (int i = 0; i < 2; i++) {
					Vertex sw = vertices.switches.get(random.nextInt(vertices.switches.size()));
					graph.addEdge(mec, sw);
					d = new DijkstraShortestPath<>(graph);
					if(checkNearestBS(mec, vertices.bs, d) != 2){
						graph.removeEdge(mec, sw);
						i--;
						continue;
					}
				}
			}
			//System.out.println(graph);

			// compute Dijkstra shortest path of randomGraph
			d = new DijkstraShortestPath<>(graph);

			// generating user requests
			requests = new Requests(REQUESTS_SIZE);
			// randomly select requests that request this target
			Requests.generateRandomTargets(requests, targets, 5, 10);

			// print requests
			/*for (Request request : requests.values()) {
				System.out.println(request);
				System.out.println(request.getLocations());
			}*/
			//System.out.println("sensors: " + sensors);
			//System.out.println(targets);

			// for all sensors, calculate each energy cost
			for (Sensor sensor : sensors.values()) {
				// randomly select a base station
				Vertex baseStation = vertices.bs.get(random.nextInt(vertices.bs.size()));
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
			//System.out.println("edge size: " + socialGraph.edgeSet().size());

			// compute Dijkstra shortest path of social graph
			// socialDijkstra = new DijkstraShortestPath<>(socialGraph);

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
			//System.out.println("virtualTargets: " + virtualTargets);


			/*System.out.println("-----------------------------------------sensors:");
			for(Sensor sensor : sensors.values()) {
				System.out.print(sensor);
				System.out.println("Cost:" + sensor.getCost());
				System.out.println(sensor.getCoverage());
			}

			System.out.println("------------------------------------------groups:");
			for(Sensor sensor : groups) {
				System.out.print(sensor);
				System.out.println("Cost:" + sensor.getCost());
				System.out.println(sensor.getCoverage());
			}*/

			// *******************************greedy*********************************
			// modify cost
			for(Sensor sensor : sensors.values()) {
				sensor.setCost(sensor.getCost() * NORMALIZE);
			}
			if ((sensorsSelected = SetCover.greedy(virtualTargets, sensors, groups)) != null) {
				/*Set<Sensor> groupSelected = new HashSet<>(sensorsSelected);
				groupSelected.retainAll(groups);

				groups_size.add(Double.valueOf(groups.size()));
				sensors_selected_size.add(Double.valueOf(sensorsSelected.size()));
				groups_selected_size.add(Double.valueOf(groupSelected.size()));*/

				/*System.out.println("groups.size() " + groups.size());
				System.out.println("sensorsSelected " + sensorsSelected.size());
				System.out.println("groupSelected.size(): " + groupSelected.size());*/
			} else {
				System.err.println("Greedy no solution");
				System.exit(-1);
			}
			// reset cost
			/*for(Sensor sensor : sensors.values()) {
				sensor.setCost(sensor.getCost() / 2);
			}*/
			esrs.add(SetCover.computeTotalSelectedCost(sensorsSelected));

		} // end for

		// print result
		/*System.out.println(name + " groups.size(): " + sum(groups_size) / groups_size.size());
		System.out.println(name + " sensorsSelected: " + sum(sensors_selected_size) / sensors_selected_size.size());
		System.out.println(name + " groupSelected.size(): " + sum(groups_selected_size) / groups_selected_size.size());*/

		System.out.println(name + " MSS-SPS = " + sum(mss) / mss.size());
		System.out.println(name + " G-MSC = " + sum(gmsc) / gmsc.size());
		System.out.println(name + " ESRS = " + sum(esrs) / esrs.size());

	} // end run()

	public double sum(List<Double> valuesToSum) {
		double sum = 0;
		for (Double value : valuesToSum) {
			sum += value;
		}
		return sum;
	}
	
	public double checkNearestBS(Vertex mec, List<Vertex> bs, DijkstraShortestPath<Vertex, DefaultEdge> d) {
		double min = Double.MAX_VALUE;
		double weight = 0;
		for(Vertex baseStation : bs) {
			weight = d.getPathWeight(mec, baseStation);
			if(weight <= min) {
				min = weight;
			}
		}
		return min;
	}

}
