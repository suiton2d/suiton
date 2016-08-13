package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.MusicTrack;
import com.suiton2d.assets.loaders.MusicTrackLoader;
import com.suiton2d.components.audio.MusicSource;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class MusicSourceLoader implements Loader<MusicSource> {

    private Scene scene;
    private AssetManager assetManager;

    public MusicSourceLoader(Scene scene, AssetManager assetManager) {
        this.scene = scene;
        this.assetManager = assetManager;
    }

    @Override
    public MusicSource load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        String filename = fr.readLine();
        MusicTrackLoader.MusicTrackParameter parameter = new MusicTrackLoader.MusicTrackParameter();
        parameter.musicParameter = new MusicLoader.MusicParameter();
        assetManager.registerAsset(scene.getName(), new AssetDescriptor<>(filename, MusicTrack.class, parameter));
        return new MusicSource(name, filename, assetManager);
    }
}
