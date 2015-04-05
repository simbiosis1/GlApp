package org.simbiosis.ui.gl.admin.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoaList extends Place {
	String token;

	public CoaList(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<CoaList> {

		@Override
		public CoaList getPlace(String token) {
			return new CoaList(token);
		}

		@Override
		public String getToken(CoaList place) {
			return place.getToken();
		}

	}
}
