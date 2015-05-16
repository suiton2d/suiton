package com.nebula2d.editor.io.savers;

import com.nebula2d.components.Animation;
import com.nebula2d.components.KeyFrameAnimation;
import com.nebula2d.components.SpriteRenderer;
import com.nebula2d.editor.io.FullBufferedWriter;
import com.nebula2d.editor.io.Types;

import java.io.IOException;
import java.util.List;

public class SpriteRendererSaver extends BaseComponentSaver<SpriteRenderer> {

    public SpriteRendererSaver(SpriteRenderer component) {
        super(component);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.RendererType.SPRITE.name());
        fw.writeLine(getComponent().getFilename());
        fw.writeIntLine(getComponent().getAnimations().size());
        for (Animation anim : (List<Animation>) getComponent().getAnimations()) {
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

        fw.writeIntLine(getComponent().getCurrentAnimationIndex());
    }
}
