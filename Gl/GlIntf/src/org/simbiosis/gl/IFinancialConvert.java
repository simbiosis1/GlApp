package org.simbiosis.gl;

import java.util.List;

import org.simbiosis.gl.model.FinancialConvert;
import org.simbiosis.gl.model.FinancialRef;

public interface IFinancialConvert {
	List<FinancialRef> listFinancialRef(int schema, int group);

	List<FinancialConvert> listFinancialConvert(long company, int group,
			String code);
}
