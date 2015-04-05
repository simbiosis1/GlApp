package org.simbiosis.gl.model;

import java.io.Serializable;
import java.util.Date;

public class PublicFinancialRptPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1657016139550997943L;
	Date date;
	int group;
	String code;
	long company;
	long branch;

	public PublicFinancialRptPK() {

	}

	public PublicFinancialRptPK(Date date, int group, String code,
			long company, long branch) {
		this.date = date;
		this.group = group;
		this.code = code;
		this.company = company;
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

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (branch ^ (branch >>> 32));
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + (int) (company ^ (company >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + group;
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
		PublicFinancialRptPK other = (PublicFinancialRptPK) obj;
		if (branch != other.branch)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (company != other.company)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (group != other.group)
			return false;
		return true;
	}

}
