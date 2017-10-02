package com.untamedfox.ggj2017.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.untamedfox.ggj2017.GGJ2017;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        config.width = (int)(screenSize.getWidth()*0.7f);//800;
        config.height = config.width*GGJ2017.HEIGHT/GGJ2017.WIDTH;
		new LwjglApplication(new GGJ2017(), config);
	}
}
