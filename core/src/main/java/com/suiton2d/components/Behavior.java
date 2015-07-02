package com.suiton2d.components;

import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.Script;
import com.suiton2d.scene.GameObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Behavior is a {@link Component} for executing
 * {@link Script} assets, providing custom functionality to
 * {@link GameObject}s via javascript.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class Behavior extends Component {

    private Scriptable scope;
    private Function startFunction;
    private Function updateFunction;
    private Function finishFunction;
    private Function beginCollisionFunction;
    private Function endCollisionFunction;

    private String filename;

    public Behavior() {}

    public Behavior(String name, String filename) {
        super(name);

        this.filename = filename;
    }

    public void init() {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            ScriptableObject globalScope = AssetManager.getGlobalScriptScope();
            scope = context.newObject(globalScope);
            scope.setPrototype(globalScope);
            scope.setParentScope(null);
            context.evaluateString(scope, getScript().getData(), getScript().getFilename(), 1, null);
            startFunction = (Function) scope.get("start", scope);
            updateFunction = (Function) scope.get("update", scope);
            finishFunction = (Function) scope.get("finish", scope);
            beginCollisionFunction = (Function) scope.get("beginCollision", scope);
            endCollisionFunction = (Function) scope.get("endCollision", scope);
        } finally {
            Context.exit();
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Script getScript() {
        return AssetManager.getAsset(filename, Script.class);
    }

    @Override
    public void start() {
        init();
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
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
        context.setOptimizationLevel(-1);
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
        context.setOptimizationLevel(-1);
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
        context.setOptimizationLevel(-1);
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
        context.setOptimizationLevel(-1);
        try {
            if (endCollisionFunction != Scriptable.NOT_FOUND) {
                endCollisionFunction.call(context, scope, scope, new Object[]{go1, go2});
            }
        } finally {
            Context.exit();
        }
    }
}
