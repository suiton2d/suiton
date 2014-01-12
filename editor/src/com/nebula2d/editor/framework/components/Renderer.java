package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.ui.ImagePanel;
import com.nebula2d.editor.ui.NewAnimationPopup;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Renderer extends Component {

    //region members
    protected Texture texture;

    protected List<Animation> animations;

    protected int currentAnim;
    //endregion

    //region constructor
    public Renderer(String name) {
        super(name);
        animations = new ArrayList<Animation>();
        currentAnim = -1;
    }
    //endregion

    //region Accessors
    public List<Animation> getAnimations() {
        return animations;
    }

    public void addAnimation(Animation anim) {
        animations.add(anim);
    }

    public void removeAnimation(Animation anim) {
        animations.remove(anim);
    }

    public void removeAnimation(int idx) {
        animations.remove(idx);
    }

    public Animation getAnimation(String name) {
        for (Animation anim : animations) {
            if (anim.getName().equals(name)) {
                return anim;
            }
        }

        return null;
    }

    public Animation getAnimation(int idx) {
        return animations.get(idx);
    }

    public Animation getCurrentAnimation() {
        return currentAnim != -1 ? animations.get(currentAnim) : null;
    }

    public void setCurrentAnim(Animation anim) {
        int idx = 0;
        for (Animation a : animations) {
            if (a == anim) {
                currentAnim = idx;
                return;
            }

            idx++;
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public int getBoundingWidth() {
        return texture.getWidth();
    }

    public int getBoundingHeight() {
        return texture.getHeight();
    }

    public Rectangle getBoundingBox() {
        if (texture == null)
            return null;

        float x = parent.getPosition().x  -  (getBoundingWidth() / 2.0f);
        float y = parent.getPosition().y - (getBoundingHeight() / 2.0f);

        return new Rectangle(x, y, getBoundingWidth(), getBoundingHeight());
    }

    public void setTexture(Texture tex) {
        this.texture = tex;
    }
    //endregion

    //region overrided methods from Component
    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        super.save(fw);

        if (texture == null) {
            fw.writeLine("0");
        } else {
            fw.writeLine("1");
            fw.writeLine(texture.getPath());
        }

        fw.writeIntLine(currentAnim);
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        super.load(fr);

        int tmp = fr.readIntLine();

        if (tmp == 1) {
            texture = new Texture(fr.readLine());
        }

        currentAnim = fr.readIntLine();
    }
    //endregion

    @Override
    public JPanel forgeComponentContentPanel(final ComponentsDialog parent) {

        final ImagePanel imagePanel = new ImagePanel();

        final JButton addButton = new JButton("Add");
        final JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
        final JTextField imageTf = new JTextField(20);

        if (texture != null) {
            imageTf.setText(texture.getPath());
            imagePanel.setImage(getTexture().getPath());
        }
        final JButton browseBtn = new JButton("...");
        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Select a file.");

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final String path = fc.getSelectedFile().getAbsolutePath();
                    imageTf.setText(path);
                    imagePanel.setImage(path);
                    addButton.setEnabled(true);
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            Renderer.this.texture = new Texture(path);
                        }
                    });
                    imagePanel.getParent().revalidate();
                }
            }
        });

        final DefaultListModel<Animation> listModel = new DefaultListModel<Animation>();
        for (Animation anim : getAnimations()) {
            listModel.addElement(anim);
        }
        final JList<Animation> animationList = new JList<Animation>();
        animationList.setModel(listModel);
        final JScrollPane sp = new JScrollPane(animationList);
        sp.setPreferredSize(new Dimension(200, 300));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewAnimationPopup(Renderer.this, listModel, imageTf.getText()).
                        show(addButton, -1, addButton.getHeight());

            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Animation anim = animationList.getSelectedValue();
                removeAnimation(anim);
                listModel.removeElement(anim);
            }
        });

        animationList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Animation anim = animationList.getSelectedValue();

                removeButton.setEnabled(anim != null);
            }
        });
        final JPanel addRemoveBtnPanel = new JPanel();
        addRemoveBtnPanel.add(addButton);
        addRemoveBtnPanel.add(removeButton);

        final JPanel animationPanel = new JPanel(new BorderLayout());
        animationPanel.add(addRemoveBtnPanel, BorderLayout.NORTH);
        animationPanel.add(sp);

        final JLabel imageLbl = new JLabel("Texture:");
        final JLabel nameLbl = new JLabel("Name:");
        final JTextField nameTf = new JTextField(20);
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
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        nameTf.setText(name);
        final JCheckBox enabledCb = new JCheckBox("Enabled");
        enabledCb.setSelected(enabled);
        enabledCb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                enabled = enabledCb.isSelected();
            }
        });
        JPanel leftPanel = new JPanel();
        GroupLayout layout = new GroupLayout(leftPanel);
        leftPanel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameLbl).
                addComponent(imageLbl).addComponent(enabledCb));
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameTf).addComponent(imageTf));//.
                //addComponent(imagePanel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(browseBtn));
        //hGroup.addGroup(layout.createParallelGroup().addComponent(animationPanel));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLbl).
                addComponent(nameTf));//.addComponent(animationPanel));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(imageLbl).
                addComponent(imageTf).addComponent(browseBtn));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enabledCb));
        //vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(imagePanel).
        //        addComponent(animationPanel));
        layout.setVerticalGroup(vGroup);
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        bottomPanel.add(animationPanel, BorderLayout.EAST);
        bottomPanel.add(imagePanel);

        final JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(leftPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel);
        return mainPanel;
    }
}
