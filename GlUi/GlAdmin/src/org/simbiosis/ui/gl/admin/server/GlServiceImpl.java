package org.simbiosis.ui.gl.admin.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ConfigDto;
import org.simbiosis.ui.gl.admin.client.rpc.GlService;
import org.simbiosis.ui.gl.admin.shared.CoaDv;
import org.simbiosis.ui.gl.admin.shared.ConfigDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GlServiceImpl extends RemoteServiceServlet implements GlService {

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp iGlBp;
	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystemBp;

	private CoaDv createCoaDv(Coa coa, Map<Long, String> branchMap,
			boolean withHasChildren) {
		CoaDv coaDv = new CoaDv();
		coaDv.setId(coa.getId());
		coaDv.setRefId(coa.getRefId());
		coaDv.setPrefix(coa.getPrefix());
		coaDv.setCode(coa.getCode());
		coaDv.setDescription(coa.getDescription());
		coaDv.setCodeDescription(coa.getCode() + " - " + coa.getDescription());
		//
		Coa parent = coa.getParent();
		if (parent != null) {
			coaDv.setParent(parent.getId());
			coaDv.setParentCodeDescription(parent.getCode() + " - "
					+ parent.getDescription());
			if (parent.getParent() != null) {
				coaDv.setGrandParent(parent.getParent().getId());
			}
		}
		if (withHasChildren && coa.getLevel() < 3) {
			coaDv.setHasChildren(iGlBp.hasChildren(coa.getId()));
		}
		//
		coaDv.setLevel(coa.getLevel());
		coaDv.setBranch(coa.getBranch());
		coaDv.setStrBranch(branchMap.get(coa.getBranch()));
		coaDv.setExcBranch(coa.getExcBranch());
		coaDv.setStrExcBranch(branchMap.get(coa.getExcBranch()));
		return coaDv;
	}

	private CoaDv createSimpleCoaDv(Coa coa) {
		CoaDv coaDv = new CoaDv();
		coaDv.setId(coa.getId());
		coaDv.setCode(coa.getCode());
		coaDv.setDescription(coa.getDescription());
		coaDv.setCodeDescription(coa.getCode() + " - " + coa.getDescription());
		return coaDv;
	}

	@Override
	public List<CoaDv> listCoaByParent(String key, Long parent) {
		Map<Long, String> map = listBranchMap(key);
		//
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		for (Coa coa : iGlBp.listCoaByParent(key, parent)) {
			returnList.add(createCoaDv(coa, map, true));
		}
		return returnList;
	}

	@Override
	public List<CoaDv> listCoaByType(String key, Integer type) {
		Map<Long, String> map = listBranchMap(key);
		//
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		for (Coa coa : iGlBp.listCoaByType(key, type)) {
			returnList.add(createCoaDv(coa, map, false));
		}
		return returnList;
	}

	@Override
	public CoaDv saveCoa(String key, CoaDv dv) {
		Coa coa = new Coa();
		coa.setId(dv.getId());
		if (coa.getId() == 0) {
			coa.setActive(1);
		}
		coa.setCode(dv.getCode() != null ? dv.getCode().toUpperCase() : "");
		coa.setPrefix(dv.getPrefix() != null ? dv.getPrefix().toUpperCase()
				: "");
		coa.setDescription(dv.getDescription() != null ? dv.getDescription()
				.toUpperCase() : "");
		Coa parent = iGlBp.getCoa(dv.getParent());
		if (parent != null) {
			if (parent.getLevel() < 2) {
				coa.setBranch(0);
				coa.setExcBranch(0);
			} else {
				coa.setBranch(dv.getBranch());
				coa.setExcBranch(dv.getExcBranch());
			}
			coa.setLevel(parent.getLevel() + 1);
		} else {
			coa.setLevel(0);
			coa.setBranch(0);
			coa.setExcBranch(0);
		}
		coa.setParent(parent);
		long id = iGlBp.saveCoa(key, coa);
		coa = iGlBp.getCoa(id);
		//
		Map<Long, String> map = listBranchMap(key);
		//
		CoaDv resultCoaDv = createCoaDv(coa, map, true);
		//
		return resultCoaDv;
	}

	private Map<Long, String> listBranchMap(String key) {
		List<BranchDto> listAllBranch = iSystemBp.listBranchByLevel(key);
		Map<Long, String> map = new HashMap<Long, String>();
		// map.put(0L, "SEMUA CABANG / KONSOLIDASI");
		for (BranchDto branchDto : listAllBranch) {
			map.put(branchDto.getId(),
					branchDto.getId() == 0 ? branchDto.getName() : branchDto
							.getCode() + " - " + branchDto.getName());
		}
		return map;
	}

	@Override
	public void removeCoa(long id) {
		iGlBp.removeCoa(id);
	}

	@Override
	public List<CoaDv> listCoaForTransaction(String key) {
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		for (Coa coa : iGlBp.listCoaForTransaction(key)) {
			returnList.add(createSimpleCoaDv(coa));
		}
		return returnList;
	}

	String getCoaDescription(long id) {
		Coa coa = iGlBp.getCoa(id);
		return coa == null ? "-" : (coa.getCode() + " - " + coa
				.getDescription());
	}

	@Override
	public ConfigDv loadConfig(String key) {
		ConfigDv config = new ConfigDv();
		ConfigDto dto = iSystemBp.getConfig(key, "finReport.lastPL");
		config.setLlr(dto == null ? 0 : dto.getLongValue());
		config.setStrLlr(getCoaDescription(config.getLlr()));
		//
		dto = iSystemBp.getConfig(key, "finReport.curPL");
		config.setClr(dto == null ? 0 : dto.getLongValue());
		config.setStrClr(getCoaDescription(config.getClr()));
		//
		dto = iSystemBp.getConfig(key, "finReport.reserve");
		config.setReserve(dto == null ? 0 : dto.getLongValue());
		config.setStrReserve(getCoaDescription(config.getReserve()));
		//
		dto = iSystemBp.getConfig(key, "finReport.tax");
		config.setTax(dto == null ? 0 : dto.getLongValue());
		config.setStrTax(getCoaDescription(config.getTax()));
		//
		dto = iSystemBp.getConfig(key, "finReport.zakat");
		config.setZakat(dto == null ? 0 : dto.getLongValue());
		config.setStrZakat(getCoaDescription(config.getZakat()));
		return config;
	}

	@Override
	public void saveConfig(String key, ConfigDv config) {
		ConfigDto dto = new ConfigDto();
		dto.setKey("finReport.lastPL");
		dto.setLongValue(config.getLlr());
		iSystemBp.saveConfig(key, dto);
		//
		dto = new ConfigDto();
		dto.setKey("finReport.curPL");
		dto.setLongValue(config.getClr());
		iSystemBp.saveConfig(key, dto);
		//
		dto = new ConfigDto();
		dto.setKey("finReport.reserve");
		dto.setLongValue(config.getReserve());
		iSystemBp.saveConfig(key, dto);
		//
		dto = new ConfigDto();
		dto.setKey("finReport.tax");
		dto.setLongValue(config.getTax());
		iSystemBp.saveConfig(key, dto);
		//
		dto = new ConfigDto();
		dto.setKey("finReport.zakat");
		dto.setLongValue(config.getZakat());
		iSystemBp.saveConfig(key, dto);
	}
}
