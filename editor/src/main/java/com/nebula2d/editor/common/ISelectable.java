package com.nebula2d.editor.common;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public interface ISelectable extends IRenderable {


    public Rectangle getBoundingBox(Camera cam);

    public Color getColor();

    public boolean isMoveable();
}
