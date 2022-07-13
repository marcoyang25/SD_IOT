package com.mnetlab.sdiot.device;

import java.util.*;

public class Sensors extends HashMap<Integer, Sensor> {
	public Sensors() {
	}

	public Sensors(int n) {
		for (int i = 0; i < n; i++) {
			Sensor s = new Sensor();
			this.put(s.getId(), s);
		}
	}

	public Sensors(int n, Targets targets) {
		for (int i = 0; i < n; i++) {
			Sensor s = new Sensor(targets);
			this.put(s.getId(), s);
		}
	}

	// replace each's coverage with virtual targets
	public static void replaceWithVirtualTargets(Sensors sensors, Requests requests) {
		for (Sensor sensor : sensors.values()) {
			Set<VirtualTarget> coverage = new HashSet<>();
			for (Target target : sensor.getCoverage()) {
				// for each request, find virtual targets which
				// correspond to this target
				for (Request request : requests.values()) {
					VirtualTarget v = null;
					if ((v = request.findVirtualTarget(target)) != null) {
						coverage.add(v);
					}
				}
			}
			sensor.setVirtualTargetsCoverage(coverage);
		}
	} // end method replaceWithVirtualTargets

	// replace a sensor group's coverage with virtual targets
	public static void replaceWithVirtualTargets(Sensor sensorGroup, Request request) {
		Set<VirtualTarget> coverage = new HashSet<>();
		for (Target target : sensorGroup.getCoverage()) {
			VirtualTarget v = new VirtualTarget(target.getId(), request.getId());
			coverage.add(v);
		}
		sensorGroup.setVirtualTargetsCoverage(coverage);
	} // end method replaceWithVirtualTargets

	@Override
	public String toString() {
		return String.format("Sensors: %s", super.toString());
	}
}
