package com.nebula2d.editor.common;

import com.badlogic.gdx.utils.XmlWriter;

import java.io.IOException;

/**
 * Interface for compiling objects to XML.
 *
 * Created by bonazza on 8/7/14.
 */
public interface IBuildable {

    public void build(XmlWriter sceneXml, XmlWriter assetsXml, String sceneName) throws IOException;
}
