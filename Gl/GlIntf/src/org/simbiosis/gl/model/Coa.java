package org.simbiosis.gl.model;

import java.io.Serializable;

public class Coa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3081687051833784208L;
	long id;
	long refId;
	String code;
	String description;
	int level;
	String prefix;
	long company;
	long branch;
	long excBranch;
	int active;
	int protect;
	Coa parent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public Coa getParent() {
		return parent;
	}

	public void setParent(Coa parent) {
		this.parent = parent;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public long getExcBranch() {
		return excBranch;
	}

	public void setExcBranch(long excBranch) {
		this.excBranch = excBranch;
	}

	public int getProtect() {
		return protect;
	}

	public void setProtect(int protect) {
		this.protect = protect;
	}

}
