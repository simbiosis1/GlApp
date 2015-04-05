package org.simbiosis.ui.gl.admin.client.report;

import org.simbiosis.ui.gl.admin.shared.ConfigDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ConfigViewer extends Composite implements Editor<ConfigDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, ConfigViewer> {
	}

	interface Driver extends SimpleBeanEditorDriver<ConfigDv, ConfigViewer> {
	}

	static Driver driver = GWT.create(Driver.class);

	@UiField
	Label strLlr;
	@UiField
	Label strClr;
	@UiField
	Label strReserve;
	@UiField
	Label strTax;
	@UiField
	Label strZakat;

	public ConfigViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		driver.edit(new ConfigDv());
	}

	void setData(ConfigDv config) {
		driver.edit(config);
	}

}
