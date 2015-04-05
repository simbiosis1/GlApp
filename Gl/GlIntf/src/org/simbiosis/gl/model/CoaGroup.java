package org.simbiosis.gl.model;

import java.io.Serializable;

public class CoaGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6224946421328212110L;
	long id;
	long company;
	int group;
	int factor;
	String code;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
