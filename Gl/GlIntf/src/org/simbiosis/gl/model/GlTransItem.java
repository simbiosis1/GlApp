package org.simbiosis.gl.model;

import java.io.Serializable;

public class GlTransItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7350346246447277516L;
	long id;
	String description;
	int direction;
	double value;
	long reportId;
	GlTrans transaction;
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public GlTrans getTransaction() {
		return transaction;
	}

	public void setTransaction(GlTrans transaction) {
		this.transaction = transaction;
	}

	public Coa getCoa() {
		return coa;
	}

	public void setCoa(Coa coa) {
		this.coa = coa;
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
}
