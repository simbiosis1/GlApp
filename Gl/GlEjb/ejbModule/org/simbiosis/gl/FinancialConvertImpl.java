package org.simbiosis.gl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.gl.model.FinancialConvert;
import org.simbiosis.gl.model.FinancialRef;

@Stateless
@Remote(IFinancialConvert.class)
public class FinancialConvertImpl implements IFinancialConvert {
	@PersistenceContext(unitName = "GlEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialRef> listFinancialRef(int scheme, int group) {
		Query qry = em.createNamedQuery("listFinancialRef");
		qry.setParameter("scheme", scheme);
		qry.setParameter("group", group);
		return qry.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialConvert> listFinancialConvert(long company, int group, String code) {
		Query qry = em.createNamedQuery("listFinancialConvert");
		qry.setParameter("company", company);
		qry.setParameter("group", group);
		qry.setParameter("code", code);
		return qry.getResultList();
	}

}
