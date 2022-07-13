package com.mnetlab.sdiot.device;

import java.util.*;

public class Request {
	private int id; // unique ID
	private static int count = 0;
	private Set<Target> locations; // user's request corresponds to some
									// locations

	public Request() {
		this.id = ++count;
		this.locations = new HashSet<>();
	}
	
	public Request(int id) {
		this.id = id;
		this.locations = new HashSet<>();
	}

	public int getId() {
		return id;
	}

	// add a target to a request's locations
	public void AddLocation(Target target) {
		locations.add(target);
	}

	public Set<Target> getLocations() {
		return Collections.unmodifiableSet(locations);
	}

	// replace targets in locations with virtual targets
	public void setVirtualLocations(Set<VirtualTarget> virtualTargets) {
		locations = new HashSet<>(virtualTargets);
	}

	// return a virtual target with the same target ID
	public VirtualTarget findVirtualTarget(Target target) {
		for (Target virtualTarget : locations) {
			if (virtualTarget.getId() == target.getId()) {
				return (VirtualTarget) virtualTarget;
			}
		}
		return null;
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
		if (!(obj instanceof Request))
			return false;
		Request other = (Request) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Request [id=%s, locations=%s]", id, locations.size());
	}

}
