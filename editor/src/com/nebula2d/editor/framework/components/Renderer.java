package com.nebula2d.editor.framework.components;

import com.nebula2d.editor.common.ILoadable;
import com.nebula2d.editor.common.ISaveable;
import com.nebula2d.editor.framework.assets.Texture;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Renderer extends Component {

    protected Texture texture;

    protected List<Animation> animations;

    protected int currentAnim;

    public Renderer(String name) {
        super(name);
        animations = new ArrayList<Animation>();
        currentAnim = -1;
    }


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
    //endregion

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
}
