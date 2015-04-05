package org.simbiosis.ui.gl.input.client.julistview;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class JUListViewPlace extends Place {
	String token;

	public JUListViewPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<JUListViewPlace> {

		@Override
		public JUListViewPlace getPlace(String token) {
			return new JUListViewPlace(token);
		}

		@Override
		public String getToken(JUListViewPlace place) {
			return place.getToken();
		}
	}
}
