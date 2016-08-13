package com.suiton2d.editor.io.savers;

import com.badlogic.gdx.utils.Array;
import com.suiton2d.components.anim.Animation;
import com.suiton2d.components.anim.KeyFrameAnimation;
import com.suiton2d.components.gfx.SpriteRenderer;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class SpriteRendererSaver implements Saver {

    private SpriteRenderer spriteRenderer;

    public SpriteRendererSaver(SpriteRenderer spriteRenderer) {
        this.spriteRenderer = spriteRenderer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.ComponentType.RENDER.name());
        fw.writeLine(spriteRenderer.getName());
        fw.writeLine(Types.RendererType.SPRITE.name());
        fw.writeLine(spriteRenderer.getFilename());
        fw.writeIntLine(spriteRenderer.getAnimations().size);
        for (Animation anim : (Array<Animation>) spriteRenderer.getAnimations()) {
            if (anim instanceof KeyFrameAnimation) {
                fw.writeLine(Types.AnimationType.KEY_FRAME.name());
                KeyFrameAnimation keyFrameAnimation = (KeyFrameAnimation) anim;
                fw.writeLine(keyFrameAnimation.getName());
                fw.writeIntLine(keyFrameAnimation.getSpriteSheet().getFrameWidth());
                fw.writeIntLine(keyFrameAnimation.getSpriteSheet().getFrameHeight());
                fw.writeIntLine(keyFrameAnimation.getStartFrame());
                fw.writeIntLine(keyFrameAnimation.getEndFrame());
                fw.writeFloatLine(keyFrameAnimation.getSpeed());
                fw.writeBoolLine(keyFrameAnimation.isWrap());
            }
        }

        fw.writeIntLine(spriteRenderer.getCurrentAnimationIndex());
    }
}
