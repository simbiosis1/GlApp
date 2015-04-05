package org.simbiosis.ui.gl.input.client.coatrans;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.gl.input.client.AppFactory;
import org.simbiosis.ui.gl.input.client.coatrans.ICoaTrans.Activity;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.FindTransItemDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class CoaTransActivity extends Activity {
	AppFactory appFactory;
	CoaTransPlace place;

	private final GlServiceAsync accSrv = GWT.create(GlService.class);
	private final SystemServiceAsync systemSrv = GWT
			.create(SystemService.class);

	public CoaTransActivity(CoaTransPlace place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ICoaTrans myForm = appFactory.getCoaTrans();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadBranch();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		case FA_EXPORTPDF:
			onExportPdf();
			break;
		default:
			break;
		}
	}

	private void onExportPdf() {
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
		ICoaTrans myForm = appFactory.getCoaTrans();
		Window.open(
				"/BprsReportingService/getGlTransPdf?branch="
						+ myForm.getBranch() + "&beginDate="
						+ fmt.format(myForm.getBeginDate()) + "&endDate="
						+ fmt.format(myForm.getEndDate()) + "&coa="
						+ myForm.getCoa(), "_blank", null);
	}

	private void onView() {
		showLoading();
		ICoaTrans myForm = appFactory.getCoaTrans();
		accSrv.listGLTransCoa(getKey(), myForm.getBranch(),
				myForm.getBeginDate(), myForm.getEndDate(), myForm.getCoa(),
				new AsyncCallback<List<GLTransItemDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listGLTransCoa");
					}

					@Override
					public void onSuccess(List<GLTransItemDv> result) {
						hideLoading();
						FindTransItemDv findDv = new FindTransItemDv();
						findDv.setItems(result);
						ICoaTrans myForm = appFactory.getCoaTrans();
						myForm.showSearchResult(findDv);
					}
				});
	}

	private void loadBranch() {
		showLoading();
		systemSrv.listSimpleBranch(getKey(), new AsyncCallback<List<SimpleBranchDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoaByType");
			}

			@Override
			public void onSuccess(List<SimpleBranchDv> result) {
				ICoaTrans myForm = appFactory.getCoaTrans();
				myForm.setListBranch(result);
				loadCoa();
			}
		});
	}

	private void loadCoa() {
		showLoading();
		accSrv.listCoaByLevel(getKey(),
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByType");
					}

					@Override
					public void onSuccess(List<CoaDv> result) {
						hideLoading();
						ICoaTrans myForm = appFactory.getCoaTrans();
						myForm.setListCoa(result);
					}
				});
	}
}
