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

package com.nebula2d.components;

/**
 * Animation is an abstract base class for animations
 *
 * @author Jon Bonazza <jonbonazza@gmail.com></jonbonazza@gmail.com>
 */
public class Animation {

    protected String name;

    public Animation(String name) {
        this.name = name;
    }

    //region accessors
    public String getName() {
        return name;
    }


    //endregion

}
