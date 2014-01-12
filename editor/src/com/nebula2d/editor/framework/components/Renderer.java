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
}
