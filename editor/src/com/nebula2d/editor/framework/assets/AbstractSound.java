package com.nebula2d.editor.framework.assets;

public abstract class AbstractSound extends Asset {

    public AbstractSound(String path) {
        super(path);
    }

    public abstract void play();
    public abstract void stop();
    public abstract void setLoop(boolean loop);
    public abstract boolean isLooping();
}
