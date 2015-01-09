package com.nebula2d.assets.loaders;


import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.nebula2d.assets.Script;
import com.nebula2d.util.ByteUtils;

public class ScriptLoader extends SynchronousAssetLoader<Script, ScriptLoader.ScriptParameter>{

    public ScriptLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Script load(AssetManager assetManager, String fileName, FileHandle file, ScriptParameter parameter) {
        String contents = ByteUtils.decodeBase64String(new String(file.readBytes()));
        return new Script(file.path(), contents);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ScriptParameter parameter) {
        return null;
    }

    public static class ScriptParameter extends AssetLoaderParameters<Script> {

    }
}
