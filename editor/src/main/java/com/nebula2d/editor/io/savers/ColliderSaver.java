package com.nebula2d.editor.io.savers;

import com.nebula2d.components.BoundingBox;
import com.nebula2d.components.Circle;
import com.nebula2d.components.Collider;
import com.nebula2d.components.RigidBody;
import com.nebula2d.editor.io.FullBufferedWriter;
import com.nebula2d.editor.io.Types;

import java.io.IOException;

public class ColliderSaver extends BaseComponentSaver<Collider> {

    public ColliderSaver(Collider component) {
        super(component);
    }

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeBoolLine(getComponent().isSensor());
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
