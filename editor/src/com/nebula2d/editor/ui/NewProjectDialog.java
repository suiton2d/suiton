package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 9/6/13
 * Time: 11:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewProjectDialog extends JDialog {

    private MainFrame parent;

    private JTextField projNameTf;
    private JTextField parentDirTf;
    private JButton browseBtn;
    private JButton createBtn;
    private JButton cancelBtn;

    public NewProjectDialog(MainFrame parent) {
        this.parent = parent;
        setTitle("New Project");

        JLabel projNameLbl = new JLabel("Project Name:");
        JLabel parentDirLbl = new JLabel("Parent Directory:");

        projNameTf = new JTextField();
        projNameTf.setMinimumSize(new Dimension(200, 5));
        parentDirTf = new JTextField();
        parentDirTf.setMinimumSize(new Dimension(200, 5));

        browseBtn = new JButton("...");
        createBtn = new JButton("Create");
        cancelBtn = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);


        JPanel mainPanel = new JPanel();
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().addComponent(projNameLbl).addComponent(parentDirLbl));
        hGroup.addGroup(layout.createParallelGroup().addComponent(projNameTf).addComponent(parentDirTf));
        hGroup.addGroup(layout.createParallelGroup().addComponent(browseBtn));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(projNameLbl).addComponent(projNameTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).
                addComponent(parentDirLbl).addComponent(parentDirTf).addComponent(browseBtn));
        layout.setVerticalGroup(vGroup);

        add(mainPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setModal(true);
        bindButtons();
        //setSize(new Dimension(400, 300));
        setVisible(true);
    }

    private void bindButtons() {
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!validateText()) {
                    JOptionPane.showMessageDialog(NewProjectDialog.this,
                            "You must provide a valid project name and parent directory.");

                    return;
                }

                MainFrame.setProject(new Project(parentDirTf.getText().trim(), projNameTf.getText().trim()));
                dispose();
            }
        });

        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Select a Directory");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);

                if (fc.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    parentDirTf.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    private boolean validateText() {
        if (projNameTf.getText().trim().equals("") || parentDirTf.getText().trim().equals("")) {
            return false;
        }

        File parentDir = new File(parentDirTf.getText().trim());

        if (!parentDir.exists() || !parentDir.isDirectory()) {
            return false;
        }

        return true;
    }
}
