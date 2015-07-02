package com.suiton2d.editor.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.suiton2d.scene.GameObject;

public class Selection {

    private GameObject gameObject;

    public Selection(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void renderSelection(Camera camera) {
        if (gameObject == null || gameObject.getRenderer() == null)
            return;

        Rectangle boundingBox = getBoundingBox(camera);

        if (boundingBox != null) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            ShapeRenderer shape = new ShapeRenderer();
            shape.setColor(new Color(0.0f, 1.0f, 0.0f, 0.5f));
            shape.begin(ShapeRenderer.ShapeType.Filled);

            float x = boundingBox.getX();
            float y = boundingBox.getY();
            shape.rect(x, y, boundingBox.getWidth(), boundingBox.getHeight());

            shape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public Rectangle getBoundingBox(Camera camera) {
        float x = gameObject.getX()  -  (gameObject.getRenderer().getBoundingWidth() / 2.0f);
        float y = gameObject.getY() - (gameObject.getRenderer().getBoundingHeight() / 2.0f);

        Vector3 projection = camera.project(new Vector3(x, y, 0));
        float zoom = ((OrthographicCamera) camera).zoom;

        return new Rectangle(projection.x, projection.y, gameObject.getRenderer().getBoundingWidth()/zoom,
                gameObject.getRenderer().getBoundingHeight()/zoom);
    }
}
