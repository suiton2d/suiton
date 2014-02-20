package com.nebula2d.components;

import com.nebula2d.assets.Asset;
import com.nebula2d.assets.Script;
import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.Function;
import sun.org.mozilla.javascript.internal.Scriptable;
import sun.org.mozilla.javascript.internal.ScriptableObject;

import java.util.Objects;

/**
 * Behavior is a {@link com.nebula2d.components.Component} for executing
 * {@link com.nebula2d.assets.Script} assets, providing custom functionality to
 * {@link com.nebula2d.scene.GameObject}s via javascript.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class Behavior extends Component {

    private Script script;
    private Context context;
    private Scriptable scope;

    public Behavior(String name, String scriptPath) {
        super(name);
        script = new Script(scriptPath);
        initScript();
    }

    private void initScript() {
        context = Context.enter();
        scope = context.initStandardObjects();
        context.evaluateString(scope, script.getContents(), script.getFilename(), 1, null);
    }

    @Override
    public void start() {
        Object f = scope.get("start", scope);
        if (f instanceof Function) {
            Object[] args = new Object[] { gameObject };
            ((Function) f).call(context, scope, scope, args);
        }
    }

    @Override
    public void update(float dt) {
        Object f = scope.get("update", scope);
        if (f instanceof Function) {
            Object[] args = new Object[] { gameObject, dt };
            ((Function) f).call(context, scope, scope, args);
        }
    }
}
