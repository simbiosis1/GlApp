package org.simbiosis.ui.gl.admin.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReportConfig extends Place {
	String token;

	public ReportConfig(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<ReportConfig> {

		@Override
		public ReportConfig getPlace(String token) {
			return new ReportConfig(token);
		}

		@Override
		public String getToken(ReportConfig place) {
			return place.getToken();
		}

	}
}
