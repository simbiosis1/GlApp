package org.simbiosis.gl.model;

import java.io.Serializable;

public class CashTransItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6307915212554945660L;
	long id;
	String description;
	double value;

	CashTrans transaction;
	Coa coa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public CashTrans getTransaction() {
		return transaction;
	}

	public void setTransaction(CashTrans transaction) {
		this.transaction = transaction;
	}

	public Coa getCoa() {
		return coa;
	}

	public void setCoa(Coa coa) {
		this.coa = coa;
	}

}
