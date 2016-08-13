package com.suiton2d.editor.io.loaders;

import com.badlogic.gdx.physics.box2d.World;
import com.suiton2d.components.physics.*;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Loader;
import com.suiton2d.editor.io.Types;
import com.suiton2d.scene.Scene;

import java.io.IOException;

public class RigidBodyLoader implements Loader<RigidBody<? extends CollisionShape>> {

    private Scene scene;
    private World world;

    public RigidBodyLoader(Scene scene, World world) {
        this.scene = scene;
        this.world = world;
    }

    @SuppressWarnings("unchecked")
    @Override
    public RigidBody<? extends CollisionShape> load(FullBufferedReader fr) throws IOException {
        String name = fr.readLine();
        boolean isKinematic = fr.readBooleanLine();
        boolean isFixedRotation = fr.readBooleanLine();
        boolean isBullet = fr.readBooleanLine();
        float mass = fr.readFloatLine();

        float density = fr.readFloatLine();
        float friction = fr.readFloatLine();
        float restitiution = fr.readFloatLine();
        PhysicsMaterial physicsMaterial = new PhysicsMaterial(density, friction, restitiution);
        Types.ShapeType shapeType = Types.ShapeType.valueOf(fr.readLine());
        CollisionShape collisionShape = null;
        if (shapeType == Types.ShapeType.BOX) {
            float w = fr.readFloatLine();
            float h = fr.readFloatLine();
            collisionShape = new BoundingBox(physicsMaterial, w, h);
        } else if (shapeType == Types.ShapeType.CIRCLE) {
            float r = fr.readFloatLine();
            collisionShape = new Circle(physicsMaterial, r);
        }

        return new RigidBody(name, collisionShape, isKinematic, mass, isFixedRotation, isBullet, world);
    }
}
