/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
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
import com.nebula2d.editor.common.IBuildable;
import com.nebula2d.editor.common.ISerializable;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class PhysicsMaterial implements ISerializable, IBuildable {

    private float density;
    private float friction;
    private float restitution;

    public float getDensity() {
        return density;
    }

    public float getFriction() {
        return friction;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        density = fr.readFloatLine();
        friction = fr.readFloatLine();
        restitution = fr.readFloatLine();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeFloatLine(density);
        fw.writeFloatLine(friction);
        fw.writeFloatLine(restitution);
    }

    @Override
    public void build(XmlWriter sceneXml, XmlWriter assetsXml, String sceneName) throws IOException {
        sceneXml.element("physicsMaterial").
                attribute("density", density).
                attribute("friction", friction).
                attribute("restitution", restitution);
        sceneXml.pop();
    }
}
