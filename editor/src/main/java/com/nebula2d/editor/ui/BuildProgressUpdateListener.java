package com.nebula2d.editor.ui;

import com.nebula2d.scene.Scene;

/**
 *
 * Created by bonazza on 11/19/14.
 */
public interface BuildProgressUpdateListener {

    void onBuildProgressUpdate(Scene scene, int idx, int size);

    void onProjectCompiled();

    void onBuildComplete();
}
