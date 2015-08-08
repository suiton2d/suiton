package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Component;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class GameObjectLoader implements Loader<GameObject> {

    private Scene scene;

    public GameObjectLoader(Scene scene) {
        this.scene = scene;
    }

    @Override
    public GameObject load(FullBufferedReader fr) throws IOException {
        GameObject gameObject = new GameObject(fr.readLine());
        gameObject.setX(fr.readFloatLine());
        gameObject.setY(fr.readFloatLine());
        gameObject.setScaleX(fr.readFloatLine());
        gameObject.setScaleY(fr.readFloatLine());
        gameObject.setRotation(fr.readFloatLine());
        int numComponents = fr.readIntLine();
        for (int i = 0; i < numComponents; ++i) {
            Loader<Component> loader = null;
            Types.ComponentType componentType = Types.ComponentType.valueOf(fr.readLine());
            switch (componentType) {
                case RENDER:
                    loader = new RendererLoader(scene);
                    break;
                case MUSIC:
                    loader = new MusicSourceLoader(scene);
                    break;
                case SFX:
                    loader = new SoundEffectSourceLoader(scene);
                    break;
                case BEHAVE:
                    loader = new BehaviorLoader(scene);
                    break;
                case RIGID_BODY:
                    loader = new RigidBodyLoader(scene);
                    break;
                case COLLIDER:
                    loader = new ColliderLoader(scene);
                    break;
            }

            gameObject.addComponent(loader.load(fr));
        }

        int numChildren = fr.readIntLine();
        for (int i = 0; i < numChildren; ++i) {
            GameObject child = new GameObjectLoader(scene).load(fr);
            gameObject.addActor(child);
        }

        return gameObject;
    }
}
