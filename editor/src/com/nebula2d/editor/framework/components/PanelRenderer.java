package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.ui.ImagePanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRenderer extends Renderer {

    //region constructor
    public PanelRenderer(String name) {
        super(name);
    }
    //endregion

    //region overrided methods from Renderer
    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        if (texture != null) {
            float halfw = getBoundingWidth() / 2.0f;
            float halfh = getBoundingHeight() / 2.0f;
            if (currentAnim == -1) {
                batcher.draw(new TextureRegion(texture.getTexture()),
                        parent.getPosition().x - halfw - cam.position.x,
                        parent.getPosition().y - halfh - cam.position.y,
                        halfw,
                        halfh,
                        texture.getTexture().getWidth(),
                        texture.getTexture().getHeight(),
                        parent.getScale().x,
                        parent.getScale().y,
                        parent.getRotation());

            } else {
                KeyFrameAnimation anim = (KeyFrameAnimation)getCurrentAnimation();
                TextureRegion frame = anim.getFrames()[0];
                batcher.draw(frame,
                        parent.getPosition().x - halfw - cam.position.x,
                        parent.getPosition().y - halfh - cam.position.y,
                        parent.getPosition().x - halfw - cam.position.x,
                        parent.getPosition().y - halfh - cam.position.y,
                        frame.getRegionWidth(),
                        frame.getRegionHeight(),
                        parent.getScale().x,
                        parent.getScale().y,
                        parent.getRotation());
            }
        }
    }
    //endregion
}
