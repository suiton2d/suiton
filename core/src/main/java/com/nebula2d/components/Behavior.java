package com.nebula2d.components;

import com.nebula2d.assets.Asset;
import com.nebula2d.assets.Script;

/**
 * Behavior is a {@link com.nebula2d.components.Component} for executing
 * {@link com.nebula2d.assets.Script} assets, providing custom functionality to
 * {@link com.nebula2d.scene.GameObject}s via javascript.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class Behavior extends Component {

    private Script script;

    public Behavior(String name, String scriptPath) {
        super(name);
        script = new Script(scriptPath);
        initScript();
    }

    private void initScript() {

    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }
}
