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

package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.google.common.base.Function;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FloatValidatedDocumentListener;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;
import com.nebula2d.editor.util.StringUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.IOException;

/**
 *
 * Created by bonazza on 5/3/14.
 */
public class BoundingBox extends CollisionShape {

    private float w;
    private float h;

    public BoundingBox(GameObject gameObject) {
        super(gameObject);
        shapeType = ShapeType.BOX;
    }

    public float getWidth() {
        return w;
    }

    public void setWidth(float w) {
        this.w = w;
    }

    public float getHeight() {
        return h;
    }

    public void setHeight(float h) {
        this.h = h;
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        if (gameObject != null && w > 0 && h > 0) {
            batcher.end();

            ShapeRenderer shape = new ShapeRenderer();
            shape.setColor(Color.RED);
            shape.begin(ShapeRenderer.ShapeType.Line);
            float halfw = w / 2.0f;
            float halfh = h / 2.0f;

            float x = gameObject.getPosition().x - halfw - cam.position.x;
            float y = gameObject.getPosition().y - halfh - cam.position.y;

            shape.rect(x, y, w, h);
            shape.end();
            batcher.begin();
        }
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        w = fr.readFloatLine();
        h = fr.readFloatLine();
        material.load(fr);
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeFloatLine(w);
        fw.writeFloatLine(h);
        material.save(fw);
    }

    @Override
    public JPanel createEditorPanel() {
        final N2DLabel widthLbl = new N2DLabel("Width: ");
        final JTextField widthTf = new JTextField(Float.toString(w), 20);
        final N2DLabel heightLbl = new N2DLabel("Height: ");
        final JTextField heightTf = new JTextField(Float.toString(h), 20);
        final N2DLabel densityLbl = new N2DLabel("Density: ");
        final JTextField densityTf = new JTextField(Float.toString(material.getDensity()), 20);
        final N2DLabel frictionLbl = new N2DLabel("Friction: ");
        final JTextField frictionTf = new JTextField(Float.toString(material.getFriction()), 20);
        final N2DLabel restitutionLbl = new N2DLabel("Restitution: ");
        final JTextField restitutionTf = new JTextField(Float.toString(material.getRestitution()), 20);

        widthTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(widthTf,
                new Function<Float, Void>() {
                    @Override
                    public Void apply(Float input) {
                        w = input;
                        return null;
                    }
                }));

        heightTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(heightTf,
                new Function<Float, Void>() {
                    @Override
                    public Void apply(Float input) {
                        h = input;
                        return null;
                    }
                }));

        densityTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(densityTf,
                new Function<Float, Void>() {
                    @Override
                    public Void apply(Float input) {
                        material.setDensity(input);
                        return null;
                    }
                }));

        frictionTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(frictionTf,
                new Function<Float, Void>() {
                    @Override
                    public Void apply(Float input) {
                        material.setFriction(input);
                        return null;
                    }
                }));

        restitutionTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(restitutionTf,
                new Function<Float, Void>() {
                    @Override
                    public Void apply(Float input) {
                        material.setRestitution(input);
                        return null;
                    }
                }));

        N2DPanel panel = new N2DPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        hGroup.addGroup(layout.createSequentialGroup().
                addComponent(widthLbl).
                addComponent(widthTf, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        hGroup.addGroup(layout.createSequentialGroup().
                addComponent(heightLbl).
                addComponent(heightTf, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        hGroup.addGroup(layout.createSequentialGroup().
                addComponent(densityLbl).
                addComponent(densityTf, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        hGroup.addGroup(layout.createSequentialGroup().
                addComponent(frictionLbl).
                addComponent(frictionTf, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        hGroup.addGroup(layout.createSequentialGroup().
                addComponent(restitutionLbl).
                addComponent(restitutionTf, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(widthLbl).
                addComponent(widthTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(heightLbl).
                addComponent(heightTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(densityLbl).
                addComponent(densityTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(frictionLbl).
                addComponent(frictionTf));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(restitutionLbl).
                addComponent(restitutionTf));
        layout.setVerticalGroup(vGroup);

        return panel;
    }
}
