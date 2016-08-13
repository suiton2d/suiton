package com.suiton2d.editor.io;

import java.io.IOException;

public interface Loader<T> {
    T load(FullBufferedReader fr) throws IOException;
}
