package org.simbiosis.gl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GlTrans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6574733893921577763L;
	long id;
	long refId;
	String code;
	String description;
	Date date;
	double rate;
	long currencyId;
	int type;
	Date releaseDate;
	int released;
	long releasedBy;
	Date postDate;
	int posted;
	long postedBy;
	long company;
	long branch;
	long user;
	Set<GlTransItem> items = new HashSet<GlTransItem>();

	public GlTrans() {
		id = 0;
		released = 0;
		posted = 0;
		type = 0;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Set<GlTransItem> getItems() {
		return items;
	}

	public void setItems(Set<GlTransItem> items) {
		this.items = items;
	}

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
		this.released = released;
	}

	public int getPosted() {
		return posted;
	}

	public void setPosted(int posted) {
		this.posted = posted;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(long releasedBy) {
		this.releasedBy = releasedBy;
	}

	public long getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(long postedBy) {
		this.postedBy = postedBy;
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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

}
