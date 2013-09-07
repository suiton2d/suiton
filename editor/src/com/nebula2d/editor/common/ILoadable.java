package com.nebula2d.editor.common;

import com.nebula2d.editor.util.FullBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interface used for input serialization
 */
public interface ILoadable {

    public void load(FullBufferedReader fr) throws IOException;
}
