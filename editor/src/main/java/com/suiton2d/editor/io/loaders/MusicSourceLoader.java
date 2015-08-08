package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.MusicTrack;
import com.suiton2d.assets.loaders.MusicTrackLoader;
import com.suiton2d.components.Component;
import com.suiton2d.components.MusicSource;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class MusicSourceLoader extends BaseComponentLoader {

    public MusicSourceLoader(Scene scene) {
        super(scene);
    }

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        MusicSource musicSource = new MusicSource();
        String filename = fr.readLine();
        musicSource.setFilename(filename);
        MusicTrackLoader.MusicTrackParameter parameter = new MusicTrackLoader.MusicTrackParameter();
        parameter.musicParameter = new MusicLoader.MusicParameter();
        AssetManager.addAsset(scene.getName(), new AssetDescriptor<>(filename, MusicTrack.class, parameter));
        return musicSource;
    }
}
