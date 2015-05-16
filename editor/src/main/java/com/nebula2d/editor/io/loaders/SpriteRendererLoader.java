package com.nebula2d.editor.io.loaders;

import com.nebula2d.assets.SpriteSheet;
import com.nebula2d.components.Animation;
import com.nebula2d.components.Component;
import com.nebula2d.components.KeyFrameAnimation;
import com.nebula2d.components.SpriteRenderer;
import com.nebula2d.editor.io.FullBufferedReader;
import com.nebula2d.editor.io.Types;

import java.io.IOException;

public class SpriteRendererLoader extends BaseComponentLoader {

    @SuppressWarnings("unchecked")
    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setFilename(fr.readLine());
        if (spriteRenderer.getSprite() == null)
            throw new IOException("Can't find sprite with filename: " + spriteRenderer.getFilename());
        int numAnimations = fr.readIntLine();
        for (int i = 0; i < numAnimations; ++i) {
            Animation animation = null;
            Types.AnimationType type = Types.AnimationType.valueOf(fr.readLine());
            if (type == Types.AnimationType.KEY_FRAME) {
                String name = fr.readLine();
                int frameWidth = fr.readIntLine();
                int frameHeight = fr.readIntLine();
                SpriteSheet spriteSheet = new SpriteSheet(spriteRenderer.getSprite(), frameWidth, frameHeight);
                int startFrame = fr.readIntLine();
                int endFrame = fr.readIntLine();
                float speed = fr.readFloatLine();
                animation = new KeyFrameAnimation(name, spriteSheet, startFrame, endFrame, speed);
                ((KeyFrameAnimation) animation).setWrap(fr.readBooleanLine());
            }
            spriteRenderer.addAnimation(animation);
        }
        spriteRenderer.setCurrentAnimation(fr.readIntLine());
        return spriteRenderer;
    }
}
