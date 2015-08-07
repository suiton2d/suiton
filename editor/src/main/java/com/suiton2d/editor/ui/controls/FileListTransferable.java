package com.suiton2d.editor.ui.controls;

import com.badlogic.gdx.files.FileHandle;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileListTransferable implements Transferable {

    private List<FileHandle> fileList;

    public FileListTransferable(FileHandle... fileHandles) {
        fileList = Arrays.asList(fileHandles);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] {DataFlavor.javaFileListFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(DataFlavor.javaFileListFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return isDataFlavorSupported(flavor) ? fileList : null;
    }
}
