package com.suiton2d.editor.io;

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

    public enum RendererType {
        TILED,
        SPRITE
    }

    public enum AnimationType {
        KEY_FRAME
    }
}
