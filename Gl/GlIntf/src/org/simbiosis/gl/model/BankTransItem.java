package org.simbiosis.gl.model;

import java.io.Serializable;

public class BankTransItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5517410072703578039L;
	long id;
	String description;
	double value;

	BankTrans transaction;
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

	public BankTrans getTransaction() {
		return transaction;
	}

	public void setTransaction(BankTrans transaction) {
		this.transaction = transaction;
	}

	public Coa getCoa() {
		return coa;
	}

	public void setCoa(Coa coa) {
		this.coa = coa;
	}

}
