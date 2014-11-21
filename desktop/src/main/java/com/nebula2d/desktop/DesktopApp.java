package com.nebula2d.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by bonazza on 10/10/14.
 */
public class DesktopApp {

    public static void main(String[] args) {
        InputStream is = DesktopApp.class.getResourceAsStream("/meta.cfg");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = properties.getProperty("title", "Untitled");
        configuration.width = Integer.parseInt(properties.getProperty("width", "800"));
        configuration.height = Integer.parseInt(properties.getProperty("height", "600"));
        new LwjglApplication(new Game(), configuration);
    }
}
