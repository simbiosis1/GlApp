package org.simbiosis.ui.gl.input.client.julistview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.gl.input.client.AppFactory;
import org.simbiosis.ui.gl.input.client.juinputview.IJUInputViewForm;
import org.simbiosis.ui.gl.input.client.juinputview.JUInputViewActivity;
import org.simbiosis.ui.gl.input.client.julistview.IJUListView.Activity;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class JUListViewActivity extends Activity {

	private final GlServiceAsync accSrv = GWT.create(GlService.class);

	AppFactory appFactory;
	JUListViewPlace place;
	JUInputViewActivity juActivity = null;

	public JUListViewActivity(JUListViewPlace place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	private JUInputViewActivity getJUActivity() {
		if (juActivity == null) {
			juActivity = new JUInputViewActivity(new JUListViewPlace(""),
					appFactory);
		}
		return juActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IJUListView juListForm = appFactory.getJUListViewForm();
		juListForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, juListForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		case FA_SEARCH:
			onSearch();
			break;
		case FA_EXPORTPDF:
			onExport();
			break;
		default:
			break;
		}
	}

	void onExport() {
		IJUListView listView = appFactory.getJUListViewForm();
		listView.exportReport();
	}

	private void loadCommonData() {

		showLoading();
		accSrv.listUsers(getKey(), new AsyncCallback<List<UserDv>>() {

			@Override
			public void onSuccess(List<UserDv> result) {
				IJUListView myForm = appFactory.getJUListViewForm();
				// inisialisasi all user
				List<UserDv> list = new ArrayList<UserDv>();
				UserDv dv = new UserDv();
				dv.setId(0L);
				dv.setRealName("SELURUHNYA");
				list.add(dv);
				list.addAll(result);
				myForm.setUsers(list);
				hideLoading();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listUser");
			}
		});

	}

	void onView() {
		showLoading();
		GLTransDv data = appFactory.getJUListViewForm().getSelectedData();
		getJUActivity().showData(data.getId());
	}

	void onSearch() {
		showLoading();
		appFactory.getJUListViewForm().searchTransaction();
	}

	@Override
	public void searchTransaction(Date begin, Date end, int status, long user) {
		accSrv.listGLTransByUser(getKey(), begin, end, status, user,
				new AsyncCallback<List<GLTransDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listGLTransByUser");
					}

					@Override
					public void onSuccess(List<GLTransDv> result) {
						FindTransDv searchResultDv = new FindTransDv();
						searchResultDv.getResultTable().addAll(result);
						appFactory.getJUListViewForm().showSearchResult(
								searchResultDv);
						hideLoading();
					}
				});
	}

	@Override
	void release(List<GLTransDv> data) {
		showLoading();
		List<Long> idList = new ArrayList<Long>();
		for (GLTransDv myData : data) {
			if (myData.getChecked()) {
				idList.add(myData.getId());
			}
		}
		accSrv.releaseGLTrans(getKey(), idList, true,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : releaseGLTrans");
					}

					@Override
					public void onSuccess(Void result) {
						appFactory.getJUListViewForm().removeChecked();
						hideLoading();
						Window.alert("Transaksi sudah di release");
					}
				});
	}

	@Override
	void initViewerEditor() {
		IJUInputViewForm juViewer = appFactory.getJUInputViewer();
		juViewer.setActivity(getJUActivity(), appFactory.getAppStatus());
		IJUInputViewForm juEditor = appFactory.getJUInputEditor();
		juEditor.setActivity(getJUActivity(), appFactory.getAppStatus());
		loadCommonData();
	}

}
