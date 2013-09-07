package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.Layer;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 8/3/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame {

    private static RenderCanvas renderCanvas = new RenderCanvas(new RenderAdapter());
    private static SceneGraph sceneGraph = new SceneGraph();
    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JScrollPane sp = new JScrollPane(sceneGraph);
        sp.setPreferredSize(new Dimension(300, 600));
        //sp.add(sceneGraph);



        //horizontalPanel.add(renderCanvas.getCanvas());

        getContentPane().add(sp, BorderLayout.WEST);
        getContentPane().add(renderCanvas.getCanvas());

        setSize(1200, 768);
        setVisible(true);
        Layer l = new Layer("test");
        Layer l2 = new Layer("test2");
        GameObject go = new GameObject("testGo");
        l.addGameObject(go);
        sceneGraph.addLayer(l);
        sceneGraph.addLayer(l2);
        sceneGraph.addGameObject(l, go);
    }

    public static RenderCanvas getRenderCanvas() {
        return renderCanvas;
    }
}
