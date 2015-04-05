package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.gl.input.client.coatrans.CoaTransActivity;
import org.simbiosis.ui.gl.input.client.coatrans.CoaTransPlace;
import org.simbiosis.ui.gl.input.client.julist.JUListActivity;
import org.simbiosis.ui.gl.input.client.julist.JUListPlace;
import org.simbiosis.ui.gl.input.client.julistview.JUListViewActivity;
import org.simbiosis.ui.gl.input.client.julistview.JUListViewPlace;
import org.simbiosis.ui.gl.input.client.postlist.PostActivity;
import org.simbiosis.ui.gl.input.client.postlist.PostPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof JUListPlace) {
			return new JUListActivity((JUListPlace) place, clientFactory);
		} else if (place instanceof PostPlace) {
			return new PostActivity((PostPlace) place, clientFactory);
		} else if (place instanceof CoaTransPlace) {
			return new CoaTransActivity((CoaTransPlace) place, clientFactory);
		}else if (place instanceof JUListViewPlace) {
			return new JUListViewActivity((JUListViewPlace) place, clientFactory);
		}
		return null;
	}

}
