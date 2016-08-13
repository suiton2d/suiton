package com.suiton2d.editor.io.savers;

import com.suiton2d.components.physics.BoundingBox;
import com.suiton2d.components.physics.Circle;
import com.suiton2d.components.physics.RigidBody;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class RigidBodySaver implements Saver {

    private RigidBody rigidBody;

    public RigidBodySaver(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.ComponentType.RIGID_BODY.name());
        fw.writeLine(rigidBody.getName());
        fw.writeBoolLine(rigidBody.isKinematic());
        fw.writeBoolLine(rigidBody.isFixedRotation());
        fw.writeBoolLine(rigidBody.isBullet());
        fw.writeFloatLine(rigidBody.getMass());
        fw.writeFloatLine(rigidBody.getCollisionShape().getPhysicsMaterial().getDensity());
        fw.writeFloatLine(rigidBody.getCollisionShape().getPhysicsMaterial().getFriction());
        fw.writeFloatLine(rigidBody.getCollisionShape().getPhysicsMaterial().getRestitution());
        if (rigidBody.getCollisionShape() instanceof BoundingBox) {
            fw.writeLine(Types.ShapeType.BOX.name());
            fw.writeFloatLine(((BoundingBox) rigidBody.getCollisionShape()).getExtents().halfw);
            fw.writeFloatLine(((BoundingBox) rigidBody.getCollisionShape()).getExtents().halfh);
        } else if (rigidBody.getCollisionShape() instanceof Circle) {
            fw.writeLine(Types.ShapeType.CIRCLE.name());
            fw.writeFloatLine(((Circle) rigidBody.getCollisionShape()).getRadius());
        }
    }
}
