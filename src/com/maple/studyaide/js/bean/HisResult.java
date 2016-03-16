package com.maple.studyaide.js.bean;

public class HisResult {

	public String curTime;
	public String useTime;
	public String hisResult;
	public String name;

	public HisResult(String curTime, String useTime, String hisResult, String name) {
		super();
		this.curTime = curTime;
		this.useTime = useTime;
		this.hisResult = hisResult;
		this.name = name;
	}

	@Override
	public String toString() {
		return "HisResult [curTime=" + curTime + ", useTime=" + useTime + ", hisResult=" + hisResult + ", name=" + name
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curTime == null) ? 0 : curTime.hashCode());
		result = prime * result + ((hisResult == null) ? 0 : hisResult.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((useTime == null) ? 0 : useTime.hashCode());
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
		HisResult other = (HisResult) obj;
		if (curTime == null) {
			if (other.curTime != null)
				return false;
		} else if (!curTime.equals(other.curTime))
			return false;
		if (hisResult == null) {
			if (other.hisResult != null)
				return false;
		} else if (!hisResult.equals(other.hisResult))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (useTime == null) {
			if (other.useTime != null)
				return false;
		} else if (!useTime.equals(other.useTime))
			return false;
		return true;
	}

	public String getCurTime() {
		return curTime;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getHisResult() {
		return hisResult;
	}

	public void setHisResult(String hisResult) {
		this.hisResult = hisResult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
