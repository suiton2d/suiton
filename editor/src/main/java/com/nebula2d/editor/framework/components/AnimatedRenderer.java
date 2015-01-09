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

import com.badlogic.gdx.utils.XmlWriter;
import com.nebula2d.editor.common.IRenderable;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AnimatedRenderer extends Renderer {

    protected List<Animation> animations;

    protected int currentAnim;

    public AnimatedRenderer(String name, RendererType rendererType) {
        super(name, rendererType);
        animations = new ArrayList<>();
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

    public abstract IRenderable getRenderable();

    @Override
    public int getBoundingWidth() {
        return currentAnim > -1 ? getCurrentAnimation().getBoundingWidth() : renderable.getBoundingWidth();
    }

    @Override
    public int getBoundingHeight() {
        return currentAnim > -1 ? getCurrentAnimation().getBoundingHeight() : renderable.getBoundingHeight();
    }

    @Override
    public boolean isReady() {
        return renderable != null && renderable.isReady();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        fw.writeIntLine(animations.size());
        for (Animation anim : animations)
            anim.save(fw);

        fw.writeIntLine(currentAnim);
    }

    @Override
    public void build(XmlWriter sceneXml, XmlWriter assetsXml, String sceneName) throws  IOException {
        super.build(sceneXml, assetsXml, sceneName);
        sceneXml.attribute("currentAnim", currentAnim);
        for (Animation anim : animations)
            anim.build(sceneXml, assetsXml, sceneName);
    }
}
