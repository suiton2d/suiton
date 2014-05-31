package com.nebula2d.editor.framework.assets;

import com.nebula2d.editor.framework.Scene;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetManager is a class for the management of {@link com.nebula2d.editor.framework.assets.Asset}s.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public final class AssetManager {

    private static AssetManager instance;

    private int currentSceneIndex;

    private Map<Integer, Map<String, Asset>> assetMap;

    private AssetManager() {
        assetMap = new HashMap<Integer, Map<String, Asset>>();
    }

    public static synchronized AssetManager getInstance() {
        if (instance == null)
            instance = new AssetManager();

        return instance;
    }

    private <T> T getAsset(int sceneIdx, String name, Class<? extends T> clazz) {
        Map<String, Asset> assets = assetMap.get(sceneIdx);

        if (assets == null)
            return null;

        Asset asset = assets.get(name);

        return asset != null ? clazz.cast(asset) : null;
    }

    public Sprite getOrCreateSprite(int sceneIdx, String path) {
        Sprite sprite = getAsset(sceneIdx, path, Sprite.class);

        return sprite != null ? sprite : (Sprite) addAsset(sceneIdx, new Sprite(path));
    }

    public MusicTrack getOrCreateMusicTrack(int sceneIdx, String path) {
        MusicTrack track = getAsset(sceneIdx, path, MusicTrack.class);
        return track != null ? track : (MusicTrack) addAsset(sceneIdx, new MusicTrack(path));
    }

    public SoundEffect getOrCreateSoundEffect(int sceneIdx, String path) {
        SoundEffect soundEffect =  getAsset(sceneIdx, path, SoundEffect.class);
        return soundEffect != null ? soundEffect : (SoundEffect) addAsset(sceneIdx, new SoundEffect(path));
    }

    public Script getOrCreateScript(int sceneIdx, String path) {
        Script script = getAsset(sceneIdx, path, Script.class);
        return script != null ? script : (Script) addAsset(sceneIdx, new Script(path));
    }

    public Asset addAsset(int sceneIdx, Asset asset) {
        Map<String, Asset> assets = assetMap.get(sceneIdx);

        if (assets == null) {
            assets = new HashMap<String, Asset>();
            assetMap.put(sceneIdx, assets);
        }

        if (assets.containsKey(asset.getPath()))
            return assets.get(asset.getPath());

        assets.put(asset.getPath(), asset);
        asset.initialize();
        return asset;
    }

    public void removeAsset(Scene scene, Asset asset) {
        Map<String, Asset> assets = assetMap.get(scene.getId());

        if (assets == null)
            return;

        assets.remove(asset.getPath());
    }

    public void changeScene(int sceneIdx) {
        dispose(currentSceneIndex);
        initialize(sceneIdx);
        currentSceneIndex = sceneIdx;
    }

    private void dispose(int sceneIdx) {
        Map<String, Asset> assets = assetMap.get(sceneIdx);

        if (assets == null)
            return;

        for (Map.Entry<String, Asset> entry : assets.entrySet())
            entry.getValue().dispose();
    }

    private void initialize(int sceneIdx) {
        Map<String, Asset> assets = assetMap.get(sceneIdx);
        if (assets == null)
            return;

        for (Map.Entry<String, Asset> entry : assets.entrySet())
            entry.getValue().initialize();
    }

    public void cleanup() {
        dispose(currentSceneIndex);
    }
}
