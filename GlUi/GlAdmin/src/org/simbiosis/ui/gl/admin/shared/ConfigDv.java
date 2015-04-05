package org.simbiosis.ui.gl.admin.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigDv implements IsSerializable {
	Long clr;
	String strClr;
	Long llr;
	String strLlr;
	Long reserve;
	String strReserve;
	Long tax;
	String strTax;
	Long zakat;
	String strZakat;

	public ConfigDv() {
		clr = 0L;
		llr = 0L;
		reserve = 0L;
		tax = 0L;
		zakat = 0L;
	}

	public Long getClr() {
		return clr;
	}

	public void setClr(Long clr) {
		this.clr = clr;
	}

	public String getStrClr() {
		return strClr;
	}

	public void setStrClr(String strClr) {
		this.strClr = strClr;
	}

	public Long getLlr() {
		return llr;
	}

	public void setLlr(Long llr) {
		this.llr = llr;
	}

	public String getStrLlr() {
		return strLlr;
	}

	public void setStrLlr(String strLlr) {
		this.strLlr = strLlr;
	}

	public Long getTax() {
		return tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public String getStrTax() {
		return strTax;
	}

	public void setStrTax(String strTax) {
		this.strTax = strTax;
	}

	public Long getZakat() {
		return zakat;
	}

	public void setZakat(Long zakat) {
		this.zakat = zakat;
	}

	public String getStrZakat() {
		return strZakat;
	}

	public void setStrZakat(String strZakat) {
		this.strZakat = strZakat;
	}

	public Long getReserve() {
		return reserve;
	}

	public void setReserve(Long reserve) {
		this.reserve = reserve;
	}

	public String getStrReserve() {
		return strReserve;
	}

	public void setStrReserve(String strReserve) {
		this.strReserve = strReserve;
	}

}
