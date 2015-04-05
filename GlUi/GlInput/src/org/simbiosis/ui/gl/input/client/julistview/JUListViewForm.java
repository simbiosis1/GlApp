package org.simbiosis.ui.gl.input.client.julistview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.gl.input.client.editor.UserListBox;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class JUListViewForm extends FormWidget implements IJUListView {

	Activity activity;

	private static JUListFormImplUiBinder uiBinder = GWT
			.create(JUListFormImplUiBinder.class);

	interface JUListFormImplUiBinder extends UiBinder<Widget, JUListViewForm> {
	}

	@UiField
	DateBox beginDate;
	@UiField
	DateBox endDate;
	@UiField
	JUListViewEditor searchEditor;
	@UiField
	UserListBox user;

	CheckBox checkAll;

	List<UserDv> listUser = new ArrayList<UserDv>();

	public JUListViewForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasSearch(true);
		setHasExportPdf(true);
		//
		// init user

		//
		// init format batasTanggal
		beginDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		beginDate.setValue(new Date());
		endDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		endDate.setValue(new Date());
		// format table hasil pencarian
		checkAll = new CheckBox();
		checkAll.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onCheckAll();
			}
		});
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin()) {
			activity.initViewerEditor();
		}
	}

	@Override
	public void setUsers(List<UserDv> users) {
		listUser.clear();
		listUser.addAll(users);
		user.setList(listUser);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void clearView() {
		searchEditor.showData(new FindTransDv());
	}

	@Override
	public void showSearchResult(FindTransDv data) {
		searchEditor.showData(data);
	}

	@Override
	public GLTransDv getSelectedData() {
		return searchEditor.getSelectedData();
	}

	@Override
	public Long removeSelectedData() {
		return searchEditor.removeSelectedData();
	}

	@Override
	public void searchTransaction() {
		int status = 0;
		activity.searchTransaction(beginDate.getValue(), endDate.getValue(),
				status, user.getValue());
	}

	private void onCheckAll() {
		searchEditor.selectAll(checkAll.getValue());
	}

	@Override
	public void removeChecked() {
		searchEditor.removeChecked();
	}

	@Override
	public void exportReport() {
		Long id = user.getValue();
		String strUser = "user=";
		strUser += id.toString();
		String strBeginDate = "beginDate=" + beginDate.getTextBox().getText();
		String strEndDate = "endDate=" + endDate.getTextBox().getText();
		String viewer = "/BprsReportingService/getGlTransViewPdf?";
		if (strUser.equalsIgnoreCase("user=0")) {
			viewer = "/BprsReportingService/getGlTransViewAllPdf?";
		}
		Window.open(viewer + strBeginDate + "&" + strEndDate + "&" + strUser,
				"_blank", null);
	}

}
