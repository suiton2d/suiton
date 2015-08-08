package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Component;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public abstract class BaseComponentLoader implements Loader<Component> {

    protected Scene scene;

    public BaseComponentLoader(Scene scene) {
        this.scene = scene;
    }

    public abstract Component onLoad(FullBufferedReader fr) throws IOException;

    @Override
    public Component load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        Component component = onLoad(fr);
        component.setName(name);
        return component;
    }
}
