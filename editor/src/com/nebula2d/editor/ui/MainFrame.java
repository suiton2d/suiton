package com.nebula2d.editor.ui;
import com.nebula2d.editor.framework.Project;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static RenderCanvas renderCanvas = new RenderCanvas(new RenderAdapter());
    private static SceneGraph sceneGraph = new SceneGraph();
    private static N2DMenuBar menuBar;
    private static Project project;

    public MainFrame() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JScrollPane sp = new JScrollPane(sceneGraph);
        sp.setPreferredSize(new Dimension(300, 600));

        getContentPane().add(sp, BorderLayout.WEST);
        getContentPane().add(renderCanvas.getCanvas());

        sceneGraph.setEnabled(false);
        renderCanvas.setEnabled(false);

        menuBar = new N2DMenuBar(this);
        setJMenuBar(menuBar);
        setSize(1200, 768);
        setVisible(true);
    }

    public static RenderCanvas getRenderCanvas() {
        return renderCanvas;
    }

    public static SceneGraph getSceneGraph() {
        return sceneGraph;
    }

    public static Project getProject() {
        return project;
    }

    public static void setProject(Project project) {
        MainFrame.project = project;
        sceneGraph.setEnabled(true);
        renderCanvas.setEnabled(true);
    }
}
