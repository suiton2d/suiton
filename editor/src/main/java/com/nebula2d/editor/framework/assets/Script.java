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

import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.*;

public class Script extends Asset {

    //region members
    protected String content;
    //endregion

    //region constructor
    public Script(String path) {
        super(path);
        init();
    }
    //endregion

    //region internal methods
    protected void init() {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            String tmp = "";
            while((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }

            content = sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file at path: " + path);
        } catch (IOException e) {
            System.out.println("Failed to read file at path: " + path);
        }
    }
    //endregion

    //region accessors
    public String getContent() {
        return this.content;
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        //Noop!
    }
    //endregion
}
