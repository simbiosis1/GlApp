package org.simbiosis.gl.model;

import java.io.Serializable;

public class FinancialRpt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2538959895453223083L;

	FinancialRptPK id;
	int group;
	int factor;
	double startValue;
	double currentValue;
	double endValue;
	double debit;
	double credit;

	String coaCode;
	String coaDescription;
	long coaParent;
	String coaParentCode;
	String coaParentDescription;
	long coaGrandParent;
	String coaGrandParentCode;
	String coaGrandParentDescription;

	public FinancialRpt() {
		coaCode = "";
		coaDescription = "";
		coaParentCode = "";
		coaParentDescription = "";
		coaGrandParentCode = "";
		coaGrandParentDescription = "";
	}

	public FinancialRptPK getId() {
		return id;
	}

	public void setId(FinancialRptPK id) {
		this.id = id;
	}

	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

	public double getStartValue() {
		return startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getCoaCode() {
		return coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public String getCoaDescription() {
		return coaDescription;
	}

	public void setCoaDescription(String coaDescription) {
		this.coaDescription = coaDescription;
	}

	public long getCoaParent() {
		return coaParent;
	}

	public void setCoaParent(long coaParent) {
		this.coaParent = coaParent;
	}

	public String getCoaParentCode() {
		return coaParentCode;
	}

	public void setCoaParentCode(String coaParentCode) {
		this.coaParentCode = coaParentCode;
	}

	public String getCoaParentDescription() {
		return coaParentDescription;
	}

	public void setCoaParentDescription(String coaParentDescription) {
		this.coaParentDescription = coaParentDescription;
	}

	public long getCoaGrandParent() {
		return coaGrandParent;
	}

	public void setCoaGrandParent(long coaGrandParent) {
		this.coaGrandParent = coaGrandParent;
	}

	public String getCoaGrandParentCode() {
		return coaGrandParentCode;
	}

	public void setCoaGrandParentCode(String coaGrandParentCode) {
		this.coaGrandParentCode = coaGrandParentCode;
	}

	public String getCoaGrandParentDescription() {
		return coaGrandParentDescription;
	}

	public void setCoaGrandParentDescription(String coaGrandParentDescription) {
		this.coaGrandParentDescription = coaGrandParentDescription;
	}

}
