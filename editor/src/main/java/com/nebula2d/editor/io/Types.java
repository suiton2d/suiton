package com.nebula2d.editor.io;

/**
 * Created by Jon on 5/15/2015.
 */
public class Types {

    private Types() {}

    public enum ShapeType {
        BOX,
        CIRCLE
    }

    public enum ComponentType {
        RENDER,
        MUSIC,
        SFX,
        BEHAVE,
        RIGID_BODY,
        COLLIDER
    }
}
