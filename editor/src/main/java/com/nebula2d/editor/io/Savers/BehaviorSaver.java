package com.nebula2d.editor.io.savers;

import com.nebula2d.components.Behavior;
import com.nebula2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public class BehaviorSaver extends BaseComponentSaver<Behavior> {
    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(getComponent().getFilename());
    }
}
