package de.cwclan.gdxtest.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.cwclan.gdxtest.core.GdxTest;

public class GdxTestDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
                config.vSyncEnabled = true;
                config.width=1280;
                config.height=720;
                config.title="todo";
                
		new LwjglApplication(new GdxTest(), config);
	}
}
