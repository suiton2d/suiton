package com.nebula2d.editor.framework.components;

import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;

public abstract class Animation implements ISaveable, ILoadable{

    protected String name;

    public Animation(String name) {
        this.name = name;
    }

    //region Accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(name);
    }
}
