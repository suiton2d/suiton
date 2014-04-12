/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
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

package com.nebula2d.avocado;

import com.google.common.base.Function;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class ScriptEditor extends JDialog {

    private RSyntaxTextArea rSyntaxTextArea;
    private Function<Void, Void> saveCallback;

    public ScriptEditor(String name, String content, Function<Void, Void> saveCallback) {
        this(name, new RSyntaxTextArea(content), saveCallback);
    }

    private ScriptEditor(String name, RSyntaxTextArea rSyntaxTextArea,
                         Function<Void, Void> saveCallback) {
        this.rSyntaxTextArea = rSyntaxTextArea;
        this.saveCallback = saveCallback;
        init(name);
    }

    private void init(String name) {
        setTitle(name);
        rSyntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        rSyntaxTextArea.setCodeFoldingEnabled(true);
        rSyntaxTextArea.setAntiAliasingEnabled(true);
        rSyntaxTextArea.setAnimateBracketMatching(true);
        rSyntaxTextArea.setBracketMatchingEnabled(true);
        rSyntaxTextArea.setAutoIndentEnabled(true);
        rSyntaxTextArea.setCloseCurlyBraces(true);
        rSyntaxTextArea.setMarkOccurrences(true);

        RTextScrollPane sp = new RTextScrollPane(rSyntaxTextArea);
        add(sp);

        CompletionProvider provider = new AvocadoCompletionProvider();
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(rSyntaxTextArea);

        setupMenuBar();
        pack();
        setLocationRelativeTo(null);
    }

    private void setupMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = fileMenu.add("Save");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("saved!");
                saveCallback.apply(null);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
}
