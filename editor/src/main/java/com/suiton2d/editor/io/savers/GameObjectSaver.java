package com.suiton2d.editor.io.savers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.suiton2d.components.*;
import com.suiton2d.components.audio.MusicSource;
import com.suiton2d.components.audio.SoundEffectSource;
import com.suiton2d.components.behavior.Behavior;
import com.suiton2d.components.gfx.SpriteRenderer;
import com.suiton2d.components.gfx.TiledMapRenderer;
import com.suiton2d.components.physics.Collider;
import com.suiton2d.components.physics.RigidBody;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.scene.GameObject;

import java.io.IOException;

public class GameObjectSaver implements Saver {

    private GameObject gameObject;

    public GameObjectSaver(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(gameObject.getName());
        fw.writeFloatLine(gameObject.getX());
        fw.writeFloatLine(gameObject.getY());
        fw.writeFloatLine(gameObject.getScaleX());
        fw.writeFloatLine(gameObject.getScaleY());
        fw.writeFloatLine(gameObject.getRotation());
        fw.writeIntLine(gameObject.getComponents().size);
        for (Component component : gameObject.getComponents()) {
            if (component instanceof TiledMapRenderer)
                new TiledMapRendererSaver((TiledMapRenderer) component).save(fw);
            else if (component instanceof SpriteRenderer)
                new SpriteRendererSaver((SpriteRenderer) component).save(fw);
            else if (component instanceof MusicSource)
                new MusicSourceSaver((MusicSource) component).save(fw);
            else if (component instanceof SoundEffectSource)
                new SoundEffectSourceSaver((SoundEffectSource) component).save(fw);
            else if (component instanceof Behavior)
                new BehaviorSaver((Behavior) component).save(fw);
            else if (component instanceof RigidBody)
                new RigidBodySaver((RigidBody) component).save(fw);
            else if (component instanceof Collider)
                new ColliderSaver((Collider) component).save(fw);
        }

        fw.writeIntLine(gameObject.getChildren().size);
        for (Actor child : gameObject.getChildren())
            new GameObjectSaver((GameObject) child).save(fw);
    }
}
