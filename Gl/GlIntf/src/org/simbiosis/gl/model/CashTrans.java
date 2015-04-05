package org.simbiosis.gl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CashTrans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6482385708129754541L;
	long id;
	String code;
	String description;
	Date date;
	int hasRab;
	int direction;
	String maker;
	int released;
	double rate;
	long currencyId;
	long glTransId;
	long company;
	long branch;
	long releasedBy;
	Date releaseDate;

	Coa coa;
	Set<CashTransItem> items = new HashSet<CashTransItem>();

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

	public int getHasRab() {
		return hasRab;
	}

	public void setHasRab(int hasRab) {
		this.hasRab = hasRab;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
		this.released = released;
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

	public Coa getCoa() {
		return coa;
	}

	public void setCoa(Coa coa) {
		this.coa = coa;
	}

	public Set<CashTransItem> getItems() {
		return items;
	}

	public void setItems(Set<CashTransItem> items) {
		this.items = items;
	}

}
