package com.suiton2d.editor.ui.build;

import com.suiton2d.scene.Scene;

/**
 *
 * Created by bonazza on 11/19/14.
 */
public interface BuildProgressUpdateListener {

    void onBuildProgressUpdate(Scene scene, int idx, int size);

    void onProjectCompiled();

    void onBuildComplete();
}
