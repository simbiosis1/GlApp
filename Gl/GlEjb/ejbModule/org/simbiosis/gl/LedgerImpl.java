package org.simbiosis.gl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;

/**
 * Session Bean implementation class Ledger
 */
@Stateless
@Remote(ILedger.class)
public class LedgerImpl implements ILedger {

	@PersistenceContext(unitName = "GlEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public LedgerImpl() {
	}

	@Override
	public long saveGLTrans(GlTrans trans) {
		for (GlTransItem item : trans.getItems()) {
			item.setTransaction(trans);
		}
		if (trans.getId() == 0) {
			em.persist(trans);
		} else {
			HashMap<Long, GlTransItem> transMap = new HashMap<Long, GlTransItem>();
			// Delete item yang tidak ada..
			GlTrans glTrans = em.find(GlTrans.class, trans.getId());
			for (GlTransItem item : glTrans.getItems()) {
				GlTransItem found = transMap.get(item.getId());
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
	public GlTrans getGLTrans(long id) {
		GlTrans glTrans = em.find(GlTrans.class, id);
		return glTrans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlTrans> listGLTransByDate(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat) {
		String strQry = "select x from GlTrans x "
				+ "where x.date>=:dateBegin and x.date<=:dateEnd "
				+ "and x.company=:company ";
		if (branch != 0) {
			strQry += "and x.branch=:branch ";
		}
		if (!strSyarat.isEmpty()) {
			strQry += " " + strSyarat;
		}
		strQry += " order by x.date";
		Query qry = em.createQuery(strQry);
		qry.setParameter("company", company);
		qry.setParameter("dateBegin", dateBegin);
		qry.setParameter("dateEnd", dateEnd);
		if (branch != 0)
			qry.setParameter("branch", branch);
		List<GlTrans> result = qry.getResultList();
		return result;
	}

	@Override
	public double getGlTransValue(long id, int direction) {
		Query qry = em.createNamedQuery("getSumGlTrans");
		qry.setParameter("id", id);
		qry.setParameter("direction", direction);
		Double value = (Double) qry.getSingleResult();
		return value == null ? 0 : value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlTrans> listReleasedGLTransByDate(long company, long branch,
			Date beginDate, Date endDate) {
		String strQry = (branch == 0) ? "listReleasedGLTransByDate1"
				: "listReleasedGLTransByDate2";
		List<GlTrans> result = new ArrayList<GlTrans>();
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0)
			qry.setParameter("company", company);
		else
			qry.setParameter("branch", branch);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		List<Object[]> resultList = qry.getResultList();
		for (Object[] object : resultList) {
			GlTrans transDto = new GlTrans();
			transDto.setId((Long) object[0]);
			transDto.setDate((Date) object[1]);
			transDto.setCode((String) object[2]);
			transDto.setDescription((String) object[3]);
			result.add(transDto);
		}
		return result;
	}

	@Override
	public void removeGLTrans(long id) {
		GlTrans glTrans = em.find(GlTrans.class, id);
		em.remove(glTrans);
	}

	@Override
	public String getMaxGLTransCode(long company, long branch, String prefixCode) {
		Query qry = em.createNamedQuery("getMaxGLTransCode");
		qry.setParameter("prefixCode", prefixCode + "/%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	@Override
	public void releaseGLTrans(GlTrans glTransDto) {
		if (glTransDto.getId() != 0) {
			GlTrans glTrans = em.find(GlTrans.class, glTransDto.getId());
			glTrans.setReleased(glTransDto.getReleased());
			glTrans.setReleaseDate(glTransDto.getReleaseDate());
			glTrans.setReleasedBy(glTransDto.getReleasedBy());
			em.merge(glTrans);
		}
	}

	@Override
	public void postGLTrans(GlTrans glTransDto) {
		if (glTransDto.getId() != 0) {
			GlTrans glTrans = em.find(GlTrans.class, glTransDto.getId());
			glTrans.setPosted(glTransDto.getPosted());
			glTrans.setPostDate(glTransDto.getPostDate());
			glTrans.setPostedBy(glTransDto.getPostedBy());
			em.merge(glTrans);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isGlTransExistByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getGLTransByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<GlTrans> glTransList = qry.getResultList();
		if (glTransList.size() > 0) {
			return glTransList.get(0).getId();
		}
		return 0;
	}

	@Override
	public double getSumGlTrans(long coaId, int direction, Date beginDate,
			Date endDate) {
		Query qry = em.createNamedQuery("getSumGlTransByCoa");
		qry.setParameter("coaId", coaId);
		qry.setParameter("direction", direction);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		try {
			Double result = (Double) qry.getSingleResult();
			if (result == null)
				result = 0D;
			return result;
		} catch (Exception e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listSumGlTransUntilDate(long company, long branch,
			Date endDate) {
		String sqlName = (branch == 0) ? "listSumGlTransUntilDate1"
				: "listSumGlTransUntilDate2";
		Query qry = em.createNamedQuery(sqlName);
		qry.setParameter("date", endDate);
		if (branch != 0)
			qry.setParameter("branch", branch);
		else
			qry.setParameter("company", company);
		List<Object[]> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listSumGlTransGroupUntilDate(long company,
			long branch, Date endDate, String code) {
		String sqlName = (branch == 0) ? "listSumGlTransGroupUntilDate1"
				: "listSumGlTransGroupUntilDate2";
		Query qry = em.createNamedQuery(sqlName);
		qry.setParameter("date", endDate);
		qry.setParameter("code", code + "%");
		if (branch != 0)
			qry.setParameter("branch", branch);
		else
			qry.setParameter("company", company);
		List<Object[]> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listSumGlTransRange(long company, long branch,
			Date beginDate, Date endDate) {
		String sqlName = (branch == 0) ? "listSumGlTransRange1"
				: "listSumGlTransRange2";
		Query qry = em.createNamedQuery(sqlName);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		if (branch != 0)
			qry.setParameter("branch", branch);
		else
			qry.setParameter("company", company);
		List<Object[]> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listSumGlTransGroupRange(long company, long branch,
			Date beginDate, Date endDate, String code) {
		String strQry = (branch == 0) ? "listSumGlTransGroupRange1"
				: "listSumGlTransGroupRange2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		qry.setParameter("code", code + "%");
		if (branch != 0)
			qry.setParameter("branch", branch);
		else
			qry.setParameter("company", company);
		List<Object[]> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlTransItem> listGLTransByCoa(long company, long branch,
			Date beginDate, Date endDate, long coa) {
		String queryName = "listGlTransCoaRange";
		queryName += (branch == 0) ? "1" : "2";
		Query qry = em.createNamedQuery(queryName);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		qry.setParameter("coa", coa);
		if (branch != 0) {
			qry.setParameter("branch", branch);
		} else {
			qry.setParameter("company", company);
		}
		List<GlTransItem> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlTransItem> listGLTransByUser(long company, long branch,
			Date beginDate, Date endDate, long user) {
		String queryName = "listGlTransUserRange";

		if (branch == 0 && user != 0) {
			queryName += "1";
		} else if (branch != 0 && user != 0) {
			queryName += "2";
		} else if (branch == 0 && user == 0) {
			queryName += "3";
		} else if (branch != 0 && user == 0) {
			queryName += "4";
		}
		Query qry = em.createNamedQuery(queryName);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		if (branch != 0)
			qry.setParameter("branch", branch);
		else
			qry.setParameter("company", company);
		if (user != 0) {
			qry.setParameter("user", user);
		}
		List<GlTransItem> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlTrans> listGLTransByUser(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat, long user) {
		String strQry = "select x from GlTrans x "
				+ "where x.date>=:dateBegin and x.date<=:dateEnd "
				+ "and x.company=:company ";
		if (branch != 0) {
			strQry += "and x.branch=:branch ";
		}
		if (!strSyarat.isEmpty()) {
			strQry += " " + strSyarat;
		}
		if (user != 0) {
			strQry += "and x.user=:user";
		}
		strQry += " order by x.date";
		Query qry = em.createQuery(strQry);
		qry.setParameter("company", company);
		qry.setParameter("dateBegin", dateBegin);
		qry.setParameter("dateEnd", dateEnd);
		if (branch != 0) {
			qry.setParameter("branch", branch);
		}
		if (user != 0) {
			qry.setParameter("user", user);
		}
		List<GlTrans> result = qry.getResultList();
		return result;
	}

}
