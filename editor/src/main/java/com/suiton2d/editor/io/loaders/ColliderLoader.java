package com.suiton2d.editor.io.loaders;

import com.nebula2d.components.*;
import com.suiton2d.components.*;
import com.suiton2d.editor.io.FullBufferedReader;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class ColliderLoader extends BaseComponentLoader {

    @SuppressWarnings("unchecked")
    @Override
    public Component onLoad(FullBufferedReader fr) throws IOException {
        Collider collider = new Collider();
        collider.setIsSensor(fr.readBooleanLine());
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
        collider.setCollisionShape(collisionShape);

        return collider;
    }
}
