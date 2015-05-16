package com.nebula2d.editor.io.savers;

import com.nebula2d.components.*;
import com.nebula2d.editor.io.ComponentSaver;
import com.nebula2d.editor.io.ComponentType;
import com.nebula2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public abstract class BaseComponentSaver<T extends Component> implements ComponentSaver {

    private T component;

    public BaseComponentSaver() {}

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
            fw.writeLine(ComponentType.RENDER.name());
        else if (component instanceof MusicSource)
            fw.writeLine(ComponentType.MUSIC.name());
        else if (component instanceof SoundEffectSource)
            fw.writeLine(ComponentType.SFX.name());
        else if (component instanceof Behavior)
            fw.writeLine(ComponentType.BEHAVE.name());
        else if (component instanceof RigidBody)
            fw.writeLine(ComponentType.RIGID_BODY.name());
        else if (component instanceof Collider)
            fw.writeLine(ComponentType.COLLIDER.name());

        fw.writeLine(component.getName());

        onSave(fw);
    }
}
