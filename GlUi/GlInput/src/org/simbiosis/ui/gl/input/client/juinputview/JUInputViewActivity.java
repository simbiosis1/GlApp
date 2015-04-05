package org.simbiosis.ui.gl.input.client.juinputview;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.gl.input.client.AppFactory;
import org.simbiosis.ui.gl.input.client.juinputview.IJUInputViewForm.Activity;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class JUInputViewActivity extends Activity {

	private final GlServiceAsync glSrv = GWT.create(GlService.class);

	AppFactory appFactory;
	Place place;
	Activity activity;

	List<CoaDv> listCoa = new ArrayList<CoaDv>();

	public JUInputViewActivity(Place place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
		this.activity = this;
	}

	public void showData(long id) {
		glSrv.getGLTrans(id, new AsyncCallback<GLTransDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getGLTrans");
			}

			@Override
			public void onSuccess(GLTransDv result) {
				hideLoading();
				IJUInputViewForm juViewer = appFactory.getJUInputViewer();
				juViewer.editData(result);
				appFactory.showApplication(null, juViewer.getFormWidget());
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	void onBack() {
		appFactory.getPlaceController().goTo(place);
	}

	@Override
	public void loadCoa() {
		showLoading();
		glSrv.listCoaForTransaction(getKey(), new AsyncCallback<List<CoaDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoaByType");
			}

			@Override
			public void onSuccess(List<CoaDv> result) {
				hideLoading();
				IJUInputViewForm juEditor = appFactory.getJUInputEditor();
				juEditor.setListCoa(result);
			}
		});
	}

}
