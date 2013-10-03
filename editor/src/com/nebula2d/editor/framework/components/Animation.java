package com.nebula2d.editor.framework.components;

import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;

public abstract class Animation implements ISaveable, ILoadable{

    //region members
    protected String name;
    //endregion

    //region constructor
    public Animation(String name) {
        this.name = name;
    }
    //endregion

    //region Accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    //region interface overrides
    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
    }
    //endregion

    @Override
    public String toString() {
        return name;
    }
}
