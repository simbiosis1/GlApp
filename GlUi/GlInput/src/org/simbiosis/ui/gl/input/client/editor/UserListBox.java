package org.simbiosis.ui.gl.input.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.kembang.module.shared.UserDv;

public class UserListBox extends ListBoxListEditor<Long, UserDv> {

	@Override
	public String convertItemId(UserDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, UserDv data) {
		return data.getId().compareTo(value);
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}
