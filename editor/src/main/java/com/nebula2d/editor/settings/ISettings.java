package com.nebula2d.editor.settings;

import javax.swing.*;
import java.io.IOException;

/**
 *
 * Created by bonazza on 8/27/14.
 */
public interface ISettings {

    public JPanel createSettingsPanel(JDialog parent);

    public void loadFromProperties() throws IOException;
}
