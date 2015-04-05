package org.simbiosis.bp.gl.model;

import java.io.Serializable;
import java.util.Date;

public class PublicReportDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7115875604813549856L;
	long branch;
	Date date;
	int group;
	String strGroup;
	String code;
	String description;
	long coa;
	int coaLevel;
	double value;
	String number;

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public long getCoa() {
		return coa;
	}

	public void setCoa(long coa) {
		this.coa = coa;
	}

	public int getCoaLevel() {
		return coaLevel;
	}

	public void setCoaLevel(int coaLevel) {
		this.coaLevel = coaLevel;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getStrGroup() {
		return strGroup;
	}

	public void setStrGroup(String strGroup) {
		this.strGroup = strGroup;
	}

}
