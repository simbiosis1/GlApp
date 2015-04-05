package org.simbiosis.ui.gl.admin.client.report;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.admin.shared.CoaDv;
import org.simbiosis.ui.gl.admin.shared.ConfigDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReportConfig extends FormWidget implements IReportConfig {

	Activity activity;
	ConfigViewer viewer = new ConfigViewer();
	ConfigEditor editor = new ConfigEditor();

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, ReportConfig> {
	}

	List<CoaDv> coaList = new ArrayList<CoaDv>();
	ConfigDv config = new ConfigDv();

	@UiField
	VerticalPanel panel;

	public ReportConfig() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasEdit(true);
		setHasSave(false);
		setHasBack(false);
		//
		panel.add(viewer);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void setCoaList(List<CoaDv> coaList) {
		this.coaList.addAll(coaList);
		editor.setCoaList(coaList);
	}

	@Override
	public void setData(ConfigDv config) {
		this.config = config;
		saveData();
	}

	@Override
	public ConfigDv getData() {
		config = editor.getData();
		return config;
	}

	@Override
	public void editData() {
		//
		showEdit(false);
		showSave(true);
		showBack(true);
		//
		editor.setData(config);
		panel.clear();
		panel.add(editor);
	}

	private void saveData() {
		//
		showEdit(true);
		showSave(false);
		showBack(false);
		//
		viewer.setData(config);
		panel.clear();
		panel.add(viewer);
	}

}
