package com.nebula2d.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nebula2d.assets.Sprite;
import com.nebula2d.scene.Transform;

/**
 * SpriteRenderer is a class to render a {@link com.nebula2d.assets.Sprite} as a sprite.
 * SpriteRenderer can render a static sprite or an animated sprite.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SpriteRenderer extends Renderer {

    private Sprite sprite;

    public SpriteRenderer(String name, Sprite sprite) {
        super(name);
        this.sprite = sprite;
    }

    @Override
    public void render(Batch batch,  float dt) {
        Camera cam = gameObject.getLayer().getScene().getCamera();
        Transform transform = gameObject.getTransform();
        if (currentAnimation != null) {
            currentAnimation.render(transform, batch, cam, dt);
        } else {
            float halfw = sprite.getWidth() / 2.0f;
            float halfh = sprite.getHeight() / 2.0f;

            batch.draw(new TextureRegion(sprite.getTexture()),
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
