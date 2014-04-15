package com.nebula2d.components;

import com.nebula2d.assets.AssetManager;
import com.nebula2d.assets.Script;
import com.sun.org.omg.SendingContext._CodeBaseStub;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Behavior is a {@link com.nebula2d.components.Component} for executing
 * {@link com.nebula2d.assets.Script} assets, providing custom functionality to
 * {@link com.nebula2d.scene.GameObject}s via javascript.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class Behavior extends Component {

    private Script script;
    private Scriptable scope;
    private Function startFunction;
    private Function updateFunction;

    public Behavior(String name, String scriptPath) {
        super(name);
        script = new Script(scriptPath);
        initScript();
    }

    private void initScript() {
        Context context = Context.enter();
        try {
            ScriptableObject globalScope = AssetManager.getInstance().getGlobalScriptScope();
            scope = context.newObject(globalScope);
            scope.setPrototype(globalScope);
            scope.setParentScope(null);
            context.evaluateString(scope, script.getContents(), script.getFilename(), 1, null);
            startFunction = (Function) scope.get("start", scope);
            updateFunction = (Function) scope.get("update", scope);
        } finally {
            Context.exit();
        }
    }

    @Override
    public void start() {
        Context context = Context.enter();
        try {
            startFunction.call(context, scope, scope, new Object[]{gameObject});
        } finally {
            Context.exit();
        }
    }

    @Override
    public void update(float dt) {
        Context context = Context.enter();
        try {
            updateFunction.call(context, scope, scope, new Object[]{gameObject, dt});
        } finally {
            Context.exit();
        }
    }
}
