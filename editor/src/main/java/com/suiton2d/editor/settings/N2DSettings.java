package com.suiton2d.editor.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * Created by bonazza on 8/27/14.
 */
public class N2DSettings {

    private Properties properties = new Properties();
    private GradleSettings gradleSettings = new GradleSettings(properties);

    public void loadFromProperties() throws IOException {
        FileHandle propertiesFile = Gdx.files.internal("n2d.cfg");
        if (propertiesFile.exists()) {
            FileInputStream fis = new FileInputStream(propertiesFile.file());
            properties.load(fis);

            gradleSettings.loadFromProperties();
        }
    }

    public GradleSettings getGradleSettings() {
        return gradleSettings;
    }

    public void saveProperties() throws IOException {
        FileOutputStream fos = new FileOutputStream("n2d.cfg");
        properties.store(fos, null);
    }
}
