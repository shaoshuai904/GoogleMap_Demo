package com.maple.studyaide.js.bean;

public class CauseInfo {
	public String timu_title;
	
	public String timu_one;
	public String timu_tow;
	public String timu_three;
	public String timu_four;
	
	public String daan_one;
	public String daan_tow;
	public String daan_three;
	public String daan_four;
	
	public String detail;
	public String types;
	
	public int reply;

	public String getTimu_title() {
		return timu_title;
	}

	public void setTimu_title(String timu_title) {
		this.timu_title = timu_title;
	}

	public String getTimu_one() {
		return timu_one;
	}

	public void setTimu_one(String timu_one) {
		this.timu_one = timu_one;
	}

	public String getTimu_tow() {
		return timu_tow;
	}

	public void setTimu_tow(String timu_tow) {
		this.timu_tow = timu_tow;
	}

	public String getTimu_three() {
		return timu_three;
	}

	public void setTimu_three(String timu_three) {
		this.timu_three = timu_three;
	}

	public String getTimu_four() {
		return timu_four;
	}

	public void setTimu_four(String timu_four) {
		this.timu_four = timu_four;
	}

	public String getDaan_one() {
		return daan_one;
	}

	public void setDaan_one(String daan_one) {
		this.daan_one = daan_one;
	}

	public String getDaan_tow() {
		return daan_tow;
	}

	public void setDaan_tow(String daan_tow) {
		this.daan_tow = daan_tow;
	}

	public String getDaan_three() {
		return daan_three;
	}

	public void setDaan_three(String daan_three) {
		this.daan_three = daan_three;
	}

	public String getDaan_four() {
		return daan_four;
	}

	public void setDaan_four(String daan_four) {
		this.daan_four = daan_four;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public CauseInfo(String timu_title, String timu_one, String timu_tow, String timu_three, String timu_four,
			String daan_one, String daan_tow, String daan_three, String daan_four, String detail, String types,
			int reply) {
		super();
		this.timu_title = timu_title;
		this.timu_one = timu_one;
		this.timu_tow = timu_tow;
		this.timu_three = timu_three;
		this.timu_four = timu_four;
		this.daan_one = daan_one;
		this.daan_tow = daan_tow;
		this.daan_three = daan_three;
		this.daan_four = daan_four;
		this.detail = detail;
		this.types = types;
		this.reply = reply;
	}

	@Override
	public String toString() {
		return "CauseInfo [timu_title=" + timu_title + ", timu_one=" + timu_one + ", timu_tow=" + timu_tow
				+ ", timu_three=" + timu_three + ", timu_four=" + timu_four + ", daan_one=" + daan_one + ", daan_tow="
				+ daan_tow + ", daan_three=" + daan_three + ", daan_four=" + daan_four + ", detail=" + detail
				+ ", types=" + types + ", reply=" + reply + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daan_four == null) ? 0 : daan_four.hashCode());
		result = prime * result + ((daan_one == null) ? 0 : daan_one.hashCode());
		result = prime * result + ((daan_three == null) ? 0 : daan_three.hashCode());
		result = prime * result + ((daan_tow == null) ? 0 : daan_tow.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + reply;
		result = prime * result + ((timu_four == null) ? 0 : timu_four.hashCode());
		result = prime * result + ((timu_one == null) ? 0 : timu_one.hashCode());
		result = prime * result + ((timu_three == null) ? 0 : timu_three.hashCode());
		result = prime * result + ((timu_title == null) ? 0 : timu_title.hashCode());
		result = prime * result + ((timu_tow == null) ? 0 : timu_tow.hashCode());
		result = prime * result + ((types == null) ? 0 : types.hashCode());
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
		CauseInfo other = (CauseInfo) obj;
		if (daan_four == null) {
			if (other.daan_four != null)
				return false;
		} else if (!daan_four.equals(other.daan_four))
			return false;
		if (daan_one == null) {
			if (other.daan_one != null)
				return false;
		} else if (!daan_one.equals(other.daan_one))
			return false;
		if (daan_three == null) {
			if (other.daan_three != null)
				return false;
		} else if (!daan_three.equals(other.daan_three))
			return false;
		if (daan_tow == null) {
			if (other.daan_tow != null)
				return false;
		} else if (!daan_tow.equals(other.daan_tow))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (reply != other.reply)
			return false;
		if (timu_four == null) {
			if (other.timu_four != null)
				return false;
		} else if (!timu_four.equals(other.timu_four))
			return false;
		if (timu_one == null) {
			if (other.timu_one != null)
				return false;
		} else if (!timu_one.equals(other.timu_one))
			return false;
		if (timu_three == null) {
			if (other.timu_three != null)
				return false;
		} else if (!timu_three.equals(other.timu_three))
			return false;
		if (timu_title == null) {
			if (other.timu_title != null)
				return false;
		} else if (!timu_title.equals(other.timu_title))
			return false;
		if (timu_tow == null) {
			if (other.timu_tow != null)
				return false;
		} else if (!timu_tow.equals(other.timu_tow))
			return false;
		if (types == null) {
			if (other.types != null)
				return false;
		} else if (!types.equals(other.types))
			return false;
		return true;
	}

}
