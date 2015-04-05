package org.simbiosis.gl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.gl.model.BankTrans;
import org.simbiosis.gl.model.BankTransItem;
import org.simbiosis.gl.model.CashTrans;
import org.simbiosis.gl.model.CashTransItem;

/**
 * Session Bean implementation class Ledger
 */
@Stateless
@Remote(ICashBank.class)
public class CashBankImpl implements ICashBank {

	@PersistenceContext(unitName = "GlEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@Override
	public String getMaxCashTransCode(long company, long branch,
			String prefixCode) {
		Query qry = em.createNamedQuery("getMaxCashTransCode");
		qry.setParameter("prefixCode", prefixCode + "/%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	@Override
	public long saveCashTrans(CashTrans trans) {
		HashMap<Long, CashTransItem> transMap = new HashMap<Long, CashTransItem>();
		if (trans.getId() == 0) {
			em.persist(trans);
		} else {
			for (CashTransItem item : trans.getItems()) {
				if (item.getId() != 0) {
					transMap.put(item.getId(), item);
				}
			}
			// Delete item yang tidak ada..
			CashTrans cbTrans = em.find(CashTrans.class, trans.getId());
			for (CashTransItem item : cbTrans.getItems()) {
				CashTransItem found = transMap.get(item.getId());
				if (found == null) {
					em.remove(item);
				}
			}
			//
			em.merge(trans);
		}
		return trans.getId();
	}

	@Override
	public CashTrans getCashTrans(long id) {
		CashTrans cbTrans = em.find(CashTrans.class, id);
		return cbTrans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CashTrans> listCashTransByDate(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat) {
		String strQry = "select x from CBTrans x "
				+ "where x.date>=:dateBegin and x.date<=:dateEnd "
				+ "and x.company=:company and x.branch=:branch";
		if (!strSyarat.isEmpty()) {
			strQry += " " + strSyarat;
		}
		Query qry = em.createQuery(strQry);
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("dateBegin", dateBegin);
		qry.setParameter("dateEnd", dateEnd);
		List<CashTrans> result = qry.getResultList();
		return result;
	}

	@Override
	public void releaseCashTrans(CashTrans cbTransDto) {
		if (cbTransDto.getId() != 0) {
			CashTrans cbTrans = em.find(CashTrans.class, cbTransDto.getId());
			cbTrans.setGlTransId(cbTransDto.getGlTransId());
			cbTrans.setReleased(cbTransDto.getReleased());
			cbTrans.setReleaseDate(cbTransDto.getReleaseDate());
			cbTrans.setReleasedBy(cbTransDto.getReleasedBy());
			em.merge(cbTrans);
		}
	}

	@Override
	public void removeCashTrans(long id) {
		CashTrans cbTrans = em.find(CashTrans.class, id);
		em.remove(cbTrans);
	}

	@Override
	public String getMaxBankTransCode(long company, long branch,
			String prefixCode) {
		Query qry = em.createNamedQuery("getMaxBankTransCode");
		qry.setParameter("prefixCode", prefixCode + "/%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	@Override
	public long saveBankTrans(BankTrans trans) {
		HashMap<Long, BankTransItem> transMap = new HashMap<Long, BankTransItem>();
		if (trans.getId() == 0) {
			em.persist(trans);
		} else {
			// Buat map
			for (BankTransItem item : trans.getItems()) {
				if (item.getId() != 0) {
					transMap.put(item.getId(), item);
				}
			}
			// Delete item yang tidak ada..
			BankTrans cbTrans = em.find(BankTrans.class, trans.getId());
			for (BankTransItem item : cbTrans.getItems()) {
				BankTransItem found = transMap.get(item.getId());
				if (found == null) {
					em.remove(item);
				}
			}
			//
			em.merge(trans);
		}
		return trans.getId();
	}

	@Override
	public BankTrans getBankTrans(long id) {
		BankTrans cbTrans = em.find(BankTrans.class, id);
		return cbTrans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankTrans> listBankTransByDate(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat) {
		String strQry = "select x from BankTrans x "
				+ "where x.date>=:dateBegin and x.date<=:dateEnd "
				+ "and x.company=:company and x.branch=:branch";
		if (!strSyarat.isEmpty()) {
			strQry += " " + strSyarat;
		}
		Query qry = em.createQuery(strQry);
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("dateBegin", dateBegin);
		qry.setParameter("dateEnd", dateEnd);
		List<BankTrans> result = qry.getResultList();
		return result;
	}

	@Override
	public void releaseBankTrans(BankTrans cbTransDto) {
		if (cbTransDto.getId() != 0) {
			BankTrans cbTrans = em.find(BankTrans.class, cbTransDto.getId());
			cbTrans.setGlTransId(cbTransDto.getGlTransId());
			cbTrans.setReleased(cbTransDto.getReleased());
			cbTrans.setReleaseDate(cbTransDto.getReleaseDate());
			cbTrans.setReleasedBy(cbTransDto.getReleasedBy());
			em.merge(cbTrans);
		}
	}

	@Override
	public void removeBankTrans(long id) {
		BankTrans cbTrans = em.find(BankTrans.class, id);
		em.remove(cbTrans);
	}

}
