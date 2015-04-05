package org.simbiosis.ui.gl.admin.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GlAdmin implements EntryPoint {
	
	AppEntryPoint appEntryPoint = new AppEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//
		//
		appEntryPoint.start();
	}

}
