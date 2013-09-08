package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Texture;

public class PanelRenderer extends Renderer {

    //region constructor
    public PanelRenderer(String name) {
        super(name);
    }
    //endregion

    //region overrided methods from Renderer
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
    //endregion
}
