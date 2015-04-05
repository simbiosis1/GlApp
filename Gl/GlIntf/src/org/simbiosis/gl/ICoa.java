package org.simbiosis.gl;

import java.util.List;

import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.FinancialConvert;
import org.simbiosis.gl.model.FinancialRpt;

public interface ICoa {
	//
	long save(Coa coaDto);

	void remove(long id);

	Coa get(long id);
	
	boolean hasChildren(long id);

	Coa getByRef(long company, long refId);

	List<Coa> listByParent(long company, long branch, long idParent);

	List<Coa> listByType(long company, long branch, int type);

	List<Coa> listByStatus(long company, long branch, int status);

	List<Coa> listForTransaction(long company, long branch);

	long isExistByRefId(long company, long refId);

	//
	CoaGroup getCoaGroup(long company, String code);

	List<CoaGroup> listCoaGroup(long company, int group);

	//
	public List<FinancialRpt> listFinancialReportRef(int scheme, int type);

	public List<FinancialConvert> listFinancialConvert(long company, String code);

}
