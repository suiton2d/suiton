package com.suiton2d.editor.io.savers;

import com.suiton2d.components.physics.BoundingBox;
import com.suiton2d.components.physics.Circle;
import com.suiton2d.components.physics.Collider;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Saver;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class ColliderSaver implements Saver {

    private Collider collider;
    
    public ColliderSaver(Collider collider) {
        this.collider = collider;
    }

    @Override
    public void save(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.ComponentType.COLLIDER.name());
        fw.writeLine(collider.getName());
        fw.writeBoolLine(collider.isSensor());
        fw.writeFloatLine(collider.getCollisionShape().getPhysicsMaterial().getDensity());
        fw.writeFloatLine(collider.getCollisionShape().getPhysicsMaterial().getFriction());
        fw.writeFloatLine(collider.getCollisionShape().getPhysicsMaterial().getRestitution());
        if (collider.getCollisionShape() instanceof BoundingBox) {
            fw.writeLine(Types.ShapeType.BOX.name());
            fw.writeFloatLine(((BoundingBox) collider.getCollisionShape()).getExtents().halfw);
            fw.writeFloatLine(((BoundingBox) collider.getCollisionShape()).getExtents().halfh);
        } else if (collider.getCollisionShape() instanceof Circle) {
            fw.writeLine(Types.ShapeType.CIRCLE.name());
            fw.writeFloatLine(((Circle) collider.getCollisionShape()).getRadius());
        }
    }
}
