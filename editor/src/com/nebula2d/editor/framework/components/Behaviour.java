package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Script;
import com.nebula2d.editor.ui.ComponentsDialog;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import javax.swing.*;
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
    public void render(GameObject selectedObject, SpriteBatch batcher, Camera cam) {
        //Noop!
    }

    @Override
    public JPanel forgeComponentContentPanel(final ComponentsDialog parent) {
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
