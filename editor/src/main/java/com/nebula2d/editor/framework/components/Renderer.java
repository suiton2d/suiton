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
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Sprite;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Renderer extends Component {

    //region members
    protected Sprite sprite;

    protected List<Animation> animations;

    protected int currentAnim;
    //endregion

    //region constructor
    public Renderer(String name) {
        super(name);
        animations = new ArrayList<Animation>();
        currentAnim = -1;
    }
    //endregion

    //region Accessors
    public List<Animation> getAnimations() {
        return animations;
    }

    public void addAnimation(Animation anim) {
        animations.add(anim);
    }

    public void removeAnimation(Animation anim) {
        animations.remove(anim);
    }

    public Animation getAnimation(String name) {
        for (Animation anim : animations) {
            if (anim.getName().equals(name)) {
                return anim;
            }
        }

        return null;
    }

    public Animation getCurrentAnimation() {
        return currentAnim != -1 ? animations.get(currentAnim) : null;
    }

    public void setCurrentAnim(Animation anim) {
        int idx = 0;
        for (Animation a : animations) {
            if (a == anim) {
                currentAnim = idx;
                return;
            }

            idx++;
        }
    }

    public Sprite getTexture() {
        return sprite;
    }

    public int getBoundingWidth() {
        return sprite.getWidth();
    }

    public int getBoundingHeight() {
        return sprite.getHeight();
    }

    public Rectangle getBoundingBox() {
        if (sprite == null)
            return null;

        float x = parent.getPosition().x  -  (getBoundingWidth() / 2.0f);
        float y = parent.getPosition().y - (getBoundingHeight() / 2.0f);

        return new Rectangle(x, y, getBoundingWidth(), getBoundingHeight());
    }
    //endregion

    //region overridden methods from Component
    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        if (sprite == null) {
            fw.writeLine("0");
        } else {
            fw.writeLine("1");
            fw.writeLine(sprite.getPath());
        }

        fw.writeIntLine(currentAnim);
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        super.load(fr);

        int tmp = fr.readIntLine();

        if (tmp == 1) {
            sprite = new Sprite(fr.readLine());
        }

        currentAnim = fr.readIntLine();
    }
    //endregion

    public abstract void render(GameObject selectedObject, SpriteBatch batcher, Camera cam);
}
