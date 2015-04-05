package org.simbiosis.bp.gl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.ILedger;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(IGlBp.class)
public class GlBp implements IGlBp {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem iSystem;
	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	ICoa iCoa;
	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	ILedger iLedger;
	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport iFinancialReport;

	String fillers[] = { "", "0", "00", "000", "0000" };

	//
	@Override
	public long saveCoa(String key, Coa coa) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			coa.setCompany(user.getCompany());
			// FIXME:Active harusnya ngikutin interface
			coa.setActive(1);
			return iCoa.save(coa);
		}
		return 0;
	}

	@Override
	public Coa getCoa(long id) {
		return iCoa.get(id);
	}

	@Override
	public void removeCoa(long id) {
		iCoa.remove(id);
	}

	@Override
	public List<Coa> listCoaByParent(String key, long id) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iCoa.listByParent(user.getCompany(), user.getBranch(), id);
		}
		return new ArrayList<Coa>();
	}

	@Override
	public List<Coa> listCoaByType(String key, int type) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iCoa.listByType(user.getCompany(), user.getBranch(), type);
		}
		return new ArrayList<Coa>();
	}

	@Override
	public List<Coa> listCoaForTransaction(String key) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iCoa.listForTransaction(user.getCompany(), user.getBranch());
		}
		return new ArrayList<Coa>();
	}

	@Override
	public List<Coa> listCoaByLevel(String key) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			if (user.getLevel() < 5) {
				return iCoa.listForTransaction(user.getCompany(), 0);
			} else {
				return iCoa.listForTransaction(user.getCompany(),
						user.getBranch());
			}
		}
		return new ArrayList<Coa>();
	}

	@Override
	public double getGlTransValue(long id, int direction) {
		return iLedger.getGlTransValue(id, direction);
	}

	@Override
	public List<GlTrans> listGLTransByDate(String key, Date begin, Date end,
			int status) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			String strSyarat = "";
			switch (status) {
			case 1: // Belum release
				strSyarat = "and x.released=0 and x.posted=0";
				break;
			case 2: // sudah release belum posting
				return iLedger.listReleasedGLTransByDate(user.getCompany(),
						user.getBranch(), begin, end);
			case 3: // sudah release sudah posting
				strSyarat = "and x.released=1 and x.posted=1";
				break;
			case 4: // semua baik yang sudah posting maupun belum posting tapi
					// sudah release
				return iLedger.listReleasedGLTransByDate(user.getCompany(),
						user.getBranch(), begin, end);
			default:
				strSyarat = "";
			}

			return iLedger.listGLTransByDate(user.getCompany(),
					user.getBranch(), begin, end, strSyarat);
		}
		return new ArrayList<GlTrans>();
	}

	@Override
	public long saveGLTrans(String key, GlTrans glTrans) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return saveGLTransByUser(user, glTrans);
		}
		return 0;
	}

	@Override
	public long saveGLTrans(GlTrans glTrans) {
		return iLedger.saveGLTrans(glTrans);
	}

	long saveGLTransByUser(UserDto user, GlTrans glTrans) {
		glTrans.setCompany(user.getCompany());
		glTrans.setBranch(user.getBranch());
		glTrans.setUser(user.getId());
		return iLedger.saveGLTrans(glTrans);
	}

	@Override
	public GlTrans getGLTrans(long id) {
		return iLedger.getGLTrans(id);
	}

	@Override
	public void removeGLTrans(long id) {
		iLedger.removeGLTrans(id);
	}

	@Override
	public void releaseGLTrans(String key, List<Long> idList, int released) {
		UserDto user = iSystem.getUserFromSession(key);
		Date releaseDate = new Date();
		if (user != null) {
			GlTrans glTrans = new GlTrans();
			glTrans.setReleased(released);
			glTrans.setReleasedBy(user.getId());
			glTrans.setReleaseDate(releaseDate);
			for (Long id : idList) {
				glTrans.setId(id);
				iLedger.releaseGLTrans(glTrans);
			}
		}
	}

	@Override
	public void releaseGLTrans(GlTrans glTrans) {
		iLedger.releaseGLTrans(glTrans);
	}

	@Override
	public void postGLTrans(String key, List<Long> idList, int posted) {
		UserDto user = iSystem.getUserFromSession(key);
		Date postDate = new Date();
		if (user != null) {
			GlTrans glTrans = new GlTrans();
			glTrans.setPosted(posted);
			glTrans.setPostedBy(user.getId());
			glTrans.setPostDate(postDate);
			for (Long id : idList) {
				glTrans.setId(id);
				iLedger.postGLTrans(glTrans);
			}
		}
	}

	@Override
	public List<GlTransItem> listGLTransByCoa(String key, long branch,
			Date begin, Date end, long coa) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			List<GlTransItem> result = new ArrayList<GlTransItem>();
			//
			DateTime curDate = new DateTime(begin).minusDays(1);
			Date ballanceDate = curDate.toDate();
			FinancialRpt fr = iFinancialReport.get(user.getCompany(), branch,
					ballanceDate, coa);
			Coa coaDto = iCoa.get(coa);
			GlTransItem ballance = new GlTransItem();
			ballance.setDescription("SALDO");
			ballance.setCoa(coaDto);
			ballance.setId(0);
			if (fr == null) {
				ballance.setDirection(1);
				ballance.setValue(0);
			} else {
				double value = fr.getEndValue();
				// Koreksi untuk tanggal 1 untuk laba rugi
				if (fr.getGroup() > 2) {
					DateTimeFormatter sday = DateTimeFormat.forPattern("dd;MM");
					String strDate = sday.print(new DateTime(begin));
					String[] strDates = strDate.split(";");
					int day = Integer.parseInt(strDates[0]);
					int month = Integer.parseInt(strDates[1]);
					if (day == 1 && month == 1) {
						value = 0;
					}
				}
				//
				ballance.setDirection(fr.getFactor() == 1 ? 1 : 2);
				ballance.setValue(value);
			}
			result.add(ballance);
			//
			result.addAll(iLedger.listGLTransByCoa(user.getCompany(), branch,
					begin, end, coa));
			return result;
		}
		return new ArrayList<GlTransItem>();
	}

	@Override
	public List<GlTransItem> listGLTransByUser(String key, Date begin,
			Date end, long users) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			List<GlTransItem> result = new ArrayList<GlTransItem>();
			//
			//
			result.addAll(iLedger.listGLTransByUser(user.getCompany(),
					user.getBranch(), begin, end, users));
			// Set factor
			// for (GlTransItem item : result) {
			// Coa coaDto = item.getCoa();
			// CoaGroup cg = iCoa.getCoaGroup(user.getCompany(), coaDto
			// .getCode().substring(0, 1));
			// item.setCoaName(coaDto.getCode() + " - "
			// + coaDto.getDescription());
			// item.setFactor(cg.getFactor());
			// }
			return result;
		}
		return new ArrayList<GlTransItem>();
	}

	@Override
	public List<GlTrans> listGLTransByUser(String key, Date begin, Date end,
			int status, long users) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			String strSyarat = "";
			switch (status) {
			case 1: // Belum release
				strSyarat = "and x.released=0 and x.posted=0";
				break;
			case 2: // sudah release belum posting
				return iLedger.listReleasedGLTransByDate(user.getCompany(),
						user.getBranch(), begin, end);
			case 3: // sudah release sudah posting
				strSyarat = "and x.released=1 and x.posted=1";
				break;
			case 4: // semua baik yang sudah posting maupun belum posting tapi
					// sudah release
				return iLedger.listReleasedGLTransByDate(user.getCompany(),
						user.getBranch(), begin, end);
			default:
				strSyarat = "";
			}

			return iLedger.listGLTransByUser(user.getCompany(),
					user.getBranch(), begin, end, strSyarat, users);
		}
		return new ArrayList<GlTrans>();
	}

	@Override
	public CoaGroup getCoaGroup(String session, String code) {
		UserDto user = iSystem.getUserFromSession(session);
		if (user != null) {
			CoaGroup cg = iCoa.getCoaGroup(user.getCompany(), code);
			return cg;
		}
		return null;
	}

	@Override
	public boolean hasChildren(long id) {
		return iCoa.hasChildren(id);
	}

}
