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

package com.nebula2d.editor.framework.assets;

import com.nebula2d.editor.common.ISerializable;
import com.nebula2d.editor.util.FullBufferedWriter;
import com.nebula2d.editor.util.PlatformUtil;

import java.io.File;
import java.io.IOException;

/**
 * Abstract base class for assets
 */
public abstract class Asset implements ISerializable {

    protected String path;
    protected String name;
    protected boolean isLoaded;

    public Asset(String path) {
        this.path = path;
        int slash = path.indexOf(File.pathSeparator);
        name = slash != -1 ? path.substring(slash + 1) : "";
        isLoaded = false;
    }

    public String getPath() {
        return this.path;
    }

    public String getBuildPath() {
        return PlatformUtil.pathJoin("assets", name);
    }

    public String getName() {
        return this.name;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public abstract void initialize();
    public abstract void dispose();

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(path);
    }
}
