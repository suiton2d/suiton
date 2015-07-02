package com.suiton2d.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class ClassPathFileHandleResolver implements FileHandleResolver {

    @Override
    public FileHandle resolve(String fileName) {
        return Gdx.files.classpath(fileName);
    }
}
