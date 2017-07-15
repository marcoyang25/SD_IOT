package device;

import java.util.*;

public class Sensors extends HashMap<Integer, Sensor> {
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

	@Override
	public String toString() {
		return String.format("Sensors: %s", super.toString());
	}
}
