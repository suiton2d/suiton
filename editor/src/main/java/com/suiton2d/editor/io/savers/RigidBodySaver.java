package com.suiton2d.editor.io.savers;

import com.suiton2d.components.BoundingBox;
import com.suiton2d.components.Circle;
import com.suiton2d.components.RigidBody;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class RigidBodySaver extends BaseComponentSaver<RigidBody> {


    public RigidBodySaver(RigidBody component) {
        super(component);
    }

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeBoolLine(getComponent().isKinematic());
        fw.writeBoolLine(getComponent().isFixedRotation());
        fw.writeBoolLine(getComponent().isBullet());
        fw.writeFloatLine(getComponent().getMass());
        fw.writeFloatLine(getComponent().getCollisionShape().getPhysicsMaterial().getDensity());
        fw.writeFloatLine(getComponent().getCollisionShape().getPhysicsMaterial().getFriction());
        fw.writeFloatLine(getComponent().getCollisionShape().getPhysicsMaterial().getRestitution());
        if (getComponent().getCollisionShape() instanceof BoundingBox) {
            fw.writeLine(Types.ShapeType.BOX.name());
            fw.writeFloatLine(((BoundingBox) getComponent().getCollisionShape()).getExtents().halfw);
            fw.writeFloatLine(((BoundingBox) getComponent().getCollisionShape()).getExtents().halfh);
        } else if (getComponent().getCollisionShape() instanceof Circle) {
            fw.writeLine(Types.ShapeType.CIRCLE.name());
            fw.writeFloatLine(((Circle) getComponent().getCollisionShape()).getRadius());
        }
    }
}
