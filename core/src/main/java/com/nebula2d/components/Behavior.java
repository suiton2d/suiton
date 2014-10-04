package com.nebula2d.components;

import com.nebula2d.assets.AssetManager;
import com.nebula2d.assets.Script;
import com.nebula2d.scene.GameObject;
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
    private Function finishFunction;
    private Function beginCollisionFunction;
    private Function endCollisionFunction;

    public Behavior(String name, Script script) {
        super(name);
        this.script = script;
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
            finishFunction = (Function) scope.get("finish", scope);
            beginCollisionFunction = (Function) scope.get("beginCollision", scope);
            endCollisionFunction = (Function) scope.get("endCollision", scope);
        } finally {
            Context.exit();
        }
    }

    @Override
    public void start() {
        initScript();
        Context context = Context.enter();
        try {
            if (startFunction != Scriptable.NOT_FOUND) {
                startFunction.call(context, scope, scope, new Object[]{gameObject});
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void update(float dt) {
        Context context = Context.enter();
        try {
            if (updateFunction != Scriptable.NOT_FOUND) {
                updateFunction.call(context, scope, scope, new Object[]{gameObject, dt});
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void finish() {
        scope = null;
        startFunction = null;
        updateFunction = null;
        finishFunction = null;
        beginCollisionFunction = null;
        endCollisionFunction = null;
        Context context = Context.enter();
        try {
            if (finishFunction != Scriptable.NOT_FOUND) {
                finishFunction.call(context, scope, scope, null);
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {
        Context context = Context.enter();
        try {
            if (beginCollisionFunction != Scriptable.NOT_FOUND) {
                beginCollisionFunction.call(context, scope, scope, new Object[]{go1, go2});
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {
        Context context = Context.enter();
        try {
            if (endCollisionFunction != Scriptable.NOT_FOUND) {
                endCollisionFunction.call(context, scope, scope, new Object[]{go1, go2});
            }
        } finally {
            Context.exit();
        }
    }
}
