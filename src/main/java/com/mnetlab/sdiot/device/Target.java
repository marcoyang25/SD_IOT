package com.mnetlab.sdiot.device;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Target {
	private int id; // unique ID
	private static int count = 0;
	private double x; // x coordinate
	private double y; // y coordinate
	private double threshold; // probability that the target covered by sensors
								// must greater than this value
	private Set<Sensor> coveredBy;
	private Set<Request> requestedBy;

	private final Random radius = new Random();
	private final Random angle = new Random();

	public Target() {
		this.id = ++count;
		// initialize x and y coordinate
		double r = Sensor.MAX_RADIUS * radius.nextDouble();
		double a = 2 * Math.PI * angle.nextDouble();
		this.x = r * Math.cos(a);
		this.y = r * Math.sin(a);
		this.coveredBy = new HashSet<>();
		this.requestedBy = new HashSet<>();
	}
	
	public Target(int id) {
		this.id = id;
		this.coveredBy = new HashSet<>();
		this.requestedBy = new HashSet<>();
	}

	private synchronized void setId() {
		this.id = ++count;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public void addCoveredBy(Sensor sensor) {
		coveredBy.add(sensor);
	}

	public Set<Sensor> getCoverdBy() {
		return Collections.unmodifiableSet(coveredBy);
	}
	
	public void addRequestedBy(Request request) {
		requestedBy.add(request);
	}

	public Set<Request> getRequestedBy() {
		return Collections.unmodifiableSet(requestedBy);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Target))
			return false;
		Target other = (Target) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Target [id=%s, covered by:%s]", id, coveredBy.size());
	}
}
