package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.Scene;

/**
 *
 * Created by bonazza on 11/19/14.
 */
public interface BuildProgressUpdateListener {

    public void onBuildProgressUpdate(Scene scene, int idx, int size);

    public void onProjectCompiled();

    public void onBuildComplete();
}
