package com.nebula2d.editor.framework.assets;

import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Abstract base class for assets
 */
public abstract class Asset implements ISaveable, ILoadable{

    //region members
    protected String path;
    protected int type;
    protected String name;
    protected boolean isExtra;
    //endregion

    //region constructor
    public Asset(String path) {
        this.path = path;
        int slash = path.indexOf(File.pathSeparator);
        name = slash != -1 ? path.substring(slash + 1) : "";
    }
    //endregion

    //region accessors
    public String getPath() {
        return this.path;
    }

    public int getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public boolean isExtra() {
        return this.isExtra;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }
    //endregion

    //region interface overrides
    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(path);
        fw.writeIntLine(type);
    }
    //endregion
}
