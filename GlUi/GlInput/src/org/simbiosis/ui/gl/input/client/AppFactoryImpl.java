package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.gl.input.client.coatrans.CoaTrans;
import org.simbiosis.ui.gl.input.client.coatrans.ICoaTrans;
import org.simbiosis.ui.gl.input.client.juinput.IJUForm;
import org.simbiosis.ui.gl.input.client.juinput.JUEditorImpl;
import org.simbiosis.ui.gl.input.client.juinput.JUViewerImpl;
import org.simbiosis.ui.gl.input.client.juinputview.IJUInputViewForm;
import org.simbiosis.ui.gl.input.client.juinputview.JUInputViewEditorImpl;
import org.simbiosis.ui.gl.input.client.juinputview.JUInputViewerImpl;
import org.simbiosis.ui.gl.input.client.julist.IJUList;
import org.simbiosis.ui.gl.input.client.julist.JUListForm;
import org.simbiosis.ui.gl.input.client.julistview.IJUListView;
import org.simbiosis.ui.gl.input.client.julistview.JUListViewForm;
import org.simbiosis.ui.gl.input.client.postlist.IPost;
import org.simbiosis.ui.gl.input.client.postlist.PostForm;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final JUListForm JU_LIST_FORM = new JUListForm();
	static final JUViewerImpl JU_VIEWER_IMPL = new JUViewerImpl();
	static final JUEditorImpl JU_EDITOR_IMPL = new JUEditorImpl();
	static final PostForm POST_FORM = new PostForm();
	static final CoaTrans COA_TRANS = new CoaTrans();
	static final JUListViewForm JU_LIST_VIEW_FORM = new JUListViewForm();
	static final JUInputViewerImpl JU_INPUT_VIEW_FORM = new JUInputViewerImpl();
	static final JUInputViewEditorImpl JU_INPUT_VIEW_EDITOR = new JUInputViewEditorImpl();

	@Override
	public IJUList getJUListForm() {
		return JU_LIST_FORM;
	}

	@Override
	public IJUForm getJUEditor() {
		return JU_EDITOR_IMPL;
	}

	@Override
	public IJUForm getJUViewer() {
		return JU_VIEWER_IMPL;
	}

	@Override
	public IPost getPostForm() {
		return POST_FORM;
	}

	@Override
	public ICoaTrans getCoaTrans() {
		return COA_TRANS;
	}

	@Override
	public IJUListView getJUListViewForm() {
		return JU_LIST_VIEW_FORM;
	}

	@Override
	public IJUInputViewForm getJUInputViewer() {
		return JU_INPUT_VIEW_FORM;
	}

	@Override
	public IJUInputViewForm getJUInputEditor() {
		return JU_INPUT_VIEW_EDITOR;
	}

}
