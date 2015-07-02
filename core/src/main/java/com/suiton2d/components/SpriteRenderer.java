package com.suiton2d.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.Sprite;
import com.suiton2d.scene.Transform;

/**
 * SpriteRenderer is a class to render a {@link Sprite} as a sprite.
 * SpriteRenderer can render a static sprite or an animated sprite.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SpriteRenderer<T extends Animation> extends AnimatedRenderer<T> {

    private String filename;

    public SpriteRenderer() {}

    public SpriteRenderer(String name, String filename) {
        super(name);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Sprite getSprite() {
        return AssetManager.getAsset(filename, Sprite.class);
    }

    @Override
    public int getBoundingWidth() {
        return getSprite() != null ? getSprite().getWidth() : 0;
    }

    @Override
    public int getBoundingHeight() {
        return getSprite() != null ? getSprite().getHeight() : 0;
    }

    @Override
    public void render(Batch batch,  float dt) {
        Camera cam = gameObject.getLayer().getScene().getCamera();
        Transform transform = new Transform(gameObject);
        if (currentAnimation != null) {
            currentAnimation.render(transform, batch, cam, dt);
        } else {
            Sprite sprite = getSprite();
            float halfw = sprite.getWidth() / 2.0f;
            float halfh = sprite.getHeight() / 2.0f;

            batch.draw(new TextureRegion(sprite.getData()),
                    transform.getPosition().x - halfw - cam.position.x,
                    transform.getPosition().y - halfh - cam.position.y,
                    transform.getPosition().x - halfw - cam.position.x,
                    transform.getPosition().y - halfh - cam.position.y,
                    sprite.getWidth(),
                    sprite.getHeight(),
                    transform.getScale().x,
                    transform.getScale().y,
                    transform.getRotation());
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }
}
