package com.suiton2d.editor.io.savers;

import com.nebula2d.components.*;
import com.suiton2d.components.*;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public abstract class BaseComponentSaver<T extends Component> implements Saver {

    private T component;

    public BaseComponentSaver(T component) {
        this.component = component;
    }

    public T getComponent() {
        return component;
    }

    public abstract void onSave(FullBufferedWriter fw) throws IOException;

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        if (component instanceof AnimatedRenderer)
            fw.writeLine(Types.ComponentType.RENDER.name());
        else if (component instanceof MusicSource)
            fw.writeLine(Types.ComponentType.MUSIC.name());
        else if (component instanceof SoundEffectSource)
            fw.writeLine(Types.ComponentType.SFX.name());
        else if (component instanceof Behavior)
            fw.writeLine(Types.ComponentType.BEHAVE.name());
        else if (component instanceof RigidBody)
            fw.writeLine(Types.ComponentType.RIGID_BODY.name());
        else if (component instanceof Collider)
            fw.writeLine(Types.ComponentType.COLLIDER.name());

        fw.writeLine(component.getName());
        onSave(fw);
    }
}
