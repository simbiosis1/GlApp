package org.simbiosis.gl.model;

import java.io.Serializable;

public class FinancialConvert implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3494798790033083364L;
	long id;
	long company;
	Coa coa;
	int group;
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

	public Coa getCoa() {
		return coa;
	}

	public void setCoa(Coa coa) {
		this.coa = coa;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
}
