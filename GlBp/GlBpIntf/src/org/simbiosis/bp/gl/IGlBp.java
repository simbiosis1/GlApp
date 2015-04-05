package org.simbiosis.bp.gl;

import java.util.Date;
import java.util.List;

import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;

public interface IGlBp {
	//
	long saveCoa(String key, Coa coaDto);

	Coa getCoa(long id);
	
	boolean hasChildren(long id);

	void removeCoa(long id);

	List<Coa> listCoaByParent(String key, long id);

	List<Coa> listCoaByType(String key, int type);

	List<Coa> listCoaForTransaction(String key);

	List<Coa> listCoaByLevel(String key);

	CoaGroup getCoaGroup(String session, String code);

	//

	List<GlTrans> listGLTransByDate(String key, Date begin, Date end, int status);

	List<GlTransItem> listGLTransByCoa(String key, long branch, Date begin,
			Date end, long coa);

	long saveGLTrans(String key, GlTrans glTrans);

	long saveGLTrans(GlTrans glTrans);

	GlTrans getGLTrans(long id);

	double getGlTransValue(long id, int direction);

	void removeGLTrans(long id);

	void releaseGLTrans(GlTrans idTrans);

	void releaseGLTrans(String key, List<Long> idList, int released);

	void postGLTrans(String key, List<Long> idList, int posted);

	List<GlTrans> listGLTransByUser(String key, Date begin, Date end,
			int status, long users);

	List<GlTransItem> listGLTransByUser(String key, Date begin, Date end,
			long users);

}
