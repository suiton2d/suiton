package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.scene.Scene;
import com.suiton2d.scene.SceneData;
import com.suiton2d.scene.loaders.SceneDataLoader;

import java.io.FileReader;
import java.io.IOException;

public class ProjectSceneDataLoader implements SceneDataLoader {

    private AssetManager assetManager;
    private World world;

    public ProjectSceneDataLoader(AssetManager assetManager, World world) {
        this.assetManager = assetManager;
        this.world = world;
    }

    @Override
    public SceneData loadSceneData(AssetManager assetManager, FileHandle fileHandle) throws IOException {
        SceneData sceneData;
        try (FullBufferedReader fbr = new FullBufferedReader(new FileReader(fileHandle.file()))) {
            String startScene = fbr.readLine();
            sceneData = new SceneData(startScene);
            int numScenes = fbr.readIntLine();
            SceneLoader sceneLoader = new SceneLoader(assetManager, world);
            for (int i = 0; i < numScenes; ++i) {
                Scene scene = sceneLoader.load(fbr);
                sceneData.put(scene.getName(), scene);
            }
        }
        return sceneData;
    }
}
