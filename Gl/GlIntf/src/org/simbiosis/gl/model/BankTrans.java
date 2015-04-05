package org.simbiosis.gl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BankTrans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1566916265652130388L;
	long id;
	String code;
	String description;
	Date date;
	int mode;
	int type;
	String receiver;
	int released;
	double rate;
	long currencyId;
	long glTransId;
	long company;
	long branch;
	long releasedBy;
	Date releaseDate;

	Coa coa;
	Set<BankTransItem> items = new HashSet<BankTransItem>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	public Set<BankTransItem> getItems() {
		return items;
	}

	public void setItems(Set<BankTransItem> items) {
		this.items = items;
	}

	public Coa getCoa() {
		return coa;
	}

	public void setCoa(Coa coa) {
		this.coa = coa;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
		this.released = released;
	}

	public long getGlTransId() {
		return glTransId;
	}

	public void setGlTransId(long glTransId) {
		this.glTransId = glTransId;
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

	public long getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(long releasedBy) {
		this.releasedBy = releasedBy;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

}
