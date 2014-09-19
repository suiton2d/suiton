package com.nebula2d.editor.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * Created by bonazza on 8/27/14.
 */
public class N2DSettings {

    private GradleSettings gradleSettings = new GradleSettings();

    public void loadFromProperties() throws IOException {
        FileHandle propertiesFile = Gdx.files.internal("n2d.cfg");
        if (propertiesFile.exists()) {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(propertiesFile.file());
            properties.load(fis);


            gradleSettings.loadFromProperties(properties);
        }
    }

    public GradleSettings getGradleSettings() {
        return gradleSettings;
    }
}
