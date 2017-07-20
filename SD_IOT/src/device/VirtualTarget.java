package device;

import java.util.*;

public class VirtualTarget extends Target{
	private int requestId;
	
	public VirtualTarget(int id, int requestId){
		super.setId(id);
		this.requestId = requestId;
	}

	public int getRequestId() {
		return requestId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + requestId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof VirtualTarget))
			return false;
		VirtualTarget other = (VirtualTarget) obj;
		if (requestId != other.requestId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("VirtualTarget [id=%s, request=%s]", super.getId(), requestId);
	}
	
}
