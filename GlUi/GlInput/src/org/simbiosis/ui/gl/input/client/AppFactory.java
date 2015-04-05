package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.gl.input.client.coatrans.ICoaTrans;
import org.simbiosis.ui.gl.input.client.juinput.IJUForm;
import org.simbiosis.ui.gl.input.client.juinputview.IJUInputViewForm;
import org.simbiosis.ui.gl.input.client.julist.IJUList;
import org.simbiosis.ui.gl.input.client.julistview.IJUListView;
import org.simbiosis.ui.gl.input.client.postlist.IPost;

public interface AppFactory extends KembangClientFactory {

	IJUList getJUListForm();

	IJUForm getJUEditor();

	IJUForm getJUViewer();

	IPost getPostForm();

	ICoaTrans getCoaTrans();

	IJUListView getJUListViewForm();

	IJUInputViewForm getJUInputViewer();

	IJUInputViewForm getJUInputEditor();
}
