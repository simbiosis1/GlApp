package org.simbiosis.gl.model;

import java.io.Serializable;

public class PublicFinancialRpt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2538959895453223083L;

	PublicFinancialRptPK id;
	String description;
	double value;

	public PublicFinancialRpt() {
		description = "";
	}

	public PublicFinancialRptPK getId() {
		return id;
	}

	public void setId(PublicFinancialRptPK id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
