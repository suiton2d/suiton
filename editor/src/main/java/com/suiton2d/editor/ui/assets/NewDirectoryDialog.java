package com.suiton2d.editor.ui.assets;

import com.suiton2d.editor.framework.FileNode;
import com.suiton2d.editor.ui.controls.SuitonDialog;
import com.suiton2d.editor.ui.controls.SuitonPanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

public class NewDirectoryDialog extends SuitonDialog {

    private FileNode parent;
    private JTextField nameTf;

    public NewDirectoryDialog(FileNode parent) {
        super("New Directory");
        this.parent = parent;
        setupContents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupContents() {
        nameTf = new JTextField(20);

        final Color defaultFg = nameTf.getForeground();
        final JLabel nameLbl = new JLabel("Directory Name: ");
        final JButton okBtn = new JButton("Ok");
        final JButton cancelBtn = new JButton("Cancel");

        final SuitonPanel namePanel = new SuitonPanel();
        final SuitonPanel btnPanel = new SuitonPanel();

        nameTf.setText(String.format("New Directory (%d)", parent.getFile().list(File::isDirectory).length+1));

        namePanel.add(nameLbl);
        namePanel.add(nameTf);

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        nameTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!validateText()) {
                    nameTf.setForeground(Color.RED);
                    okBtn.setEnabled(false);
                } else {
                    nameTf.setForeground(defaultFg);
                    okBtn.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!validateText()) {
                    nameTf.setForeground(Color.RED);
                    okBtn.setEnabled(false);
                } else {
                    nameTf.setForeground(defaultFg);
                    okBtn.setEnabled(true);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        okBtn.addActionListener(e -> {
            String newDirName = nameTf.getText();
            parent.addChildDirectory(newDirName);
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        add(btnPanel, BorderLayout.SOUTH);
        add(namePanel, BorderLayout.CENTER);
        pack();
    }

    private boolean validateText() {
        String txt = nameTf.getText();
        return !txt.isEmpty() && !parent.getFile().child(txt).exists();
    }
}
