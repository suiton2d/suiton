package com.suiton2d.editor.settings;

import javax.swing.*;
import java.io.IOException;

/**
 *
 * Created by bonazza on 8/27/14.
 */
public interface ISettings {

    JPanel createSettingsPanel(JDialog parent);

    void loadFromProperties() throws IOException;
}
