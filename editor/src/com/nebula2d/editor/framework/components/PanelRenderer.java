package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Texture;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 8/25/13
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class PanelRenderer extends Renderer {

    public PanelRenderer(String name) {
        super(name);
    }

    public void setTexture(Texture tex) {
        this.texture = tex;
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher) {
        if (texture != null) {
            if (currentAnim == -1) {
                batcher.draw(texture.getTexture(), parent.getPosition().x, parent.getPosition().y);
            } else {
                KeyFrameAnimation anim = (KeyFrameAnimation)getCurrentAnimation();
                TextureRegion frame = anim.getFrames()[0];
                batcher.draw(frame, parent.getPosition().x, parent.getPosition().y);
            }
        }
    }
}
