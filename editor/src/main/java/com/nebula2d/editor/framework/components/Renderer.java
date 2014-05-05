/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.nebula2d.editor.common.IRenderable;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Sprite;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Renderer extends Component implements IRenderable {

    public static enum RendererType {
        SPRITE_RENDERER
    }

    protected Sprite sprite;

    protected List<Animation> animations;

    protected int currentAnim;

    protected RendererType rendererType;

    public Renderer(String name) {
        super(name);
        componentType = ComponentType.RENDER;
        animations = new ArrayList<Animation>();
        currentAnim = -1;
    }

    public List<Animation> getAnimations() {
        return animations;
    }

    public void addAnimation(Animation anim) {
        animations.add(anim);
    }

    public void removeAnimation(Animation anim) {
        animations.remove(anim);
    }

    public Animation getCurrentAnimation() {
        return currentAnim != -1 ? animations.get(currentAnim) : null;
    }

    public void setCurrentAnimation(Animation anim) {
        currentAnim = animations.indexOf(anim);
    }

    public Sprite getTexture() {
        return sprite;
    }

    public int getBoundingWidth() {
        return currentAnim > -1 ? getCurrentAnimation().getBoundingWidth() : sprite.getWidth();
    }

    public int getBoundingHeight() {
        return currentAnim > -1 ? getCurrentAnimation().getBoundingHeight() : sprite.getHeight();
    }

    public Rectangle getBoundingBox() {
        if (sprite == null)
            return null;

        float x = parent.getPosition().x  -  (getBoundingWidth() / 2.0f);
        float y = parent.getPosition().y - (getBoundingHeight() / 2.0f);

        return new Rectangle(x, y, getBoundingWidth(), getBoundingHeight());
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        fw.writeLine(rendererType.name());
        if (sprite == null) {
            fw.writeIntLine(0);
        } else {
            fw.writeIntLine(1);
            sprite.save(fw);
        }

        fw.writeIntLine(animations.size());
        for (Animation anim : animations)
            anim.save(fw);

        fw.writeIntLine(currentAnim);
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {

        int tmp = fr.readIntLine();

        if (tmp == 1) {
            System.out.println("creating sprite");
            sprite = new Sprite(fr.readLine());
            sprite.load(fr);
        }

        int size = fr.readIntLine();
        for (int i = 0; i < size; ++i) {
            String animName = fr.readLine();
            Animation.AnimationType animType = Animation.AnimationType.valueOf(fr.readLine());
            Animation animation = null;
            if (animType == Animation.AnimationType.KEY_FRAME)
                animation = new KeyFrameAnimation(animName, sprite);

            if (animation == null) {
                throw new IOException("Failed to load project.");
            }

            animation.load(fr);
            animations.add(animation);
        }
        currentAnim = fr.readIntLine();
    }
}
