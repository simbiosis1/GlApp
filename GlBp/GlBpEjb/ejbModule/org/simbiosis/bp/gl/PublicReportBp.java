package org.simbiosis.bp.gl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.simbiosis.bp.gl.model.PublicReportDto;
import org.simbiosis.gl.IFinancialConvert;
import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.model.FinancialConvert;
import org.simbiosis.gl.model.FinancialRef;
import org.simbiosis.gl.model.PublicFinancialRpt;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(IPublicReport.class)
public class PublicReportBp implements IPublicReport {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem iSystem;
	@EJB(lookup = "java:global/GlEar/GlEjb/FinancialConvertImpl")
	IFinancialConvert iConvert;
	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport iReport;

	@Override
	public List<PublicReportDto> listPublicReport(String session, long branch,
			int schema, int group, Date date) {
		List<PublicReportDto> result = new ArrayList<PublicReportDto>();
		UserDto user = iSystem.getUserFromSession(session);
		if (user != null) {
			List<PublicFinancialRpt> publicValues = iReport.listPublic(
					user.getCompany(), branch, date, group);
			Map<String, PublicFinancialRpt> mapValues = new HashMap<String, PublicFinancialRpt>();
			for (PublicFinancialRpt rpt : publicValues) {
				mapValues.put(rpt.getId().getCode(), rpt);
			}
			List<FinancialRef> fRefs = iConvert.listFinancialRef(schema, group);
			for (FinancialRef fRef : fRefs) {
				PublicReportDto dto = new PublicReportDto();
				dto.setGroup(fRef.getGroup());
				dto.setNumber(fRef.getNumber());
				dto.setDescription(fRef.getDescription());
				PublicFinancialRpt rpt = mapValues.get(fRef.getCode());
				if (rpt != null) {
					dto.setValue(rpt.getValue());
				}
				result.add(dto);
			}

		}
		return result;
	}

	@Override
	public List<PublicReportDto> listCombinedFinancialReportRef(String session,
			int schema, int group) {
		List<PublicReportDto> result = new ArrayList<PublicReportDto>();
		UserDto user = iSystem.getUserFromSession(session);
		if (user != null) {
			List<FinancialRef> fRefs = iConvert.listFinancialRef(schema, group);
			for (FinancialRef fRef : fRefs) {
				List<FinancialConvert> fConverts = iConvert
						.listFinancialConvert(user.getCompany(), group, fRef.getCode());
				if (fConverts.size() > 0) {
					for (FinancialConvert fConvert : fConverts) {
						PublicReportDto dto = new PublicReportDto();
						dto.setGroup(fRef.getGroup());
						dto.setCode(fConvert.getCode());
						dto.setNumber(fRef.getNumber());
						dto.setDescription(fRef.getDescription());
						dto.setCoa(fConvert.getCoa().getId());
						dto.setCoaLevel(fConvert.getCoa().getLevel());
						result.add(dto);
					}
				}
			}

		}
		return result;
	}
}
