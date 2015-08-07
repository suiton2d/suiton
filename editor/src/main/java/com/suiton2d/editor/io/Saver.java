package com.suiton2d.editor.io;

import java.io.IOException;

public interface Saver {

    void save(FullBufferedWriter fw) throws IOException;
}
