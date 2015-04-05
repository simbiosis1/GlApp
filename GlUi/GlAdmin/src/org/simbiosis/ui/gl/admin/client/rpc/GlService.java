package org.simbiosis.ui.gl.admin.client.rpc;

import java.util.List;

import org.simbiosis.ui.gl.admin.shared.CoaDv;
import org.simbiosis.ui.gl.admin.shared.ConfigDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("accounting")
public interface GlService extends RemoteService {

	CoaDv saveCoa(String key, CoaDv coaDv);

	void removeCoa(long id);

	List<CoaDv> listCoaByType(String key, Integer type);

	List<CoaDv> listCoaByParent(String key, Long parent);

	List<CoaDv> listCoaForTransaction(String key);

	ConfigDv loadConfig(String key);

	void saveConfig(String key, ConfigDv config);
}
