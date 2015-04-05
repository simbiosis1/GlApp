package org.simbiosis.ui.gl.input.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.system.UserDto;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GlServiceImpl extends RemoteServiceServlet implements GlService {

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystemBp;

	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");

	@Override
	public List<UserDv> listUsers(String key) throws IllegalArgumentException {
		List<UserDv> result = new ArrayList<UserDv>();
		List<UserDto> users = iSystemBp.listActiveUsers(key);
		for (UserDto user : users) {
			UserDv dv = new UserDv();
			dv.setId(user.getId());
			dv.setRealName(user.getRealName());
			result.add(dv);
		}
		return result;
	}

	private CoaDv createCoaDv(Coa coa) {
		CoaDv coaDv = new CoaDv();
		coaDv.setId(coa.getId());
		coaDv.setCode(coa.getCode());
		coaDv.setDescription(coa.getDescription());
		return coaDv;
	}

	@Override
	public List<CoaDv> listCoaForTransaction(String key) {
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		for (Coa coa : glBp.listCoaForTransaction(key)) {
			returnList.add(createCoaDv(coa));
		}
		return returnList;
	}

	@Override
	public List<CoaDv> listCoaByLevel(String key) {
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		for (Coa coa : glBp.listCoaByLevel(key)) {
			returnList.add(createCoaDv(coa));
		}
		return returnList;
	}

	private GLTransDv createGLTransDv(GlTrans transDto) {
		int i = 1;
		GLTransDv transDv = new GLTransDv();
		transDv.setId(transDto.getId());
		transDv.setCode(transDto.getCode());
		transDv.setDescription(transDto.getDescription());
		transDv.setDate(transDto.getDate());
		transDv.setStrDate(dtf.print(new DateTime(transDto.getDate())));
		transDv.setCurrencyId(transDto.getCurrencyId());
		// FIXME: Hardcoded rupiah
		transDv.setStrCurrency("Rupiah");
		transDv.setRate(transDto.getRate());
		transDv.setStrRate("" + transDto.getRate());
		for (GlTransItem itemDto : transDto.getItems()) {
			GLTransItemDv itemDv = new GLTransItemDv();
			itemDv.setNr(i++);
			itemDv.setId(itemDto.getId());
			itemDv.setDescription(itemDto.getDescription());
			Coa coaDto = itemDto.getCoa();
			itemDv.setCoa(coaDto.getId());
			itemDv.setCoaStr(coaDto.getCode() + " - " + coaDto.getDescription());
			if (itemDto.getDirection() == 1) {
				itemDv.setDebit(itemDto.getValue());
				itemDv.setCredit(0.00);
			} else {
				itemDv.setDebit(0.00);
				itemDv.setCredit(itemDto.getValue());
			}
			transDv.getItems().add(itemDv);
		}
		Collections.sort(transDv.getItems(), new Comparator<GLTransItemDv>() {

			@Override
			public int compare(GLTransItemDv arg0, GLTransItemDv arg1) {
				return (arg0.getDebit() > arg1.getDebit()) ? -1 : 1;
			}
		});
		return transDv;
	}

	@Override
	public List<GLTransDv> listGLTransByUser(String key, Date begin, Date end,
			Integer status, Long user) {
		List<GLTransDv> searchResultDv = new ArrayList<GLTransDv>();
		List<GlTrans> transDtoList = glBp.listGLTransByUser(key, begin, end,
				status, user);
		int i = 1;
		for (GlTrans transDto : transDtoList) {
			GLTransDv transDv = new GLTransDv();
			transDv.setId(transDto.getId());
			transDv.setChecked(false);
			transDv.setNr(i++);
			transDv.setCode(transDto.getCode());
			transDv.setDate(transDto.getDate());
			transDv.setStrDate(dtf.print(new DateTime(transDto.getDate())));
			transDv.setDescription(transDto.getDescription());
			transDv.setValue(glBp.getGlTransValue(transDto.getId(), 1));
			searchResultDv.add(transDv);
		}
		return searchResultDv;
	}

	@Override
	public List<GLTransDv> listGLTransByDate(String key, Date begin, Date end,
			Integer status) {
		List<GLTransDv> searchResultDv = new ArrayList<GLTransDv>();
		List<GlTrans> transDtoList = glBp.listGLTransByDate(key, begin, end,
				status);
		int i = 1;
		for (GlTrans transDto : transDtoList) {
			GLTransDv transDv = new GLTransDv();
			transDv.setId(transDto.getId());
			transDv.setChecked(false);
			transDv.setNr(i++);
			transDv.setCode(transDto.getCode());
			transDv.setDate(transDto.getDate());
			transDv.setStrDate(dtf.print(new DateTime(transDto.getDate())));
			transDv.setDescription(transDto.getDescription());
			transDv.setValue(glBp.getGlTransValue(transDto.getId(), 1));
			searchResultDv.add(transDv);
		}
		return searchResultDv;
	}

	@Override
	public GLTransDv getGLTrans(Long id) {
		GlTrans transDto = glBp.getGLTrans(id);
		return createGLTransDv(transDto);
	}

	@Override
	public Long saveGLTrans(String key, GLTransDv transDv) {
		GlTrans transDto = new GlTrans();
		transDto.setId(transDv.getId());
		transDto.setCode(transDv.getCode() != null ? transDv.getCode()
				.toUpperCase() : "");
		transDto.setDescription(transDv.getDescription() != null ? transDv
				.getDescription().toUpperCase() : "");
		transDto.setDate(transDv.getDate());
		transDto.setCurrencyId(transDv.getCurrencyId());
		transDto.setRate(Double.parseDouble(transDv.getStrRate()));
		for (GLTransItemDv itemDv : transDv.getItems()) {
			GlTransItem itemDto = new GlTransItem();
			itemDto.setId(itemDv.getId());
			Coa coa = new Coa();
			coa.setId(itemDv.getCoa());
			itemDto.setCoa(coa);
			itemDto.setDescription(itemDv.getDescription() != null ? itemDv
					.getDescription().toUpperCase() : "");
			if (itemDv.getDebit() != 0) {
				itemDto.setValue(itemDv.getDebit());
				itemDto.setDirection(1);
			} else {
				itemDto.setValue(itemDv.getCredit());
				itemDto.setDirection(2);
			}
			transDto.getItems().add(itemDto);
		}
		return glBp.saveGLTrans(key, transDto);
	}

	@Override
	public void removeGLTrans(Long id) {
		glBp.removeGLTrans(id);
	}

	@Override
	public void releaseGLTrans(String key, List<Long> idList, Boolean released) {
		glBp.releaseGLTrans(key, idList, released ? 1 : 0);
	}

	@Override
	public void postGLTrans(String key, List<Long> idList, Boolean posted) {
		glBp.postGLTrans(key, idList, posted ? 1 : 0);
	}

	@Override
	public List<GLTransItemDv> listGLTransCoa(String key, Long branch,
			Date begin, Date end, Long coa) {
		List<GLTransItemDv> result = new ArrayList<GLTransItemDv>();
		//
		List<GlTransItem> items = glBp.listGLTransByCoa(key, branch, begin,
				end, coa);
		//
		int i = 1;
		double saldo = 0;
		for (GlTransItem item : items) {
			GLTransItemDv dv = new GLTransItemDv();
			dv.setNr(i++);
			if (item.getTransaction() != null) {
				dv.setDate(item.getTransaction().getDate());
				dv.setCode(item.getTransaction().getCode());
			} else {
				dv.setDate(new DateTime(begin).minusDays(1).toDate());
				dv.setCode("");
			}
			dv.setDescription(item.getDescription());
			if (item.getDirection() == 1) {
				dv.setDebit(item.getValue());
				dv.setCredit(0D);
				saldo += item.getValue();
			} else {
				dv.setDebit(0D);
				dv.setCredit(item.getValue());
				saldo -= item.getValue();
			}
			CoaGroup cg = glBp.getCoaGroup(key, item.getCoa().getCode()
					.substring(0, 1));
			dv.setSaldo(saldo * cg.getFactor());
			result.add(dv);
		}
		return result;
	}

}
