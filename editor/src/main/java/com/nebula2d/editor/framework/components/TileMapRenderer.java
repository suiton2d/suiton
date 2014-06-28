package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.AssetManager;
import com.nebula2d.editor.framework.assets.TileSheet;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.ui.MainFrame;
import com.nebula2d.editor.ui.controls.N2DCheckBox;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FullBufferedReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TileMapRenderer extends Renderer {

    public TileMapRenderer(String name) {
        super(name, RendererType.TILE_MAP_RENDERER);
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        TileSheet tileSheet = (TileSheet) renderable;
        if (tileSheet != null && tileSheet.isLoaded())
            tileSheet.render(parent, batcher, cam);
    }

    @Override
    public boolean isReady() {
        return renderable != null && renderable.isReady();
    }

    @Override
    public TileSheet getRenderable() {
        return (TileSheet) renderable;
    }

    @Override
    public int getBoundingWidth() {
        return renderable != null ? renderable.getBoundingWidth() : 0;
    }

    @Override
    public int getBoundingHeight() {
        return renderable != null ? renderable.getBoundingHeight() : 0;
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        int tmp = fr.readIntLine();

        if (tmp == 1) {
            int currScene = MainFrame.getProject().getCurrentSceneIdx();
            String path = fr.readLine();

            TileSheet.TileSheetType type = TileSheet.TileSheetType.valueOf(fr.readLine());

            if (type == TileSheet.TileSheetType.TILED)
                renderable = AssetManager.getInstance().getOrCreateTiledTileSheet(currScene, path);

            if (renderable == null)
                throw new IOException("Failed to load project.");

            renderable.load(fr);
        }
    }

    @Override
    public N2DPanel forgeComponentContentPanel(final ComponentsDialog parent) {
        TileSheet tileSheet = (TileSheet) renderable;

        final JTextField tileSheetTf = new JTextField(20);

        if (tileSheet != null) {
            tileSheetTf.setText(tileSheet.getPath());
        }

        final JButton browseBtn = new JButton("...");
        browseBtn.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Select a file.");

            if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                final String path = fc.getSelectedFile().getAbsolutePath();
                tileSheetTf.setText(path);
                int currScene = MainFrame.getProject().getCurrentSceneIdx();
                TileMapRenderer.this.renderable = AssetManager.getInstance().
                        getOrCreateTiledTileSheet(currScene, path);
            }
        });

        final N2DLabel nameLbl = new N2DLabel("Name:");
        final JTextField nameTf = new JTextField(20);
        nameTf.setText(name);
        nameTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = nameTf.getText().trim();
                if (!text.equals("")) {
                    name = text;
                    parent.getComponentList().updateUI();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = nameTf.getText().trim();
                if (!text.equals("")) {
                    name = text;
                    parent.getComponentList().updateUI();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        final N2DCheckBox enabledCb = new N2DCheckBox("Enabled");
        enabledCb.setSelected(enabled);
        enabledCb.addChangeListener(e -> enabled = enabledCb.isSelected());
        N2DPanel topPanel = new N2DPanel();
        GroupLayout layout = new GroupLayout(topPanel);
        topPanel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameLbl).addComponent(enabledCb));
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameTf).addComponent(tileSheetTf));
        hGroup.addGroup(layout.createParallelGroup().addComponent(browseBtn));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLbl).
                addComponent(nameTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(tileSheetTf).
                addComponent(browseBtn));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb));
        layout.setVerticalGroup(vGroup);
        final N2DPanel bottomPanel = new N2DPanel(new BorderLayout());
        bottomPanel.add(Box.createRigidArea(new Dimension(100, 0)));

        final N2DPanel mainPanel = new N2DPanel(new BorderLayout());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel);
        return mainPanel;
    }

    @Override
    public boolean isMoveable() {
        return false;
    }
}
