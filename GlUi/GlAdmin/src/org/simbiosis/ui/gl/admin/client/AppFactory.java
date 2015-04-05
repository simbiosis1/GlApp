package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.gl.admin.client.coa.ICoaListForm;
import org.simbiosis.ui.gl.admin.client.report.IReportConfig;

public interface AppFactory extends KembangClientFactory {
	ICoaListForm getCoaListForm();

	IReportConfig getReportConfig();
}
