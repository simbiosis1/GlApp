package org.simbiosis.gl.model;

import java.io.Serializable;

public class FinancialRef implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1232896626258525010L;
	long id;
	int scheme;
	int group;
	int order;
	String number;
	String description;
	String code;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int type) {
		this.group = type;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getScheme() {
		return scheme;
	}

	public void setScheme(int scheme) {
		this.scheme = scheme;
	}

}
