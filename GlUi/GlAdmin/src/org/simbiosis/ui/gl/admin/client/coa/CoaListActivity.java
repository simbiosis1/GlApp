package org.simbiosis.ui.gl.admin.client.coa;

import java.util.List;
import java.util.Map;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.gl.admin.client.AppFactory;
import org.simbiosis.ui.gl.admin.client.coa.ICoaListForm.Activity;
import org.simbiosis.ui.gl.admin.client.places.CoaList;
import org.simbiosis.ui.gl.admin.client.rpc.GlService;
import org.simbiosis.ui.gl.admin.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.event.shared.EventBus;

public class CoaListActivity extends Activity {

	private final GlServiceAsync glSrv = GWT.create(GlService.class);
	private final SystemServiceAsync systemSrv = GWT
			.create(SystemService.class);

	AppFactory appFactory;
	CoaList place;

	public CoaListActivity(CoaList place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ICoaListForm myForm = appFactory.getCoaListForm();
		//
		if (appFactory.getAppStatus().isLogin()) {
			myForm.clearView();
			//
			loadBranch();
			//
			listRootCoa(myForm.getListDataProvider());
		}
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_DELETE:
			onDelete();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onBack() {
		ICoaListForm coaListForm = appFactory.getCoaListForm();
		coaListForm.showBackButton(false);
		coaListForm.viewData();
	}

	void onNew() {
		appFactory.getCoaListForm().newData();
	}

	void onEdit() {
		ICoaListForm coaListForm = appFactory.getCoaListForm();
		coaListForm.showBackButton(true);
		appFactory.getCoaListForm().editData();
	}

	void onSave() {
		showLoading();
		CoaDv coa = appFactory.getCoaListForm().getData();
		final boolean isNew = (coa.getId() == 0);
		glSrv.saveCoa(getKey(), coa, new AsyncCallback<CoaDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveCoa");
			}

			@Override
			public void onSuccess(CoaDv result) {
				appFactory.getCoaListForm().saveData(result, isNew);
				hideLoading();
				Window.alert("Data Coa sudah disimpan");
			}
		});
	}

	void onDelete() {
		showLoading();
		final CoaDv coaRemoved = appFactory.getCoaListForm().getSelectedData();
		glSrv.removeCoa(coaRemoved.getId(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : removeCoa");
			}

			@Override
			public void onSuccess(Void result) {
				appFactory.getCoaListForm().removeData(coaRemoved);
				hideLoading();
				Window.alert("Data sudah dihapus");
			}
		});
	}

	private void listRootCoa(final ListDataProvider<CoaDv> dataProvider) {
		showLoading();
		glSrv.listCoaByParent(getKey(), 0L, new AsyncCallback<List<CoaDv>>() {

			@Override
			public void onSuccess(List<CoaDv> result) {
				for (CoaDv coa : result) {
					dataProvider.getList().add(coa);
				}
				listCoaByType(1);
				//
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoaByParent");
			}
		});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void listCoaByParent(Long parent,
			final ListDataProvider<CoaDv> dataProvider, final Map nodeMap) {
		showLoading();
		glSrv.listCoaByParent(getKey(), parent,
				new AsyncCallback<List<CoaDv>>() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(List<CoaDv> result) {
						for (CoaDv coa : result) {
							ListDataProvider<CoaDv> exist = (ListDataProvider<CoaDv>) nodeMap
									.get(coa.getParent());
							if (exist == null) {
								nodeMap.put(coa.getParent(), dataProvider);
							}
							dataProvider.getList().add(coa);
						}
						hideLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByParent");
					}
				});
	}

	private void listCoaByType(Integer type) {
		showLoading();
		glSrv.listCoaByType(getKey(), type, new AsyncCallback<List<CoaDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoaByType");
			}

			@Override
			public void onSuccess(List<CoaDv> result) {
				ICoaListForm myForm = appFactory.getCoaListForm();
				myForm.setCoaList(result);
				myForm.initEditorWidget();
				hideLoading();
			}
		});
	}

	private void loadBranch() {
		systemSrv.listSimpleBranch(getKey(), new AsyncCallback<List<SimpleBranchDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listBranch");
			}

			@Override
			public void onSuccess(List<SimpleBranchDv> result) {
				appFactory.getCoaListForm().setListBranch(result);
			}
		});
	}
}
