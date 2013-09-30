package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.GameObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

/**
 * custom toolbar implementation
 */
public class N2DToolbar extends JToolBar {
    public static final int RENDERER_WIDGET_TRANSLATE = 0;
    public static final int RENDERER_WIDGET_SCALE = 1;
    public static final int RENDERER_WIDGET_ROTATE = 2;

    private int selectedRendererWidget;
    private ButtonGroup renderWidgets;
    private JButton componentsButton;

    public N2DToolbar() {
        selectedRendererWidget = 0;
        addButtons();
        setRendererWidgetsEnabled(false);
    }

    //region accessors
    public int getSelectedRendererWidget() {
        return selectedRendererWidget;
    }

    public JButton getComponentsButton() {
        return componentsButton;
    }
    //endregion

    private void addButtons() {
        renderWidgets = forgeRendererWidgetButtons();

        Enumeration buttons = renderWidgets.getElements();

        while(buttons.hasMoreElements()) {
            add((JToggleButton)buttons.nextElement());
        }
        addSeparator();

        componentsButton = forgeComponentButton();
        add(componentsButton);
    }

    private JButton forgeComponentButton() {
        JButton componentsButton = new JButton("Components");
        componentsButton.setEnabled(false);


        componentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameObject selectedGameObject = (GameObject) MainFrame.getSceneGraph().
                        getSelectionPath().getLastPathComponent();
                new ComponentsDialog(selectedGameObject);
            }
        });
        return componentsButton;
    }

    private ButtonGroup forgeRendererWidgetButtons() {
        ButtonGroup group = new ButtonGroup();
        JToggleButton translateWidget = new JToggleButton("Translate");
        translateWidget.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedRendererWidget = RENDERER_WIDGET_TRANSLATE;
            }
        });
        translateWidget.setSelected(true);
        JToggleButton scaleWidget = new JToggleButton("Scale");
        scaleWidget.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedRendererWidget = RENDERER_WIDGET_SCALE;
            }
        });
        JToggleButton rotateWidget = new JToggleButton("Rotate");
        rotateWidget.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedRendererWidget = RENDERER_WIDGET_ROTATE;
            }
        });
        group.add(translateWidget);
        group.add(scaleWidget);
        group.add(rotateWidget);

        return group;
    }

    public void setRendererWidgetsEnabled(boolean enable) {
        Enumeration buttons = renderWidgets.getElements();
        while (buttons.hasMoreElements()) {
            JToggleButton button = (JToggleButton) buttons.nextElement();
            button.setEnabled(enable);
        }
    }
}

