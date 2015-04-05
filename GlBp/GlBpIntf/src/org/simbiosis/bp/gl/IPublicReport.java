package org.simbiosis.bp.gl;

import java.util.Date;
import java.util.List;

import org.simbiosis.bp.gl.model.PublicReportDto;

public interface IPublicReport {
	List<PublicReportDto> listCombinedFinancialReportRef(String session, int schema, int group);
	
	List<PublicReportDto> listPublicReport(String session, long branch,
			int schema, int group, Date date);
}
