package org.simbiosis.glreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.gl.model.FinancialStartRpt;
import org.simbiosis.gl.model.PublicFinancialRpt;

@Stateless
@Remote(IFinancialReport.class)
public class FinancialReport implements IFinancialReport {

	@PersistenceContext(unitName = "GlReportEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialStartRpt> listStart(long company, long branch,
			int month, int year) {
		String strQry = (branch == 0) ? "listGLTransStart1"
				: "listGLTransStart2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("company", company);
		qry.setParameter("month", month);
		qry.setParameter("year", year);
		if (branch != 0) {
			qry.setParameter("branch", branch);
		}
		Map<Long, FinancialStartRpt> map = new HashMap<Long, FinancialStartRpt>();
		List<FinancialStartRpt> transList = qry.getResultList();
		for (FinancialStartRpt trans : transList) {
			FinancialStartRpt dto = null;
			if (branch == 0) {
				dto = map.get(trans.getCoa());
				if (dto != null) {
					dto.setValue(dto.getValue() + trans.getValue());
				} else {
					em.detach(trans);
					dto = trans;
				}
			} else {
				dto = trans;
			}
			map.put(trans.getCoa(), dto);
		}
		//
		List<FinancialStartRpt> result = new ArrayList<FinancialStartRpt>(
				map.values());
		//
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FinancialStartRpt getStart(long company, long branch, int month,
			int year, long coa) {
		Query qry = em.createNamedQuery("getGLTransStart");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("month", month);
		qry.setParameter("year", year);
		qry.setParameter("coa", coa);
		List<FinancialStartRpt> trans = qry.getResultList();
		if (trans.size() > 0) {
			return trans.get(0);
		}
		return null;
	}

	@Override
	public long saveStart(FinancialStartRpt trans) {
		em.persist(trans);
		return trans.getId();
	}

	@Override
	public void deleteStart(long company, int year, int month) {
		Query qry = em.createNamedQuery("deleteGLTransStart");
		qry.setParameter("company", company);
		qry.setParameter("year", year);
		qry.setParameter("month", month);
		qry.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialRpt> list(long company, long branch, Date date) {
		Query qry = em.createNamedQuery("listFinancialRpt");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("date", date);
		return qry.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public FinancialRpt get(long company, long branch, Date date, long coa) {
		Query qry = em.createNamedQuery("getFinancialRpt");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("date", date);
		qry.setParameter("coa", coa);
		List<FinancialRpt> finRefs = qry.getResultList();
		if (finRefs.size() > 0) {
			return finRefs.get(0);
		}
		return null;
	}

	@Override
	public void delete(long company, Date date) {
		Query qry = em.createNamedQuery("deleteFinancialRpt");
		qry.setParameter("company", company);
		qry.setParameter("date", date);
		qry.executeUpdate();
	}

	@Override
	public void save(FinancialRpt report) {
		em.merge(report);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PublicFinancialRpt> listPublic(long company, long branch, Date date, int group) {
		Query qry = em.createNamedQuery("listPublicFinancialRpt");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("date", date);
		qry.setParameter("group", group);
		return qry.getResultList();
	}

	@Override
	public void deletePublic(long company, Date date) {
		Query qry = em.createNamedQuery("deletePublicFinancialRpt");
		qry.setParameter("company", company);
		qry.setParameter("date", date);
		qry.executeUpdate();
	}

	@Override
	public void savePublic(PublicFinancialRpt report) {
		em.merge(report);
	}

}
