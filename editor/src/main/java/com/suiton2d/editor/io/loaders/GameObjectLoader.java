package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.physics.box2d.World;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.components.Component;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class GameObjectLoader implements Loader<GameObject> {

    private Scene scene;
    private AssetManager assetManager;
    private World world;

    public GameObjectLoader(Scene scene, AssetManager assetManager, World world) {
        this.scene = scene;
        this.assetManager = assetManager;
        this. world = world;
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
            Loader<? extends Component> loader = null;
            Types.ComponentType componentType = Types.ComponentType.valueOf(fr.readLine());
            switch (componentType) {
                case RENDER:
                    loader = new RendererLoader(scene, assetManager);
                    break;
                case MUSIC:
                    loader = new MusicSourceLoader(scene, assetManager);
                    break;
                case SFX:
                    loader = new SoundEffectSourceLoader(scene, assetManager);
                    break;
                case BEHAVE:
                    loader = new BehaviorLoader(scene, assetManager);
                    break;
                case RIGID_BODY:
                    loader = new RigidBodyLoader(scene, world);
                    break;
                case COLLIDER:
                    loader = new ColliderLoader(scene, world);
                    break;
            }

            gameObject.addComponent(loader.load(fr));
        }

        int numChildren = fr.readIntLine();
        for (int i = 0; i < numChildren; ++i) {
            GameObject child = new GameObjectLoader(scene, assetManager, world).load(fr);
            gameObject.addActor(child);
        }

        return gameObject;
    }
}
