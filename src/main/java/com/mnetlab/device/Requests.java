package com.mnetlab.device;

import java.util.*;

public class Requests extends HashMap<Integer, Request> {
	public Requests(){
	}
	
	public Requests(int n) {
		for (int i = 0; i < n; i++) {
			Request r = new Request();
			this.put(r.getId(), r);
		}
	}

	public static void generateRandomTargets(Requests requests, Targets targets, int start, int bound) {
		final Random random = new Random();
		for (Request request : requests.values()) {
			int randomTargetsNumber = random.nextInt(bound) + start;
			List<Target> values = new ArrayList<>(targets.values());
			for (int i = 0; i < randomTargetsNumber; i++) {
				// shuffle the list
				Collections.shuffle(values);
				Target randomTarget = values.get(i);
				
				if(randomTarget.getRequestedBy().size() < 5){
					request.AddLocation(randomTarget);
					randomTarget.addRequestedBy(request);
					values.remove(randomTarget);
				}
				else{
					--i;
					continue;
				}
			}
		}
	}
	
	// generate virtual targets for each request
	public static void generateVirtualTargets(Requests requests) {
		for (Request request : requests.values()) {
			Set<VirtualTarget> locations = new HashSet<>();
			for (Target target : request.getLocations()) {
				VirtualTarget v = new VirtualTarget(target.getId(), request.getId());
				locations.add(v);
			}
			request.setVirtualLocations(locations);
		}
	}

	/** Union all virtual locations of each request. */
	public static Set<Target> getAllVirtualTargets(Requests requests) {
		Set<Target> VirtualTargetsToCover = new HashSet<>();
		for (Request request : requests.values()) {
			// VirtualTargetsToCover ∪ request.getLocations
			VirtualTargetsToCover.addAll(request.getLocations());
		}
		return VirtualTargetsToCover;
	}

	@Override
	public String toString() {
		return String.format("Requests: %s", super.toString());
	}

}
