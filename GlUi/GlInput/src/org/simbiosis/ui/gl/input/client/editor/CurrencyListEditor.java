package org.simbiosis.ui.gl.input.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.gl.input.shared.CurrencyDv;

public class CurrencyListEditor extends ListBoxListEditor<Long, CurrencyDv> {

	@Override
	public String convertItemId(CurrencyDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, CurrencyDv data) {
		return data.getId().compareTo(value);
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}
