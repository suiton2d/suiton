package com.nebula2d.editor.framework.assets;

import com.nebula2d.editor.util.FullBufferedReader;

import java.io.IOException;

public class SharedObject extends Asset {

    //region constructor
    public SharedObject(String path) {
        super(path);
    }
    //endregion

    //region interface overrides
    @Override
    public void load(FullBufferedReader fr) throws IOException {
        //Noop!
    }
    //endregion
}
