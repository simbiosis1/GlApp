package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.gl.admin.client.coa.CoaListForm;
import org.simbiosis.ui.gl.admin.client.coa.ICoaListForm;
import org.simbiosis.ui.gl.admin.client.report.IReportConfig;
import org.simbiosis.ui.gl.admin.client.report.ReportConfig;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final CoaListForm COA_LIST_FORM = new CoaListForm();
	static final ReportConfig REPORT_CONFIG = new ReportConfig();

	@Override
	public ICoaListForm getCoaListForm() {
		return COA_LIST_FORM;
	}

	@Override
	public IReportConfig getReportConfig() {
		return REPORT_CONFIG;
	}

}
