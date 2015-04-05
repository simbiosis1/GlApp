package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.gl.admin.client.coa.CoaListActivity;
import org.simbiosis.ui.gl.admin.client.places.CoaList;
import org.simbiosis.ui.gl.admin.client.places.ReportConfig;
import org.simbiosis.ui.gl.admin.client.report.ReportConfigActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof CoaList) {
			return new CoaListActivity((CoaList) place, clientFactory);
		} else if (place instanceof ReportConfig) {
			return new ReportConfigActivity((ReportConfig) place, clientFactory);
		}
		return null;
	}

}
