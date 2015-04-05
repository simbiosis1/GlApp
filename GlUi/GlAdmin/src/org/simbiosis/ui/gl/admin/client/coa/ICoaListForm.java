package org.simbiosis.ui.gl.admin.client.coa;

import java.util.List;
import java.util.Map;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.view.client.ListDataProvider;

public interface ICoaListForm {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void clearView();

	void showBackButton(boolean show);

	void editData();

	void viewData();

	void newData();

	void saveData(CoaDv coaDv, boolean isNew);

	void removeData(CoaDv coaDv);

	CoaDv getData();

	CoaDv getSelectedData();

	void initEditorWidget();

	List<SimpleBranchDv> getListBranch();

	ListDataProvider<CoaDv> getListDataProvider();

	void setCoaList(List<CoaDv> coaList);

	void setListBranch(List<SimpleBranchDv> listBranch);

	public abstract class Activity extends FormActivity {
		@SuppressWarnings("rawtypes")
		public abstract void listCoaByParent(Long parent,
				final ListDataProvider<CoaDv> dataProvider, final Map nodeMap);

	}

}
