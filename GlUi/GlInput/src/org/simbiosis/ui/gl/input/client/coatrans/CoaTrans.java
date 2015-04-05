package org.simbiosis.ui.gl.input.client.coatrans;

import java.util.Date;
import java.util.List;

import org.kembang.grid.client.FlexTableHelper;
import org.kembang.module.client.editor.BranchListBox;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.gl.input.client.editor.CoaListEditor;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.FindTransItemDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class CoaTrans extends FormWidget implements ICoaTrans {

	Activity activity;
	private static CoaTransUiBinder uiBinder = GWT
			.create(CoaTransUiBinder.class);

	interface CoaTransUiBinder extends UiBinder<Widget, CoaTrans> {
	}

	@UiField
	BranchListBox branch;
	@UiField
	CoaListEditor coa;
	@UiField
	DateBox beginDate;
	@UiField
	DateBox endDate;
	@UiField
	CoaTransEditor searchEditor;
	@UiField
	FlexTable footer;

	String[] footerText = { "", "", "Jumlah", "", "0", "0", "0" };
	String[] widthsText = { "30px", "130px", "130px", "", "150px", "150px",
			"150px" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");
	DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-yyyy");

	public CoaTrans() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		// init format batasTanggal
		beginDate.setFormat(new DateBox.DefaultFormat(dtf));
		beginDate.setValue(new Date());
		endDate.setFormat(new DateBox.DefaultFormat(dtf));
		endDate.setValue(new Date());
		//
		FlexTableHelper.setupHeader(footer, 7, footerText, widthsText);
		//
		setHasView(true);
		setHasExportPdf(true);
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
	public void showSearchResult(FindTransItemDv data) {
		searchEditor.showData(data);

		Double debit = 0.00, kredit = 0.00, saldo = 0.00;
		// Hitung debit kredit
		for (GLTransItemDv dataitem : data.getItems()) {
			debit += (dataitem.getDebit() == null) ? 0 : dataitem.getDebit();
			kredit += (dataitem.getCredit() == null) ? 0 : dataitem.getCredit();
			saldo = dataitem.getSaldo();
		}
		footer.getCellFormatter().setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(footer, 4, numberFormat.format(debit));
		//
		footer.getCellFormatter().setHorizontalAlignment(0, 5,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(footer, 5, numberFormat.format(kredit));
		//
		footer.getCellFormatter().setHorizontalAlignment(0, 6,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(footer, 6, numberFormat.format(saldo));
	}

	@Override
	public void setListCoa(List<CoaDv> listCoa) {
		coa.setList(listCoa);
	}

	@Override
	public void setListBranch(List<SimpleBranchDv> listBranch) {
		branch.setList(listBranch);
	}

	@Override
	public Date getBeginDate() {
		return beginDate.getValue();
	}

	@Override
	public Date getEndDate() {
		return endDate.getValue();
	}

	@Override
	public Long getCoa() {
		return coa.getValue();
	}

	@Override
	public Long getBranch() {
		return branch.getValue();
	}

}
