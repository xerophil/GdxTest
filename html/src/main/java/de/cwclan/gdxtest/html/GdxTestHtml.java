package de.cwclan.gdxtest.html;

import de.cwclan.gdxtest.core.GdxTest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GdxTestHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new GdxTest();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
