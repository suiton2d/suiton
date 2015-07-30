package com.suiton2d.editor.util.builders;

import com.badlogic.gdx.utils.XmlWriter;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class SceneBuilder {

    private Scene scene;

    public SceneBuilder(Scene scene) {
        this.scene = scene;
    }

    public void build(XmlWriter sceneXml, XmlWriter assetsXml) throws IOException {
        sceneXml.element("scene").
                attribute("name", scene.getName());

        // TODO: Implement
    }
}
