package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Behavior;
import com.suiton2d.components.Component;
import com.suiton2d.editor.io.FullBufferedReader;

import java.io.IOException;

public class BehaviorLoader extends BaseComponentLoader {

    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        Behavior behavior = new Behavior();
        behavior.setFilename(fr.readLine());
        return behavior;
    }
}
