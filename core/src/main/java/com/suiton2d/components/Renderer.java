package com.suiton2d.components;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Renderer {

    int getBoundingWidth();

    int getBoundingHeight();

    /**
     * Abstract method used for rendering.
     * @param batch The SpriteBatch instance to use for rendering.
     * @param dt The time since the last frame update.
     */
    void render(Batch batch, float dt);
}
