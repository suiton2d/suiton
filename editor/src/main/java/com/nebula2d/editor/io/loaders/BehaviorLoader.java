package com.nebula2d.editor.io.loaders;

import com.nebula2d.components.Behavior;
import com.nebula2d.components.Component;
import com.nebula2d.editor.io.FullBufferedReader;

import java.io.IOException;

public class BehaviorLoader extends BaseComponentLoader {

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        Behavior behavior = new Behavior();
        behavior.setFilename(fr.readLine());
        return behavior;
    }
}
