package com.suiton2d.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.suiton2d.assets.MusicTrack;

public class MusicTrackLoader extends AsynchronousAssetLoader<MusicTrack, MusicTrackLoader.MusicTrackParameter> {

    private MusicLoader musicLoader;

    public MusicTrackLoader(FileHandleResolver resolver) {
        super(resolver);
        this.musicLoader = new MusicLoader(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, MusicTrackParameter parameter) {
        musicLoader.loadAsync(manager, fileName, file, parameter.musicParameter);
    }

    @Override
    public MusicTrack loadSync(AssetManager manager, String fileName, FileHandle file, MusicTrackParameter parameter) {
        Music music = musicLoader.loadSync(manager, fileName, file, parameter.musicParameter);
        return music != null ? new MusicTrack(file.path(), music) : null;
    }


    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, MusicTrackParameter parameter) {
        return null;
    }

    public static class MusicTrackParameter extends AssetLoaderParameters<MusicTrack> {
        public MusicLoader.MusicParameter musicParameter;
    }
}
