package org.simbiosis.gl;

import java.util.Date;
import java.util.List;

import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;

public interface ILedger {
	//
	long saveGLTrans(GlTrans glTransDto);

	GlTrans getGLTrans(long id);

	List<GlTrans> listGLTransByUser(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat, long user);

	void removeGLTrans(long id);

	void releaseGLTrans(GlTrans glTrans);

	void postGLTrans(GlTrans glTrans);
	
	double getGlTransValue(long id, int direction);
	
	List<GlTransItem> listGLTransByCoa(long company, long branch,
			Date dateBegin, Date dateEnd, long coa);

	List<GlTrans> listGLTransByDate(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat);

	List<GlTrans> listReleasedGLTransByDate(long company, long branch,
			Date dateBegin, Date dateEnd);

	String getMaxGLTransCode(long company, long branch, String prefixCode);

	long isGlTransExistByRefId(long company, long branch, long refId);

	double getSumGlTrans(long coaId, int direction, Date beginDate, Date endDate);

	List<Object[]> listSumGlTransGroupUntilDate(long company, long branch,
			Date endDate, String code);

	List<Object[]> listSumGlTransGroupRange(long company, long branch,
			Date beginDate, Date endDate, String code);

	List<Object[]> listSumGlTransUntilDate(long company, long branch,
			Date endDate);

	List<Object[]> listSumGlTransRange(long company, long branch,
			Date beginDate, Date endDate);

	List<GlTransItem> listGLTransByUser(long company, long branch,
			Date beginDate, Date endDate, long user);

}
