package org.simbiosis.gl.model;

import java.io.Serializable;
import java.util.Date;

public class FinancialRptPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1657016139550997943L;
	Date date;
	long coa;
	long company;
	long branch;

	public FinancialRptPK() {

	}

	public FinancialRptPK(Date date, long coa, long company, long branch) {
		this.date = date;
		this.coa = coa;
		this.company = company;
		this.branch = branch;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getCoa() {
		return coa;
	}

	public void setCoa(long coa) {
		this.coa = coa;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (branch ^ (branch >>> 32));
		result = prime * result + (int) (coa ^ (coa >>> 32));
		result = prime * result + (int) (company ^ (company >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		FinancialRptPK other = (FinancialRptPK) obj;
		if (branch != other.branch)
			return false;
		if (coa != other.coa)
			return false;
		if (company != other.company)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

}
