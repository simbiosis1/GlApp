package org.simbiosis.gl.model;

import java.io.Serializable;

public class FinancialStartRpt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3085821990540597225L;
	long id;
	int month;
	int year;
	long company;
	long branch;
	double value;
	int group;
	long coa;
	String coaCode;
	String coaDescription;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public long getCoa() {
		return coa;
	}

	public void setCoa(long coa) {
		this.coa = coa;
	}

	public String getCoaCode() {
		return coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public String getCoaDescription() {
		return coaDescription;
	}

	public void setCoaDescription(String coaDescription) {
		this.coaDescription = coaDescription;
	}

}
