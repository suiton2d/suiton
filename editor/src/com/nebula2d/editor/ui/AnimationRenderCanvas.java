package com.nebula2d.editor.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.nebula2d.editor.framework.assets.Texture;

import java.awt.*;

public class AnimationRenderCanvas extends LwjglAWTCanvas {

    private AnimationRenderAdapter adapter;

    public AnimationRenderCanvas(AnimationRenderAdapter adapter) {
        super(adapter, true, MainFrame.getRenderCanvas());
        this.adapter = adapter;
        Texture tex = adapter.getAnimation().getTexture();
        getCanvas().setPreferredSize(new Dimension(tex.getWidth(), tex.getHeight()));
    }

    public void initCamera() {
        adapter.initCamera(getCanvas().getSize().width, getCanvas().getSize().height);
    }
}
