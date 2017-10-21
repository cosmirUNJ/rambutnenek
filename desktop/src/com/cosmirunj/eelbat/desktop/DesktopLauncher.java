package com.cosmirunj.eelbat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cosmirunj.eelbat.EelbatCosmir;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = (int)(screenSize.getWidth()*0.7f);//800;
		config.height = config.width*EelbatCosmir.HEIGHT/EelbatCosmir.WIDTH;
		new LwjglApplication(new EelbatCosmir(), config);
	}
}
