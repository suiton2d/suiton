package com.suiton2d.editor.io.loaders;

import com.suiton2d.components.Component;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.GameObject;

import java.io.IOException;

public class GameObjectLoader implements Loader<GameObject> {

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
                    Types.RendererType rendererType = Types.RendererType.valueOf(fr.readLine());
                    switch (rendererType) {
                        case TILED:
                            loader = new TiledMapRendererLoader();
                            break;
                        case SPRITE:
                            loader = new SpriteRendererLoader();
                            break;
                    }
                    break;
                case MUSIC:
                    loader = new MusicSourceLoader();
                    break;
                case SFX:
                    loader = new SoundEffectSourceLoader();
                    break;
                case BEHAVE:
                    loader = new BehaviorLoader();
                    break;
                case RIGID_BODY:
                    loader = new RigidBodyLoader();
                    break;
                case COLLIDER:
                    loader = new ColliderLoader();
                    break;
            }

            gameObject.addComponent(loader.load(fr));
        }

        int numChildren = fr.readIntLine();
        for (int i = 0; i < numChildren; ++i) {
            GameObject child = new GameObjectLoader().load(fr);
            gameObject.addActor(child);
        }

        return gameObject;
    }
}
