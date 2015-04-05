package org.simbiosis.ui.gl.admin.client.report;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.admin.shared.CoaDv;
import org.simbiosis.ui.gl.admin.shared.ConfigDv;

public interface IReportConfig {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setCoaList(List<CoaDv> coaList);

	void setData(ConfigDv config);
	
	ConfigDv getData();
	
	void editData();
	
	public abstract class Activity extends FormActivity {
	}

}
