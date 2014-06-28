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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;
import com.nebula2d.editor.util.FloatValidatedDocumentListener;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
import java.io.IOException;

/**
 *
 * Created by bonazza on 5/3/14.
 */
public class Circle extends CollisionShape {

    private float r;

    public Circle(GameObject gameObject) {
        super(gameObject);
        shapeType = ShapeType.CIRCLE;
    }

    public float getRadius() {
        return r;
    }

    public void setRadius(float r) {
        this.r = r;
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        if (gameObject != null && r % 360 != 0) {
            batcher.end();
            ShapeRenderer shape = new ShapeRenderer();
            shape.setProjectionMatrix(cam.combined);
            shape.setColor(Color.RED);
            shape.begin(ShapeRenderer.ShapeType.Line);

            float x = gameObject.getPosition().x;
            float y = gameObject.getPosition().y;

            shape.circle(x, y, r);

            shape.end();
            batcher.begin();
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public int getBoundingWidth() {
        return (int) r;
    }

    @Override
    public int getBoundingHeight() {
        return (int) r;
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        r = fr.readFloatLine();
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeFloatLine(r);
    }

    @Override
    public JPanel createEditorPanel() {
        final N2DLabel radiusLbl = new N2DLabel("Radius: ");
        final JTextField radiusTf = new JTextField(Float.toString(r), 20);
        final N2DLabel densityLbl = new N2DLabel("Density: ");
        final JTextField densityTf = new JTextField(Float.toString(material.getDensity()), 20);
        final N2DLabel frictionLbl = new N2DLabel("Friction: ");
        final JTextField frictionTf = new JTextField(Float.toString(material.getFriction()), 20);
        final N2DLabel restitutionLbl = new N2DLabel("Restitution: ");
        final JTextField restitutionTf = new JTextField(Float.toString(material.getRestitution()), 20);

        radiusTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(radiusTf,
                input -> {
                    r = input;
                    return null;
                }));

        densityTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(densityTf,
                input -> {
                    material.setDensity(input);
                    return null;
                }));

        frictionTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(frictionTf,
                input -> {
                    material.setFriction(input);
                    return null;
                }));

        restitutionTf.getDocument().addDocumentListener(new FloatValidatedDocumentListener(restitutionTf,
                input -> {
                    material.setRestitution(input);
                    return null;
                }));

        N2DPanel panel = new N2DPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        hGroup.addGroup(layout.createSequentialGroup().
                addComponent(radiusLbl).
                addComponent(radiusTf, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
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
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(radiusLbl).
                addComponent(radiusTf));
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
