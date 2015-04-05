package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class AppEntryPoint extends KembangEntryPoint {

	public AppEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		AppFactory clientFactory = GWT.create(AppFactory.class);
		setClientFactory(clientFactory);
		AppHistoryMapper historyMapper = GWT
				.create(AppHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setActivityMapper(new AppActivityMapper(clientFactory));
	}

}
