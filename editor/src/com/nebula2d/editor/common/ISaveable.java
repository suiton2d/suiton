package com.nebula2d.editor.common;

import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Interface used for output serialization
 */
public interface ISaveable {

    /**
     * save
     * @return whether or not the save was successful
     */
    public void save(FullBufferedWriter fw) throws IOException;
}
