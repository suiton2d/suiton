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

package com.suiton2d.avocado;

/**
 * Interface used as a callback when a file is saved.
 *
 * Created by bonazza on 4/12/14.
 */
public interface SaveListener {

    public void onSave(String content, String path);
}
