package org.simbiosis.gl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.FinancialConvert;
import org.simbiosis.gl.model.FinancialRpt;

@Stateless
@Remote(ICoa.class)
public class CoaImpl implements ICoa {
	@PersistenceContext(unitName = "GlEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	String groups[] = { "", "AKTIVA", "PASIVA" };

	public CoaImpl() {
	}

	@Override
	public long save(Coa coa) {
		if (coa.getId() == 0) {
			em.persist(coa);
		} else {
			em.merge(coa);
		}
		return coa.getId();
	}

	@Override
	public Coa get(long id) {
		Coa coa = em.find(Coa.class, id);
		return coa;
	}

	@Override
	public void remove(long id) {
		Coa coa = em.find(Coa.class, id);
		em.remove(coa);
	}

	@Override
	public boolean hasChildren(long id) {
		Query qry = em.createNamedQuery("hasChildren");
		qry.setParameter("id", id);
		Long counter = (Long) qry.getSingleResult();
		return (counter == null || counter == 0) ? false : true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coa> listByParent(long company, long branch, long idParent) {
		String strQry = (idParent == 0) ? "listCoaByParent0"
				: "listCoaByParent";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("company", company);
		qry.setParameter("idParent", idParent);
		List<Coa> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coa> listByType(long company, long branch, int type) {
		Query qry = null;
		switch (type) {
		case 1:
			qry = em.createNamedQuery("listCoaByType1");
			qry.setParameter("company", company);
			break;
		case 2:
			qry = em.createNamedQuery("listCoaByType2");
			qry.setParameter("branch", branch);
			break;
		default:
			break;
		}
		List<Coa> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coa> listByStatus(long company, long branch, int status) {
		Query qry = em.createNamedQuery("listCoaByStatus");
		qry.setParameter("company", company);
		qry.setParameter("active", status);
		List<Coa> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isExistByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getCoaByRefId");
		qry.setParameter("company", company);
		qry.setParameter("refId", refId);
		List<Coa> coaList = qry.getResultList();
		if (coaList.size() > 0) {
			return coaList.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coa> listForTransaction(long company, long branch) {
		List<Coa> result = new ArrayList<Coa>();
		String strQuery = (branch == 0) ? "listCoaForTransaction1"
				: "listCoaForTransaction2";
		Query qry = em.createNamedQuery(strQuery);
		qry.setParameter("company", company);
		if (branch != 0) {
			qry.setParameter("branch", branch);
		}
		List<Object[]> qryResult = qry.getResultList();
		for (Object[] object : qryResult) {
			Coa coaDto = new Coa();
			coaDto.setId((Long) object[0]);
			coaDto.setCode((String) object[1]);
			coaDto.setDescription((String) object[2]);
			result.add(coaDto);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Coa getByRef(long company, long refId) {
		Query qry = em.createNamedQuery("getCoaByRefId");
		qry.setParameter("company", company);
		qry.setParameter("refId", refId);
		List<Coa> coaList = qry.getResultList();
		if (coaList.size() > 0) {
			return coaList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CoaGroup getCoaGroup(long company, String code) {
		Query qry = em.createNamedQuery("getCoaGroup");
		qry.setParameter("company", company);
		qry.setParameter("code", code);
		List<CoaGroup> resultList = qry.getResultList();
		if (resultList.size() == 1) {
			return resultList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoaGroup> listCoaGroup(long company, int group) {
		Query qry = em.createNamedQuery("listCoaGroup");
		qry.setParameter("company", company);
		qry.setParameter("group", group);
		List<CoaGroup> result = qry.getResultList();
		return result;
	}

	@Override
	public List<FinancialRpt> listFinancialReportRef(int scheme, int type) {
		List<FinancialRpt> result = new ArrayList<FinancialRpt>();
		// Query qry = em.createNamedQuery("listFinancialRef");
		// qry.setParameter("scheme", scheme);
		// qry.setParameter("type", type);
		// List<FinancialRef> finRefs = qry.getResultList();
		// for (FinancialRef finRef : finRefs) {
		// FinancialRpt rpt = new FinancialRpt();
		// rpt.setNumber(finRef.getNumber());
		// rpt.setOrder(finRef.getOrder());
		// rpt.setCode(finRef.getCode());
		// rpt.setDescription(finRef.getDescription());
		// rpt.setGroup(groups[finRef.getType()]);
		// result.add(rpt);
		// }
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialConvert> listFinancialConvert(long company, String code) {
		Query qry = em.createNamedQuery("listFinancialConvert");
		qry.setParameter("company", company);
		qry.setParameter("code", code);
		List<FinancialConvert> result = qry.getResultList();
		return result;
	}
}
