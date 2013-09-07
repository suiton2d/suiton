package com.nebula2d.editor.framework.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.assets.Script;
import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 8/2/13
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Behaviour extends Component {

    protected Script script;

    public Behaviour(String name) {
        super(name);
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    @Override
    public void render(GameObject selectedObject, SpriteBatch batcher) {
        //Noop!
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
}
