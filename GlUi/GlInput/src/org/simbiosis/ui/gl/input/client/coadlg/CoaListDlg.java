package org.simbiosis.ui.gl.input.client.coadlg;

import org.kembang.editor.client.KembangDialogBox;
import org.kembang.grid.client.AdvancedGrid;
import org.simbiosis.ui.gl.input.client.editor.CoaListTable;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.FindCoaDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class CoaListDlg extends KembangDialogBox implements Editor<FindCoaDv> {

	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, CoaListDlg> {
	}

	interface Driver extends SimpleBeanEditorDriver<FindCoaDv, CoaListDlg> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	CoaListTable resultTable;

	FindCoaDv findCoaDv;
	Widget table;
	int col;

	public CoaListDlg(FindCoaDv findCoaDv, Widget table, int col) {
		super();
		//
		setWidget(binder.createAndBindUi(this));
		setText("Daftar Coa");
		//
		Button btnSelect = new Button("Pilih");
		btnSelect.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onBtnSelect();
			}
		});
		addButton(btnSelect);
		//
		Button btnCancel = new Button("Batal");
		btnCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addButton(btnCancel);
		//
		this.findCoaDv = findCoaDv;
		this.table = table;
		this.col = col;
		//
		driver.initialize(this);
		driver.edit(findCoaDv);
	}

	@SuppressWarnings("rawtypes")
	void onBtnSelect() {
		CoaDv coaDv = resultTable.getSelectedData();
		String strCoa = coaDv.getCode() + " - " + coaDv.getDescription();
		((AdvancedGrid) table).setTextSuggest(col, strCoa);
		hide();
	}
}
