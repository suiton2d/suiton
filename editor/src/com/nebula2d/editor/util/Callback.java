package com.nebula2d.editor.util;

public interface Callback<T, U> {

    public T execute(U arg);
}
