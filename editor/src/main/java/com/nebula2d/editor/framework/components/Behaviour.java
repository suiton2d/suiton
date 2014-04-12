/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.Gdx;
import com.nebula2d.editor.framework.assets.MusicTrack;
import com.nebula2d.editor.framework.assets.Script;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Behaviour extends Component {

    //region members
    protected Script script;
    //endregion

    //region constructor
    public Behaviour(String name) {
        super(name);
    }
    //endregion

    //region accessors
    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }
    //endregion

    //region overrided methods from Component
    @Override
    public JPanel forgeComponentContentPanel(final ComponentsDialog parent) {
        final JLabel nameLbl = new JLabel("Name:");
        final JLabel fileLbl = new JLabel("File:");
        final JLabel filePathLbl = new JLabel("");
        final JTextField nameTf = new JTextField(name, 20);
        final JCheckBox enabledCb = new JCheckBox("Enabled", enabled);
        final JButton browseBtn = new JButton("...");

        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Select a file.");

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final String path = fc.getSelectedFile().getAbsolutePath();
                    filePathLbl.setText(path);
                }
            }
        });
        return null;
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        super.load(fr);
        int tmp = fr.readIntLine();
        if (tmp != 0) {
            String path = fr.readLine();
            script = new Script(path);
        }
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        if (script == null) {
            fw.writeLine("0");
        } else {
            fw.writeLine("1");
            fw.writeLine(script.getPath());
        }
    }
    //endregion
}
