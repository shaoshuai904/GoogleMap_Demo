package com.maple.studyaide.js.bean;

public class Digital {
	
	public int id;

	public Digital(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Digital [id=" + id + "]";
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
		if (getClass() != obj.getClass())
			return false;
		Digital other = (Digital) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
