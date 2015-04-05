package org.simbiosis.ui.gl.admin.client.report;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.gl.admin.client.AppFactory;
import org.simbiosis.ui.gl.admin.client.places.ReportConfig;
import org.simbiosis.ui.gl.admin.client.report.IReportConfig.Activity;
import org.simbiosis.ui.gl.admin.client.rpc.GlService;
import org.simbiosis.ui.gl.admin.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.admin.shared.CoaDv;
import org.simbiosis.ui.gl.admin.shared.ConfigDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class ReportConfigActivity extends Activity {

	private final GlServiceAsync glSrv = GWT.create(GlService.class);
	// private final SystemServiceAsync systemSrv = GWT
	// .create(SystemService.class);

	AppFactory appFactory;
	ReportConfig place;

	public ReportConfigActivity(ReportConfig place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IReportConfig myForm = appFactory.getReportConfig();
		//
		if (appFactory.getAppStatus().isLogin()) {
			listCoaForTransaction();
		}
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onBack() {
		showLoading();
		loadConfig();
	}

	void onEdit() {
		IReportConfig myForm = appFactory.getReportConfig();
		myForm.editData();
	}

	void onSave() {
		showLoading();
		IReportConfig myForm = appFactory.getReportConfig();
		ConfigDv dv = myForm.getData();
		glSrv.saveConfig(getKey(), dv, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveConfig");
			}

			@Override
			public void onSuccess(Void data) {
				hideLoading();
				Window.alert("Data konfigurasi sudah tersimpan");
				//
				showLoading();
				loadConfig();
			}
		});
	}

	private void listCoaForTransaction() {
		showLoading();
		glSrv.listCoaForTransaction(getKey(), new AsyncCallback<List<CoaDv>>() {

			@Override
			public void onSuccess(List<CoaDv> result) {
				IReportConfig myForm = appFactory.getReportConfig();
				myForm.setCoaList(result);
				loadConfig();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoaForTransaction");
			}
		});
	}

	private void loadConfig() {
		glSrv.loadConfig(getKey(), new AsyncCallback<ConfigDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadConfig");
			}

			@Override
			public void onSuccess(ConfigDv result) {
				hideLoading();
				IReportConfig myForm = appFactory.getReportConfig();
				myForm.setData(result);
			}
		});
	}
}
