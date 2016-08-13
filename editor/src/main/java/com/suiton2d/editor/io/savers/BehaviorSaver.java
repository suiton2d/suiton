package com.suiton2d.editor.io.savers;

import com.suiton2d.components.behavior.Behavior;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class BehaviorSaver implements Saver {

    private Behavior behavior;

    public BehaviorSaver(Behavior behavior) {
        this.behavior = behavior;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.ComponentType.BEHAVE.name());
        fw.writeLine(behavior.getName());
        fw.writeLine(behavior.getFilename());
    }
}
