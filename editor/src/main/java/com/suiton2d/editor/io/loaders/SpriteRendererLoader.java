package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.Sprite;
import com.suiton2d.assets.SpriteSheet;
import com.suiton2d.assets.loaders.SpriteLoader;
import com.suiton2d.components.Animation;
import com.suiton2d.components.Component;
import com.suiton2d.components.KeyFrameAnimation;
import com.suiton2d.components.SpriteRenderer;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class SpriteRendererLoader implements Loader<Component> {

    private Scene scene;
    private String name;

    public SpriteRendererLoader(Scene scene, String name) {
        this.scene = scene;
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Component load(FullBufferedReader fr) throws IOException {
        String filename = fr.readLine();
        SpriteRenderer spriteRenderer = new SpriteRenderer(name, filename);
        SpriteLoader.SpriteParameter parameter = new SpriteLoader.SpriteParameter();
        parameter.textureParameter = new TextureLoader.TextureParameter();
        AssetManager.addAsset(scene.getName(), new AssetDescriptor<>(filename, Sprite.class, parameter));

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
        int currentAnimation = fr.readIntLine();
        if (currentAnimation >= 0)
            spriteRenderer.setCurrentAnimation(currentAnimation);
        return spriteRenderer;
    }
}
