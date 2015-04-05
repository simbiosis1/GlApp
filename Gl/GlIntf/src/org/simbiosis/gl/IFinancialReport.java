package org.simbiosis.gl;

import java.util.Date;
import java.util.List;

import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.gl.model.FinancialStartRpt;
import org.simbiosis.gl.model.PublicFinancialRpt;

public interface IFinancialReport {

	List<FinancialStartRpt> listStart(long company, long branch, int month,
			int year);

	FinancialStartRpt getStart(long company, long branch, int month, int year,
			long coa);

	long saveStart(FinancialStartRpt dto);

	void deleteStart(long company, int year, int month);

	//

	public FinancialRpt get(long company, long branch, Date date, long coa);

	public void delete(long company, Date pos);

	public void save(FinancialRpt report);

	public List<FinancialRpt> list(long company, long branch, Date date);

	//

	public void deletePublic(long company, Date pos);

	public void savePublic(PublicFinancialRpt report);

	List<PublicFinancialRpt> listPublic(long company, long branch, Date date, int group);
}
