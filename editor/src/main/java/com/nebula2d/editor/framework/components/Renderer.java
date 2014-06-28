package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nebula2d.editor.common.IRenderable;
import com.nebula2d.editor.common.ISelectable;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public abstract class Renderer extends Component implements ISelectable {

    public static enum RendererType {
        SPRITE_RENDERER,
        TILE_MAP_RENDERER
    }

    protected IRenderable renderable;

    protected RendererType rendererType;

    public Renderer(String name, RendererType rendererType) {
        super(name);
        componentType = ComponentType.RENDER;
        this.rendererType = rendererType;
    }

    public boolean isReady() {
        return renderable != null && renderable.isReady();
    }

    public abstract IRenderable getRenderable();

    public abstract int getBoundingWidth();

    public abstract int getBoundingHeight();

    @Override
    public Rectangle getBoundingBox(Camera camera) {

        if (renderable == null)
            return null;

        float x = parent.getPosition().x  -  (getBoundingWidth() / 2.0f);
        float y = parent.getPosition().y - (getBoundingHeight() / 2.0f);

        Vector3 projection = camera.project(new Vector3(x, y, 0));
        float zoom = ((OrthographicCamera) camera).zoom;
//        System.out.println("gx: " + projection.x);
//        System.out.println("gy: " + projection.y);

//        float zoom = 1;
        return new Rectangle(projection.x, projection.y, getBoundingWidth()/zoom, getBoundingHeight()/zoom);
    }

    @Override
    public Color getColor() {
        return new Color(0.0f, 1.0f, 0.0f, 0.5f);
    }

    public abstract void render(GameObject selectedGameObejct, SpriteBatch batch, Camera cam);

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);
        fw.writeLine(rendererType.name());
        if (renderable == null) {
            fw.writeIntLine(0);
        } else {
            fw.writeIntLine(1);
            renderable.save(fw);
        }
    }
}
